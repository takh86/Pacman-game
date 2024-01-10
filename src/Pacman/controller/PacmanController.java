package Pacman.controller;


import Pacman.model.PacmanModel;
import Pacman.view.IPacmanView;

/**
 * PacmanController class
 * This class is the controller of the game
 * It is responsible for the communication between the model and the view
 * It is also responsible for the game logic
 * It implements the IPacmanController interface
 */
public class PacmanController implements IPacmanController{
    private PacmanModel model;
    private IPacmanView view;
    private GameState state;

    /**
     * PacmanController constructor
     * It initializes the state of the game to TitleScreen
     */
    public PacmanController() {
        this.state = GameState.TitleScreen;
    }
    /**
     * Sets the model for this controller.
     * @param model The Pacman model to be used.
     */
    public void setModel(PacmanModel model) {
        this.model = model;
    }
    /**
     * Sets the view for this controller.
     * @param view The Pacman view to be used.
     */
    public void setView(IPacmanView view) {
        this.view = view;
    }
    /**
     * Executes actions based on the current game state in each frame.
     */
    @Override
    public void nextFrame() {
        switch (state) {
            case TitleScreen-> {
                view.drawTitleScreen();
                if (view.isStartButtonPressed()) {
                    state = GameState.PLAYING;
                }
            }
            case PLAYING -> {
                char c = ' ';
                model.movePacman(c);
                model.updateGame();
                view.drawGame(model.getPacman(), model.getGhosts());
                if (model.isGameOver()) {
                    state = GameState.GAME_OVER;
                } else if (model.isGameWon()) {
                    state = GameState.GAME_WON;
                }
            }
            case GAME_OVER -> {
                view.drawGameOver();
                if (view.isReStartButtonPressed()) {
                    restartGame();
                }
            }
            case GAME_WON-> {
                view.drawGameWon();
                if (view.isReStartButtonPressed()) {
                    restartGame();
                }
            }
        }
    }
    /**
     * Returns the current generated map.
     * @return The current generated map.
     */
    @Override
    public int[][] getMap() {
        if (model != null) {
            return model.getMap();
        }
        return new int[0][];
    }
    /**
     * Handles user input for controlling the game state and player character (Pacman).
     * Depending on the current game state, this method responds to specific key presses
     * and updates the game accordingly.
     * @param key The character representing the key pressed by the user.
     */
    @Override
    public void userInput(char key) {
        switch (state) {
            case TitleScreen -> {
                if (view.isStartButtonPressed()) {
                    state = GameState.PLAYING;
                }
            }
            case GAME_WON , GAME_OVER -> {
                if (view.isReStartButtonPressed()) {
                    state = GameState.PLAYING;
                }
            }
            case PLAYING -> {
                if (key == 'w') {
                    model.movePacman('w');
                }
                if (key == 's') {
                    model.movePacman('s');
                }
                if (key == 'a') {
                    model.movePacman('a');
                }
                if (key == 'd') {
                    model.movePacman('d');
                }
            }

        }

    }
    /**
     * Restarts the game by resetting the game state and the player character (Pacman).
     */
    @Override
    public void restartGame() {
        if (model != null) {
            state = GameState.PLAYING;
            model.resetGame();
            view.drawGame(model.getPacman(), model.getGhosts());
        }
    }
    /**
     * Returns the current model.
     * @return The current model.
     */
    public PacmanModel getModel() {
        return model;
    }


}



