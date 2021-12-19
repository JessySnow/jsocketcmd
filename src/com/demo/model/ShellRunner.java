package com.demo.model;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * 通过JavaRuntime新建一个Windows CMD进程，执行命令，并读取命令的返回流通过socket返回到telnet客户端上
 * 所有IO错误都护在成员函数内处理
 */
public class ShellRunner{
    
    private String CMD;
    private Runtime WindowsEV;
    private static final String EXE = "cmd /c ";

    public ShellRunner(String CMD){
        this.CMD = CMD;
        WindowsEV = Runtime.getRuntime();
    }

    // 调用构造函数传入的命令，在完成构造后可以先调用一个默认命令
    public void callCMD(){
        if(!CMD.equals("NULL")){
            try{
                WindowsEV.exec(EXE + this.CMD);
            }catch(Exception ignored){;}
        }
    }

    /**
    * 传入一个命令，JRT调用
    * @param cmd 需要调用的命令
     */
    public String runShell(String cmd){
        String Line = "NULRET\n";
        try(BufferedReader sbReader = new BufferedReader(new InputStreamReader(WindowsEV.exec(EXE + cmd).getInputStream(), "GB2312"))){
            StringBuilder sb = new StringBuilder();

            while((Line = sbReader.readLine()) != null)
                sb.append(Line);
            
            sb.append("\n");

            Line = (sb.toString().equals("") || sb.toString().isEmpty()) ? Line : sb.toString();
        }catch(Exception ignored){;}

        return Line;
    }
}
