import operator.*;

import java.awt.*;
import java.io.*;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;

import static Utils.FilePathUtil.HierarchicalDir;
import static Utils.FilePathUtil.combineHierDir;

public class Server {
    int port = 8019;
    static int connect_count = 0;// 连接次数统计
    ArrayList<String> msgBackList;
    ArrayList<String> cmdList;
    KEY KeyMan = new KEY();
    public Server() throws AWTException {
        // TODO Auto-generated constructor stub
    }

    public Server(int port) throws AWTException {
        super();
        this.port = port;
    }

    private void printLocalIp(ServerSocket serverSocket) {// 枚举打印服务端的IP
        try {
            System.out.println("服务端命令端口port=" + serverSocket.getLocalPort());
            Enumeration<NetworkInterface> interfaces = null;
            interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface ni = interfaces.nextElement();
                Enumeration<InetAddress> addresss = ni.getInetAddresses();
                while (addresss.hasMoreElements()) {
                    InetAddress nextElement = addresss.nextElement();
                    String hostAddress = nextElement.getHostAddress();
                    System.out.println("本机IP地址为：" + hostAddress);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void work() throws Exception {
        // 注意：由于Socket的工作是阻塞式，Android端Socket的工作必须在新的线程中实现，若在UI主线程中工作会报错

        ServerSocket serverSocket = new ServerSocket(port);
        printLocalIp(serverSocket);
        while (true) {// 无限循环，使之能结束当前socket服务后，准备下一次socket服务

            System.out.println("Waiting client to connect.....");
            Socket socket = serverSocket.accept();// 阻塞式，直到有客户端连接进来，才会继续往下执行，否则一直停留在此代码
            System.out.println("Client connected from: "
                    + socket.getRemoteSocketAddress().toString());

            // eclipse 快捷键
            // alt+/ 代码补全
            // ctr+1 代码修正
            // ctr+2，L 命名给局部变量

            // TODO: As follows:
            cmdList = readSocketMsg(socket);
            msgBackList = dealCmd(cmdList);
            // 实现读客户端发送过来的命令，例如实现private ArrayList<String> readSocketMsg(Socket socket) throws IOException函数
            // 调用 ArrayList<String> cmdList=readSocketMsg(socket);
            // 定义一个全局变量 ArrayList<String>  msgBackList,供服务端处理命令，并将返回结果赋值给msgBackList
            // msgBackList=dealCmd(cmdList);//处理命令，例如dir命令，并将处理结果给msgBackList
            // 实现服务端写回数据函数 private void writebackMsg(Socket socket) throws IOException
            // 将msgBackList按规定的格式写回给客户端
            writeBackMsg(socket);
            // 实现 private void close(Socket socket) throws IOException，关闭socket
            // 调用 close(socket);
            close(socket);
            System.out.println("当前Socket服务结束");
        }
    }

    public ArrayList<String> readSocketMsg(Socket socket) throws IOException {
        // 读socket的输入流，传入的socket参数是已经连接成功未处于关闭的socket
        //首先读取一行，并将读取的字符串内容转换为int型数据，已获得后续需要读取的行数
        ArrayList<String> msgList=new ArrayList<String>();
        InputStream inputStream = socket.getInputStream();
        InputStreamReader reader = new InputStreamReader(inputStream, "utf-8");
        BufferedReader bufferedReader=new BufferedReader(reader);
        String lineNumStr = bufferedReader.readLine();
        try {
            int lineNum=Integer.parseInt(lineNumStr);
            for(int i=0;i<lineNum;i++){
                String str = bufferedReader.readLine();
                System.out.println("str === " + str);
                msgList.add(str);
            }
            //读取结束后，输入流不能关闭，此时关闭，会将socket关闭，从而导致后续对socket写操作无法实现
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        return msgList;
    }

    private void writeBackMsg(Socket socket) throws IOException {
        // TODO Auto-generated method stub
        BufferedOutputStream os = new BufferedOutputStream(socket.getOutputStream());//socket.getOutputStream()是输出流，BufferedOutputStream则将其封装为带缓冲的输出流

        //			OutputStreamWriter writer=new OutputStreamWriter(os);//默认的字符编码，有可能是GB2312也有可能是UTF-8，取决于系统
        //			//建议不要用默认字符编码，而是指定UTF-8，以保证发送接收字符编码一致，不至于出乱码
        //输出流是字节传输的，还不具备字符串直接写入功能，因此再将其封装入OutputStreamWriter，使其支持字符串直接写入
        OutputStreamWriter writer=new OutputStreamWriter(os,"UTF-8");//尝试将字符编码改成"GB2312"
        writer.write(""+msgBackList.size()+"\n");//未真正写入的输出流，仅仅在内存中
        writer.flush();//写入输出流，真正将数据传输出去
        for(int i=0;i<msgBackList.size();i++){
            System.out.println(String.format("msgBacklist %d %s", i, msgBackList.get(i)));
            writer.write(msgBackList.get(i)+"\n");
            writer.flush();
        }
    }
    private ArrayList<String> dealCmd(ArrayList<String> cmdList) throws Exception {
        ArrayList<String> backMsg = new ArrayList<>();
        for (String cmd : cmdList) {
            String[] split = cmd.split(":");
            String cmdHead = split[0];
            String cmdBody = cmd.substring(4);
            System.out.println("cmdHead == " + cmdHead);
            System.out.println("cmdBody == " + cmdBody);
            switch (cmdHead) {
                case "dir":
                    backMsg = new DIR().exe(cmdBody);
                    break;
                case "opn":
                    backMsg = new OPN().exe(cmdBody);
                    break;
                case "key":
                    backMsg = KeyMan.exeKey(cmdBody);
                    break;
                case "clk":
                    backMsg = KeyMan.exeClk(cmdBody);
                    break;
                case "mov":
                    backMsg = KeyMan.exeMov(cmdBody);
                    break;
                case "rol":
                    backMsg = KeyMan.exeRol(cmdBody);
                    break;
                case "poi":
                    backMsg = KeyMan.exePointerInfo();
                    break;
                case "del":
                    backMsg = new DEL().exe(cmdBody);
                    break;
                case "cmd":
                    backMsg = new CMD().exe(cmdBody);
                    break;
                case "cps":
                    backMsg = new CPS().exe(cmdBody);
                    break;
                case "dlf": // 下载文件
                    backMsg = new DLF().exe(cmdBody);
                    break;
            }
        }
        return backMsg;
    }



    private void close(Socket socket) throws IOException {
        socket.close();
    }

    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {
        // TODO Auto-generated method stub
        try {
            new Server().work();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
