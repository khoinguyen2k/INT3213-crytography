import java.math.BigInteger;
import java.util.Arrays;

public class EllipticCurveCryptography {
   public static BigInteger a = new BigInteger("394");
   public static BigInteger b = new BigInteger("3536");
   public static BigInteger p = new BigInteger("1986181080048982133654031961719942703022737256317820295873954303129190220078988097483363098098394348954253427763979256167416182433022201");

   public static BigInteger n = new BigInteger("1986181080048982133654031961719942703022737256317820295873954303129163145008998024445410245294025436761552884742663512165880975593842133");

   public static String m = "chaomunghaimuoinamthanhlaptruongdhcn";

   //điểm sinh - based point
   public static BigInteger Px = new BigInteger("1");
   public static BigInteger Py = new BigInteger("1127192882512754483507509182931933354801519523674465656843034567073536247015055219833847275458324664146961205580874879468445387079011038");
   public static BigInteger[] P = {Px, Py};

   public static void main(String[] args) {

      BigInteger s = ECCsignature.randomBigInteger(13);
      System.out.println("random s = " + s);

      //B = sP
      BigInteger[] B = ElipticCurve.multiplyPoint(P, s, p, a);

      BigInteger tokenizedM = RSA.stringToBigInteger(m);
      System.out.println("Tokenized message = " + tokenizedM);

      //message is stored in Mx
      BigInteger bitsAddition = new BigInteger("1");
      System.out.println("At the last position of message, bits addition = " + bitsAddition);
      BigInteger Mx = new BigInteger(tokenizedM.toString() + bitsAddition.toString());
      BigInteger My = TonelliShanksAlgorithm.tonelliShanks(Mx.pow(3).add(a.multiply(Mx)).add(b), p);

      BigInteger[] M = {Mx, My};
      System.out.println("M = " + Arrays.toString(M));
      BigInteger k = ECCsignature.randomBigInteger(13);
      System.out.println("Random key k = " + k);

      //M1 = kP
      BigInteger[] M1 = ElipticCurve.multiplyPoint(P, k, p, a);

      BigInteger[] kB = ElipticCurve.multiplyPoint(B, k, p, a);

      //M2 = M + kB
      BigInteger[] M2 = ElipticCurve.addPoints(M, kB, p, a);

      //Encode result
      System.out.println("Encode:");
      System.out.println("  M1 = " + Arrays.toString(M1));
      System.out.println("  M2 = " + Arrays.toString(M2));

      /**Decode process
       * M = M2 - sM1
       *   = M2 + nM1 - sM1
       *   = M2 + (n - s) * M1
       */

      //(n - s) * M1
      BigInteger[] sM1 = ElipticCurve.multiplyPoint(M1, n.subtract(s), p, a);

      //M = M2 + (n - s) * M1
      BigInteger[] decode = ElipticCurve.addPoints(M2, sM1, p, a);

      System.out.println("decode result = " + decode[0]);
   }
}