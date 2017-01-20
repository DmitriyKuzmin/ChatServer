package com.kdp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


/**
 * Поток запуска сервера чата
 */
class ChatServerThread extends Thread {

    /**
     *  Создаёт сервер-Сокет, и ждёт входа клиента.
     *  Когда клиет заходит, добавляет его в список и запускает поток по работе с клиентом.
     */
    @Override
    public void run() {
        Socket socket = null;

        try {
            //
            ServerSocket serverSocket = new ServerSocket(Main.SocketServerPORT);
            System.out.println("I'm waiting here: "
                    + serverSocket.getLocalPort());
            System.out.println("CTRL + C to quit");

            while (true) {
                //
                socket = serverSocket.accept();
                //
                ChatClient client = new ChatClient();
                //
                Main.userList.add(client);
                //
                ClientThread clientThread = new ClientThread(client, socket);
                clientThread.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

    }

}
