import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class EllipticCurve {
   private final static BigInteger[] infinnityPoint = new BigInteger[]{BigInteger.valueOf(-1), BigInteger.valueOf(-1)};

   private static void findEllipticCurve() {
      long p = 23;
      long numOfPoint = 0;

      Map<Long, Long> thangDuBacHai = new LinkedHashMap<>();

      for (long i = 0; i <= (p - 1) / 2; i++) {
         //i -> module
         thangDuBacHai.put((i * i) % p, i);
      }

      List<Long> xList = new ArrayList<>();
      List<Long> yList = new ArrayList<>();
      boolean found = false;
      long aSolution = 0;
      long bSolution = 0;

      for (long a = 1; a <= 1; a++) {
         if (found) break;
         for (long b = 1; b <= 1; b++)
            if ((4 * a * a * a + 27 * b * b) % p != 0) {
               if (found) break;

               for (long x = 0; x < p; x++) {
                  long key = (((x * x) % p * x) % p + a * x + b) % p;
                  //solve y*y = key
                  if (thangDuBacHai.containsKey(key)) {
                     yList.add(thangDuBacHai.get(key));
                     yList.add(p - thangDuBacHai.get(key));
                     xList.add(x);
                  }
               }

               numOfPoint = yList.size() + 1;
               if (!PrimeNumberList.maybePrime(numOfPoint)) {
                  found = true;
                  aSolution = a;
                  bSolution = b;
               }
            }
      }

      System.out.println("Number of points: " + numOfPoint);
      System.out.println("(a, b): " + aSolution + " " + bSolution);
      if (yList.size() < 5000) {
         for (int i = 0; i < xList.size(); i++)
            System.out.println(xList.get(i) + " " + yList.get(i) + "; " + xList.get(i) + " " + yList.get(i + 1));
      }
   }

   public static BigInteger[] addPoints(BigInteger[] point1, BigInteger[] point2, BigInteger p, BigInteger a) {

      if (point1[0].equals(infinnityPoint[0]) && point1[1].equals(infinnityPoint[1]))
         return point2;
      if (point2[0].equals(infinnityPoint[0]) && point2[1].equals(infinnityPoint[1]))
         return point1;

      try {
         BigInteger x1 = point1[0];
         BigInteger y1 = point1[1];
         BigInteger x2 = point2[0];
         BigInteger y2 = point2[1];

         BigInteger lambda;
         if (x1.equals(x2) && y1.equals(y2)) {
            // Point doubling
            BigInteger numerator = x1.pow(2).multiply(BigInteger.valueOf(3)).add(a);
            BigInteger denominator = y1.multiply(BigInteger.valueOf(2)).modInverse(p);
            lambda = numerator.multiply(denominator).mod(p);
         } else {
            // Point addition
            BigInteger numerator = y2.subtract(y1);
            BigInteger denominator = x2.subtract(x1).modInverse(p);
            lambda = numerator.multiply(denominator).mod(p);

         }

         BigInteger x3 = lambda.pow(2).subtract(x1).subtract(x2).mod(p);
         BigInteger y3 = lambda.multiply(x1.subtract(x3)).subtract(y1).mod(p);

         return new BigInteger[]{x3, y3};
      } catch (ArithmeticException e) {
         return infinnityPoint;
      }
   }

   public static BigInteger[] multiplyPoint(BigInteger[] point, BigInteger scalar, BigInteger p, BigInteger a) {
      BigInteger x = point[0];
      BigInteger y = point[1];

      BigInteger[] result = {x, y};

      String scalarBits = scalar.toString(2);
      for (int i = 1; i < scalarBits.length(); i++) {
         result = doublePoint(result, p, a);
         if (scalarBits.charAt(i) == '1')
            result = addPoints(result, point, p, a);
      }

      return result;
   }

   public static BigInteger[] doublePoint(BigInteger[] point, BigInteger p, BigInteger a) {
      if (point[0].equals(infinnityPoint[0]) && point[1].equals(infinnityPoint[1]))
         return infinnityPoint;

      try {
         BigInteger x = point[0];
         BigInteger y = point[1];

         BigInteger numerator = x.pow(2).multiply(BigInteger.valueOf(3)).add(a);

         BigInteger denominator = y.multiply(BigInteger.valueOf(2)).modInverse(p);

         BigInteger lambda = numerator.multiply(denominator).mod(p);
         BigInteger x3 = lambda.pow(2).subtract(x).subtract(x).mod(p);
         BigInteger y3 = lambda.multiply(x.subtract(x3)).subtract(y).mod(p);

         return new BigInteger[]{x3, y3};
      } catch (ArithmeticException e) {
         return infinnityPoint;
      }
   }

   public static boolean isInfinityPoint(BigInteger[] p) {
      return p[0].equals(infinnityPoint[0]) &&p[1].equals(infinnityPoint[1]);
   }

   public static void main(String[] args) {
      BigInteger p = new BigInteger("13"); // Prime modulo
      BigInteger a = new BigInteger("1"); // Curve parameter a

      BigInteger[] point1 = {new BigInteger("2"), new BigInteger("6")};
      BigInteger[] point2 = {new BigInteger("2"), new BigInteger("7")};

      BigInteger[] result = addPoints(point1, point2, p, a);
//      System.out.println("Resulting point: (" + result[0] + ", " + result[1] + ")");

      for (long i = 1; i < 20; i++) {
         System.out.println("i=" + i);
         result = multiplyPoint(point1, BigInteger.valueOf(i), p, a);
         System.out.println("Resulting point: (" + result[0] + ", " + result[1] + ")");
      }
   }
}
