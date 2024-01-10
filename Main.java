import Pacman.controller.PacmanController;
import Pacman.model.PacmanModel;
import Pacman.view.PacmanView;
import processing.core.PApplet;

/**
 * The main class for the Pacman game.
 * It sets up the model, view, and controller, and starts the Processing sketch.
 */
public class Main {
    public static void main(String[] args) {
        final int GAME_Width = 1000;
        final int GAME_HEIGHT = 1000 ;

        var model = new PacmanModel(GAME_Width, GAME_HEIGHT);
        var controller = new PacmanController();
        var view = new PacmanView();


        controller.setModel(model);
        controller.setView(view);
        view.setController(controller,GAME_Width,GAME_HEIGHT);
        PApplet.runSketch(new String[]{"PacmanView"}, view);

    }
}