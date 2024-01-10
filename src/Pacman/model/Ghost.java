package Pacman.model;

import java.util.Random;

/**
 * This class is used to create the ghosts.
 * It contains the map of the game.
 * The map is a 2D array of integers.
 */
public class Ghost implements Runnable {
    private Pacman pacman;
    protected int ghost_X;
    protected int ghost_Y;
    private int speedGhost;
    private int[][] map;
    private boolean isActive = true;
    private Random random = new Random();


    /**
     * This method is used to create a ghost.
     * @param x the x coordinate of the ghost.
     * @param y the y coordinate of the ghost.
     * @param speed the speed of the ghost.
     * @param map the map of the game.
     * @param pacman the pacman of the game.
     */
    public Ghost(int x, int y, int speed, int[][] map,Pacman pacman) {
        this.ghost_X = x;
        this.ghost_Y= y;
        this.speedGhost = speed;
        this.map = map;
        this.pacman = pacman;
    }
    /**
     * This method contains the code that is executed when the ghost's thread starts.
     * It handles the ghost's movement and checks for interruptions.
     */
    @Override
    public void run() {
        while (isActive) {
            moveGhosts();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                isActive = false;
            }
        }
    }
    /**
     * This method is used to move the ghost.
     * It checks if the ghost can move to the new position.
     * If it can, it moves the ghost to the new position.
     */
    private void moveGhosts() {
        int randomDirection = random.nextInt(4);
        int newX = ghost_X;
        int newY = ghost_Y;

        switch (randomDirection) {
            case 0: newY -= speedGhost; break;
            case 1: newY += speedGhost; break;
            case 2: newX -= speedGhost; break;
            case 3: newX += speedGhost; break;
        }

        if (isValidPosition(newX, newY, map)) {
            ghost_X = newX;
            ghost_Y = newY;
        }
    }
    /**
     * This method is used to check if the ghost can move to the new position.
     * @param x the x coordinate of the ghost.
     * @param y the y coordinate of the ghost.
     * @param map the map of the game.
     * @return true if the ghost can move to the new position, false otherwise.
     */
    // Synchronized to ensure thread safety
    private synchronized boolean isValidPosition(int x, int y, int[][] map) {
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
     * This method is used to get the x coordinate of the ghost.
     * @return the x coordinate of the ghost.
     */
    public int getGhost_X() {
        return ghost_X;
    }
    /**
     * This method is used to get the y coordinate of the ghost.
     * @return the y coordinate of the ghost.
     */
    public int getGhost_Y() {
        return ghost_Y;
    }

}
