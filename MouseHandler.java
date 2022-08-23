package assignment2;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseHandler implements MouseListener {

    TreadHandler th;

    //constructor
    public MouseHandler(TreadHandler th){
        this.th = th;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        //stores the position of the cursor when the mouse is clicked
        int mX = e.getX();
        int mY = e.getY();

        //at the end of the game, if the mouse is clicked while the cursor is within the bounds of the artificial submit button, the name input will be submitted
        if(th.endGame && mX > th.textFieldButtonX && mX < th.textFieldButtonX + th.textFieldButtonW && mY > th.textFieldButtonY && mY < th.textFieldButtonY + th.textFieldButtonH){
            th.handleInput();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
