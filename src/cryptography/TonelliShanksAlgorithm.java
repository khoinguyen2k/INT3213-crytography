package cryptography;

import java.math.BigInteger;

public class TonelliShanksAlgorithm {
   public static BigInteger tonelliShanks(BigInteger n, BigInteger p) {
      if (p.compareTo(BigInteger.valueOf(2)) == 0) {
         return n.mod(p);
      }

      if (n.modPow(p.subtract(BigInteger.ONE).divide(BigInteger.valueOf(2)), p).compareTo(BigInteger.ONE) != 0) {
         // n is not a quadratic residue modulo p
         return BigInteger.valueOf(-1);
      }

      BigInteger q = p.subtract(BigInteger.ONE);
      int s = 0;
      while (q.mod(BigInteger.valueOf(2)).equals(BigInteger.ZERO)) {
         q = q.divide(BigInteger.valueOf(2));
         s++;
      }

      if (s == 1) {
         return n.modPow(p.add(BigInteger.ONE).divide(BigInteger.valueOf(4)), p);
      }

      BigInteger z = BigInteger.valueOf(2);
      while (z.modPow(p.subtract(BigInteger.ONE).divide(BigInteger.valueOf(2)), p).compareTo(BigInteger.ONE) == 0) {
         z = z.add(BigInteger.ONE);
      }

      BigInteger c = z.modPow(q, p);
      BigInteger r = n.modPow(q.add(BigInteger.ONE).divide(BigInteger.valueOf(2)), p);
      BigInteger t = n.modPow(q, p);
      BigInteger m = BigInteger.valueOf((long) s);

      while (t.compareTo(BigInteger.ONE) != 0) {
         BigInteger temp = t;
         int i = 0;
         while (temp.compareTo(BigInteger.ONE) != 0 && i < m.intValue()) {
            temp = temp.modPow(BigInteger.valueOf(2), p);
            i++;
         }

         if (i == 0) {
            return r;
         }

         BigInteger b = c.modPow(BigInteger.valueOf(2).pow(m.intValue() - i - 1), p);
         r = r.multiply(b).mod(p);
         t = t.multiply(b.modPow(BigInteger.valueOf(2), p)).mod(p);
         c = b.modPow(BigInteger.valueOf(2), p);
         m = BigInteger.valueOf(i);
      }

      return r;
   }

   private static void solve1() {
      BigInteger n = new BigInteger("52");
      BigInteger p = new BigInteger("1926675854730070750478218605986095034471982001159"); // Prime modulo

      BigInteger sqrt = tonelliShanks(n, p);
      String msg = "5924594078";
      BigInteger randomBitAddition = ECCsignature.randomBigInteger(13);

      BigInteger x = new BigInteger(msg + randomBitAddition.toString());
      BigInteger a = BigInteger.valueOf(867);
      BigInteger val = new BigInteger("52"); // Number to find square root of

      while (sqrt.equals(BigInteger.valueOf(-1))) {
         randomBitAddition = ECCsignature.randomBigInteger(13);

         x = new BigInteger(msg + randomBitAddition.toString());

         BigInteger temp1 = x.pow(3);
         BigInteger temp2 = a.multiply(x);
         temp1 = temp1.add(temp2);
         val = n.add(temp1);
         sqrt = tonelliShanks(val, p);
      }

      System.out.println(x);
      System.out.println("Square root of " + val + " modulo " + p + " is: " + sqrt);
   }

   private static void solve() {
      BigInteger p = new BigInteger("1986181080048982133654031961719942703022737256317820295873954303129190220078988097483363098098394348954253427763979256167416182433022201"); // Prime modulo
      BigInteger x = BigInteger.ZERO;
      BigInteger a = new BigInteger("394");
      BigInteger b = new BigInteger("3536");

      BigInteger val = b;
      BigInteger sqrt = tonelliShanks(val, p);

      BigInteger xPow3;
      BigInteger ax = BigInteger.ZERO;
      while (sqrt.equals(BigInteger.valueOf(-1))) {
         x = x.add(BigInteger.ONE);
         System.out.println("x = " + x);

         xPow3 = x.pow(3);
         ax = ax.add(a);
         val = val.add(xPow3).add(ax);

         sqrt = tonelliShanks(val, p);
      }

      System.out.println(x);
      System.out.println("Square root of " + val + " modulo " + p + " is: " + sqrt);
   }

   public static void main(String[] args) {

      String m = "chaomunghaimuoinamthanhlaptruongdhcn";
      BigInteger tokenizedM = RSA.stringToBigInteger(m);
      System.out.println("mesg = " + tokenizedM);

      BigInteger a = new BigInteger("394");
      BigInteger b = new BigInteger("3536");
      BigInteger p = new BigInteger("1986181080048982133654031961719942703022737256317820295873954303129190220078988097483363098098394348954253427763979256167416182433022201");

      BigInteger xPow3 = tokenizedM.pow(3);
      BigInteger ax = a.multiply(tokenizedM);
      BigInteger val = xPow3.add(ax).add(b);
      BigInteger sqrt = tonelliShanks(val, p);

      BigInteger randomBitAddition = new BigInteger("1");
      while (sqrt.equals(BigInteger.valueOf(-1))) {
         randomBitAddition = ECCsignature.randomBigInteger(2);
         BigInteger paddedM = new BigInteger(tokenizedM.toString() + randomBitAddition.toString());

         xPow3 = paddedM.pow(3);
         ax = a.multiply(paddedM);
         val = xPow3.add(ax).add(b);
         sqrt = tonelliShanks(val, p);
      }

      System.out.println("Bit addition: " + randomBitAddition);

      System.out.println("y = " + sqrt);

   }
}