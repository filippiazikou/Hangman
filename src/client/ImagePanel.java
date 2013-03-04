package client;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class ImagePanel extends JPanel{

    private BufferedImage image;

    public ImagePanel() {
       try {                
          image = ImageIO.read(new File("images/hung.gif"));
       } catch (IOException ex) {
          System.out.println("Cannot load image from given destination.");
       }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, null); // see javadoc for more info on the parameters            
    }
    
    public void setImage(int i){
       String x = Integer.toString(i);
        try {  
            if (i==0)
               image = ImageIO.read(new File("images/hungdead.gif"));
            else if (i==-1)
               image = ImageIO.read(new File("images/hung.gif"));
            else 
               image = ImageIO.read(new File("images/hung" + x + ".gif"));
       } catch (IOException ex) {
          System.out.println("Cannot load image from given destination.");
       }
    }

}