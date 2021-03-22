package com.sugon.chat3;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;

/**
 * @author: byLi
 * @date: 2021/3/15 21:28
 * @description: Server thread processing class
 */
public class ServerThread implements Runnable {
    private Socket socket = null;
    InputStream in = null;
    DataInputStream din = null;
    OutputStream out = null;
    DataOutputStream dos = null;
    boolean flag = true;

    public ServerThread(Socket socket) {
        this.socket = socket;
        try {
            in = socket.getInputStream();
            din = new DataInputStream(in);
        } catch (IOException e) {
            System.out.println("接受消息失败");
            e.printStackTrace();
        }
    }

    /**
     *  Function:The operation performed by the thread in response to the client's request
     */
    public void run() {
        String message;
        try {
            while (flag) {
                message = din.readUTF();
                forwordToAllClients(message);
            }
        } catch (Exception e) {
            flag = false;
            System.out.println("接收消息失败，或客户端已下线。");
            Constans.clients.remove(this);
        }finally {
            if (din != null) {
                try {
                    din.close();
                } catch (IOException e) {
                    System.out.println("din关闭失败");
                    e.printStackTrace();
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    System.out.println("din关闭失败");
                    e.printStackTrace();
                }
            }
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    System.out.println("din关闭失败");
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Function: forward to all clients
     *
     * @param message
     * @throws IOException
     */
    private void forwordToAllClients(String message) throws IOException {
        for (ServerThread c : Constans.clients) {
            if (c != this) {
                out = c.socket.getOutputStream();
                dos = new DataOutputStream(out);
                dos.writeUTF(message);
                dos.flush();
            }
        }
    }

}
