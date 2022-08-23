package assignment2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class Tread {

    public static void main(String[] args) {

        //setting up frame
        JFrame jf = new JFrame("Tread - Jamal Craig");
        JPanel jPanel = new JPanel();
        jPanel.setBackground(Color.pink);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        final int FRAME_WIDTH = 1070;
        final int FRAME_HEIGHT = 650;

        //creating objects that will be put into the game
        Bumper bumper = new Bumper(0);
        Bumper bumper2 = new Bumper(1);
        Circle player = new Circle(500, 100, bumper, bumper2);
        ConveyorBelt conveyorBelt = new ConveyorBelt();
        TreadHandler treadHandler = new TreadHandler(player, conveyorBelt, bumper, bumper2);
        jf.add(treadHandler);

        //tracks which buttons are pressed, instead of using inconsistent key listeners
        addKeyBinding(treadHandler, KeyEvent.VK_RIGHT, false,"Move Right", (event) -> { treadHandler.playerMoveToRight = true;} );
        addKeyBinding(treadHandler, KeyEvent.VK_RIGHT, true,"Move Right Release", (event) -> { treadHandler.playerMoveToRight = false;} );
        addKeyBinding(treadHandler, KeyEvent.VK_LEFT, false,"Move Left", (event) -> { treadHandler.playerMoveToLeft = true;} );
        addKeyBinding(treadHandler, KeyEvent.VK_LEFT, true,"Move Left Release", (event) -> { treadHandler.playerMoveToLeft = false;} );
        addKeyBinding(treadHandler, KeyEvent.VK_UP, false,"Move Up", (event) -> { treadHandler.jump = true;} );
        addKeyBinding(treadHandler, KeyEvent.VK_UP, true,"Move Up Release", (event) -> { treadHandler.jump = false;} );
        addKeyBinding(treadHandler, KeyEvent.VK_SPACE, false,"Fall", (event) -> { if (treadHandler.player.getyPos() <= 100){player.fall = true;}} );

        //finalizing frame
        jf.setVisible(true);
        jf.setFocusable(false);
        jf.setSize(FRAME_WIDTH, FRAME_HEIGHT);
    }

    //adds keybinds to keys
    public static void addKeyBinding(JComponent component, int keyCode, boolean onKeyRelease, String id, ActionListener actionListener){
        InputMap inputMap = component.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = component.getActionMap();

        inputMap.put(KeyStroke.getKeyStroke(keyCode, 0, onKeyRelease), id);
        actionMap.put(id, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actionListener.actionPerformed(e);
            }
        });

    }
}
