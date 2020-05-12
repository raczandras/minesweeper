package gameplayLogic;

/**
 * Class representing the game's minefield.
 */
@lombok.Data
public class Field {

    /**
     * Indicates whether the field was clicked on before or not.
     */
    private boolean isClicked = false;

    /**
     * Indicates whether the field is a mine or not.
     */
    private boolean isMine = false;

    /**
     * Indicates whether the field is flagged or not.
     */
    private boolean isFlagged = false;

    /**
     * Indicates how many of the field's neighbours are mines.
     */
    private int neighborMines = 0;

    /**
     * Indicates whether the field was checked before by the program or not.
     */
    private boolean wasChecked = false;
}
