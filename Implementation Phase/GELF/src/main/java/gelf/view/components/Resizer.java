package gelf.view.components;

import java.awt.Component;

/**
 * Resizer
 */
public class Resizer {
    private ResizeMode top;
    private ResizeMode left;
    private ResizeMode bottom;
    private ResizeMode right;
    
    public Resizer(ResizeMode t, ResizeMode l, ResizeMode b, ResizeMode r) {
        top = t;
        left = l;
        bottom = b;
        right = r;
    }

    //resize c to fit the new dimensions of the parent component specified by width, height, newWidth, newHeight
    public void resize(Component c, int width, int height, int newWidth, int newHeight){
        int cTop = c.getY();
        int cLeft = c.getX();
        int cBottom = cTop + c.getWidth();
        int cRight = cLeft + c.getHeight();

        switch(top){
            case ABSOLUTE_TOP_LEFT:
            break;
            case ABSOLUTE_BOTTOM_RIGHT:
            cTop -= height - newHeight;
            break;
            default:
            break;
        }
        switch(bottom){
            case ABSOLUTE_TOP_LEFT:
            break;
            case ABSOLUTE_BOTTOM_RIGHT:
            cBottom -= height - newHeight;
            break;
            default:
            break;
        }
        switch(left){
            case ABSOLUTE_TOP_LEFT:
            break;
            case ABSOLUTE_BOTTOM_RIGHT:
            cLeft -= width - newWidth;
            break;
            default:
            break;
        }
        switch(right){
            case ABSOLUTE_TOP_LEFT:
            break;
            case ABSOLUTE_BOTTOM_RIGHT:
            cRight -= width - newWidth;
            break;
            default:
            break;
        }

        c.setBounds(cLeft, cRight, cRight - cLeft, cBottom - cTop);
    }
}