package gameplayLogic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class deals with the logic of the game.
 */
public class ManageField {

    /**
     * Indicates how many fields there are in one row
     */
    private static int numberOfFields;

    /**
     * Indicates how many mines there are in total.
     */
    private static int numberOfMines;

    /**
     * Contains the minefield itself.
     */
    public static Field[][] field;
    private static final Logger logger = LoggerFactory.getLogger(ManageField.class);

    /**
     * Returns the minefield
     *
     * @return the minefield
     */
    public static Field[][] getField() {
        return field;
    }

    /**
     * Initializes the field after a difficulty has been selected.
     */
    public void initField(int numberOfFields, int numberOfMines){
        ManageField.numberOfFields = numberOfFields;
        ManageField.numberOfMines = numberOfMines;

        field = new Field[numberOfFields][numberOfFields];

        for(int i = 0; i < numberOfFields; i++){
            for(int j = 0; j < numberOfFields; j++){
                field[i][j] = new Field();
            }
        }

        randomizeMines();
    }

    /**
     * Flags or unflags a field if it's not clicked yet.
     *
     * @return true if the field hasn't been clicked before, false if it was
     */
    public boolean flagAField(int rownum, int colnum){
        if( field[rownum][colnum].isClicked() ) {
            return false;
        }
        else{
            field[rownum][colnum].setFlagged( !field[rownum][colnum].isFlagged() );
            return true;
        }
    }

    /**
     * Opens a field if it hasn't been opened yet.
     *
     * @return true if the field hasn't been clicked before, false if it was
     */
    public boolean openAField(int rownum, int colnum){
        if(field[rownum][colnum].isClicked() ){
            return false;
        }
        else{
            field[rownum][colnum].setFlagged(false);
            field[rownum][colnum].setClicked(true);
            if( field[rownum][colnum].getNeighborMines() == 0 ) {
                openZeros(rownum, colnum);
            }
            return true;
        }
    }

    /**
     * Assigns mines randomly on the minefield.
     */
    public void randomizeMines(){
        logger.trace("randomizeMines() started");
        for( int i = 0; i < numberOfMines; i++){
            int randomx = (int)(Math.random() * numberOfFields);
            int randomy = (int)(Math.random() * numberOfFields);

            if( field[randomx][randomy].isMine() ){
                i--;
            }
            else{
                field[randomx][randomy].setMine(true);
            }
        }
        logger.trace("adding mines finished");
        setNeighbours();
    }

    /**
     * Sets every field's neighboursMines variable.
     */
    private void setNeighbours(){
        for( int i = 0; i < field.length; i++){
            for( int j = 0; j< field.length; j++){
                if(field[i][j].isMine()){

                    int[][] neighbours = getNeighboursIndexes(i,j);
                    for(int[] neighbour : neighbours) {
                        field[neighbour[0]][neighbour[1]].setNeighborMines(field[neighbour[0]][neighbour[1]].getNeighborMines() + 1);
                    }
                }
            }
        }
    }

    /**
     * Returns a 2d array containing the indexes of a field's neighbours.
     *
     * @param row the field's row number
     * @param col the field's column number
     * @return a 2d array containing the indexes of a field's neighbours
     */
    public int[][] getNeighboursIndexes(int row, int col){
        int[][] neighbours;
        int rowcount = 0;

        for(int colNum = col - 1; colNum <= (col + 1); colNum++  ) {
            for (int rowNum = row - 1; rowNum <= (row + 1); rowNum++  ) {
                if(!((colNum == col) && (rowNum == row))) {
                    if(withinGrid (colNum, rowNum)) {
                        rowcount++;
                    }
                }
            }
        }
        neighbours = new int[rowcount][2];
        rowcount = 0;

        for(int colNum = col - 1; colNum <= (col + 1); colNum++  ) {
            for (int rowNum = row - 1; rowNum <= (row + 1); rowNum++  ) {
                if(!((colNum == col) && (rowNum == row))) {
                    if(withinGrid (colNum, rowNum)) {
                        neighbours[rowcount][0] = rowNum;
                        neighbours[rowcount][1] = colNum;
                        rowcount++;
                    }
                }
            }
        }

        return neighbours;
    }


    /**
     * Returns a 2d array containing the not checked neighbours indexes of a field.
     *
     * @param row the field's row number
     * @param col the field's column number
     * @return {@code neighbours} a 2d array containing the not checked neighbours indexes of a field
     */
    private int[][] getNotCheckedIndexes(int row, int col){
        int[][] neighbours;
        int rowcount = 0;

        for(int colNum = col - 1; colNum <= (col + 1); colNum++  ) {
            for (int rowNum = row - 1; rowNum <= (row + 1); rowNum++  ) {
                if(!((colNum == col) && (rowNum == row))) {
                    if(withinGrid (colNum, rowNum) && !field[colNum][rowNum].isWasChecked() ) {
                        rowcount++;
                    }
                }
            }
        }
        neighbours = new int[rowcount][2];
        rowcount = 0;

        for(int colNum = col - 1; colNum <= (col + 1); colNum++  ) {
            for (int rowNum = row - 1; rowNum <= (row + 1); rowNum++  ) {
                if(!((colNum == col) && (rowNum == row))) {
                    if(withinGrid (colNum, rowNum) && !field[colNum][rowNum].isWasChecked() ) {
                        neighbours[rowcount][0] = rowNum;
                        neighbours[rowcount][1] = colNum;
                        field[colNum][rowNum].setWasChecked(true);
                        rowcount++;
                    }
                }
            }
        }
        return neighbours;
    }

    /**
     * Opens a field's neighbours if their neighbourMines variable is 0.
     */
    private void openZeros(int rownum, int colnum ) {

        int[][] neighbours = getNotCheckedIndexes(rownum, colnum);

        for (int[] neighbour : neighbours) {

            int rowindex = neighbour[0];
            int colindex = neighbour[1];

            if (!field[rowindex][colindex].isClicked()) {
                field[rowindex][colindex].setClicked(true);
            }

            if (field[rowindex][colindex].getNeighborMines() == 0) {
                openZeros(rowindex, colindex );
            }
        }
    }

    /**
     * Checks if a given index is part of the minefield or not.
     *
     * @return true if the index is part of the minefield, and false is it's not
     */
    public boolean withinGrid(int colNum, int rowNum) {

        if((colNum < 0) || (rowNum <0) || colNum >= numberOfFields || rowNum >= numberOfFields ) {
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * Checks if the player has won the game or not.
     *
     * @return true if the player won, and false if not.
     */
    public boolean didwin() {
        int numberOfClicked = 0;

        for(int i = 0; i < numberOfFields; i++){
            for(int j = 0; j < numberOfFields; j++){
                if( field[i][j].isClicked() ){
                    numberOfClicked++;
                }
            }
        }

        if( numberOfClicked == (numberOfFields*numberOfFields - numberOfMines) ){
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Checks if the player has lost the game or not.
     *
     * @return true if the player lost, and false if not.
     */
    public boolean didlost() {

        for( int i = 0; i < numberOfFields; i++){
            for( int j = 0; j< numberOfFields; j++){
                if(field[i][j].isMine() && field[i][j].isClicked()){
                    return true;
                }
            }
        }
        return false;
    }
}
