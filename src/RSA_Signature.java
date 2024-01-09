
import java.math.BigInteger;

class Person {
   public BigInteger p;
   public BigInteger q;
   public BigInteger a; //part of public key
   public BigInteger b; //private key

   public BigInteger n;
   public BigInteger phiN;

   public Person(BigInteger p, BigInteger q) {
      this.p = p;
      this.q = q;

      this.n = p.multiply(q);
      this.phiN = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));

      this.a = getA();
      this.b = a.modInverse(phiN);
   }

   public BigInteger getA() {
      return RSA.getCoprime(phiN);
   }

   public BigInteger encode(BigInteger n, BigInteger a, BigInteger m) {
      return m.modPow(a, n);
   }

   public BigInteger decode(BigInteger n, BigInteger b, BigInteger encode) {
      return encode.modPow(b, n);
   }
}

class Sender extends Person {
   public Sender(BigInteger p, BigInteger q) {
      super(p, q);
   }

   public void showPublicKey() {
      System.out.println("public key b = " + b);
   }

   public void showPrivateKey() {
      System.out.println("private key (n,a) = (" + n + ", " + a + ")");
   }
}

class Receiver extends Person {
   public Receiver(BigInteger p, BigInteger q) {
      super(p, q);
   }

   public void showPublicKey() {
      System.out.println("public key (n,b) = (" + n + ", " + b + ")");
   }

   public void showPrivateKey() {
      System.out.println("private key a = " + a);
   }
}

public class RSA_Signature {
   public static void main(String[] args) {
      BigInteger p1 = RSA.p;
      BigInteger q1 = RSA.q;

      BigInteger p2 = new BigInteger("1395121328180370123079579088896742567381689377698113663300143191824516381156064172378752515268100368777828555906120452654052425678638960251591958083134567353531213630835601804176139853701646909276172913295378061976661261232263706945625231475888202272098981084830930720064441399180906921665549436058612167204044963698352724403268327389375718488289660767494408611464946649809243632118166524356687174683345460824880442814892069346950759050958470818118932343572697879");
      BigInteger q2 = new BigInteger("51765927879466459000928038702177149607659268565849877712108402760361526245912935287157717581435810705120116635968627404968793714589027324067621766122217885005087934647236209814467697558085590146399678515862898377724937157337526775460870808454564393010525444733408805039729414137885759154666206510775888431505288351485376462018544505102484712767187922726601851077987444734031808798077431835353083499930662567488856075579416477286812230678876252093761986676432117");

      Sender Alice = new Sender(p1, q1);
      Receiver Bob = new Receiver(p2, q2);

      String m = EllipticCurveCryptography.m;
      BigInteger x = BigInteger.valueOf(m.hashCode());
      System.out.println("hash message = " + x);

      System.out.println("Alice:");
      Alice.showPrivateKey();
      Alice.showPublicKey();

      System.out.println("Bob:");
      Bob.showPublicKey();
      Bob.showPrivateKey();

      BigInteger AliceEncode = Alice.encode(Bob.n, Bob.b, x);
      System.out.println("encode = " + AliceEncode);

      BigInteger AliceSignature = Alice.encode(Alice.n, Alice.a, x);
      System.out.println("signature = " + AliceSignature);

      BigInteger BobDecode = Bob.decode(Bob.n, Bob.a, AliceEncode);
      System.out.println("decode = " + BobDecode);

      BigInteger checkSignature = Bob.decode(Alice.n, Alice.b, AliceSignature);
      System.out.println("checkSignature = " + checkSignature);
   }
}
