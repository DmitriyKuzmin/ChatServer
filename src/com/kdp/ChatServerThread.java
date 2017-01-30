package com.kdp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


/**
 * Поток запуска сервера чата
 */
class ChatServerThread extends Thread {

    /**
     * Создаёт сервер-Сокет, и ждёт входа клиента.
     * Когда клиет заходит, добавляет его в список и запускает поток по работе с клиентом.
     */
    @Override
    public void run() {
        Socket socket = null;

        try {
            //Создаём сокет сервер
            ServerSocket serverSocket = new ServerSocket(Main.SocketServerPORT);
            System.out.println("I'm waiting here: "
                    + serverSocket.getLocalPort());
            System.out.println("CTRL + C to quit");

            while (true) {
                //Принимаем клиента
                socket = serverSocket.accept();
                //Создаём нового клиента
                ChatClient client = new ChatClient();
                //Заносим его в общий список
                Main.userList.add(client);
                //Создаём поток по работе с этим клиентом
                ClientThread clientThread = new ClientThread(client, socket);
                clientThread.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //Закрываем сокет, если он не закрыт
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
