package com.kdp;

import java.net.Socket;


/**
 *
 */
class ChatClient {

    private String name;
    private Socket socket;
    private ConnectThread chatThread;

    public String getName() {
        return name;
    }

    public Socket getSocket() {
        return socket;
    }

    public ConnectThread getChatThread() {
        return chatThread;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public void setChatThread(ConnectThread chatThread) {
        this.chatThread = chatThread;
    }
}
