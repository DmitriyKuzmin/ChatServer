package com.kdp;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


/**
 *
 */
class ChatServerThread extends Thread{

    /**
     *
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
                ConnectThread connectThread = new ConnectThread(client, socket);
                connectThread.start();
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
