package assignment2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//adds an extra life
public class Square extends Shape{

    //initialisation of variables
    final int WIDTH = 20;
    final int HEIGHT = 20;
    Circle player;
    boolean fall = false;

    //constructor
    public Square(Circle player, Bumper bumperLeft, Bumper bumperRight){

        this.name = "Square";
        moveTo(spawnXL, spawnYL);
        this.width = WIDTH;
        this.height = HEIGHT;
        this.allowance = 20;
        this.player = player;
        this.bumperLeft = bumperLeft;
        this.bumperRight = bumperRight;
        this.color = Color.GREEN;

        timer.start();

    }


    //timer to check for conditions
    Timer timer = new Timer(10, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (TreadHandler.lives > 0) {
                checkTouchingConveyorBelt(height);

                //moves down if above the conveyor belt
                if (getyPos() < 240 && fall) {
                    moveDown();
                }


                checkToCrush(bumperLeft, bumperRight);
                checkTouchingPlayer(player, allowance, height);
                spawnCorrectly();
            } else {
                timer.stop();
            }
        }
    });


    @Override
    public void drawShape(Graphics g) {
        super.drawShape(g);
        g.fillRect(getxPos(), getyPos(), WIDTH, HEIGHT);
    }

    //methods inherited from Shape
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

    //adds a life to the player
    @Override
    public void isTouchingPlayer() {
        reset(position, variation);
        TreadHandler.lives++;
        TreadHandler.livesText.setText("Lives: " + TreadHandler.lives);
        fall = false;
        System.out.println("Extra Life");
    }

    @Override
    public void checkAboveConveyorBelt() {
        super.checkAboveConveyorBelt();
    }

    @Override
    public void checkTouchingConveyorBelt() {
        super.checkTouchingConveyorBelt();
    }

    @Override
    public void reset(int position, int variation) {
        super.reset(position, variation);
        fall = false;
    }

    @Override
    public boolean checkToCrush(Bumper bumperLeft, Bumper bumperRight) {
        return super.checkToCrush(bumperLeft, bumperRight);
    }
}
