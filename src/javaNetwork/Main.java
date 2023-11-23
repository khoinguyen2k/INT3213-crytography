package javaNetwork;

import java.io.IOException;
import java.net.InetAddress;

public class Main {
   public static void sendPingRequest(String ipAddress) throws IOException {

      InetAddress inetAddress = InetAddress.getByName(ipAddress);
      if (inetAddress.isReachable(5000))
         System.out.println("Host is reachable");
      else
         System.out.println("Sorry ! We can't reach to this host");
   }

   public static void main(String[] args) throws IOException {

      String ipAddress = "www.google.com";
      sendPingRequest(ipAddress);
   }
}