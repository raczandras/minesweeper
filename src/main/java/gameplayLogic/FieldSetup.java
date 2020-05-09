package gameplayLogic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FieldSetup {

    private static int numberOfFields;
    private static int numberOfMines;
    public static Field[][] field;
    private static final Logger logger = LoggerFactory.getLogger(FieldSetup.class);

    public static Field[][] getField() {
        return field;
    }

    public static int getNumberOfFields() {
        return numberOfFields;
    }

    public static int getNumberOfMines() {
        return numberOfMines;
    }

    public static void initField(int numberOfFields, int numberOfMines){
        FieldSetup.numberOfFields = numberOfFields;
        FieldSetup.numberOfMines = numberOfMines;
        field = new Field[numberOfFields][numberOfFields];

        for(int i = 0; i < numberOfFields; i++){
            for(int j = 0; j < numberOfFields; j++){
                field[i][j] = new Field();
            }
        }

        randomizeMines();
    }

    public static boolean flagAField(int rownum, int colnum){
        if( field[rownum][colnum].isClicked() ) {
            return false;
        }
        else{
            field[rownum][colnum].setFlagged( !field[rownum][colnum].isFlagged() );
            return true;
        }
    }

    public static boolean openAField(int rownum, int colnum){
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

    public static void randomizeMines(){ //Assigns the mines randomly on the minefield
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


    private static void setNeighbours(){
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

    public static int[][] getNeighboursIndexes(int row, int col){
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

    private static int[][] getNotCheckedIndexes(int row, int col){
        int[][] neighbours;
        int rowcount = 0;

        for(int colNum = col - 1; colNum <= (col + 1); colNum++  ) {
            for (int rowNum = row - 1; rowNum <= (row + 1); rowNum++  ) {
                if(!((colNum == col) && (rowNum == row))) {
                    if(Field.withinGrid (colNum, rowNum, numberOfFields) && !field[colNum][rowNum].isWasChecked() ) {
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
                    if(Field.withinGrid (colNum, rowNum, numberOfFields) && !field[colNum][rowNum].isWasChecked() ) {
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

    private static void openZeros(int rownum, int colnum ) {

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

    public static boolean withinGrid(int colNum, int rowNum) {

        if((colNum < 0) || (rowNum <0) || colNum >= numberOfFields || rowNum >= numberOfFields ) {
            return false;
        }
        else {
            return true;
        }
    }
}
