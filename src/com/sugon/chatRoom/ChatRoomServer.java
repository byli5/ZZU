package com.sugon.chat3;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author: byLi
 * @date: 2021/3/15 16:06
 * @description: Chat service
 */

public class ChatRoomServer {

    /**
     * function：Start chatsever
     *
     * @param args
     */
    public static void main(String[] args) {
        new ChatRoomServer().init();
    }

    /**
     * function：Initialize chatServer
     */
    private void init() {
        System.out.println("服务器已开启");
        try {
            //1.Create a server-side socket, that is, ServerSocket, specify the bound port, and listen on this port
            ServerSocket serverSocket = new ServerSocket(8888);
            //2. Loop monitoring, waiting for client connection
            Socket socket = null;
            int count = 0;
            while (true){
                socket = serverSocket.accept();
                // Create a new thread
                ServerThread serverThread = new ServerThread(socket);
                // Add the created thread to the collection
                Constans.clients.add(serverThread);
                // Start thread
                new Thread(serverThread).start();
                //Count the number of clients
                count++;
                System.out.println("客户端的数量："+count);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
