package gameplayLogic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

/**
 * Class deals with the logic of the game.
 */
public class ManageField {

    /**
     * Indicates how many fields there are in one row.
     */
    private static int numberOfFields;

    /**
     * Indicates how many mines there are in total.
     */
    private static int numberOfMines;

    /**
     * Contains the minefield itself.
     */
    private static Field[][] field;

    private static final Logger logger = LoggerFactory.getLogger(ManageField.class);

    /**
     * Returns the minefield.
     *
     * @return the minefield
     */
    public static Field[][] getField() {
        return field;
    }

    /**
     * Initializes the field after a difficulty has been selected.
     *
     * @param numberOfFields indicates the number of fields in a row
     * @param numberOfMines indicates the number of mines on the minefield.
     */
    public static void initField(int numberOfFields, int numberOfMines){
        ManageField.numberOfFields = numberOfFields;
        ManageField.numberOfMines = numberOfMines;

        field = new Field[numberOfFields][numberOfFields];

        for(int i = 0; i < numberOfFields; i++){
            for(int j = 0; j < numberOfFields; j++){
                field[i][j] = new Field();
            }
        }
        logger.trace("The field has been initialized");
    }

    /**
     * Assigns mines randomly on the minefield.
     */
    public static void randomizeMines(){
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
        logger.trace("Randomizing mines finished");
        setNeighbours();
    }

    /**
     * Sets every field's neighboursMines variable.
     */
    public static void setNeighbours(){
        for( int i = 0; i < field.length; i++){
            for( int j = 0; j< field.length; j++){
                if(field[i][j].isMine()){

                    ArrayList<ArrayList<Integer>> neighbours = getNeighboursIndexes(i,j);
                    for(ArrayList<Integer> neighbour : neighbours) {
                        field[neighbour.get(0)][neighbour.get(1)].setNeighborMines(field[neighbour.get(0)][neighbour.get(1)].getNeighborMines() + 1);
                    }
                }
            }
        }
        logger.trace("Neighbours are set. Let the game begin.");
    }

    /**
     * Flags or unflags a field if it's not clicked yet.
     *
     * @param rownum indicates the row number of the field
     * @param colnum indicates column number of the field
     */
    public static void flagAField(int rownum, int colnum){
        if( !field[rownum][colnum].isClicked() ) {
            field[rownum][colnum].setFlagged( !field[rownum][colnum].isFlagged() );
            logger.trace("The field at {} {} has been flagged or unflagged",rownum,colnum);
        }
    }

    /**
     * Opens a field if it hasn't been opened yet.
     *
     * @param rownum indicates the row number of the field
     * @param colnum indicates the column number of the field
     * @return true if the field hasn't been clicked before, false if it was
     */
    public static boolean openAField(int rownum, int colnum){
        if(field[rownum][colnum].isClicked() ){
            return false;
        }
        else{
            field[rownum][colnum].setFlagged(false);
            field[rownum][colnum].setClicked(true);
            logger.trace("The field at {} {} has been opened",rownum,colnum);
            if( field[rownum][colnum].getNeighborMines() == 0 ) {
                openZeros(rownum, colnum);
            }
            return true;
        }
    }

    /**
     * Opens a field's neighbours if their neighbourMines variable is 0.
     *
     * @param rownum the field's row number
     * @param colnum the field's column number
     */
    public static void openZeros(int rownum, int colnum ) {
        ArrayList<ArrayList<Integer>> neighbours = getNotCheckedIndexes(rownum, colnum);

        for (ArrayList<Integer> neighbour : neighbours) {
            int rowindex = neighbour.get(0);
            int colindex = neighbour.get(1);

            if (!field[rowindex][colindex].isClicked()) {
                field[rowindex][colindex].setClicked(true);
            }
            if (field[rowindex][colindex].getNeighborMines() == 0) {
                openZeros(rowindex, colindex );
            }
        }
    }

    /**
     * Checks if the player has lost the game or not.
     *
     * @return true if the player lost, and false if not.
     */
    public static boolean didlost() {

        for( int i = 0; i < numberOfFields; i++){
            for( int j = 0; j< numberOfFields; j++){
                if(field[i][j].isMine() && field[i][j].isClicked()){
                    logger.trace("The player has lost the game.");
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks if the player has won the game or not.
     *
     * @return true if the player won, and false if not.
     */
    public static boolean didwin() {
        int numberOfClicked = 0;

        for(int i = 0; i < numberOfFields; i++){
            for(int j = 0; j < numberOfFields; j++){
                if( field[i][j].isClicked() ){
                    numberOfClicked++;
                }
            }
        }

        if( numberOfClicked == (numberOfFields*numberOfFields - numberOfMines) ){
            logger.trace("The player has won the game.");
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Returns a 2d arrayList containing the indexes of a field's neighbours.
     *
     * @param row the field's row number
     * @param col the field's column number
     * @return {@code neighbours} a 2d arrayList containing the indexes of a field's neighbours
     */
    public static ArrayList<ArrayList<Integer>> getNeighboursIndexes(int row, int col){
        int rowcount = 0;
        ArrayList<ArrayList<Integer>> neighbours = new ArrayList<>();

        for(int colNum = col - 1; colNum <= (col + 1); colNum++  ) {
            for (int rowNum = row - 1; rowNum <= (row + 1); rowNum++  ) {
                if(!((colNum == col) && (rowNum == row))) {
                    if(withinGrid (colNum, rowNum)) {
                        neighbours.add(rowcount, new ArrayList<Integer>());
                        neighbours.get(rowcount).add(0,rowNum);
                        neighbours.get(rowcount).add(1,colNum);
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
     * @return {@code neighbours} a 2d arrayList containing the not checked neighbours indexes of a field
     */
    public static ArrayList<ArrayList<Integer>> getNotCheckedIndexes(int row, int col){
        ArrayList<ArrayList<Integer>> neighbours = new ArrayList<>();
        int rowcount = 0;

        for(int colNum = col - 1; colNum <= (col + 1); colNum++  ) {
            for (int rowNum = row - 1; rowNum <= (row + 1); rowNum++  ) {
                if(!((colNum == col) && (rowNum == row))) {
                    if(withinGrid (colNum, rowNum) && !field[colNum][rowNum].isWasChecked() ){
                        neighbours.add(rowcount, new ArrayList<Integer>());
                        neighbours.get(rowcount).add(0,rowNum);
                        neighbours.get(rowcount).add(1,colNum);

                        field[colNum][rowNum].setWasChecked(true);
                        rowcount++;
                    }
                }
            }
        }
        return neighbours;
    }

    /**
     * Checks if a given index is within the 2d array representing the minefield or not.
     *
     * @param colNum the starting column index
     * @param rowNum the starting row index
     * @return true if the index is part of the minefield, and false is it's not
     */
    public static boolean withinGrid(int colNum, int rowNum) {

        if((colNum < 0) || (rowNum <0) || colNum >= numberOfFields || rowNum >= numberOfFields ) {
            return false;
        }
        else {
            return true;
        }
    }
}
