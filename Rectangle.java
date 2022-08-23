package assignment2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//player is invincible when touching this power up
public class Rectangle extends Shape {

    final int WIDTH = 100;
    final int HEIGHT = 5;
    boolean fill = true;

    //constructor
    public Rectangle(Circle player){
        this.name = "Rectangle";
        moveTo(-100, -100);
        this.player = player;
        this.width = WIDTH;
        this.height = HEIGHT;
        this.allowance = 20;
        this.color = Color.CYAN;

        timer.start();

    }


    //timer to continually check conditions
    Timer timer = new Timer(10, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (TreadHandler.lives > 0) {
                checkTouchingPlayer(player, allowance, 8000);
            } else {
                despawnTimer.stop();
                startBlink.stop();
                blinkTimer.stop();
                timer.stop();
            }
        }
    });

    //the rectangle will despawn after 10 seconds of being active
    Timer despawnTimer = new Timer(10000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            despawn();
        }
    });

    //after 7 seconds of being active, the rectangle will flash to signify it's about to despawn
    Timer startBlink = new Timer(7000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            blinkTimer.start();
            startBlink.stop();
        }
    });

    //every quarter of a second, the rectangle will change state
    Timer blinkTimer = new Timer(250, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            blink();
        }
    });


    @Override
    public void drawShape(Graphics g) {
        super.drawShape(g);

        //to create a flicker effect
        if (fill) {
            g.fillRect(getxPos(), getyPos(), WIDTH, HEIGHT);
        } else {
            g.drawRect(getxPos(), getyPos(), WIDTH, HEIGHT);
        }
    }

    //moves the rectangle to the position where the player touched the pie
    public void spawn(){
        this.setxPos(player.getxPos() - WIDTH/2);
        this.setyPos(ConveyorBelt.Y - HEIGHT);
        System.out.println("Spawned Rectangle");
        despawnTimer.stop();
        startBlink.stop();
        blinkTimer.stop();
        despawnTimer.start();
        startBlink.start();
        fill = true;
    }

    //'despawns' the rectangle by moving it to a position where it can't be seen
    public void despawn(){

        moveTo(-100, -100);
        despawnTimer.stop();
        startBlink.stop();
        blinkTimer.stop();
        System.out.println("despawned rectangle");
    }

    //every time this method is called, it will cycle whether it gets drawn as a fill or as an outline in the TreadHandler class
    public void blink(){
        if (fill){
            fill = false;
        } else {
            fill = true;
        }
    }

    //makes the player invincible when touching the rectangle
    @Override
    public void checkTouchingPlayer(Circle player, int allowance, int height) {
        //hit box is slightly different to the template defined in the Shape class
        if (player.getxPos() >= this.getxPos()-allowance && player.getxPos() <= this.getxPos()+WIDTH+allowance && player.getyPos() >= this.getyPos()-height && player.getyPos() <= this.getyPos()+height){
            isTouchingPlayer();
        } else {
            player.invincible = false;
        }
    }

    @Override
    public void isTouchingPlayer() {
        super.isTouchingPlayer();
        player.invincible = true;
    }
}
