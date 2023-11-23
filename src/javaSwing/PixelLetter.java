package javaSwing;

import java.util.Random;

public class PixelLetter {
   public static final int x = 1;
   public static final int white = 2;
   public static final int red = 3;
   public static final int blue = 4;

   public static int randomColor() {
      Random random = new Random();
      int randomNumber = random.nextInt(3) + 2;
      return randomNumber;
   }

   public static int randomOffset() {
      return randomColor() - 2 - 1;
   }

   public static int[][] detectLetter(char c) {
      switch (c) {
         case 'h':
            return letterH;

         case 'a':
            return letterA;

         case 'p':
            return letterP;

         case 'y':
            return letterY;

         case 'b':
            return letterB;

         case 'i':
            return letterI;

         case 'r':
            return letterR;

         case 't':
            return letterT;

         case 'd':
            return letterD;

         case 'l':
            return letterL;

         case 'e':
            return letterE;

         case 'o':
            return letterO;

         case 'n':
            return letterN;

         case 'g':
            return letterG;

         case 'q':
            return letterQ;

         case 'u':
            return letterU;

         case ' ':
            return space;

         default:
            return new int[1][1];
      }
   }

   public static int[][] letterH = {
         {x, 0, x},
         {x, 0, x},
         {x, x, x},
         {x, 0, x},
         {x, 0, x}
   };
   public static int[][] letterA = {
         {0, x, 0},
         {x, 0, x},
         {x, x, x},
         {x, 0, x},
         {x, 0, x}
   };
   public static int[][] letterP = {
         {x, x, x},
         {x, 0, x},
         {x, x, x},
         {x, 0, 0},
         {x, 0, 0}
   };
   public static int[][] letterY = {
         {x, 0, x},
         {x, 0, x},
         {0, x, 0},
         {0, x, 0},
         {0, x, 0}
   };
   public static int[][] letterB = {
         {x, x, 0},
         {x, 0, x},
         {x, x, 0},
         {x, 0, x},
         {x, x, x}
   };
   public static int[][] letterI = {
         {x, x, x},
         {0, x, 0},
         {0, x, 0},
         {0, x, 0},
         {x, x, x}
   };
   public static int[][] letterR = {
         {x, x, 0},
         {x, 0, x},
         {x, x, 0},
         {x, 0, x},
         {x, 0, x}
   };
   public static int[][] letterT = {
         {x, x, x},
         {0, x, 0},
         {0, x, 0},
         {0, x, 0},
         {0, x, 0}
   };
   public static int[][] letterD = {
         {x, x, 0},
         {x, 0, x},
         {x, 0, x},
         {x, 0, x},
         {x, x, 0}
   };
   public static int[][] space = {
         {0},
         {0},
         {0},
         {0},
         {0}
   };
   public static int[][] letterL = {
         {x, 0, 0},
         {x, 0, 0},
         {x, 0, 0},
         {x, 0, 0},
         {x, x, x}
   };
   public static int[][] letterE = {
         {x, x, x},
         {x, 0, 0},
         {x, x, 0},
         {x, 0, 0},
         {x, x, x}
   };
   public static int[][] letterO = {
         {0, x, 0},
         {x, 0, x},
         {x, 0, x},
         {x, 0, x},
         {0, x, 0}
   };
   public static int[][] letterQ = {
         {0, x, 0},
         {x, 0, x},
         {x, 0, x},
         {x, 0, x},
         {x, x, 0},
         {0, 0, x}
   };
   public static int[][] letterU = {
         {x, 0, x},
         {x, 0, x},
         {x, 0, x},
         {x, 0, x},
         {0, x, 0}
   };
   public static int[][] letterN = {
         {x, 0, 0, x},
         {x, x, 0, x},
         {x, 0, x, x},
         {x, 0, 0, x},
         {x, 0, 0, x}
   };
   public static int[][] letterG = {
         {0, x, x, 0},
         {x, 0, 0, 0},
         {x, 0, x, x},
         {x, 0, 0, x},
         {0, x, x, 0}
   };
}
