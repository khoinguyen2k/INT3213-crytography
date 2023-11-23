package javaNetwork;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Client {

   public static void writeln(String s) {
      System.out.println(s);
   }

   public static void write(String s) {
      System.out.print(s);
   }

   public static void test3() throws IOException {
      Socket socket = new Socket("112.137.129.129", 27001);
      DataInputStream input = new DataInputStream(socket.getInputStream());
      DataOutputStream output = new DataOutputStream(socket.getOutputStream());

      // Send PKT_HELLO
      String studentId = "21020780";
      byte[] studentIdBytes = studentId.getBytes("UTF-8");
      ByteBuffer helloBuffer = ByteBuffer.allocate(8 + studentIdBytes.length);
      helloBuffer.order(ByteOrder.LITTLE_ENDIAN);
      helloBuffer.putInt(0); // PKT_HELLO
      helloBuffer.putInt(studentIdBytes.length);
      helloBuffer.put(studentIdBytes);
      output.write(helloBuffer.array());

      for (int i = 0; i < 1000; i++) {
         // Receive PKT_CALC
         byte[] headerPacketPart = new byte[8];
         int iterator = input.read(headerPacketPart);
         ByteBuffer headerBuffer = ByteBuffer.wrap(headerPacketPart);
         headerBuffer.order(ByteOrder.LITTLE_ENDIAN);
         int calcType = headerBuffer.getInt();
         int calcLen = headerBuffer.getInt();

         byte[] numPacketPart = new byte[8];
         int iterator2 = input.read(numPacketPart);
         ByteBuffer numBuffer = ByteBuffer.wrap(numPacketPart);
         numBuffer.order(ByteOrder.LITTLE_ENDIAN);
         int a = numBuffer.getInt();
         int b = numBuffer.getInt();
         writeln(calcType + " " + calcLen + " " + a + " " + b);

         // Calculate a+b and send PKT_RESULT
         int result = a + b;
         ByteBuffer resultBuffer = ByteBuffer.allocate(12);
         resultBuffer.order(ByteOrder.LITTLE_ENDIAN);
         resultBuffer.putInt(2); // PKT_RESULT
         resultBuffer.putInt(4);
         resultBuffer.putInt(result);
         output.write(resultBuffer.array());
      }

      // Receive PKT_FLAG and print the flag
      byte[] flagPacket = new byte[1024];
      int flagLen = input.read(flagPacket);
      String flag = new String(flagPacket, 8, flagLen - 8, "UTF-8");
      writeln("Flag:" + flag + "!");

      socket.close();
      input.close();
      output.close();
   }

   public static void sendPKT_BYE(DataOutputStream output) throws IOException {
      // Send PKT_BYE and close the socket
      ByteBuffer byeBuffer = ByteBuffer.allocate(8);
      byeBuffer.order(ByteOrder.LITTLE_ENDIAN);
      byeBuffer.putInt(3); // PKT_BYE
      byeBuffer.putInt(0);
      output.write(byeBuffer.array());
   }

   public static void TCPHello(DataInputStream input) throws IOException {
      byte[] helloPacket = new byte[16];
      int iterator0 = input.read(helloPacket);
   }

   public static void test() throws IOException {
      int port = 27001;
      Socket socket = new Socket("112.137.129.129", port);
      DataInputStream input = new DataInputStream(socket.getInputStream());
      DataOutputStream output = new DataOutputStream(socket.getOutputStream());

      // Send PKT_HELLO
      String studentId = "21020780";
      byte[] studentIdBytes = studentId.getBytes("UTF-8");
      ByteBuffer helloBuffer = ByteBuffer.allocate(8 + studentIdBytes.length);
      helloBuffer.order(ByteOrder.LITTLE_ENDIAN);

      helloBuffer.putInt(0); // PKT_HELLO
      helloBuffer.putInt(studentIdBytes.length);
      helloBuffer.put(studentIdBytes);
      output.write(helloBuffer.array());

      //TCPHello(input);

      while (true) {
         // Receive and read header
         byte[] headerPacketPart = new byte[8];
         int iterator = input.read(headerPacketPart);
         ByteBuffer headerBuffer = ByteBuffer.wrap(headerPacketPart);
         headerBuffer.order(ByteOrder.LITTLE_ENDIAN);
         int calcType = headerBuffer.getInt();
         int calcLen = headerBuffer.getInt();
         write(calcType + " " + calcLen);
         if (calcType == 3 || calcType == 4) break;

         byte[] numPacketPart = new byte[8];
         int iterator2 = input.read(numPacketPart);
         ByteBuffer numBuffer = ByteBuffer.wrap(numPacketPart);
         numBuffer.order(ByteOrder.LITTLE_ENDIAN);
         int a = numBuffer.getInt();
         int b = numBuffer.getInt();

//         byte[] operatorPacketPart = new byte[4];
//         int iterator3 = input.read(operatorPacketPart);
//         ByteBuffer operatorBuffer = ByteBuffer.wrap(operatorPacketPart);
//         operatorBuffer.order(ByteOrder.BIG_ENDIAN);
//         int q = operatorBuffer.getInt();

         writeln(" " + a + " " + b);
//         writeln(" " + a);
//         writeln(" " + a + " " + b + " " + q);

         // Calculate a+b and send PKT_RESULT
         int result = solve(a, b);
         ByteBuffer resultBuffer = ByteBuffer.allocate(4 + 4 + 4);
         resultBuffer.order(ByteOrder.LITTLE_ENDIAN);
         resultBuffer.putInt(2); // PKT_RESULT
         resultBuffer.putInt(4);
         resultBuffer.putInt(result);

//         String result = calc(a);
//         byte[] resultBytes = result.getBytes("UTF-8");
//         ByteBuffer resultBuffer = ByteBuffer.allocate(8 + resultBytes.length);
//         resultBuffer.order(ByteOrder.LITTLE_ENDIAN);
//         resultBuffer.putInt(2);
//         resultBuffer.putInt(resultBytes.length);
//         resultBuffer.put(resultBytes);

         output.write(resultBuffer.array());
      }

      // Receive PKT_FLAG and print the flag
      byte[] flagPacket = new byte[1024];
      int flagLen = input.read(flagPacket);
      String flag = new String(flagPacket, 0, flagLen, "UTF-8");
      writeln("");
      write("Flag:" + flag + "!");

      socket.close();
      input.close();
      output.close();
   }

   public static int solve(int a, int b) {
      return 1;
   }

   public static boolean isPrime(int n) {
      for (int i = 2; i * i <= n; i++) {
         if (n % i == 0) return false;
      }
      return true;
   }

   public static long fib(int n) {
      return 1;
   }

   public static int solve(int n) {
      int ans = n + 1;
      while (!isPrime(ans)) ans++;
      return ans;
//      int ans =0;
//      for (int i =2; i <n; i++)
//         if (isPrime(i)) ans++;
//      return ans;
   }

   public static int solve(int a, int b, int q) {
      switch (q) {
         case 1:
            return a + b;
         case 2:
            return a - b;
         case 3:
            return a * b;
         case 4:
            return (int) Math.pow(a, b);
         default:
            return 0;
      }
   }

   public static String calc(int n) {
      String lunar = "Nam nhuan";
      String notLunar = "Nam khong nhuan";
      if (n % 4 != 0)
         return notLunar;
      else if (n % 100 == 0 && n % 400 != 0)
         return notLunar;
      else return lunar;
   }

   public static void main(String[] args) throws IOException {
      test();
   }
}
