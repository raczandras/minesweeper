package gamelogic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class Main {

    private static Logger logger = LoggerFactory.getLogger(Main.class);
    private static int numberOfFields;
    private static int numberOfMines;
    private static Field[][] field;

    public static void main(String[] args){
        logger.trace("Please type in the difficulty you want. easy or normal or hard");
        selectDifficulty();

        field = new Field[numberOfFields][numberOfFields];

        for(int i = 0; i < numberOfFields; i++){
            for( int j = 0; j < numberOfFields; j++){
                field[i][j] = new Field();
            }
        }

        randomizeMines(field);
        

    }

    private static void selectDifficulty(){
        Scanner sc = new Scanner(System.in);
        String difficulty = sc.nextLine();

        if( difficulty.equalsIgnoreCase("easy") || difficulty.equalsIgnoreCase("normal") || difficulty.equalsIgnoreCase("hard") ){
            switch(difficulty.toLowerCase()) {
                case "easy":
                    numberOfFields = 9;
                    numberOfMines = 10;
                    break;
                case "normal":
                    numberOfFields = 16;
                    numberOfMines = 40;
                    break;
                case "hard":
                    numberOfFields = 24;
                    numberOfMines = 99;
            }
        }
        else{
            logger.info("I don't understand. Try again:");
            selectDifficulty();
        }
  }


  private static void randomizeMines(Field[][] field){
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
}


