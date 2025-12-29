package hero;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Arena {

    private final int width;
    private final int height;
    private final Hero hero;
    private final List<Wall> walls;
    private final List<Coin> coins;
    private final List<Monster> monsters;

    public Arena(int w, int h) {
        this.width = w;
        this.height = h;
        this.hero = new Hero(w / 2, h / 2);
        this.walls = createWalls();
        this.coins = createCoins();
        this.monsters = createMonsters();
    }

    private List<Wall> createWalls() {
        List<Wall> walls = new ArrayList<>();

        for (int c = 0; c < width; c++) {
            walls.add(new Wall(c, 0));
            walls.add(new Wall(c, height - 1));
        }

        for (int r = 1; r < height - 1; r++) {
            walls.add(new Wall(0, r));
            walls.add(new Wall(width - 1, r));
        }

        return walls;
    }

    private List<Coin> createCoins() {
        Random random = new Random();
        ArrayList<Coin> coins = new ArrayList<>();
        for (int i = 0; i < 5; i++)
            coins.add(new Coin(random.nextInt(width - 2) + 1, random.nextInt(height - 2) + 1));
        return coins;
    }

    private List<Monster> createMonsters() {
        Random random = new Random();
        ArrayList<Monster> monsters = new ArrayList<>();
        for (int i = 0; i < 3; i++)
            monsters.add(new Monster(random.nextInt(width - 2) + 1, random.nextInt(height - 2) + 1));
        return monsters;
    }

    public void draw(TextGraphics graphics) {

        graphics.setBackgroundColor(TextColor.Factory.fromString("#336699"));
        graphics.fillRectangle(new TerminalPosition(0, 0), new TerminalSize(width, height), ' ');

        for (Wall wall : walls)
            wall.draw(graphics);
        for (Coin coin : coins)
            coin.draw(graphics);
        for (Monster monster : monsters)
            monster.draw(graphics);
        hero.draw(graphics);
    }

    public boolean canHeroMove(Position position) {
        for (Wall wall : walls) {
            if (wall.getPosition().equals(position)) {
                return false;
            }
        }
        return true;

    }

    public void retrieveCoins() {
        if (coins.removeIf(coin -> hero.getPosition().equals(coin.getPosition()))) {
            hero.addScore(1);
        }
    }

    public void moveHero(Position position) {
        if (canHeroMove(position))
            hero.setPosition(position);
    }

    public void processKey(KeyStroke key) {

        if (key.getKeyType() == KeyType.ArrowUp)
            moveHero(hero.moveUp());
        if (key.getKeyType() == KeyType.ArrowDown)
            moveHero(hero.moveDown());
        if (key.getKeyType() == KeyType.ArrowLeft)
            moveHero(hero.moveLeft());
        if (key.getKeyType() == KeyType.ArrowRight)
            moveHero(hero.moveRight());

        verifyMonsterCollisions();
        moveMonsters();
        verifyMonsterCollisions();
        retrieveCoins();
    }

    public void moveMonsters() {
        for (Monster monster : monsters) {
            Position next = monster.move();
            if (canMonsterMove(next)) {
                monster.setPosition(next);
            }
        }
    }

    private boolean canMonsterMove(Position position) {
        if (position.getX() <= 0 || position.getX() >= width - 1)
            return false;
        if (position.getY() <= 0 || position.getY() >= height - 1)
            return false;
        for (Wall wall : walls) {
            if (wall.getPosition().equals(position))
                return false;
        }
        return true;
    }

    private void verifyMonsterCollisions() {
        for (Monster monster : monsters) {
            if (hero.getPosition().equals(monster.getPosition())) {
                System.out.println("The Hero has died!");
                System.exit(0);
            }
        }
    }

}
