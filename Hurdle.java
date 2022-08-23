package assignment2;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class Hurdle extends Shape {

    boolean scored = false;
    static int variationStatic = 1000;

    private BufferedImage currentFrame;

    public BufferedImage getFrame(){
        return currentFrame;
    }

    //constructor
    public Hurdle(int xPos, int yPos, Bumper bumperLeft, Bumper bumperRight, Circle player){
        //initializing values
        moveTo(xPos, yPos);
        this.name = "Hurdle";
        this.bumperRight = bumperRight;
        this.bumperLeft = bumperLeft;
        this.player = player;
        this.variation = variationStatic;
        this.color = Color.BLACK;
        this.sprite = true;



        //assigns image to hurdle
        try {
            currentFrame = ImageIO.read(getClass().getResourceAsStream("Spike Hurdle.png"));
            this.height = currentFrame.getHeight();
            this.width = currentFrame.getWidth();
        } catch (Exception e){
            System.out.println("Couldn't open hurdle image");
            this.width = 47;
            this.height = 83;
            this.sprite = false;
        }

        timer.start();

    }

    //timer to check conditions
    Timer timer = new Timer(10, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (TreadHandler.lives > 0) {


                //calls inherited methods from the Shape class
                //they literally do what their names say
                checkTouchingConveyorBelt();

                checkAboveConveyorBelt();

                checkToCrush(bumperLeft, bumperRight);

                //if player jumps above hurdle, increase score
                if (player.getxPos() + 10 >= getxPos() && player.getxPos() + 10 <= getxPos() + /*currentFrame.getWidth()*/ 47 && player.getyPos() + 10 < getyPos() && player.getyPos() + 10 > getyPos() - 100 && !scored) {
                    TreadHandler.score++;
                    TreadHandler.scoreText.setText("Score: " + TreadHandler.score);
                    scored = true;
                    System.out.println("Score: " + TreadHandler.score);
                }

                checkTouchingPlayer(player, 15, height);
                spawnCorrectly();
            } else {
                timer.stop();
            }

        }
    });



    @Override
    public void drawShape(Graphics g) {
        super.drawShape(g);

        //if the sprite was loaded correctly, draws a sprite
        //else it will draw a rectangle
        if (sprite) {
            g.drawImage(currentFrame, getxPos(), getyPos(), currentFrame.getWidth() / 2, currentFrame.getHeight() / 2, null);
        } else {
            g.fillRect(getxPos(), getyPos(), 47/2, 83/2);
        }
    }

    //inherited methods from the Shape class
    @Override
    public void slideRight() {
        super.slideRight();
    }

    @Override
    public void slideLeft() {
        super.slideLeft();
    }

    @Override
    public void moveDown() {
        super.moveDown();
    }

    @Override
    public void checkTouchingPlayer(Circle player, int allowance, int height) {
        super.checkTouchingPlayer(player, allowance, height);
    }

    @Override
    public void isTouchingPlayer() {
        //kills the player if it touches the hurdle
        if (!player.invincible) {
            player.die();

            System.out.println("Lose a life");
        }
    }

    @Override
    public boolean checkToCrush(Bumper bumperLeft, Bumper bumperRight) {
        //the range of positions that the hurdle can be respawned in will be increased to the current variation number
        variation = variationStatic;
        return super.checkToCrush(bumperLeft, bumperRight);
    }

    //respawns the shape
    @Override
    public void reset(int position, int variation) {
        super.reset(position, variation);

        //the player will be able to earn score when it jumps over it again
        scored = false;
    }
}
