package assignment2;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.TreeMap;

public class TreadHandler extends JPanel implements ActionListener {
// this class handles painting all the components


    @Override
    public void setBackground(Color bg) {
        super.setBackground(Color.WHITE);
    }

    //initialising objects
    Circle player;
    ConveyorBelt conveyorBelt;
    Bumper bumperLeft;
    Bumper bumperRight;
    Chute chuteLeft;
    Chute chuteRight;
    ArrayList<Hurdle> hurdleList = new ArrayList<>();
    Square square;
    Triangle triangle;
    Pie pie;
    Rectangle rectangle;
    ArrayList<Shape> shapes = new ArrayList<>();

    String input;

    static int score = 0;
    static int lives = 3;

    //initializing text that will appear in the game
    static JLabel scoreText = new JLabel("Score: 0");
    static JLabel livesText = new JLabel("Lives: " + lives);
    static JLabel gameOverText = new JLabel("Game Over");
    static JLabel enterNameText = new JLabel("Enter your name");
    static JTextField textField = new JTextField(10);
    JLabel instructionsText = new JLabel("<html>Instructions: <br><br> Move Left: Left Arrow Key <br> Move Right: Right Arrow Key <br> Jump: Up Arrow Key <br> Fall: Spacebar <br><br> Square: Extra Life <br> Triangle: Reverses Treadmill <br> Pie: Releases Invincibility Rectangle");

    //co-ordinates for a text field
    int textFieldX = 500;
    int textFieldY = 20;
    int textFieldW = 150;
    int textFieldH = 20;

    int textFieldButtonX = textFieldX + textFieldW + 10;
    int textFieldButtonY = textFieldY;
    int textFieldButtonW = 70;
    int textFieldButtonH = textFieldH;

    //booleans that are used to move the player
    boolean playerMoveToRight;
    boolean playerMoveToLeft;
    boolean jump;
    public boolean fall;

    boolean endGame = false;
    boolean outputted = false;


    //the class' constructor
    public TreadHandler(Circle player, ConveyorBelt conveyorBelt, Bumper bumperLeft, Bumper bumperRight){
        this.player = player;
        this.conveyorBelt = conveyorBelt;
        this.bumperLeft = bumperLeft;
        this.bumperRight = bumperRight;


        //instantiating objects
        square = new Square(player, bumperLeft, bumperRight);
        triangle = new Triangle(player, bumperLeft, bumperRight);
        rectangle = new Rectangle(player);
        pie = new Pie(player, bumperLeft, bumperRight, rectangle);
        chuteLeft = new Chute(0);
        chuteRight = new Chute(1);

        //adds all the shapes to a collection of shapes
        shapes.add(this.conveyorBelt);
        shapes.add(square);
        shapes.add(triangle);
        shapes.add(rectangle);
        shapes.add(pie);
        shapes.add(this.player);
        shapes.add(bumperLeft);
        shapes.add(bumperRight);
        shapes.add(chuteLeft);
        shapes.add(chuteRight);

        SpikedPlatform sp = new SpikedPlatform(player);
        sp.spawn();
        shapes.add(sp);


        //starts timers
        paintTimer.start();
        addHurdle.start();
        dropSquare.start();
        dropTriangle.start();
        dropPie.start();
        spawnSpikedPlatform.start();


        //sets up the text
        setLayout(null);

        Font f = new Font("arial", Font.PLAIN, 30);
        Font f2 = new Font("arial", Font.PLAIN, 50);
        Font f3 = new Font("arial", Font.PLAIN, 20);
        Font f4 = new Font("arial", Font.PLAIN, 15);

        scoreText.setBounds(350,325,1000,100);
        scoreText.setFont(f);
        add(scoreText);

        livesText.setBounds(600,325,1000,100);
        livesText.setFont(f);
        add(livesText);

        gameOverText.setBounds(400, 100, 1000, 100);
        gameOverText.setFont(f2);

        textField.setBounds(textFieldX, textFieldY,textFieldW,textFieldH);

        enterNameText.setBounds(textFieldX - 150, textFieldY, 300, textFieldH);
        enterNameText.setFont(f3);

        instructionsText.setBounds(420, 360, 1000, 300);
        instructionsText.setFont(f4);
        add(instructionsText);

        //adds mouse and key listeners
        addMouseListener(new MouseHandler(this));
        textField.addKeyListener(new KeyHandler(this));
        setFocusable(true);
    }



    //timers

    Timer paintTimer = new Timer(10, this);

    //adds 3 hurdles at the start of the game
    Timer addHurdle = new Timer(1000, new ActionListener() {
        int i = 0;
        @Override
        public void actionPerformed(ActionEvent e) {
            if(i > 2){
                addHurdle.stop();
                addLateHurdle.start();

            }
            hurdleList.add(new Hurdle(230, 50, bumperLeft, bumperRight, player));
            i++;
        }
    });

    //adds hurdles every 10 seconds after the game has started
    Timer addLateHurdle = new Timer(10000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            Hurdle.variationStatic += 200;
            hurdleList.add(new Hurdle(230, 50, bumperLeft, bumperRight, player));
            int last = hurdleList.size()-1;
            hurdleList.get(last).reset(Hurdle.position, 1);
            System.out.println("Added new hurdle");
            System.out.println("Variation: " + Hurdle.variationStatic);
        }
    });

    //drops the square
    Timer dropSquare = new Timer(15200, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            square.fall = true;
            System.out.println("Square falling");
            dropSquare.stop();
        }
    });

    //drops the triangle
    Timer dropTriangle = new Timer(13000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            triangle.fall = true;
            System.out.println("Triangle falling");
            dropTriangle.stop();
        }
    });

    //drops the pie
    Timer dropPie = new Timer(25000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            pie.fall = true;
            System.out.println("Pie falling");
            dropPie.stop();
        }
    });

    Timer spawnSpikedPlatform = new Timer(30000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

            SpikedPlatform sp = new SpikedPlatform(player);
            sp.spawn();
            shapes.add(sp);
        }
    });


            //paints all the components in the game

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        //draws every hurdle in the list
        for (Hurdle h : hurdleList){
            h.drawShape(g);
        }

        //draws the rest of the shapes/images
        for (Shape s : shapes){
            s.drawShape(g);
        }

        //draws rectangle that I will use as an artificial button ( so that i can satisfy using a mouse listener :) )
        if (endGame && !outputted){
            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(textFieldButtonX, textFieldButtonY, textFieldButtonW, textFieldButtonH);
        }


    }


    //every time the paint timer ticks
    @Override
    public void actionPerformed(ActionEvent e) {

        if (lives > 0) {

            //moves the player
            if (playerMoveToRight && player.canMove) {
                player.moveRight();
            }
            if (playerMoveToLeft && player.canMove) {
                player.moveLeft();
            }

            if (jump && player.grounded && player.getyPos() + 10 >= 230 && player.canMove) {
                player.grounded = false;
                player.jump();
            }

            //if the spacebar has been pressed to make the player fall back down to the conveyor belt
            if (player.fall) {
                if (player.getyPos() <= 240) {
                    player.place();
                } else {
                    player.setyPos(240);
                    player.fall = false;

                    //enables the hurdles to award a point again if they have already been jumped over
                    for (int i = 0; i < hurdleList.size(); i++) {
                        hurdleList.get(i).scored = false;
                    }
                }
            }


            player.checkTouchingConveyorBelt(player.HEIGHT);

            //if the player isn't touching the conveyor belt and is about to fall to their death
            if (player.getyPos() >= 240 && (player.getxPos() >= player.fallPosRight || player.getxPos() <= player.fallPosLeft)){
                player.fallToDeath();
            }


            //if the objects aren't touching the conveyor belt their timer will be started so that they can respawn
            if (!square.fall) {
                dropSquare.start();
            }

            if (!triangle.fall) {
                dropTriangle.start();
            }

            if (!pie.fall) {
                dropPie.start();
            }


            //if the player dies or the conveyor belt gets reversed, the player will now be able to gain points from jumping over hurdles they have already jumped over
            if (triangle.reEnableScoring) {
                for (int i = 0; i < hurdleList.size(); i++) {
                    hurdleList.get(i).scored = false;
                }
                triangle.reEnableScoring = false;
            }



        } else {
            //stops all timers so that the game ends
            endGame = true;
            addHurdle.stop();
            addLateHurdle.stop();
            dropSquare.stop();
            dropTriangle.stop();
            dropPie.stop();
            spawnSpikedPlatform.stop();
            player.moveTo(-100,-100);
            paintTimer.stop();
            endGame();
        }


        repaint();
    }

    public void endGame(){
        System.out.println("end game");
        add(gameOverText);
        System.out.println("added game over");
        repaint();

        //allows player to input their name
        add(textField);
        add(enterNameText);

    }

    public void outputScores(String input){

        //adds name and score to the text file
        ScoreHandler.outputScore(input);
        System.out.println("outputed score");

        //gets sorted scores from text file
        TreeMap<String, Integer> sortedScores = new TreeMap<>();
        sortedScores.putAll(ScoreHandler.sortScores());

        //had to sort them again because for some reason it becomes unsorted when put into another map
        CompareMap compareMap = new CompareMap(sortedScores);
        TreeMap<String, Integer> sortedScoress = new TreeMap<>(compareMap);
        sortedScoress.putAll(sortedScores);

        //initializing variables that will be used to iterate through the map
        Iterator<String> it = sortedScoress.keySet().iterator();
        Integer mapScore;
        String mapName;
        int i = 0;
        int rank = 1;
        int y = 380;

        //adds high score text
        JLabel endScoreText1 = new JLabel("High Scores:");
        endScoreText1.setBounds(460, y-20,1000,100);
        add(endScoreText1);

        //displays the top 5 scores
        while (it.hasNext() && i < 5){

            mapName = it.next();
            mapScore = sortedScores.get(mapName);

            System.out.println(rank + ") " + mapScore + " " + mapName);
            JLabel endScoreText = new JLabel(rank + ") " + mapScore + " " + mapName);
            endScoreText.setBounds(460, y,1000,100);
            add(endScoreText);
            y+=20;
            rank++;
            i++;
        }
        outputted = true;
        //removes the text field so the user can no longer enter and submit their game
        remove(textField);
        remove(enterNameText);
        remove(instructionsText);
        repaint();
    }

    //gets the text that was put into the text field then submits it to be handled
    public void handleInput(){
        System.out.println("Handling input");
        input = textField.getText();
        if (!input.isEmpty()) {
            outputScores(input);
        }
    }



}
