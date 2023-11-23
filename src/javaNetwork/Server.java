package javaNetwork;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
   public static void main(String[] args) throws IOException {
      ServerSocket serverSocket = new ServerSocket(9877);
      System.out.println("created!");
      Socket socket = serverSocket.accept();
      BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

      String line = br.readLine();
      if (line != null) System.out.println(line);
      br.close();
   }
}
