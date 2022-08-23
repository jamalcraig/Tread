package assignment2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//spawns invincibility power up when touched
public class Pie extends Shape {

    //initializing variables
    boolean fall = false;
    final int WIDTH = 20;
    final int HEIGHT = 20;
    final int START_ANGLE = 315;
    final int ARC_LENGTH = 270;
    int y;

    Rectangle rectangle;


    //constructor
    public Pie(Circle player, Bumper bumperLeft, Bumper bumperRight, Rectangle rectangle){

        this.name = "Pie";
        this.player = player;
        this.bumperLeft = bumperLeft;
        this.bumperRight = bumperRight;
        moveTo(spawnXL, spawnYL - 50);
        y = this.getyPos()-7;
        this.width = WIDTH;
        this.height = HEIGHT;
        this.allowance = 20;
        this.rectangle = rectangle;
        this.color = Color.CYAN;

        timer.start();

    }

    //timer to continually check conditions
    Timer timer = new Timer(10, new ActionListener(){

        @Override
        public void actionPerformed(ActionEvent e) {
            if (TreadHandler.lives > 0) {

                //calls inherited methods from the Shape class that do what their names are
                checkTouchingConveyorBelt();
                checkTouchingPlayer(player, allowance, height);
                checkToCrush(bumperLeft, bumperRight);

                //the pie will fall down until it touches the conveyor belt
                if (getyPos() < 250 && fall) {
                    moveDown();
                }

                //because the ypos for an arc seems to be the center of it...
                y = getyPos() - 7;
                spawnCorrectly();
            } else {
                timer.stop();
            }
        }
    });


    @Override
    public void drawShape(Graphics g) {
        super.drawShape(g);
        g.fillArc(getxPos(), this.y, WIDTH, HEIGHT, START_ANGLE, ARC_LENGTH);
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
        //the hit box is a bit different to the other shapes
        if (player.getxPos() >= this.getxPos()-allowance && player.getxPos() <= this.getxPos()+allowance && player.getyPos() >= this.getyPos()-height && player.getyPos() <= this.getyPos()+height){
            isTouchingPlayer();
            System.out.println("touching");
        }
    }


    //the pie will be respawned and a rectangle will be spawned in its place
    @Override
    public void isTouchingPlayer() {
        super.isTouchingPlayer();
        reset(position, variation);
        rectangle.spawn();
        System.out.println("Hit invincibility");
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
