import java.math.BigInteger;

public class PrimeGenerator {
   private static BigInteger getPrime(int bitLength) {
      BigInteger p = ECCsignature.randomBigInteger(bitLength);
      if (p.mod(BigInteger.TWO).equals(BigInteger.ZERO)) p = p.add(BigInteger.ONE);

      while (!p.isProbablePrime(bitLength / 10)) {
         p = p.add(BigInteger.TWO);
      }

      return p;
   }

   public static void main(String[] args) {
      System.out.println(getPrime(1024));
   }
}
