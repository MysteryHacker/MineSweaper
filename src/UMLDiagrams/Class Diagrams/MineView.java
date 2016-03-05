package blattneun.hausafugabe;

import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Alert.*;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.GridPane;

import java.util.Optional;

public class MineView extends GridPane {

    public MineView(MineGame game) {
        super();
        // Board
        GridPane boardView = new GridPane();
        for (MineCell cell : game.getBoard().getCells()) {
            Position pos = cell.getPosition();
            Button btn = new Button();
            btn.setMinWidth(Default.cellWidth);
            btn.setMinHeight(Default.cellHeight);
            btn.setMaxWidth(Double.MAX_VALUE);
            btn.setMaxHeight(Double.MAX_VALUE);
            btn.setOnMouseClicked(event -> game.revealCell(event, pos));
            cell.addObserver((o,arg) -> editButton(btn, cell));

            boardView.add(btn, pos.getX(), pos.getY());
        }

        game.addObserver((o,arg) -> {
            if(!game.isGameOngoing())
            gameOver(boardView, game,(System.currentTimeMillis() - game.getTime())/1000 );
            if(game.isResetFlag()){
                for(Node button: boardView.getChildren()){
                    button.setDisable(false);
                    button.setStyle(new Button().getStyle());
                }

                game.setResetFlag(false);
            }
        });
        this.add(boardView,0,0);
    }

    private void editButton(Button btn, MineCell cell) {
        if(cell.getValueToShow().equals("O")){
            btn.setDisable(true);
            //btn.setStyle("-fx-background-color: lightgray; -fx-border-color: darkgray; -fx-border-width: 1px");
        } else {
            btn.setText(cell.getValueToShow());
        }
    }

    private void gameOver(GridPane boardView, MineGame game, long delta) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        if(game.hasWon()){
            alert.setTitle("Congratulations! It took you "+delta+" seconds to win");
            alert.setHeaderText("Do you want to play again?");
        }else {
            for(MineCell cell: game.getBoard().getCells()){
                if(cell.getValue() == Default.mineValue) {
                    boardView.getChildren().get(game.getBoard().getCellIndex(cell)).setStyle("-fx-background-color: red;");
                    }
            }
            alert.setTitle("You stepped on a mine :(");
            alert.setHeaderText("Do you want to play again?");
        }
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            game.reset();
        } else {
            System.exit(0);
        }
    }
}
