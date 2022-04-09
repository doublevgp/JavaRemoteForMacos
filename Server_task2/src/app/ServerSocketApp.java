package app;

//import jdk.incubator.vector.VectorOperators;

import java.io.*;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.*;

public class ServerSocketApp {
    int port = 8019;
    static int connect_count = 0;// 连接次数统计
    ArrayList<String> msgBackList;
    ArrayList<String> cmdList;
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
            String cmdType = cmdList.get(0);
            boolean isTouchPadCmd = false;
            switch (cmdType) {
                case "clk":
                case "mov":
                case "poi":
                case "rol":
                    isTouchPadCmd = true;
                    break;
            }
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
            if (isTouchPadCmd) {
                Timer timer = new Timer();
                TimerTask timerTask = new TimerTask() {
                    @Override
                    public void run() {
                        try {
                            close(socket);
                            System.out.println("当前Socket服务结束");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                };
                timer.schedule(timerTask, 3000);
            } else {
                close(socket);
                System.out.println("当前Socket服务结束");
            }
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
        int lineNum=Integer.parseInt(lineNumStr);
        for(int i=0;i<lineNum;i++){
            String str = bufferedReader.readLine();
            System.out.println("str === " + str);
            msgList.add(str);
        }
        //读取结束后，输入流不能关闭，此时关闭，会将socket关闭，从而导致后续对socket写操作无法实现
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
            writer.write(msgBackList.get(i)+"\n");
            writer.flush();
        }
    }
    private ArrayList<String> dealCmd(ArrayList<String> cmdList) throws Exception {
        ArrayList<String> backMsg = new ArrayList<>();
        for (String cmd : cmdList) {
            String[] split = cmd.split(":");
            String cmdHead = split[0], cmdBody = split[1];
            System.out.println("cmdHead == " + cmdHead);
            System.out.println("cmdBody == " + cmdBody);
            switch (cmdHead) {
                case "dir":
                    backMsg = exeDir(cmdBody);
                    break;
            }
        }
        return backMsg;
    }

    private ArrayList<String> exeDir(String cmdBody) throws Exception {
        // TODO Auto-generated method stub
        ArrayList<String> backList=new ArrayList<String>();
        File file = new File(cmdBody);
        File[] listFiles = file.listFiles();
        for(File mfile:listFiles){
            String fileName = mfile.getName();
            long lastModified = mfile.lastModified();//获取文件修改时间
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//给时间格式，例如：2018-03-16 09:50:23
            String fileDate = dateFormat.format(new Date(lastModified));//取得文件最后修改时间，并按格式转为字符串
            String fileSize="0";
            String isDir="1";
            if(!mfile.isDirectory()){//判断是否为目录
                isDir="0";
                fileSize=""+mfile.length();
            }
            backList.add(fileName+">"+fileDate+">"+fileSize+">"+isDir+">");
        }
        return backList;
    }

    private void close(Socket socket) throws IOException {
        socket.close();
    }

    private void printLocalIp(ServerSocket serverSocket) {// 枚举打印服务端的IP
        try {
            System.out.println("服务端命令端口prot=" + serverSocket.getLocalPort());
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

//    public void getAndDealCmd(Socket socket) throws Exception {//对比里程碑1代码，修改为抛出异常
//
//        ArrayList<String> cmdList = readSocketMsg(socket);
//        if(cmdList.size()==0){
//            cmdFail("Cmd size is 0. ");//若命令长度0行，则返回错误信息
//        }
//        // 相比里程碑2代码，增加了处理多行命令的功能（需要特殊指令做前缀)
//        if(cmdList.size()==1){
//            msgBackList= VectorOperators.Operator.exeCmd(cmdList.get(0));// Operator类为自定义类，实现命令头部和主体的分离和调用判断
//        }else{
//            //	msgBackList=MultiOperator.exeCmd(cmdList);//待实现的，支持多条命令串行执行
//        }
//
//        if(msgBackList.size()>0){
//            msgBackList.add(0,"ok");//增加正常执行返回的代码"ok"
//        }else{
//            msgBackList.add(0,"invalid cmd:"+cmdList.toString());//msgBackList长度为0，说明命令没有得到解析和执行
//        }
//
//    }

    private void cmdFail(String e) {//将错误信息放入反馈消息
        msgBackList.clear();//
        msgBackList.add(e);//将出错信息放入msgBackList
    }
}
