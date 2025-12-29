package hero;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

public class Coin extends Element {
    private boolean collected = false;

    public Coin(int x, int y) {
        super(x, y);
    }

    public boolean isCollected() {
        return collected;
    }

    public void setCollected(boolean collected) {
        this.collected = collected;
    }

    public void draw(TextGraphics graphics) {
        if (collected) {
            return;
        }
        graphics.setCharacter(position.getX(), position.getY(), TextCharacter.fromCharacter('o')[0]);
        graphics.setForegroundColor(TextColor.Factory.fromString("#ff00ea"));
        graphics.enableModifiers(SGR.BOLD);
        graphics.putString(new TerminalPosition(position.getX(), position.getY()), "o");

    }

}
