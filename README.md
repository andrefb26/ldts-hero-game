# Hero: a Terminal Game in Java

A small turn-based arcade game that runs in the terminal, written in Java with the [Lanterna](https://github.com/mabe02/lanterna) text-GUI library and built with Gradle.

Developed by André Baptista ([@andrefb26](https://github.com/andrefb26)) for the **Software Design and Testing Laboratory** course (L.EIC014) of the Bachelor in Informatics and Computing Engineering at FEUP (University of Porto), 2025/26. It is lab assignment "Java and Gradle" (week 3).

## Gameplay

| Symbol | Element |
|--------|---------|
| `X` | the hero, controlled with the arrow keys |
| `M` | monsters, which move randomly every turn |
| `o` | coins: walking over one picks it up and increases the score |
| `#` | walls around the arena |

- The arena is 40×20 cells. Five coins and three monsters are placed at random positions at the start.
- Every key press is one turn: the hero moves (unless a wall is in the way), then every monster moves one cell in a random direction.
- If a monster reaches the hero's cell, the game ends with *"The Hero has died!"*.
- Press `q` to quit.

## Design

```
Application ──> Game ──> Arena ──> Hero, Monster, Coin, Wall
                 │                        │
          Lanterna Screen          all extend Element (abstract)
          (input + rendering)      └── Position (value object)
```

- **`Game`** owns the Lanterna terminal and screen and runs the game loop: draw, read a key, update.
- **`Arena`** holds the game state and rules: wall collisions, monster movement, coin pickup and monster–hero collisions.
- **`Element`** is an abstract base class with a `Position` and an abstract `draw()`. `Hero`, `Monster`, `Coin` and `Wall` each implement their own rendering (polymorphism).
- **`Position`** implements `equals`, so collisions are detected by comparing positions.

## Running

Requirements: JDK 21 (Gradle downloads the rest).

```bash
./gradlew run        # Linux / macOS
gradlew.bat run      # Windows
```

Lanterna opens its own terminal window when it doesn't detect a compatible terminal.

## Technologies

Java 21 · Gradle (application plugin, toolchains) · Lanterna 3.1.1
