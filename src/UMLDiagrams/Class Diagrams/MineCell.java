package blattneun.hausafugabe;

import java.util.Observable;

public class MineCell extends Observable {


    private final Position position;
    private int value;
    private boolean opened;

    private String valueToShow = "";

    public MineCell(Position position) {
        this(position, 0);
    }

    public MineCell(Position position, int value) {
        this.position = position;
        this.value = value;
        this.opened = false;
    }

    public Position getPosition() {
        return position;
    }

    public int getValue() {
        return value;
    }

    public boolean isOpened() {
        return opened;
    }

    public void setOpened(boolean opened) {
        this.opened = opened;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getValueToShow() {
        return valueToShow;
    }

    public void setValueToShow(String valueToShow) {
        this.valueToShow = valueToShow;
        setChanged();
        notifyObservers();
    }

}
