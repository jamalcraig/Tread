package assignment2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//power up that reverses the conveyor belt when touched
public class Triangle extends Shape{

    //initialization of variables
    final int WIDTH = 20;
    final int HEIGHT = 20;

    boolean fall = false;
    boolean reEnableScoring;

    int[] pointsX = {0,0,0};
    int[] pointsY = {0,0,0};

    //constructor
    public Triangle(Circle player, Bumper bumperLeft, Bumper bumperRight){

        this.name = "Triangle";
        moveTo(spawnXL, spawnYL);
        this.width = WIDTH;
        this.height = HEIGHT;
        this.allowance = 20;
        this.player = player;
        this.bumperLeft = bumperLeft;
        this.bumperRight = bumperRight;
        this.color = Color.PINK;
        reEnableScoring = false;

        setPoints();


        timer.start();

    }

    //timer to check conditions
    Timer timer = new Timer(10, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (TreadHandler.lives > 0) {
                setPoints();
                checkTouchingConveyorBelt(height);
                checkToCrush(bumperLeft, bumperRight);
                checkTouchingPlayer(player, allowance, height);

                //if above the conveyor belt, the triangle will fall
                if (getyPos() < 240 && fall) {
                    moveDown();
                }
                spawnCorrectly();
            } else {
                timer.stop();
            }
        }
    });



    //updates all the polygon points when called
    public void setPoints(){

        pointsX[0] = this.getxPos();
        pointsX[1] = getxPos() + width;
        pointsX[2] = getxPos() + width/2;

        pointsY[0] = getyPos()+height;
        pointsY[1] = getyPos()+height;
        pointsY[2] = getyPos();

    }

    @Override
    public void drawShape(Graphics g) {
        super.drawShape(g);
        g.fillPolygon(pointsX, pointsY, 3);
    }

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

    //reverses the direction the conveyor belt moves in and changes the spawn positions to the corresponding direction
    @Override
    public void isTouchingPlayer() {
        super.isTouchingPlayer();
        changePosition();
        reEnableScoring = true;
        reset(position, variation);
        System.out.println("Reverse conveyor belt");

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
        setPoints();
        fall = false;
    }

    @Override
    public boolean checkToCrush(Bumper bumperLeft, Bumper bumperRight) {
       return super.checkToCrush(bumperLeft, bumperRight);
    }

    //reverses the direction the conveyor belt moves in and changes the spawn positions to the corresponding direction
    public void changePosition(){
        if (position == 1){
            position = 2;
        } else if (position == 2){
            position = 1;
        }
        ConveyorBelt.direction = position;
    }
}
