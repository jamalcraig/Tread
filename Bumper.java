package assignment2;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class Bumper extends Shape {

    //initializing variables
    private BufferedImage currentFrame;
    private BufferedImage frame1;
    private BufferedImage frame2;
    private BufferedImage frame3;
    private BufferedImage frame4;
    private BufferedImage frame5;
    private BufferedImage frame6;
    private BufferedImage frame7;
    private BufferedImage frame8;
    private int pos;

    public BufferedImage getFrame(){
        return currentFrame;
    }

    public int getPos(){
        return pos;
    }

    //every 20ms, the timer will cycle the image to the next frame so that it looks like animation
    Timer bumperTimer = new Timer(20, new ActionListener() {
        int i = 1;
        int direction = 1;
        boolean finish = false;
        @Override
        public void actionPerformed(ActionEvent e) {
            if (TreadHandler.lives > 0) {
                switch (i) {

                    //each frame is cycled through to the last one, then it cycles backwards to the first and ends the animation
                    case 1:
                        currentFrame = frame1;
                        direction = Math.abs(direction);
                        if (finish) {
                            bumperTimer.stop();
                        }
                        i += direction;
                        break;
                    case 2:
                        currentFrame = frame2;
                        i += direction;
                        break;
                    case 3:
                        currentFrame = frame3;
                        i += direction;
                        break;
                    case 4:
                        currentFrame = frame4;
                        i += direction;
                        break;
                    case 5:
                        currentFrame = frame5;
                        i += direction;
                        break;
                    case 6:
                        currentFrame = frame6;
                        i += direction;
                        break;

                    case 7:
                        currentFrame = frame7;
                        i += direction;
                        break;
                    case 8:
                        currentFrame = frame8;
                        direction = -Math.abs(direction);
                        i += direction;
                        finish = true;
                        break;
                }
            } else {
                bumperTimer.stop();
            }
        }
    });

    //constructor
    public Bumper(int pos){

        moveTo(0,0);
        this.name = "Bumper";
        this.color = Color.CYAN;
        this.pos = pos;

        //loads all the images needed in the animation
        try {
            frame1 = ImageIO.read(getClass().getResourceAsStream("Bumper Sprite 1.png"));
            frame2 = ImageIO.read(getClass().getResourceAsStream("Bumper Sprite 2.png"));
            frame3 = ImageIO.read(getClass().getResourceAsStream("Bumper Sprite 3.png"));
            frame4 = ImageIO.read(getClass().getResourceAsStream("Bumper Sprite 4.png"));
            frame5 = ImageIO.read(getClass().getResourceAsStream("Bumper Sprite 5.png"));
            frame6 = ImageIO.read(getClass().getResourceAsStream("Bumper Sprite 6.png"));
            frame7 = ImageIO.read(getClass().getResourceAsStream("Bumper Sprite 7.png"));
            frame8 = ImageIO.read(getClass().getResourceAsStream("Bumper Sprite 8.png"));
            currentFrame = frame1;
            sprite = true;
        } catch (Exception e){
            System.out.println("failed to open" + name + " image");
            currentFrame = null;
            frame1 = null;
            frame2 = null;
            frame3 = null;
            frame4 = null;
            frame5 = null;
            frame6 = null;
            frame7 = null;
            frame8 = null;
            sprite = false;
        }

    }

    @Override
    public void drawShape(Graphics g) {
        super.drawShape(g);

        //if the sprite was loaded correctly, draws a sprite
        //else it will draw a rectangle
        if (sprite){
            if (pos == 0){
                g.drawImage(this.currentFrame, getxPos(), getyPos(), currentFrame.getWidth() / 3, currentFrame.getHeight() / 3, null);
            } else if (pos == 1) {
                g.drawImage(currentFrame, 1070, getyPos(), currentFrame.getWidth() / -3, currentFrame.getHeight() / 3, null);
            }
        } else {
            if (pos == 0) {
                g.fillRect(getxPos(), 400, 304, 300);
            } else if (pos == 1){
                g.fillRect(1070-304, 400, 304, 300);
            }
        }
    }
}
