package Pacman.controller;


import Pacman.model.PacmanModel;

/**
 * Interface for a Pacman game controller.
 */
public interface IPacmanController {
    int[][] getMap();
    void nextFrame();
    void userInput(char b);

    void restartGame();
    PacmanModel getModel();
}
