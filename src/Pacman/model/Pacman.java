package Pacman.model;

import Pacman.controller.GameState;

/**
 * This class represents the Pacman character in the game.
 * It manages Pacman position, movement, and collision logic.
 */
public class Pacman {
    protected int pacman_X;
    protected int pacman_Y;
    protected int speed = 50;
    private int lives = 3;
    private Direction currentDirection = Direction.RIGHT;
    private GameState gameState;

    /**
     * Constructor to initialize Pacman position.
     * @param x The initial X position of Pacman.
     * @param y The initial Y position of Pacman.
     */
    public Pacman(int x, int y) {
        this.pacman_X = x;
        this.pacman_Y = y;
    }
    /**
     * Checks for collisions between Pacman and a ghost.
     * @param ghost The ghost to check for collision with.
     * @return true if Pacman collides with the ghost, false otherwise.
     */
    public boolean collidesWith(Ghost ghost) {
        int pacmanX = getPacmanX();
        int pacmanY = getPacmanY();
        int ghostX = ghost.getGhost_X();
        int ghostY = ghost.getGhost_Y();
        // Calculate the distance between Pac-Man and the ghost(Pythagoras)
        double distance = Math.sqrt(Math.pow(pacmanX - ghostX, 2) + Math.pow(pacmanY - ghostY, 2));
        // Define a collision threshold (you can adjust this value)
        double collisionThreshold = 20.0; // Adjust as needed
        if (distance < collisionThreshold) {
            // Pac-Man collided with the ghost
            handleCollisionWithGhost();
        }
        return distance < collisionThreshold;
    }
    /**
     * Handles the logic when Pacman collides with a ghost.
     * Decreases lives and sets the game state to GAME_OVER if lives are depleted.
     */
    private void handleCollisionWithGhost() {
        // Verringern Sie die Anzahl der Leben
        lives--;
        // Überprüfen Sie, ob noch Leben übrig sind
        if (lives <= 0) {
            // Spiel ist vorbei, setzen Sie den entsprechenden Zustand
            boolean isGameOver = true;
            gameState = GameState.GAME_OVER;
        } else {
            // Setzen Sie Pac-Man und die Geister auf ihre Startpositionen zurück
            resetPosition();
            // Implementieren Sie hier die Logik, um die Geister zurückzusetzen
        }
    }
    /**
     * Resets Pacman position to the starting position.
     */
    private void resetPosition() {
        // Setzen Sie hier die Position von Pac-Man zurück
        this.pacman_X = 50; // Setzen Sie start_X auf die Startposition von Pac-Man
        this.pacman_Y = 100; // Setzen Sie start_Y auf die Startposition von Pac-Man
    }
    public Direction getCurrentDirection() {
        return currentDirection;
    }
    public void setCurrentDirection(Direction currentDirection) {
        this.currentDirection = currentDirection;
    }

    /**
     * this method is used to check if Pacman is alive.
     * @return true if Pacman is alive, false otherwise.
     */
    public boolean isAlive () {
        return true;
    }
    public int getPacmanX() {
        return pacman_X;
    }

    public int getPacmanY() {
        return pacman_Y;
    }

    public int getLives() {
        return lives;
    }
    /**
     * Enum representing possible movement directions.
     */
    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

}
