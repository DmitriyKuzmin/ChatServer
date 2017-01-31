# ChatServer
# Консольный сервер для чата.
##Запуск сервера
В папке проекта следует выполнить следующие команды
```
javac -sourcepath ./src -d out src/com/kdp/Main.java
java -classpath ./out com.kdp.Main
```

При запуске сервера выводятся следующие данные:
  1. ip-адрес к которому можо подключиться
  2. Порт адресса
  3. Информация о выходе из данной программы
Пример данных при запуске:
```
SiteLocalAddress: 192.168.56.1
SiteLocalAddress: 192.168.43.186
I'm waiting here: 8080
CTRL + C to quit
```
Клиент этого чата доступен по ссылке [https://github.com/DmitriyKuzmin/ChatClient]
