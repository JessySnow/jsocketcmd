# README.md
## 文件结构
1. classes编译后的类
2. src Java源文件
3. compile.bat 编译命令
4. run_server.bat 控制主机上运行监听
5. run.bat 受控主机上运行监听
   
---

## 类说明
1. com.demo.hc.Entrance -- 受控端程序的入口，负责处理控制端输入的命令和监听端口来新建连接 (受控端)
2. com.demo.model.ShellRunner -- 通过 **JavaRuntime** 新建控制台进程来执行命令 (受控端)
3. com.demo.model.InfoSender -- 向指定IP的指定端口发送当前主机的 **ipconfig** 信息 (受控端)
4. com.demo.model.hostlistener -- 接受来自受控主机的连接请求，并打印受控主机发送的信息 (控制端)