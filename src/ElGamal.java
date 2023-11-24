import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class ElGamal {
   public static List<BigInteger> factorize(BigInteger number) {
      List<BigInteger> factors = new ArrayList<>();

      // Check for 2 as a factor
      while (number.mod(BigInteger.TWO).equals(BigInteger.ZERO)) {
         factors.add(BigInteger.TWO);
         number = number.divide(BigInteger.TWO);
      }

      // Check for other prime factors
      for (BigInteger i = BigInteger.valueOf(3); i.multiply(i).compareTo(number) <= 0; i = i.add(BigInteger.TWO)) {
         while (number.mod(i).equals(BigInteger.ZERO)) {
            factors.add(i);
            number = number.divide(i);
         }
      }

      // If there is a remaining factor greater than sqrt(number)
      if (number.compareTo(BigInteger.TWO) > 0) {
         factors.add(number);
      }

      return factors;
   }

   public static boolean isPrimitiveElement(BigInteger a, BigInteger field) {
      BigInteger aSubOne = a.subtract(BigInteger.ONE);
      List<BigInteger> factorList = factorize(aSubOne);
      for (BigInteger factor : factorList) {
         BigInteger power = aSubOne.divide(factor);
         if (a.modPow(power, field).equals(BigInteger.ONE))
            return false;
      }
      return true;
   }

   public static BigInteger p = new BigInteger("170147723489609029438836400750368700750275333251589712681057898509970527715872979473027117956284674747018058646330950862066127495557443588804897216465681513516116595637353449327957322995666365123804392733064311578457542957506785086113994970592172412120618780867933487469140772525159587695971518529710821202429");

   public static void main(String[] args) {
      BigInteger p = new BigInteger("170147723489609029438836400750368700750275333251589712681057898509970527715872979473027117956284674747018058646330950862066127495557443588804897216465681513516116595637353449327957322995666365123804392733064311578457542957506785086113994970592172412120618780867933487469140772525159587695971518529710821202429");
      BigInteger alpha = new BigInteger("2");
      System.out.println("Is primitive? " + isPrimitiveElement(alpha, p));

      if (isPrimitiveElement(alpha, p)) {
         BigInteger a = ECCsignature.randomBigInteger(13);
         System.out.println("a = " + a);

         BigInteger beta = alpha.modPow(a, p);
         System.out.println("beta = " + beta);

         BigInteger k = RSA.getCoprime(p.subtract(BigInteger.ONE));
         System.out.println("k = " + k);

         String message = EllipticCurveCryptography.m;
         List<String> splitMessage = RSA.splitString(message, RSA.findGroupSize(p));

         for (String m : splitMessage) {
            BigInteger mBigInt = RSA.stringToBigInteger(m);

            System.out.println("chunk: " + mBigInt);
            BigInteger y1 = alpha.modPow(k, p);
            BigInteger y2 = beta.modPow(k, p).multiply(mBigInt);
            System.out.println("y1 = " + y1);
            System.out.println("y2 = " + y2);

            BigInteger decode = y1.modPow(p.subtract(a).subtract(BigInteger.ONE), p).multiply(y2).mod(p);
            System.out.println("Decode: " + decode);
            System.out.println("Original text: " + RSA.bigIntegertoString(decode));
            System.out.println("--------------------------------");
         }

      }

   }
}
