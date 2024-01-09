import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class RSA {
   private static BigInteger getRandomBigInt(int bitLength) {
      Random random = new Random();
      return new BigInteger(bitLength, random);
   }

   public static BigInteger GCD(BigInteger a, BigInteger b) {
      while (!b.equals(BigInteger.ZERO)) {
         BigInteger temp = b;
         b = a.mod(b);
         a = temp;
      }
      return a;
   }

   public static List<String> splitString(String s, int groupSize) {
      List<String> res = new ArrayList<>();
      for (int i = s.length(); i >= 0; i -= groupSize) {
         int startPosition = i >= groupSize ? i - groupSize : 0;
         String part = s.substring(startPosition, i);
         res.add(part);
      }

      Collections.reverse(res);
      return res;
   }

   public static boolean isPrime(BigInteger n) {
      for (BigInteger factor = BigInteger.TWO; factor.multiply(factor).compareTo(n) != 1; factor = factor.add(BigInteger.ONE))
         if (n.mod(factor).equals(BigInteger.ZERO))
            return false;
      return true;
   }

   public static BigInteger modularMultiplyInverse(BigInteger number, BigInteger modulus) {

      return number.modInverse(modulus);
   }

   public static int findGroupSize(BigInteger n) {
      int ans = 0;
      BigInteger val = BigInteger.ONE;
      BigInteger factor = new BigInteger("26");
      while (val.compareTo(n) <= 0) {
         val = val.multiply(factor);
         ans++;
      }
      return ans - 1;
   }

   public static BigInteger stringToBigInteger(String s) {
      BigInteger ans = BigInteger.ZERO;
      BigInteger factor26 = new BigInteger("26");
      for (int i = 0; i < s.length(); i++) {
         int charCode = s.charAt(i) - 'a';
         BigInteger charCodeBigInt = new BigInteger(Integer.toString(charCode));
         ans = ans.multiply(factor26).add(charCodeBigInt);
      }
      return ans;
   }

   public static String bigIntegertoString(BigInteger number) {
      StringBuilder result = new StringBuilder();
      int base = 26;

      while (number.compareTo(BigInteger.ZERO) > 0) {
         BigInteger remainder = number.mod(BigInteger.valueOf(base));
         char c = (char) (remainder.intValue() + 'a');
         result.insert(0, c);
         number = number.divide(BigInteger.valueOf(base));
      }

      return result.toString();
   }

   public static BigInteger getCoprime(BigInteger n) {
      BigInteger result = ECC_Signature.randomBigInteger(n.bitLength() - 1);
      while (!RSA.GCD(result, n).equals(BigInteger.ONE)) {
         result = ECC_Signature.randomBigInteger(n.bitLength());
      }
      return result;
   }

   public static BigInteger p = new BigInteger("452968543976106356889691095003558702119810891736320489623180497459962502803113054856012289738814660072189257888401129339612091494127868969261675188408778209050737745712873146591551548907109601558078563090905660073888135539003980395797098254283827043282726134352499343457257595484433116019984897084926117049796652356635555677587785726491013129699430883816033558253797315166372944754430432202374437656984906775187442427543589338175609846845072479366907962029161149");
   public static BigInteger q = new BigInteger("647180897253368013803811420473912107970238328693918151421305990035499061124333815918238699288680082533507370354270291549619101632842309293950026759122137604249155972132122607701853192387639811865365009247201291892699104386408132795604944860973221907446927443173607815520506325848052599316932060997727259926052174578364696360696575019563460130945105240888427133465390714306513969011284974313977833996402713026628183487435635346440274833839706028559002141436713959");

   public static void main(String[] args) {
//      BigInteger n = new BigInteger("25");
//      BigInteger k = new BigInteger("12");

      BigInteger n = p.multiply(q);
      System.out.println("n = " + n);
      BigInteger phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
      System.out.println("phi = " + phi);

      BigInteger e = new BigInteger("2913087140870536276348035352345194203");
      BigInteger d = modularMultiplyInverse(e, phi);
      System.out.println("d = " + d);

      //public K'=(n,e), private K''=d

      String m = EllipticCurveCryptography.m;
      int groupSize = findGroupSize(n);
      System.out.println("chunkSize = " + groupSize);
      List<String> splitMessage = splitString(m, groupSize);

      for (String chunk : splitMessage) {
         BigInteger mBigInt = stringToBigInteger(chunk);
         System.out.println("message = " + mBigInt);

         BigInteger encode = mBigInt.modPow(e, n);
         System.out.println("Encode: " + encode);

         BigInteger decode = encode.modPow(d, n);
         System.out.println("Decode: " + decode);
         System.out.println("Decode: " + bigIntegertoString(decode));
         System.out.println();

      }

   }
}
