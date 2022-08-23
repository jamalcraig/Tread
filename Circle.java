package assignment2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//this is the player
public class Circle extends Shape {

    //initialization of variables
    final int WIDTH = 20;
    final int HEIGHT = 20;

    private int speed = 2;
    private int jumpSpeed = 2;
    private int jumpHeight = 100;
    private int gravity = 2;

    boolean grounded = true;
    boolean invincible = false;
    boolean canMove = true;
    boolean fall = false;


    //constructor
    public Circle(int xPos, int yPos, Bumper bumperLeft, Bumper bumperRight){

        moveTo(xPos, yPos);
        this.name = "Player";
        this.bumperLeft = bumperLeft;
        this.bumperRight = bumperRight;
        this.color = Color.BLUE;
    }


    //handles the player jumping
    Timer jumpTimer = new Timer(5, new ActionListener() {

        int i = 0;
        @Override
        public void actionPerformed(ActionEvent e) {
            if (TreadHandler.lives > 0) {

                //makes the player jump
                //the player moves up  until it reaches its maximum jump height, then it falls back down
                if (i < jumpHeight / 2) {
                    moveUp();

                } else {
                    fall();
                }

                i++;

                //if touching the conveyor belt, the player is allowed to jump
                if (getyPos() >= 240) {
                    grounded = true;

                    jumpTimer.stop();
                    i = 0;
                }

            } else {
                //moved from visibility when the game ends
                moveTo(-100, -100);
                jumpTimer.stop();
            }
        }
    });



    @Override
    public void drawShape(Graphics g) {
        super.drawShape(g);
        g.fillOval(getxPos(), getyPos(), WIDTH, HEIGHT);
    }

    //methods that the player uses to move the circle
    public void moveRight(){
        //if the player is in the respawn position, it will only be able to move between the two chutes
        //else it will be able to move the length of the conveyor belt plus a bit more
        if (this.getyPos() > 80 && this.getyPos() < 125) {
            if ((this.getxPos() <= 750)){
                this.setxPos(this.getxPos() + speed);
            }
        } else {
            if ((this.getxPos() <= fallPosRight + 10 && this.getxPos() >= fallPosLeft - 10)) {
                this.setxPos(this.getxPos() + speed);
            }
        }
    }

    public void moveLeft(){
        //if the player is in the respawn position, it will only be able to move between the two chutes
        //else it will be able to move the length of the conveyor belt plus a bit more
        if (this.getyPos() > 80 && this.getyPos() < 125) {
            if ((this.getxPos() >= 300)){
                this.setxPos(this.getxPos() - speed);
            }
        } else {
            if ((this.getxPos() <= fallPosRight + 10 && this.getxPos() >= fallPosLeft - 10)) {
                this.setxPos(this.getxPos() - speed);
            }
        }
    }

    public void moveUp(){
        this.setyPos(getyPos() - jumpSpeed);
    }

    public void jump(){
        jumpTimer.start();
    }

    public void fall(){
        this.setyPos(getyPos() + gravity);
    }

    //stops the player from being able to move when they are falling to their death
    public void fallToDeath(){
        canMove = false;
        checkToCrush(bumperLeft, bumperRight);
    }


    //inherited methods
    @Override
    public boolean checkToCrush(Bumper bumperLeft, Bumper bumperRight) {
        return super.checkToCrush(bumperLeft, bumperRight);
    }

    @Override
    public void checkTouchingConveyorBelt() {
        super.checkTouchingConveyorBelt();
    }

    @Override
    public void moveDown(){
        super.moveDown();
    }

    @Override
    public void slideRight(){
        super.slideRight();
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public void slideLeft(){
        super.slideLeft();
    }

    //the player moves to their unique respawn position after being crushed by the bumpers
    @Override
    public void reset(int position, int variation) {
        die();
    }

    //respawns the player upon death
    public void die(){
        jumpTimer.stop();
        grounded = true;
        canMove = true;
        fall = false;
        moveTo(500,100);

        //a life is deducted
        TreadHandler.lives--;
        TreadHandler.livesText.setText("Lives: " + TreadHandler.lives);
    }

    //makes the player fall quickly when they press the spacebar after respawning
    public void place(){
        this.setyPos(getyPos() + 5);
    }


}
