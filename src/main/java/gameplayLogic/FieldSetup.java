package gameplayLogic;

import controller.launchController;
import main.Field;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class FieldSetup {

    private static int numberOfFields;
    private static int numberOfMines;
    public static Field[][] field;
    private static final Logger logger = LoggerFactory.getLogger(FieldSetup.class);

    public static Field[][] getField() {
        return field;
    }

    public static void setField(Field[][] field) {
        FieldSetup.field = field;
    }

    public static int getNumberOfFields() {
        return numberOfFields;
    }

    public static int getNumberOfMines() {
        return numberOfMines;
    }

    public static void setNumberOfMines(int numberOfMines) {
        FieldSetup.numberOfMines = numberOfMines;
    }

    public static void setNumberOfFields(int numberOfFields) {
        FieldSetup.numberOfFields = numberOfFields;
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

    public static void selectDifficulty(){ //Makes a player select a difficulty setting before the start of the game
        Scanner sc = new Scanner(System.in);
        String difficulty = sc.nextLine();

        if( difficulty.equalsIgnoreCase("easy") || difficulty.equalsIgnoreCase("normal") || difficulty.equalsIgnoreCase("hard") ){
            switch(difficulty.toLowerCase()) {
                case "easy":
                    numberOfFields = 9;
                    numberOfMines = 10;
                    break;
                case "normal":
                    numberOfFields = 16;;
                    numberOfMines = 40;;
                    break;
                case "hard":
                    numberOfFields = 24;;
                    numberOfMines = 99;;
            }
        }
        else{
            System.out.println("I don't understand. Try again:");
            selectDifficulty();
        }
    }

    private static void setNeighbours(){

        for( int i = 0; i < field.length; i++){
            for( int j = 0; j< field.length; j++){
                if(field[i][j].isMine()){

                    int[][] neighbours = Field.getNeighboursIndexes(i,j,numberOfFields);

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

    public static boolean withinGrid(int colNum, int rowNum) {

        if((colNum < 0) || (rowNum <0) ) {
            return false;
        }
        if((colNum >= numberOfFields) || (rowNum >= numberOfFields)) {
            return false;
        }
        return true;
    }
}
