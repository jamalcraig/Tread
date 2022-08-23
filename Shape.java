package assignment2;

import javax.swing.*;
import java.awt.*;

public abstract class Shape {

    //variables shapes will inherit
    protected String name;

    protected int width;
    protected int height;

    private int xPos;
    private int yPos;

    //the positions the shape will spawn from the chute
    protected static int spawnXL = 230;
    protected static int spawnYL = 50;
    protected static int spawnXR = 840;

    //the positions where the shape will fall to be crushed
    protected int fallPosLeft = 168;
    protected int fallPosRight = 875;

    protected int slide = 1;
    protected int gravity = 2;

    //objects that need to referenced in some of the shape methods
    Bumper bumperLeft;
    Bumper bumperRight;
    Circle player;

    int allowance;

    int variation = 1;

    //position the shape will respawn in
    static int position = 1;

    protected boolean sprite = false;
    protected Color color = Color.BLACK;

    //getter and setter encapsulation
    public int getxPos() {
        return xPos;
    }

    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    public int getyPos(){
        return yPos;
    }

    public void setyPos(int yPos){
        this.yPos = yPos;
    }


    //returns co ordinates of the object
    @Override
    public String toString() {
        return name + " (" + xPos + ", " + yPos + ")";
    }

    //moves the shape to the right/left - when it's touching the conveyor belt
    public void slideRight(){
        xPos += slide;
    }

    public void slideLeft(){
        xPos -= slide;
    }

    //moves the shape down - if it's not touching the conveyor belt
    public void moveDown(){
        yPos += gravity;
    }

    //moves shape to arbitrary position
    public void moveTo(int x, int y){
        xPos = x;
        yPos = y;
    }

    //draws the shape
    public void drawShape(Graphics g){
        g.setColor(color);
    }

    //checks to see if the shape is touching the player
    public void checkTouchingPlayer(Circle player, int allowance, int height){
        //generic hit box detection that is tailored to the shape that uses this method
        if (player.getxPos() >= xPos-allowance && player.getxPos() <= xPos+allowance && player.getyPos() >= yPos && player.getyPos() <= yPos+height){
            isTouchingPlayer();
        }
    }

    public void isTouchingPlayer(){
        //I add specific code to be triggered in each class when they touch the player
    }

    //respawns the shape
    public void reset(int position, int variation){
        if (position == 1) {
            xPos = spawnXL;
        } if (position == 2){
            xPos = spawnXR;
        }

        //variation is used so that the shape doesn't fall out the chute immediately after being crushed
        double r = Math.random()*(-variation) - (50);
        yPos = (int)r;

    }

    //if the direction of the conveyor belt is changed while the shape is falling and is still not visible, it will be moved to fall out of the corresponding chute for that belt direction
    public void spawnCorrectly(){
        if (yPos < 50) {
            if (position == 1) {
                xPos = spawnXL;
            }
            if (position == 2) {
                xPos = spawnXR;
            }
        }
    }

    //moves the object in the direction the belt is moving in if it is touching the belt
    public void checkTouchingConveyorBelt(){
        if (yPos >= ConveyorBelt.Y-(height/2) && xPos <= fallPosRight && xPos >= fallPosLeft){
            if (ConveyorBelt.direction == 1) {
                slideRight();
            } else if (ConveyorBelt.direction == 2){
                slideLeft();
            }
        }

        //if the shape is off the edge of the belt it will fall to be crushed
        if (xPos > fallPosRight || xPos < fallPosLeft){
            moveDown();
        }
    }

    //checks if the shape is touching the conveyor belt
    public void checkTouchingConveyorBelt(int height){
        //if touching bumper and not off the right edge
        if (yPos >= ConveyorBelt.Y-(height) && xPos <= fallPosRight && xPos >= fallPosLeft){
            if (ConveyorBelt.direction == 1) {
                slideRight();
                //System.out.println(name + " is touching conveyor belt and will slide right");
            } else if (ConveyorBelt.direction == 2){
                slideLeft();
            }
        }

        //if off the right edge
        if (xPos > fallPosRight || xPos < fallPosLeft){
            moveDown();
        }
    }

    //checks to see if the shape is above the conveyor belt
    public void checkAboveConveyorBelt(){
        //if above conveyor belt, the shape will fall down
        if (yPos <= ConveyorBelt.Y-(height/2)){
            moveDown();
        }
    }

    //checks to see if the bumper crush animation needs to be played
    //will respawn the shape at the end of the animation
    public boolean checkToCrush(Bumper bumperLeft, Bumper bumperRight){
        boolean r = false;
        //if the shape is in between the metal of the bumpers and bumper animation hasn't started
        if (yPos > 400 && xPos >= fallPosRight && !bumperRight.bumperTimer.isRunning()){
            bumperRight.bumperTimer.start();
            r = true;
        }
        if (yPos > 400 && xPos <= fallPosLeft && !bumperLeft.bumperTimer.isRunning()){
            bumperLeft.bumperTimer.start();
            r = true;
        }

        //by the time the shape falls to this the position, the bumper animation would've crushed it and now the shape will be respawned
        if (yPos > 425){
            reset(position, variation);
            return true;
        }
        return r;
    }
}
