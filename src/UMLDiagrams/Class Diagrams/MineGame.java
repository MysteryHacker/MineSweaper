
package blattneun.hausafugabe;

import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import java.util.Observable;

public class MineGame extends Observable {

    public static final int height = Default.HEIGHT;
    public static final int width  = Default.WIDTH;

    private long time;

    private MineBoard board;

    private boolean won = false;
    private boolean gameOngoing = false;
    private boolean resetFlag = false;

    public MineGame() {
        this.board = new MineBoard();
        this.gameOngoing = true;
        time = System.currentTimeMillis();
        setChanged();
        notifyObservers();
    }

    public boolean isResetFlag() {
        return resetFlag;
    }

    public void setResetFlag(boolean resetFlag) {
        this.resetFlag = resetFlag;
        setChanged();
        notifyObservers();
    }

    public boolean hasWon() {
        return won;
    }

    public void setWon(boolean won) {
        this.won = won;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public void reset() {
        this.setGameOngoing(true);
        this.getBoard().reset();
        this.setResetFlag(true);
        this.setTime(System.currentTimeMillis());
    }


    public void revealCell(MouseEvent event, Position pos) {
        // test
//        if(event.getClickCount() == 2){
//            setWon(true);
//            setGameOngoing(false);
//            return;
//        }

        MineCell cell = this.getBoard().getCell(pos);

        if(!cell.isOpened()) {
            cell.setOpened(true);
            if (event.getButton() == MouseButton.SECONDARY) {
                cell.setOpened(false);
                switch (cell.getValueToShow()) {
                    case "":
                        cell.setValueToShow("P");
                        break;
                    case "P":
                        cell.setValueToShow("");
                }
                return;
            }

            if (cell.getValue() == Default.mineValue) {
                this.setWon(false);
                this.setGameOngoing(false);
            } else {
                if (cell.getValue() == 0) {
                    cell.setValueToShow("O");
                    for (MineCell mineCell : this.getBoard().getNeighbors(pos.getX(), pos.getY())) {
                        if(!mineCell.getValueToShow().equals("P"))
                        revealCell(event, mineCell.getPosition());
                    }
                } else { // if value > 0
                    cell.setValueToShow(String.valueOf(cell.getValue()));
                }
                if(this.getBoard().isWinningMove()){
                    this.setWon(true);
                    this.setGameOngoing(false);
                }
            }
        }

    }


    public static int getHeight() {
        return height;
    }

    public static int getWidth() {
        return width;
    }

    public MineBoard getBoard() {
        return board;
    }

    public void setBoard(MineBoard board) {
        this.board = board;
    }

    public boolean isGameOngoing() {
        return gameOngoing;
    }

    public void setGameOngoing(boolean gameOngoing) {
        this.gameOngoing = gameOngoing;
        setChanged();
        notifyObservers();
    }
}
