package gameplayLogic;

import main.Field;
import java.util.Scanner;

public class FieldSetup {

    private static int numberOfFields;
    private static int numberOfMines;

    public static int getNumberOfMines() { return numberOfMines; }
    public static int getNumberOfFields() {
        return numberOfFields;
    }

    public static void randomizeMines(Field[][] field){ //Assigns the mines randomly on the minefield

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

    public static void setNeighbours(Field[][] field){

        for( int i = 0; i < field.length; i++){
            for( int j = 0; j< field.length; j++){
                if(field[i][j].isMine()){

                    int[][] neighbours = Field.getNeighboursIndexes(i,j,numberOfFields);

                    for(int[] neighbour : neighbours) {
                        int rowindex = neighbour[0];
                        int colindex = neighbour[1];

                        field[rowindex][colindex].setNeighborMines(field[rowindex][colindex].getNeighborMines() + 1);
                    }
                }
            }
        }
    }
}
