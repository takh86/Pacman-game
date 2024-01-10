package Pacman.model;

import Pacman.controller.GameState;

/**
 * This class is used to create the model of the game.
 * It contains the methods that are used to create the game.
 */
public class PacmanModel implements IPacmanModel {
    private int score;
    private int lives;
    private boolean isGameOver;
    private boolean isGameWon;
    private GameBoard gameBoard;
    private GameState gameState;
    private char readKey;
    public Ghost[] ghosts;

    public Pacman pacman;

    /**
     * This method is used to create the model of the game.
     * @param width the width of the game.
     * @param height the height of the game.
     */
    public PacmanModel(int width, int height) {
        this.score = 0;
        this.lives = 3;
        this.isGameOver = false;
        this.gameBoard = new GameBoard();
        this.ghosts = new Ghost[3];
        this.pacman = new Pacman(50, 100);
        initializeGhosts();
    }
    /**
     * This method is used to initialize the ghosts.
     */
    @Override
    public void initializeGhosts() {
        for (int i = 0; i < ghosts.length; i++) {
            ghosts[i] = new Ghost(switch (i) {
                case 0 -> 900;
                case 1 -> 50;
                case 2 -> 450;
                default -> 0;
            }, switch (i) {
                case 0 -> 100;
                case 1 -> 900;
                case 2 -> 500;
                default -> 0;
            }, 50, gameBoard.getMap(), pacman);

            Thread ghostThread = new Thread(ghosts[i]);
            ghostThread.start();
        }
    }
    /**
     * Moves Pacman based on the current readKey value.
     * @param readKey The last key pressed by the user.
     */
    public void movePacman(char readKey) {
        int newX = pacman.pacman_X;
        int newY = pacman.pacman_Y;

        switch (readKey) {
            case 'w':
                newY -= pacman.speed;
                pacman.setCurrentDirection(Pacman.Direction.UP);
                break;
            case 'a':
                newX -= pacman.speed;
                pacman.setCurrentDirection(Pacman.Direction.LEFT);
                break;
            case 's':
                newY += pacman.speed;
                pacman.setCurrentDirection(Pacman.Direction.DOWN);
                break;
            case 'd':
                pacman.setCurrentDirection(Pacman.Direction.RIGHT);
                newX += pacman.speed;
                break;
        }
        // Update the position only if it's valid
        if (isValidPosition(newX, newY, gameBoard.getMap())) {
            pacman.pacman_X = newX;
            pacman.pacman_Y = newY;
        }
    }
    /**
     * Checks if the given position is valid within the game board.
     * @param x   The x-coordinate to check.
     * @param y   The y-coordinate to check.
     * @param map The current map of the game board.
     * @return true if the position is valid; false otherwise.
     */
    private boolean isValidPosition(int x, int y, int[][] map) {
        // Convert pixel coordinates to array indices
        int tileX = x / 50; // Assuming each tile is 50 pixels wide
        int tileY = y / 50; // Assuming each tile is 50 pixels high
        // Check if the tile coordinates are within the map boundaries
        if (tileX < 0 || tileX >= map[0].length || tileY < 0 || tileY >= map.length) {
            return false;
        }
        // Check if the tile is not a wall
        return map[tileY][tileX] != 1; // 1 stands for a wall
    }
    /**
     * Updates the game state, including moving Pacman, checking for collisions,
     * and determining if the game is won or lost.
     */
    public void updateGame () {
        if (!isGameOver && !isGameWon) {
            int pacmanX = pacman.getPacmanX();
            int pacmanY = pacman.getPacmanY();

            movePacman(readKey);
            int foodEaten = checkFoodCollision(pacman);
            updateScore(foodEaten);
            for (Ghost ghost : ghosts) {
                if (pacman.collidesWith(ghost)) {
                    handleGameOver();
                    break;
                }
            }
            if (isAllFoodEaten()) {
                isGameWon = true;
            }
            if (lives <= 0) {
                handleGameOver();
            }
        }
    }
    /**
     * Checks for collision between Pacman and food items on the game board.
     * @param pacman The Pacman object.
     * @return The number of food items eaten.
     */
    @Override
    public int checkFoodCollision(Pacman pacman) {
        // Convert pixel coordinates to array indices
        int tileX = pacman.getPacmanX() / 50;
        int tileY = pacman.getPacmanY() / 50;
        int[][] map = gameBoard.getMap();
        int tileValue = map[tileY][tileX];
        if (tileValue == 0) {
            // Food found, decrement the food count and update the map
            map[tileY][tileX] = 2; // Update the tile to empty
            updateScore(1);
            // Update your food count variable or logic here
            return 1; // Return the number of food items eaten (1 in this case)
        }
        return 0; // No food eaten
    }
    /**
     * Updates the score based on the number of food items eaten.
     * @param foodEaten The number of food items Pacman has eaten.
     */
    @Override
    public void updateScore ( int foodEaten){
        // Annahme: Jedes gegessene Nahrungsmittel gibt 10 Punkte
        int pointsEarned = foodEaten * 10;
        score += pointsEarned;
    }
    /**
     * Checks if all food items have been eaten.
     * @return true if all food items have been eaten; false otherwise.
     */
    public boolean isAllFoodEaten() {
        int[][] map = gameBoard.getMap(); // Get the current map
        for (int[] row : map) {
            for (int tileValue : row) {
                if (tileValue == 0) {
                    return false; // Found remaining food
                }
            }
        }
        return true; // No remaining food
    }
    /**
     * Checks if the game has been won.
     * @return true if the game is won; false otherwise.
     */
    public boolean isGameWon () {
        return isGameWon;
    }
    /**
     * Checks if the game is over.
     * @return true if the game is over; false otherwise.
     */
    public boolean isGameOver () {
        return isGameOver;
    }
    /**
     * Retrieves the current game board map.
     * @return A 2D array representing the game board.
     */
    public int[][] getMap () {
        return gameBoard.getMap();
    }
    /**
     * Resets the game to its initial state.
     */
    public void resetGame () {
        this.score = 0;
        this.lives = 3;
        this.isGameOver = false;
        this.isGameWon = false;
        this.gameBoard = new GameBoard();
        this.ghosts = new Ghost[3];
        this.pacman = new Pacman(50, 100);
        initializeGhosts();

    }
    /**
     * Handles the event of a game over, such as updating lives and setting the game state.
     */
    private void handleGameOver () {
        lives--;
        if (lives <= 0) {
            isGameOver = true;
            gameState = GameState.GAME_OVER;

        } else {
            gameState = GameState.PLAYING;
        }
    }
    /**
     * Retrieves the current score.
     * @return The current score.
     */
    public int getScore() {
        return score;
    }
    /**
     * Retrieves the Pacman object.
     * @return The Pacman object.
     */
    public Pacman getPacman() {
        return pacman;
    }
    /**
     * Retrieves the array of Ghost objects.
     * @return An array of Ghost objects.
     */
    public Ghost[] getGhosts() {
        return ghosts;
    }

}


