package Pacman.view;

import Pacman.model.Ghost;
import Pacman.model.Pacman;

/**
 * Interface for the view of the Pacman game.
 * The view is responsible for drawing the game on the screen.
 * The view is also responsible for getting input from the user.
 */
public interface IPacmanView {
    void drawGame(Pacman pacman, Ghost[] ghosts);
    void drawTitleScreen();
    boolean isStartButtonPressed();
    boolean isReStartButtonPressed();
    void drawGameOver();
    void drawGameWon();

}
