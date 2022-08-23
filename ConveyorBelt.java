package assignment2;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ConveyorBelt extends Shape {

    //other classes will reference these variables
    static int Y;
    static int direction = 1;

    //initializing variables
    private BufferedImage currentFrame;
    private BufferedImage frame1;
    private BufferedImage frame2;
    private BufferedImage frame3;
    private BufferedImage frame4;
    private BufferedImage frame5;
    private BufferedImage frame6;
    private BufferedImage frame7;

    public BufferedImage getFrame(){
        return currentFrame;
    }

    //constructor
    public ConveyorBelt(){

        this.name = "Conveyor Belt";
        this.color = Color.RED;

        moveTo(190, 260);

        //the y position of the conveyor belt will be used by many classes as a reference
        Y = this.getyPos();


        //gets the images that will be used in the animation
        try {
            frame1 = ImageIO.read(getClass().getResourceAsStream("Treadmill Sprite 1.png"));
            frame2 = ImageIO.read(getClass().getResourceAsStream("Treadmill Sprite 2.png"));
            frame3 = ImageIO.read(getClass().getResourceAsStream("Treadmill Sprite 3.png"));
            frame4 = ImageIO.read(getClass().getResourceAsStream("Treadmill Sprite 4.png"));
            frame5 = ImageIO.read(getClass().getResourceAsStream("Treadmill Sprite 5.png"));
            frame6 = ImageIO.read(getClass().getResourceAsStream("Treadmill Sprite 6.png"));
            frame7 = ImageIO.read(getClass().getResourceAsStream("Treadmill Sprite 7.png"));
            currentFrame = frame1;
            sprite = true;
        } catch (Exception e){
            System.out.println("failed to open " + name + " image");
            currentFrame = null;
            frame1 = null;
            frame2 = null;
            frame3 = null;
            frame4 = null;
            frame5 = null;
            frame6 = null;
            frame7 = null;
            sprite = false;
        }

        //if the conveyor belt images are initialised correctly, the animation timer will be started
        if (currentFrame != null){
            treadmillTimer.start();
        }
    }

    //timer that animates the conveyor belt
    Timer treadmillTimer = new Timer(82, new ActionListener() {
        int i = 1;
        @Override
        public void actionPerformed(ActionEvent e) {
            if (TreadHandler.lives > 0) {
                //cycles through each frame, depending on which direction the belt needs to move in
                switch (i) {

                    case 1:
                        currentFrame = frame1;
                        if (direction == 1) {
                            i++;
                        } else if (direction == 2) {
                            i = 7;
                        }
                        break;
                    case 2:
                        currentFrame = frame2;
                        if (direction == 1) {
                            i++;
                        } else if (direction == 2) {
                            i--;
                        }
                        break;
                    case 3:
                        currentFrame = frame3;
                        if (direction == 1) {
                            i++;
                        } else if (direction == 2) {
                            i--;
                        }
                        break;
                    case 4:
                        currentFrame = frame4;
                        if (direction == 1) {
                            i++;
                        } else if (direction == 2) {
                            i--;
                        }
                        break;
                    case 5:
                        currentFrame = frame5;
                        if (direction == 1) {
                            i++;
                        } else if (direction == 2) {
                            i--;
                        }
                        break;
                    case 6:
                        currentFrame = frame6;
                        if (direction == 1) {
                            i++;
                        } else if (direction == 2) {
                            i--;
                        }
                        break;

                    case 7:
                        currentFrame = frame7;
                        if (direction == 1) {
                            //this is the last frame so it will cycle to the first frame
                            i = 1;
                        } else if (direction == 2) {
                            i--;
                        }
                        break;
                }
            } else {
                treadmillTimer.stop();
            }
        }
    });


    @Override
    public void drawShape(Graphics g) {
        super.drawShape(g);

        //if the sprite was loaded correctly, draws a sprite
        //else it will draw a rectangle
        if (sprite) {
            g.drawImage(currentFrame, getxPos(), getyPos(), currentFrame.getWidth() / 3, currentFrame.getHeight() / 3, null);
        } else {
            g.fillRect(getxPos(), getyPos(), 680, 20);
        }
    }
}
