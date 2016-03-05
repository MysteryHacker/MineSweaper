package blattneun.hausafugabe;
import java.util.ArrayList;
import java.util.HashSet;

public class MineBoard {

    MineCell[][] board;

    private final int width = Default.WIDTH;
    private final int height = Default.HEIGHT;

    public MineBoard() {
        this.board = new MineCell[width][height];
        for (int x=0; x< width; x++) {
            for (int y = 0; y < height; y++) {
                board[x][y] = new MineCell(new Position(x,y));
            }
        }
        HashSet<MineCell> minesSet = getMineCells(Default.nrMines);
        addMineValues(minesSet);
        calculateNeighbors(board);
    }

    private void calculateNeighbors(MineCell[][] board) {
        for (int x=0; x< width; x++) {
            for (int y = 0; y < height; y++) {
                if(board[x][y].getValue() != Default.mineValue) {
                    board[x][y].setValue(getMinesNearby(x, y));
                }
            }
        }
    }

    private int getMinesNearby(int x, int y) {
        int result = 0;
        ArrayList<MineCell> cells = new ArrayList<>(getNeighbors(x,y));
        for(MineCell cell: cells){
            if(cell.getValue() == Default.mineValue){
                result++;
            }
        }
        return result;
    }

    public ArrayList<MineCell> getNeighbors(int x, int y) {

        ArrayList<MineCell> result = new ArrayList<>();

        int xMin = x==0? 0: x-1;
        int xMax = x==width-1? width-1: x+1;
        int yMin = y==0? 0: y-1;
        int yMax = y==height-1? height-1: y+1;

        for (int i = xMin; i <=xMax; i++) {
            for (int j = yMin; j <=yMax; j++) {
                if(i==x && j==y){
                    continue;
                }
                result.add(getCell(new Position(i,j)));
            }
        }
        return result;
    }

    private void addMineValues(HashSet<MineCell> minesSet) {
        for(MineCell cell: minesSet){
            getCell(cell.getPosition()).setValue(Default.mineValue);
        }
    }

    private HashSet<MineCell> getMineCells(int nrMines) {
        HashSet<MineCell> cells = new HashSet<>();

        while(cells.size()<nrMines){
            cells.add(new MineCell(new Position((int) (Math.random()*width),(int) (Math.random()*height)), Default.mineValue));
        }

        return cells;
    }

    public ArrayList<MineCell> getCells() {
        ArrayList<MineCell> result = new ArrayList<>(width * height);
        for (int x=0; x< width; x++) {
            for (int y = 0; y < height; y++) {
                result.add(board[x][y]);
            }
        }
        return  result;
    }


    public MineCell getCell(Position pos) {
        if (pos.getX() >= 0 && pos.getX() < width && pos.getY() >= 0 && pos.getY() < height) {
            return board[pos.getX()][pos.getY()];
        } else {
            return null;
        }
    }

    public boolean isWinningMove() {
        int cellNr = 0;

        for(MineCell cell: this.getCells()){
            if(!cell.isOpened()){
                cellNr++;
            }
        }
        return cellNr == Default.nrMines;
    }


    public void reset() {
        for (MineCell cell: this.getCells()){
            cell.setValue(0);
            cell.setOpened(false);
            cell.setValueToShow("");
        }
        HashSet<MineCell> minesSet = getMineCells(Default.nrMines);
        addMineValues(minesSet);
        calculateNeighbors(board);
    }

    public int getCellIndex(MineCell mineCell) {
        return mineCell.getPosition().getX() * Default.HEIGHT + mineCell.getPosition().getY();
    }
}
