package blattneun.hausafugabe;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Minesweeper extends Application  {

    @Override
    public void start(Stage primaryStage) throws Exception{
        MineGame game = new MineGame();
        MineView view = new MineView(game);
        Scene scene = new Scene(view);
        primaryStage.setTitle("Minesweeper: "+Default.nrMines+" mines");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

}
