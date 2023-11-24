import java.math.BigInteger;

public class ElGamalSignature {
   public static void main(String[] args) {

      BigInteger p = ElGamal.p;
      BigInteger alpha = new BigInteger("2");
      System.out.println("Is primitive? " + ElGamal.isPrimitiveElement(alpha, p));

      BigInteger a = new BigInteger("6043");
      BigInteger beta = alpha.modPow(a, p);

      BigInteger x = BigInteger.valueOf(EllipticCurveCryptography.m.hashCode());

      if (ElGamal.isPrimitiveElement(alpha, p)) {
         BigInteger k = new BigInteger("82214871548367599913110398239314496960684940023043881768262321571536087030799493714236842706679348359610398100117448671222331651489141446705865667949639087554525098878117723004395180953052314444511396425866913784364224855320317835254133449383494594979010188625968484771213334205086158235425989941053338153377");
         BigInteger gamma = alpha.modPow(k, p);
         System.out.println("gamma = " + gamma);

         System.out.println("k inverse mod p = " + k.modInverse(p.subtract(BigInteger.ONE)));
         BigInteger delta = x.subtract(a.multiply(gamma)).multiply(k.modInverse(p.subtract(BigInteger.ONE))).mod(p.subtract(BigInteger.ONE));
         System.out.println("delta = " + delta);

         BigInteger betaPowerGammaTimesGammaPowerDelta = beta.modPow(gamma, p).multiply(gamma.modPow(delta, p)).mod(p);
         BigInteger alphaPowerHashX = alpha.modPow(x, p);

         System.out.println(betaPowerGammaTimesGammaPowerDelta.subtract(alphaPowerHashX).mod(p));
      }

   }
}
