package assignment2;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.Random;

public class SpikedPlatform extends Shape {

    //determines which size of the Spiked Platform will be used
    private int size;

    //all images it can display
    private BufferedImage frame1;
    private BufferedImage frame2;
    private BufferedImage frame3;
    private BufferedImage small1;
    private BufferedImage small2;
    private BufferedImage small3;
    private BufferedImage medium1;
    private BufferedImage medium2;
    private BufferedImage medium3;
    private BufferedImage large1;
    private BufferedImage large2;
    private BufferedImage large3;

    private boolean fill = false;
    private boolean active = false;
    private boolean despawning = false;

    //constructor
    public SpikedPlatform(Circle player){
        this.size = randomizeSize();
        this.name = "Spiked Platform - Size " + size + " has been created";
        this.color = new Color(120,0,255);
        this.player = player;
        this.allowance = 0;

        //loads all the sprites
        try {
            small1 = ImageIO.read(getClass().getResourceAsStream("Spiked Platform Small.png"));
            small2 = ImageIO.read(getClass().getResourceAsStream("Spiked Platform Small Blank.png"));
            small3 = ImageIO.read(getClass().getResourceAsStream("Spiked Platform Small Red.png"));
            medium1 = ImageIO.read(getClass().getResourceAsStream("Spiked Platform Medium.png"));
            medium2 = ImageIO.read(getClass().getResourceAsStream("Spiked Platform Medium Blank.png"));
            medium3 = ImageIO.read(getClass().getResourceAsStream("Spiked Platform Medium Red.png"));
            large1 = ImageIO.read(getClass().getResourceAsStream("Spiked Platform Large.png"));
            large2 = ImageIO.read(getClass().getResourceAsStream("Spiked Platform Large Blank.png"));
            large3 = ImageIO.read(getClass().getResourceAsStream("Spiked Platform Large Red.png"));

            //determines which sprites are going to be used, depending on the size this spiked platform is supposed to be
            switch (size){
                case 0:
                    frame1 = small1;
                    frame2 = small2;
                    frame3 = small3;
                    break;
                case 1:
                    frame1 = medium1;
                    frame2 = medium2;
                    frame3 = medium3;
                    break;
                case 2:
                    frame1 = large1;
                    frame2 = large2;
                    frame3 = large3;
                    break;
            }
            sprite = true;


        } catch (Exception e1){
            System.out.println("Spiked platform images could not be loaded");
            couldntBeLoaded();
        }

        //stores the width and height the spike platform will be
        if (frame1 != null){
            this.width = frame1.getWidth()/2;
            this.height = frame1.getHeight()/2;
            allowance = 1;
        } else {
            //width and are height are set to preset values
            switch (size){
                case 0:
                    this.width = 43/2;

                    break;
                case 1:
                    this.width = 84/2;

                    break;

                case 2:
                    this.width = 123/2;

                    break;
            }
            this.height = 47/2;
        }
        timer.start();
    }

    //sets the sprite variables to null
    public void couldntBeLoaded(){
        frame1 = null;
        frame2 = null;
        sprite = false;
    }

    //timer to continually check conditions
    Timer timer = new Timer(10, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (TreadHandler.lives > 0) {
                checkTouchingPlayer(player, allowance, height);
            } else {
                despawnTimer.stop();
                startBlink.stop();
                blinkTimer.stop();
                timer.stop();
                activateTimer.stop();
                spawningBlinkTimer.stop();
                spawnBackInTimer.stop();
            }

        }
    });

    //the spiked platform will despawn after 10 seconds of being active
    Timer despawnTimer = new Timer(10000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            despawn();
        }
    });

    //after 7 seconds of being active, the spiked platform will flash to signify it's about to despawn
    Timer startBlink = new Timer(7000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            despawning = true;
            blinkTimer.start();
            startBlink.stop();
        }
    });

    //every quarter of a second, the spiked platform will change state
    Timer blinkTimer = new Timer(250, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            blink();
        }
    });

    //returns a random number between 0-2, including 0 and 2
    public int randomizeSize(){
        return new Random().nextInt(3);
    }

    //spawns the spiked platform
    public void spawn(){
        randomizeSize();
        int rx = new Random().nextInt(750 - 300) + 300;
        moveTo(rx, 190);
        System.out.println("Spawned Spiked Platform Size " + size);

        despawnTimer.stop();
        startBlink.stop();
        blinkTimer.stop();

        despawnTimer.start();
        spawningBlinkTimer.start();
        startBlink.start();

        active = false;
        fill = true;
        despawning = false;

    }

    //'despawns' the spiked platform by moving it to a position where it can't be seen
    public void despawn(){

        moveTo(-100, -100);
        despawnTimer.stop();
        startBlink.stop();
        blinkTimer.stop();
        active = false;
        despawning = false;
        spawnBackInTimer.start();
        System.out.println("despawned spiked platform size " + size);
    }

    //every time this method is called, it will cycle whether it gets drawn as a fill or as an outline in the TreadHandler class
    public void blink(){
        if (fill){
            fill = false;
        } else {
            fill = true;
        }
    }

    //every quarter of a second, the spiked platform will change state
    Timer spawningBlinkTimer = new Timer(250, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            blink();
            activateTimer.start();
        }
    });

    //this will cause the spiked platform to kill the player when they touch it
    Timer activateTimer = new Timer(3000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            active = true;
            spawningBlinkTimer.stop();
            fill = true;
            activateTimer.stop();
        }
    });

    //respawns the spiked platform
    Timer spawnBackInTimer = new Timer(45000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("Spawning Spiked Platform Size " + size + " back in");
            spawn();
            spawnBackInTimer.stop();
        }
    });

    //will be called in the TreadHandler class
    @Override
    public void drawShape(Graphics g) {
        super.drawShape(g);

        //if the spites have been loaded, sprites will be drawn
        //else rectangles will be drawn
        if (sprite){
            //to create flicker effect
            if (fill){
                g.drawImage(frame1, getxPos(), getyPos(), width, height, null);
            } else {
                if (!despawning) {
                    g.drawImage(frame2, getxPos(), getyPos(), width, height, null);
                } else {
                    g.drawImage(frame3, getxPos(), getyPos(), width, height, null);
                }
            }
        } else {
            //to create a flicker effect
            if (fill) {
                g.setColor(color);
                g.fillRect(getxPos(), getyPos(), width, height);
            } else {
                if (!despawning) {
                    g.setColor(color);
                    g.drawRect(getxPos(), getyPos(), width, height);
                } else {
                    g.setColor(Color.RED);
                    g.fillRect(getxPos(), getyPos(), width, height);
                }
            }
        }



    }

    @Override
    public void checkTouchingPlayer(Circle player, int allowance, int height) {
        super.checkTouchingPlayer(player, allowance, height);

        //the generic hitbox in the super method doesn't really work for the spiked platform for some reason...
        //     middle xpos of player                this left corner      and   middle xpos of player     less than     this xpos right corner
        if (player.getxPos()+player.WIDTH/2 >= this.getxPos() + allowance && player.getxPos()-player.WIDTH/2 <= this.getxPos() + width - allowance && player.getyPos() >= this.getyPos() - height && player.getyPos() <= this.getyPos() + height - 10) {
            isTouchingPlayer();
        }
    }

    //kills player when the two objects collide
    @Override
    public void isTouchingPlayer() {

        if (active && !player.invincible) {
            player.die();
        }
    }
}
