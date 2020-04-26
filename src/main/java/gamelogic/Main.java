package gamelogic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class Main {

    private static Logger logger = LoggerFactory.getLogger(Main.class);
    private static int numberOfFields;
    private static int numberOfMines;

    public static void main(String[] args){
        logger.info("Please type in the difficulty you want. easy or medium or hard");
        selectDifficulty();
        

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
}
