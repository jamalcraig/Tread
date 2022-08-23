package assignment2;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    boolean enterPressed = false;
    TreadHandler th;

    //constructor
    //this class handles SOME of the keyboard inputs - well only the pressing of the enter key, because I used keybinding in the Tread class to handle the keys that move the player
    public KeyHandler(TreadHandler th){
        this.th = th;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        switch (e.getKeyCode()){
            //when the enter key is pressed, the name the player inputs will be submitted
            case KeyEvent.VK_ENTER:
                enterPressed = true;
                th.handleInput();
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()){
            case KeyEvent.VK_ENTER:
                enterPressed = false;
                break;
        }
    }
}
