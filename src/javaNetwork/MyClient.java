package javaNetwork;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class MyClient {

   public static void writeln(String s) {
      System.out.println(s);
   }

   public static void write(String s) {
      System.out.print(s);
   }

   public static void main(String[] args) throws IOException {
      int port = 27001;
      Socket socket = new Socket("112.137.129.129", port);
      DataInputStream input = new DataInputStream(socket.getInputStream());
      DataOutputStream output = new DataOutputStream(socket.getOutputStream());

      // Send greeting packet
      String studentId = "21020780";
      byte[] studentIdBytes = studentId.getBytes("UTF-8");
      ByteBuffer helloBuffer = ByteBuffer.allocate(8 + studentIdBytes.length);
      helloBuffer.order(ByteOrder.LITTLE_ENDIAN);

      helloBuffer.putInt(0); // PKT_HELLO
      helloBuffer.putInt(studentIdBytes.length);
      helloBuffer.put(studentIdBytes);
      output.write(helloBuffer.array());

      while (true) {
         // Receive and read header
         byte[] headerPacketPart = new byte[8];
         int iterator = input.read(headerPacketPart);
         ByteBuffer headerBuffer = ByteBuffer.wrap(headerPacketPart);
         headerBuffer.order(ByteOrder.LITTLE_ENDIAN);
         int calcType = headerBuffer.getInt();
         int calcLen = headerBuffer.getInt();
         if (calcType == 3 || calcType == 4) break;

         byte[] numPacketPart = new byte[8];
         int iterator2 = input.read(numPacketPart);
         ByteBuffer numBuffer = ByteBuffer.wrap(numPacketPart);
         numBuffer.order(ByteOrder.LITTLE_ENDIAN);
         int a = numBuffer.getInt();
         int b = numBuffer.getInt();

         //send PKT_RESULT
         int result = a + b;
         ByteBuffer resultBuffer = ByteBuffer.allocate(4 + 4 + 4);
         resultBuffer.order(ByteOrder.LITTLE_ENDIAN);
         resultBuffer.putInt(2); // PKT_RESULT
         resultBuffer.putInt(4);
         resultBuffer.putInt(result);

         output.write(resultBuffer.array());
      }

      // Receive PKT_FLAG and print the flag
      byte[] flagPacket = new byte[1024];
      int flagLen = input.read(flagPacket);
      String flag = new String(flagPacket, 0, flagLen, "UTF-8");
      write(flag );

      socket.close();
      input.close();
      output.close();
   }
}
