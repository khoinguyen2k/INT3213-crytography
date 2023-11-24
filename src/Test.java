import java.math.BigInteger;

public class Test {

   private static void test2() {
      BigInteger a = new BigInteger("1");
      BigInteger b = new BigInteger("0");
      BigInteger p = new BigInteger("13");

      BigInteger gx = new BigInteger("2");
      BigInteger gy = new BigInteger("6");

      BigInteger[] g = {gx, gy};

      BigInteger hx = new BigInteger("3");
      BigInteger hy = new BigInteger("2");

      BigInteger[] h = {hx, hy};

//      (7,8) + (3,2) = (2,6)

//      (2,6) - (3,2) = (7,8)
//      (2,6)+9*(3,2) = ?

      BigInteger[] res1 = ElipticCurve.multiplyPoint(h, BigInteger.valueOf(11), p, a);
      System.out.println(res1[0] + " " +res1[1]);


   }

   private static void test3() {
      BigInteger n = new BigInteger("5");
      BigInteger p = new BigInteger("13");
      System.out.println(n.modInverse(p));
      System.out.println((n.modInverse(p)).modInverse(p));
   }

   private static void test4() {
      BigInteger p = new BigInteger("13"); // Prime modulo
      BigInteger a = new BigInteger("1"); // Curve parameter a

      BigInteger[] point1 = {new BigInteger("3"), new BigInteger("2")};

      BigInteger[] res1 = ElipticCurve.multiplyPoint(point1, BigInteger.valueOf(3), p, a);
      BigInteger[] res = ElipticCurve.multiplyPoint(res1, BigInteger.valueOf(5), p, a);

      System.out.println(res[0] + " " +res[1]);

      BigInteger[] res2 = ElipticCurve.multiplyPoint(res1, BigInteger.valueOf(15), p, a);
      System.out.println(res2[0] + " " +res2[1]);
   }

   public static void main(String[] args) {
//      test2();

      //need 1536 * 2 = 3072

      System.out.println(BigInteger.valueOf(-23).mod(BigInteger.TWO));
   }
}
