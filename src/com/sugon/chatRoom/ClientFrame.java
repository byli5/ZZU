package com.sugon.chatRoom;


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

    private TextField textField = new TextField(25);
    //    private TextArea textAreaContent = new TextArea();
    //-------begin--------
    JPanel jPanel1 = new JPanel();
    JPanel jPanel = new JPanel();
    JScrollPane jScrollPane = new JScrollPane(jPanel1);

    //---end---------------

    private Socket socket = null;
    private OutputStream out = null;
    private DataOutputStream dos = null;
    private InputStream in = null;
    private DataInputStream dis = null;
    private BufferedWriter bw = null;
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
    public void init() {
        // Create form
        JFrame frame = new JFrame(clientName);
        frame.setBounds(300, 300, 300, 400);
        frame.setResizable(false);

        JButton button = new JButton("发送");

        //---edit  begin 2021/4/6----
        frame.add(jScrollPane, BorderLayout.CENTER);
        jPanel1.setLayout(new GridLayout(100, 1));
        frame.add(jPanel, BorderLayout.SOUTH);
        jPanel.add(textField);
        jPanel.add(button);

        //---edit  end 2021/4/6----
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        // Make a connection
        this.connect();
        button.addActionListener(new BListener());
        bw = FileUtils.createWriteFlow(clientName);
        // Start multithreading
        new Thread(new ClientThread(dis,jPanel1, bw)).start();
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
            String text = textField.getText();
            if (text == null || ("").equals(text)) {
                JOptionPane.showMessageDialog(null, "消息不能为空");
                return;
            }
            // send message
            sendMessageToServer(text);
            String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            buffer.append(time).append("\n");
            String message = clientName + ":" + textField.getText().trim();
            buffer.append(message).append("\n");
            textField.setText("");
            // saveMessage to file
            FileUtils.saveMessage(buffer.toString(), bw);
            buffer.delete(0, buffer.length());
        }
    }

    /**
     * Function: send message to server
     *
     * @param text
     */
    private void sendMessageToServer(String text) {
        try {
            dos.writeUTF(clientName + ":" + textField.getText().trim());
            dos.flush();

            JLabel tempLabel = new JLabel(   text + " : 我 "+" "  +"\n");
            tempLabel.setHorizontalAlignment(JLabel.RIGHT);
            jPanel1.add(tempLabel);
            tempLabel.revalidate();

        } catch (IOException e) {
            System.out.println("发送消息失败");
            e.printStackTrace();
        }
    }

}