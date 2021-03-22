package com.sugon.chat3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author: byLi
 * @date: 2021/3/15 16:04
 * @description: Chat room interface
 */
public class ClientFrame {

    private TextField textFieldContent = new TextField();
    private TextArea textAreaContent = new TextArea();
    private Socket socket = null;
    private OutputStream out = null;
    private DataOutputStream dos = null;
    private InputStream in = null;
    private DataInputStream dis = null;
    private  BufferedWriter bw = null;
    StringBuffer buffer = null;

    // clinetName
    private String clientName;

    public ClientFrame(String clientName) {
        this.clientName = clientName;
        buffer = new StringBuffer();
    }


    /**
     * Function:Interface initialization
     */
    public void init(){
        // Create form
        JFrame frame = new JFrame(clientName);
        frame.setLayout(null);
        // Set size and location
        frame.setSize(400, 300);
        frame.setLocation(100, 200);

        // Set the send button
        JButton button = new JButton("发送");
        button.setBounds(290, 220, 80, 30);
        frame.add(button);
        button.addActionListener(new BListener());

        textFieldContent.setBounds(10, 220, 260, 30);
        frame.add(textFieldContent);

        textAreaContent.setBounds(10, 10, 360, 200);
        frame.add(textAreaContent);

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        // Make a connection
        this.connect();
        bw = FileUtils.createWriteFlow(clientName);
        new Thread(new ClientThread(dis,textAreaContent,bw)).start();
    }

    /**
     * Function: apply for socket link
     */
    private void connect() {
        try {
            socket = new Socket("localhost", 8888);
            out = socket.getOutputStream();
            dos = new DataOutputStream(out);
            in = socket.getInputStream();
            dis = new DataInputStream(in);
        } catch (UnknownHostException e) {
            System.out.println("申请链接失败");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("申请链接失败");
            e.printStackTrace();
        }
    }


    /**
     * Function: events triggered by "send" button
     */
    private class BListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String message = clientName+":"+textFieldContent.getText().trim();
            if (message != null && !message.equals("")) {
                String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                buffer.append(time).append("\n");
                buffer.append(message).append("\n");
//                textAreaContent.append(time + "\n" + message + "\n");
                textAreaContent.append(buffer.toString());
                textFieldContent.setText("");
                FileUtils.saveMessage(buffer.toString(),bw);
                sendMessageToServer(message);
                buffer.delete(0,buffer.length());
            }
        }
    }

    /**
     * Function: send message to server
     *
     * @param message
     */
    private void sendMessageToServer(String message) {
        try {
            dos.writeUTF(message);
            dos.flush();
        } catch (IOException e) {
            System.out.println("发送消息失败");
            e.printStackTrace();
        }
    }
}