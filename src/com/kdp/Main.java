package com.kdp;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;



public class Main {

    //Список пользователей, которые подключены
    static List<ChatClient> userList;

    //Порт для сокета
    static final int SocketServerPORT = 8080;


    public static void main(String[] args) {
        new Main();
    }


    private Main() {
        System.out.print(getIpAddress());
        userList = new ArrayList<>();

        //Создаём поток сервера чата
        new ChatServerThread().start();
    }

    /**
     * Метод находит локальный ip-адрес, к которому можно подключиться с андроида
     *
     * @return - локальный ip-адрес, к которому можно подключиться через android
     */
    private static String getIpAddress() {
        String ip = "";
        try {

            //Все интерфейсы на этой машине
            Enumeration<NetworkInterface> enumNetworkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (enumNetworkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = enumNetworkInterfaces.nextElement();
                //Все доступные адреса
                Enumeration<InetAddress> enumInetAddress = networkInterface.getInetAddresses();
                while (enumInetAddress.hasMoreElements()) {
                    InetAddress inetAddress = enumInetAddress.nextElement();

                    //Есть ли доступные локальные адреса
                    if (inetAddress.isSiteLocalAddress()) {

                        //строка IP-адреса  в текстовом представлении
                        ip += "SiteLocalAddress: " + inetAddress.getHostAddress() + "\n";
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
            ip += "Something Wrong! " + e.toString() + "\n";
        }
        return ip;
    }
}
