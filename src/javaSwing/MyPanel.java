package javaSwing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyPanel extends JPanel implements ActionListener {

   private Image image = new ImageIcon("D:/Downloads/JAVA/Starter/src/Pixilart Sprite Sheet.png").getImage();
   private Image leHongQuang = new ImageIcon("D:/Downloads/JAVA/Starter/src/lhq.png").getImage();

   private int width = image.getWidth(null); //x axis
   private int height = image.getHeight(null); //y axis

   private Timer timer;
   private int fps = 5;

   public MyPanel() {
      timer = new Timer(1000 / fps, this);
      timer.start();
      this.setPreferredSize(new Dimension(1200, 600));
   }

   private void drawWhite(Graphics2D graphics2D, int x, int y) {
      graphics2D.drawImage(image, x * size, y * size, x * size + width / 3, y * size + height, 0, 0, width / 3, height, null);
   }

   private void drawRed(Graphics2D graphics2D, int x, int y) {
      graphics2D.drawImage(image, x * size, y * size, x * size + width / 3, y * size + height, width / 3, 0, 2 * width / 3, height, null);
   }

   private void drawBlue(Graphics2D graphics2D, int x, int y) {
      graphics2D.drawImage(image, x * size, y * size, x * size + width / 3, y * size + height, 2 * width / 3, 0, width, height, null);
   }

   private void drawCharacter(Graphics2D graphics2D, int[][] letter, int x, int y, int color) {
      for (int height = 0; height < letter.length; height++)
         for (int width = 0; width < letter[height].length; width++)
            if (letter[height][width] == PixelLetter.x) {

               switch (color) {
                  case PixelLetter.white:
                     drawWhite(graphics2D, width + x, height + y);
                     break;
                  case PixelLetter.red:
                     drawRed(graphics2D, width + x, height + y);
                     break;
                  case PixelLetter.blue:
                     drawBlue(graphics2D, width + x, height + y);
                     break;
                  default:
                     break;
               }
            }
   }

   private void fillCharacter(Graphics2D graphics2D, int[][] letter, int x, int y, int color) {
      drawCharacter(graphics2D, letter, x, y, color);
      drawCharacter(graphics2D, PixelLetter.space, x, y, color);
   }

   private int[][] canvas = new int[120][60];
   private int size = 10;

   private int imageX = 58;
   private int imageY = 25;

   public void paint(Graphics graphics) {

      super.paint(graphics);

      Graphics2D graphics2D = (Graphics2D) graphics;
      graphics2D.setColor(Color.LIGHT_GRAY);
      graphics2D.drawRect(0, 0, width, height);

      String s1 = "happy birthday";
      String s2 = "le hong quang";
      int positionX = 5;
      int positionY = 5;

      for (int i = 0; i < s1.length(); i++) {
         fillCharacter(graphics2D, PixelLetter.detectLetter(s1.charAt(i)), positionX, positionY, PixelLetter.randomColor());
         int letterSize = PixelLetter.detectLetter(s1.charAt(i))[0].length;
         positionX = positionX + letterSize + 1;
      }

      positionX = 5;
      positionY = 15;
      for (int i = 0; i < s2.length(); i++) {
         fillCharacter(graphics2D, PixelLetter.detectLetter(s2.charAt(i)), positionX, positionY, PixelLetter.randomColor());
         int letterSize = PixelLetter.detectLetter(s2.charAt(i))[0].length;
         positionX = positionX + letterSize + 1;
      }

      graphics2D.drawImage(leHongQuang, imageX * size, imageY * size, null);
   }

   @Override
   public void actionPerformed(ActionEvent e) {
      imageX = 58 + PixelLetter.randomOffset();
      imageY = 24 + PixelLetter.randomOffset();
      repaint();
   }
}