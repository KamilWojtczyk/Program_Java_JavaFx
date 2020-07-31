package sample;

import javafx.application.Application;
import javafx.stage.Stage;

import java.awt.event.MouseEvent;
import java.util.List;

/**
 * Klasa odpowiadająca za uruchomienie aplikacji - tworzy instancję GUI oraz obiekt kontrolera odpowiadającego za obsługę interfejsu graficznego
 */
public class Main extends Application {


    @Override
    public void start(Stage primaryStage) {
        Controller controller = Controller.getInstance();
        GUICreator guiCreator = GUICreator.getInstance();
        guiCreator.createScene(primaryStage);
        guiCreator.setControls(controller);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
