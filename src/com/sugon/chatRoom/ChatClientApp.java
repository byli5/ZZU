package com.sugon.chat3;

/**
 * @author: byLi
 * @date: 2021/3/17 10:50
 * @description:  Client startup class
 */
public class ChatClientApp {

    public static void main(String[] args) {
        // 创建两个客户端类
        ClientFrame clientFrameA = new ClientFrame("客户端A");
        clientFrameA.init();
        ClientFrame clientFrameB = new ClientFrame("客户端B");
        clientFrameB.init();
    }
}
