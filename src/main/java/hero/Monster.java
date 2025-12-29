package hero;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

import java.util.Random;

public class Monster extends Element {

    public Monster(int x, int y) {
        super(x, y);
    }

    public Position move() {
        Random random = new Random();
        int direction = random.nextInt(4);
        switch (direction) {
            case 0:
                return new Position(position.getX(), position.getY() - 1);  
            case 1:
                return new Position(position.getX(), position.getY() + 1);  
            case 2:
                return new Position(position.getX() - 1, position.getY());  
            case 3:
                return new Position(position.getX() + 1, position.getY());  
            default:
                return position;
        }
    }

    @Override
    public void draw(TextGraphics graphics) {
        graphics.setForegroundColor(TextColor.Factory.fromString("#FF0000"));
        graphics.enableModifiers(SGR.BOLD);
        graphics.putString(new TerminalPosition(position.getX(), position.getY()), "M");
    }
}
