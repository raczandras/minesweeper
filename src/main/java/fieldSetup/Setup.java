package fieldSetup;

import main.Field;

import java.util.Scanner;

public class Setup {

    private static int numberOfFields;
    private static int numberOfMines;

    public static int getNumberOfFields() {
        return numberOfFields;
    }

    public static void randomizeMines(Field[][] field){ //Assigns the mines randomly on the minefield
        int randomx;
        int randomy;

        for( int i = 1; i < numberOfMines; i++){
            randomx = (int)(Math.random() * numberOfFields);
            randomy = (int)(Math.random() * numberOfFields);

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

                    if( i == 0 && j == 0){  //happens if the mine is in the first column of the first row
                        field[i+1][j].setNeighborMines( field[i+1][j].getNeighborMines()+1 );
                        field[i][j+1].setNeighborMines( field[i][j+1].getNeighborMines()+1 );
                        field[i+1][j+1].setNeighborMines( field[i+1][j+1].getNeighborMines()+1 );
                    }
                    else if( i == field.length-1 && j == 0){ //happens if the mine is in the first column of the last row
                        field[i-1][j].setNeighborMines( field[i-1][j].getNeighborMines()+1 );
                        field[i][j+1].setNeighborMines( field[i][j+1].getNeighborMines()+1 );
                        field[i-1][j+1].setNeighborMines( field[i-1][j+1].getNeighborMines()+1 );
                    }
                    else if( i == 0 && j == field.length-1){ //happens if the mine is in the last column of the first row
                        field[i+1][j].setNeighborMines( field[i+1][j].getNeighborMines()+1 );
                        field[i][j-1].setNeighborMines( field[i][j-1].getNeighborMines()+1 );
                        field[i+1][j-1].setNeighborMines( field[i+1][j-1].getNeighborMines()+1 );
                    }
                    else if( i == field.length-1 && j == field.length-1){ //happens if the mine is in the last column of the last row
                        field[i-1][j].setNeighborMines( field[i-1][j].getNeighborMines()+1 );
                        field[i][j-1].setNeighborMines( field[i][j-1].getNeighborMines()+1 );
                        field[i-1][j-1].setNeighborMines( field[i-1][j-1].getNeighborMines()+1 );
                    }
                    else if( i == 0 ){ //happens if the mine is in the first row, but not on the edges
                        field[i][j-1].setNeighborMines( field[i][j-1].getNeighborMines()+1 );
                        field[i+1][j-1].setNeighborMines( field[i+1][j-1].getNeighborMines()+1 );
                        field[i+1][j].setNeighborMines( field[i+1][j].getNeighborMines()+1 );
                        field[i+1][j+1].setNeighborMines( field[i+1][j+1].getNeighborMines()+1 );
                        field[i][j+1].setNeighborMines( field[i][j+1].getNeighborMines()+1 );
                    }
                    else if( i == field.length-1 ){ //happens if the mine is in the last row, but not on the edges
                        field[i][j-1].setNeighborMines( field[i][j-1].getNeighborMines()+1 );
                        field[i-1][j-1].setNeighborMines( field[i-1][j-1].getNeighborMines()+1 );
                        field[i-1][j].setNeighborMines( field[i-1][j].getNeighborMines()+1 );
                        field[i-1][j+1].setNeighborMines( field[i-1][j+1].getNeighborMines()+1 );
                        field[i][j+1].setNeighborMines( field[i][j+1].getNeighborMines()+1 );
                    }
                    else if( j == 0 ){ //happens if the mine is in the first column but not on the edges
                        field[i-1][j].setNeighborMines( field[i-1][j].getNeighborMines()+1 );
                        field[i-1][j+1].setNeighborMines( field[i-1][j+1].getNeighborMines()+1 );
                        field[i][j+1].setNeighborMines( field[i][j+1].getNeighborMines()+1 );
                        field[i+1][j+1].setNeighborMines( field[i+1][j+1].getNeighborMines()+1 );
                        field[i+1][j].setNeighborMines( field[i+1][j].getNeighborMines()+1 );
                    }
                    else if( j == field.length-1 ){ //happens if the mine is in the last column but not on the edges
                        field[i-1][j].setNeighborMines( field[i-1][j].getNeighborMines()+1 );
                        field[i-1][j-1].setNeighborMines( field[i-1][j-1].getNeighborMines()+1 );
                        field[i][j-1].setNeighborMines( field[i][j-1].getNeighborMines()+1 );
                        field[i+1][j-1].setNeighborMines( field[i+1][j-1].getNeighborMines()+1 );
                        field[i+1][j].setNeighborMines( field[i+1][j].getNeighborMines()+1 );
                    }
                    else{ //Happens if the mine is not on any of the edges, so it has 8 neighbours.
                        field[i-1][j-1].setNeighborMines( field[i-1][j-1].getNeighborMines()+1 );
                        field[i-1][j].setNeighborMines( field[i-1][j].getNeighborMines()+1 );
                        field[i-1][j+1].setNeighborMines( field[i-1][j+1].getNeighborMines()+1 );
                        field[i][j+1].setNeighborMines( field[i][j+1].getNeighborMines()+1 );
                        field[i+1][j+1].setNeighborMines( field[i+1][j+1].getNeighborMines()+1 );
                        field[i+1][j].setNeighborMines( field[i+1][j].getNeighborMines()+1 );
                        field[i+1][j-1].setNeighborMines( field[i+1][j-1].getNeighborMines()+1 );
                        field[i][j-1].setNeighborMines( field[i][j-1].getNeighborMines()+1 );
                    }
                }
            }
        }
    }
}
