package com.kdp;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;


/**
 * Поток для работы с клиентом
 */
class ClientThread extends Thread {

    //Сокет клиента
    private Socket socket;

    //Клиент
    private ChatClient connectClient;

    //Сообщение клиента, которое потом отсылается остальным клиентам
    private String msgToSend = "";

    //Сообщение клиента, которое выводится в консоль
    private String msgLog = "";

    /**
     * Конструктор Клинт-Потока
     *
     * @param client - Клиет
     * @param socket - Сокет клиента
     */
    ClientThread(ChatClient client, Socket socket) {
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
        //Подготавливаем входной и выходной поток
        DataInputStream dataInputStream = null;
        DataOutputStream dataOutputStream = null;

        try {
            //Создаём входной и выходной поток
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());

            //Принимаем имя от пользователя
            String n = dataInputStream.readUTF();

            //Устанавливаем имя пользователя
            connectClient.setName(n);

            //Информация в консоль по подключению пользователя
            msgLog = connectClient.getName() + " connected@"
                    + connectClient.getSocket().getInetAddress()
                    + ":" + connectClient.getSocket().getPort() + "\n";
            System.out.println(msgLog);


            //Приветствуем пользователя
            dataOutputStream.writeUTF("Welcome " + n + "\n");
            //"Выталкиваем" данные, иначе они будут потерены
            dataOutputStream.flush();

            //Сообщаем всем пользователям, что вошёл новый клиент
            broadcastMsg(n + " join our chat.\n");


            //Цикл для приёма и передачи сообщений
            while (true) {
                //Проверяем входной поток
                if (dataInputStream.available() > 0) {

                    //Считываем новое сообщение
                    String newMsg = dataInputStream.readUTF();

                    if (newMsg.equals("disconnect\n")) {
                        break;
                    }

                    //Выводим сообщение в консоль
                    msgLog = n + ": " + newMsg;
                    System.out.print(msgLog);

                    //Подготовка для отправки клиенту
                    broadcastMsg(n + ": " + newMsg);
                }

                //Проверяем,есть ли сообщения для отправки пользователю.
                if (!msgToSend.equals("")) {
                    //Отравляем сообщение
                    dataOutputStream.writeUTF(msgToSend);
                    dataOutputStream.flush();
                    msgToSend = "";
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //Закрываем потоки ввода и вывода, если они не закрыты
            if (dataInputStream != null) {
                try {
                    dataInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (dataOutputStream != null) {
                try {
                    dataOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            //Удаляем этого пользователя
            Main.userList.remove(connectClient);

            //Выводим в консоль, что этот пользователь вышел
            msgLog = "-- " + connectClient.getName() + " logged out\n";
            System.out.println(msgLog);

            //Сообщаем всем остальным пользователям, что этот пользователь покинул чат
            broadcastMsg("-- " + connectClient.getName() + " logged out\n");

        }
    }

    /**
     * Метод подготовки для отправления сообщения пользователю
     *
     * @param msg - сообщение, которое следует отправить
     */
    private void sendMsg(String msg) {
        msgToSend = msg;
    }

    /**
     * Метод подготовки для отправления  сообщения всем пользователям
     *
     * @param msg - сообщение, которое следует отправить
     */
    private void broadcastMsg(String msg) {
        for (ChatClient anUserList : Main.userList) {
            anUserList.getChatThread().sendMsg(msg);
            System.out.print(msgLog);
        }

        System.out.println();

    }
}
