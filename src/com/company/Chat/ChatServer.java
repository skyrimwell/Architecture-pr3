package com.company.Chat;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ChatServer {
    public static void main(String[] args) throws Exception {
        try (var listener = new ServerSocket(8080)) {
            System.out.println("Сервер запущен...");
            var pool = Executors.newFixedThreadPool(20);
            while (true) {
                pool.execute(new ChatServer.Chater(listener.accept()));
            }
        }
    }

    private static class Chater implements Runnable {
        private Socket socket;

        Chater(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            System.out.println("Подключение: " + socket);
            try {
                var in = new Scanner(socket.getInputStream());
                var out = new PrintWriter(socket.getOutputStream(), true);
                while (in.hasNextLine()) {
                    TimeUnit.SECONDS.sleep(5);
                    out.println(in.nextLine());
                }
            } catch (Exception e) {
                System.out.println("Ошибка:" + socket);
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                }
                System.out.println("Closed: " + socket);
            }
        }
    }
}