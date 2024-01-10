package Pacman.view;
import Pacman.controller.IPacmanController;
import processing.core.PApplet;
import processing.core.PImage;
import Pacman.model.Pacman;
import Pacman.model.Ghost;

/**
 * This class represents the view component of the Pacman game.
 * It is responsible for rendering the game graphics and detecting user input.
 */
public class PacmanView extends PApplet implements IPacmanView{
    private IPacmanController controller;
    private int tileSize = 50;
    private PImage pacmanImageRight;
    private PImage pacmanImageLeft;
    private PImage pacmanImageUp;
    private PImage pacmanImageDown;
    private PImage ghostImage;
    private PImage wallImage;
    private PImage TitleScreen;
    private PImage GameOver;
    private PImage GameWon;
    private int backgroundColor;
    private int pelletColor;
    private int [][] map;

    /**
     * Sets the controller for this view.
     * @param controller The controller for this view.
     * @param width The width of the window.
     * @param height The height of the window.
     */
    public void setController(IPacmanController controller, int width, int height) {
        this.controller = controller;
        this.width = width;
        this.height = height;
        this.backgroundColor = color(0, 0, 0);
        this.pelletColor = color(255, 255, 255);
    }
    /**
     * Processing method for setting the size of the window.
     */
    public void settings() {
        pixelDensity(2);
   }
    /**
     * Processing method for setting up the window.
     */
    public void setup() {
        frameRate(30);
        pacmanImageRight  = loadImage("images/pacmanRight.png");
        pacmanImageLeft  = loadImage("images/pacmanLeft.png");
        pacmanImageUp  = loadImage("images/pacmanUp.png");
        pacmanImageDown  = loadImage("images/pacmanDown.png");

        ghostImage  = loadImage("./images/ghost.png");
        wallImage  = loadImage("./images/wall.png");

        TitleScreen = loadImage("./images/TitleScreen.png");
        GameOver = loadImage("./images/GameOver.png");
        GameWon = loadImage("./images/GameWon.png");

    }
    /**
     * Processing method for drawing the game.
     */
    public void draw() {
        background(0);
        controller.nextFrame();
    }
    /**
     * Draws the game on the screen.
     * @param pacman The Pacman object to draw.
     * @param ghosts The Ghost objects to draw.
     */
    public void drawGame(Pacman pacman, Ghost[] ghosts) {
        background(backgroundColor);
        drawGameBoard();
        drawPacman(pacman);
        for (Ghost ghost : ghosts) {
            drawGhost(ghost);
        }
        drawScore();
        drawLives();
    }
    /**
     * Draws a ghost on the screen.
     * @param ghost The ghost to draw.
     */
    public void drawGhost(Ghost ghost) {
        image(ghostImage, ghost.getGhost_X(), ghost.getGhost_Y(), tileSize, tileSize);

    }
    /**
     * Draws a wall on the screen.
     * @param x The x coordinate of the wall.
     * @param y The y coordinate of the wall.
     */
    public void drawWall(int x, int y) {
        image(wallImage, x, y, tileSize, tileSize);
    }
    /**
     * Draws Food on the screen.
     * @param x The x coordinate of the pellet.
     * @param y The y coordinate of the pellet.
     */
    public void drawFood(int x, int y) {
        fill(pelletColor); // Setzt die Farbe für die Nahrung
        ellipse(x, y, (float) tileSize / 4, (float) tileSize / 4); // Zeichnet einen Kreis für die Nahrung
    }
    /**
     * Draws the game board on the screen.
     */
    public void drawGameBoard(){
        map = controller.getMap();
        rectMode(CORNER);
        for(int i = 0; i < map.length; i++){
            for(int j = 0; j < map[i].length; j++){
                switch(map[i][j]){
                    case 0:
                        fill(backgroundColor);
                        fill(pelletColor);
                        drawFood(j * tileSize + tileSize / 2, i * tileSize + tileSize / 2);
                        break;
                    case 1:
//                        drawWall(j * tileSize, i * tileSize);
                        drawWall(j * tileSize, i * tileSize);
                        break;
                    case 2:
                        break
                        ;
                }
            }
        }

    }
    /**
     * Draws the score on the screen.
     */
    private void drawScore() {
        fill(252, 161, 3);
        textSize(30);
        textAlign(LEFT, BOTTOM); // Align text to the left and bottom
        text("Score: " + controller.getModel().getScore(), 10,  35);
    }
    /**
     * Draws the lives on the screen.
     */
    private void drawLives() {
        fill(252, 161, 3);
        textSize(30);
        textAlign(LEFT, BOTTOM); // Text links oben ausrichten
        text("Leben: " + controller.getModel().pacman.getLives(), 175,  35);
    }
    /**
     * Draws Pacman on the screen.
     * @param p The Pacman object to draw.
     */
    public void drawPacman( Pacman p) {
        if (p.isAlive()) {
            switch (p.getCurrentDirection()) {
                case UP:
                    image(pacmanImageUp, p.getPacmanX(), p.getPacmanY(), 0.8f*tileSize, 0.8f*tileSize);
                    break;
                case DOWN:
                    image(pacmanImageDown, p.getPacmanX(), p.getPacmanY(), 0.8f*tileSize, 0.8f*tileSize);
                    break;
                case LEFT:
                    image(pacmanImageLeft, p.getPacmanX(), p.getPacmanY(), 0.8f*tileSize, 0.8f*tileSize);
                    break;
                case RIGHT:
                    image(pacmanImageRight, p.getPacmanX(), p.getPacmanY(), 0.8f*tileSize, 0.8f*tileSize);
                    break;
            }
        }
    }
    /**
     * Draws the title screen on the screen.
     */
    @Override
    public void drawTitleScreen() {
        rectMode(CENTER);
        image(TitleScreen, 0, 0, width, height);
    }
    /**
     * Checks if the start button is pressed.
     * @return True if the start button is pressed, false otherwise.
     */
    @Override
    public boolean isStartButtonPressed() {
        return key == ' ' || key == 's';
    }
    /**
     * Checks if the restart button is pressed.
     * @return True if the restart button is pressed, false otherwise.
     */
    @Override
    public boolean isReStartButtonPressed() {
        return key == 'r' || key == 'R';
    }
    /**
     * Draws the game over screen on the screen.
     */
    @Override
    public void drawGameOver() {
        background(backgroundColor);
        image(GameOver, 0, 0, width, height);
    }
    /**
     * Draws the game won screen on the screen.
     */
    @Override
    public void drawGameWon() {
        background(backgroundColor);
        image(GameWon, 0, 0, width, height);
    }
    /**
     * Processing method for handling key presses.
     * Called when a key is pressed. Detects user input and notifies the controller.
     */
    public void keyPressed() {
        if (key == 'r' || key == 'R') {
            if (controller.getModel().isGameOver() || controller.getModel().isGameWon()) {
                controller.restartGame();
            }
        } else if (key == 'w' || key == 's' || key == 'a' || key == 'd') {
            controller.userInput(key);
        }
    }

}
