package com.kdp;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 *
 */
class ConnectThread extends Thread {
    //
    private Socket socket;
    //
    private ChatClient connectClient;
    //
    private String msgToSend = "";
    //
    private String msgLog = "";

    /**
     *
     * @param client
     * @param socket
     */
    ConnectThread(ChatClient client, Socket socket) {
        connectClient = client;
        this.socket = socket;
        client.setSocket(socket);
        client.setChatThread(this);
    }

    /**
     *
     */
    @Override
    public void run() {
        //
        DataInputStream dataInputStream = null;
        //
        DataOutputStream dataOutputStream = null;

        try {
            //
            dataInputStream = new DataInputStream(socket.getInputStream());
            //
            dataOutputStream = new DataOutputStream(socket.getOutputStream());

            //
            String n = dataInputStream.readUTF();

            //
            connectClient.setName(n);

            //
            msgLog = connectClient.getName() + " connected@"
                    + connectClient.getSocket().getInetAddress()
                    + ":" + connectClient.getSocket().getPort() + "\n";

            System.out.println(msgLog);

            //
            dataOutputStream.writeUTF("Welcome " + n + "\n");
            //
            dataOutputStream.flush();

            //
            broadcastMsg(n + " join our chat.\n");


            //
            while (true) {
                if (dataInputStream.available() > 0) {
                    //
                    String newMsg = dataInputStream.readUTF();

                    if (newMsg.equals("disconnect\n")){
                        break;
                    }
                    //
                    msgLog = n + ": " + newMsg;
                    System.out.print(msgLog);

                    //
                    broadcastMsg(n + ": " + newMsg);
                }

                if (!msgToSend.equals("")) {
                    //
                    dataOutputStream.writeUTF(msgToSend);
                    //
                    dataOutputStream.flush();
                    //
                    msgToSend = "";
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        if (dataInputStream != null) {
            try {
                dataInputStream.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        if (dataOutputStream != null) {
            try {
                dataOutputStream.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        //
        Main.userList.remove(connectClient);

        System.out.println(connectClient.getName() + " removed.");

        //
        msgLog = "-- " + connectClient.getName() + " leaved\n";
        System.out.println(msgLog);

        //
        broadcastMsg("-- " + connectClient.getName() + " leaved\n");

    }
    }

    /**
     *
     * @param msg
     */
    private void sendMsg(String msg) {
        msgToSend = msg;
    }

    /**
     *
     * @param msg
     */
    private void broadcastMsg(String msg) {
        for (ChatClient anUserList : Main.userList) {
            anUserList.getChatThread().sendMsg(msg);
            msgLog = "- send to " + anUserList.getName() + "\n";
            System.out.print(msgLog);
        }

        System.out.println();

    }
}
