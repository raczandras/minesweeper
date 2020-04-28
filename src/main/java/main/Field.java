package main;

@lombok.Data
public class Field {

    private boolean isClicked = false;
    private boolean isMine = false;
    private boolean isFlagged = false;
    private int neighborMines = 0;

}
