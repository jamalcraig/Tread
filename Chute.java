package assignment2;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Chute extends Shape {

    BufferedImage img;
    int pos;

    //constructor
    public Chute(int pos){

        this.name = "Chute";
        this.color = Color.YELLOW;
        this.pos = pos;

        //gets the image that will be used to represent the chute
        try{
            img = ImageIO.read(getClass().getResourceAsStream("Chute.png"));
            this.sprite = true;
        } catch (Exception e){
            System.out.println("Failed to open chute image");
            this.sprite = false;
        }
    }

    @Override
    public void drawShape(Graphics g) {
        super.drawShape(g);

        //if the sprite was loaded correctly, draws a sprite
        //else it will draw a rectangle
        if (sprite) {
            if(pos == 0) {
                g.drawImage(img, 200, 0, img.getWidth() / 3, img.getHeight() / 3, null);
            } else {
                g.drawImage(img, 800, 0, img.getWidth() / 3, img.getHeight() / 3, null);
            }
        } else {
            if (pos == 0){
                g.fillRect(200, 0, 80, 100);
            } else if (pos == 1){
                g.fillRect(800, 0, 80, 100);
            }
        }
    }
}
