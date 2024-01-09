import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

public class ECC_Signature {

   public static BigInteger randomBigInteger(int bitLength) {
      SecureRandom random = new SecureRandom();

      BigInteger bigInteger = new BigInteger(bitLength, random);
      return bigInteger.compareTo(BigInteger.ZERO) > 0 ? bigInteger : bigInteger.negate();
   }

   public static BigInteger randomBigInteger() {
      SecureRandom random = new SecureRandom();

      BigInteger bigInteger = new BigInteger(128, random);
      return bigInteger.compareTo(BigInteger.ZERO) > 0 ? bigInteger : bigInteger.negate();
   }

   public static byte[] computeSHA512Hash(String input) throws NoSuchAlgorithmException {
      MessageDigest sha512Digest = MessageDigest.getInstance("SHA-512");
      sha512Digest.update(input.getBytes());

      return sha512Digest.digest();
   }

   public static BigInteger SHA512(String input) throws NoSuchAlgorithmException {
      MessageDigest sha512Digest = MessageDigest.getInstance("SHA-512");
      sha512Digest.update(input.getBytes());

      return new BigInteger(1, sha512Digest.digest());
   }

   public static String bytesToHex(byte[] bytes) {
      StringBuilder hexString = new StringBuilder();

      for (byte b : bytes) {
         String hex = Integer.toHexString(0xff & b);
         if (hex.length() == 1) {
            hexString.append('0');
         }
         hexString.append(hex);
      }

      return hexString.toString();
   }

   public static void main(String[] args) throws NoSuchAlgorithmException {
      BigInteger p = EllipticCurveCryptography.p;
      BigInteger a = EllipticCurveCryptography.a;
      BigInteger b = EllipticCurveCryptography.b;

      BigInteger[] g = EllipticCurveCryptography.P;

      //n = #Ep(a,b)
      BigInteger n = EllipticCurveCryptography.n;

      BigInteger d = randomBigInteger(6);
      System.out.println("random d = " + d);

      //Q = dg
      BigInteger[] Q = EllipticCurve.multiplyPoint(g, d, p, a);

      //public key display
      System.out.println("Public key: p, a, b, g, n = Ep(a,b)");
      System.out.println("Q = " + Arrays.toString(Q));

      String M = EllipticCurveCryptography.m;

      BigInteger k = randomBigInteger(6);
      System.out.println("random k = " + k);

      BigInteger[] kg = EllipticCurve.multiplyPoint(g, k, p, a);

      BigInteger x1 = kg[0];
      BigInteger r = x1.mod(n);
      BigInteger h = SHA512(M);
      //s = (h + d * r) * (k ^ -1) mod n
      BigInteger s = BigInteger.ZERO;

      while (r.equals(BigInteger.ZERO) || s.equals(BigInteger.ZERO) || EllipticCurve.isInfinityPoint(kg)) {
         k = randomBigInteger(p.bitLength());
         kg = EllipticCurve.multiplyPoint(g, k, p, a);
         x1 = kg[0];
         r = x1.mod(n);
         if (!r.equals(BigInteger.ZERO))
            s = d.multiply(r).add(h).multiply(RSA.modularMultiplyInverse(k, n)).mod(n);
      }

      System.out.println("Signature (r, s) = (" + r + ", " + s + ")");

      //Verify signature

      //w = s ^ -1 mod n
      BigInteger w = RSA.modularMultiplyInverse(s, n);

      //u1 = hw mod n
      BigInteger u1 = h.multiply(w).mod(n);
      System.out.println("u1 = " + u1);

      //u2 = rw mod n
      BigInteger u2 = r.multiply(w).mod(n);
      System.out.println("u2 = " + u2);

      BigInteger[] u1g = EllipticCurve.multiplyPoint(g, u1, p, a);
      BigInteger[] u2Q = EllipticCurve.multiplyPoint(Q, u2, p, a);

      //u1 * g + u2 * Q = (x0, y0)
      BigInteger[] u1gAddu2Q = EllipticCurve.addPoints(u1g, u2Q, p, a);
      System.out.println("u1 * g +u2 * Q = " + Arrays.toString(u1gAddu2Q));

      BigInteger x0 = u1gAddu2Q[0];
      BigInteger v = x0.mod(n);
      System.out.println("v = " + v);
      System.out.print("If v = r then signature is verified: ");
      System.out.println((v.equals(r)) ? "yes" : "no");
   }
}
