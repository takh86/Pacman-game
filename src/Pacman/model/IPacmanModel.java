package Pacman.model;

/**
 * This interface is used to create the model of the game.
 * It contains the methods that are used to create the game.
 */
public interface IPacmanModel {
    public void initializeGhosts();
    public void movePacman(char readKey);
    public void updateGame();
    int checkFoodCollision(Pacman pacman);
    void updateScore(int foodEaten);
}
