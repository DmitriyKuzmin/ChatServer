package com.kdp;

import java.net.Socket;


/**
 * Класс по работе с клиентами чата.
 */
class ChatClient {
    private String name;
    private Socket socket;
    //Поток клиента
    private ClientThread chatThread;

    String getName() {
        return name;
    }

    Socket getSocket() {
        return socket;
    }

    ClientThread getChatThread() {
        return chatThread;
    }

    void setName(String name) {
        this.name = name;
    }

    void setSocket(Socket socket) {
        this.socket = socket;
    }

    void setChatThread(ClientThread chatThread) {
        this.chatThread = chatThread;
    }
}
