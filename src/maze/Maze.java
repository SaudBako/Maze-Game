package maze;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class Maze extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        stage.setResizable(false);
        stage.setWidth(700);
        stage.setHeight(500);
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLDocument.fxml"));
        Parent root = loader.load();
        
        Scene scene = new Scene(root);
        
        // Saves an instance of the Controller
        final Controller controller = loader.getController();
        
        stage.setScene(scene);
        stage.show();
        
        // Handles key inputs
        scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            switch (key.getCode()) {
                case LEFT:
                case A:
                    Game.move(-1, 0);   break;
                case RIGHT:
                case D:
                    Game.move(1, 0);    break;
                case UP:
                    Game.move(0, -1);    break;
                case DOWN:
                    Game.move(0, 1);   break;
            }
            
            controller.drawBoard();
            
            if (Game.won) {
                Game.loadNextLevel();
                controller.changeLevel();
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
