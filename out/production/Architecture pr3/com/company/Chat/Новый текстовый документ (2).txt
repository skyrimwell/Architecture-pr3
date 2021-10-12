package com.company.Chat;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {
    public static void main(String[] args) throws Exception {
        try (var socket = new Socket("localhost", 8080)) {

            var login = new Scanner(System.in);
            System.out.println("Введите свой логин");
            String log = login.nextLine();
            System.out.println(log + " зашёл на сервер");
            var scanner = new Scanner(System.in);
            var in = new Scanner(socket.getInputStream());
            var out = new PrintWriter(socket.getOutputStream(), true);
            while (scanner.hasNextLine()) {
                out.println(scanner.nextLine());
                System.out.println(log + ": " + in.nextLine());
            }
        }
    }
}
