package com.sugon.chatRoom;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author: byLi
 * @date: 2021/3/17 10:40
 * @description: It is mainly used to open threads and receive server information
 */
public class ClientThread implements Runnable {

    private DataInputStream dis;
    private JPanel jPanel;
    private BufferedWriter bw;
    StringBuffer buffer = null;

    public ClientThread(DataInputStream dis, JPanel jPanel,BufferedWriter bw) {
        this.dis = dis;
        this.jPanel = jPanel;
        this.bw = bw;
        buffer = new StringBuffer();
    }

    @Override
    public void run() {

        try {
            while (true) {
                String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                String str = dis.readUTF();
                buffer.append(time).append("\n").append(str).append("\n");
                JLabel tempLabel = new JLabel(str);
                jPanel.add(tempLabel);
                jPanel.revalidate();
                System.out.println("接收到的消息："+str);
                FileUtils.saveMessage(buffer.toString(),bw);
                buffer.delete(0,buffer.length());
            }
        } catch (EOFException e) {
            System.out.println("客户端已关闭");
        } catch (SocketException e) {
            System.out.println("客户端已关闭");
        } catch (IOException e) {
            System.out.println("接受消息失败");
            e.printStackTrace();
        }
    }
}
