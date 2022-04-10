package app;

//import jdk.incubator.vector.VectorOperators;

import socket.CmdServerSocket;

import java.io.*;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.*;

public class ServerSocketApp {
    public static void main(String[] paramArrayOfString) throws Throwable {
        try {
//            while (true) {
                new CmdServerSocket(8019).run();
                System.out.println("The CmdServerSocketThread is finished!");
//                Thread.sleep(100L);
//            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
//        finally {
//        } throw new Throwable();
    } // cmd:www.wzu.edu.cn
}
