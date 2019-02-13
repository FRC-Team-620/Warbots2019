package org.usfirst.frc620.Warbots2019.utility;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;
public class MyClientSocket {
    private Socket socket;
    private Scanner scanner;
    private MyClientSocket(InetAddress serverAddress, int serverPort) throws Exception {
        this.socket = new Socket(serverAddress, serverPort);
        this.scanner = new Scanner(System.in);
    }
    private void start() throws IOException {
        String input;
        while (true) {
            input = scanner.nextLine();
            PrintWriter out = new PrintWriter(this.socket.getOutputStream(), true);
            out.println("Hi");
            out.flush();
        }
    }
    
    public static void main(String[] args) throws Exception {
        MyClientSocket client = new MyClientSocket(
                InetAddress.getByName("127.0.0.1"), 
                Integer.parseInt("51015"));
        
        System.out.println("\r\nConnected to Server: " + client.socket.getInetAddress());
        client.start();                
    }
}