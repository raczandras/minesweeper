package main;

import gameplayLogic.FieldSetup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import gameplayLogic.gameplay;
import java.util.Scanner;

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        logger.trace("Please type in the difficulty you want. easy or normal or hard");
        FieldSetup.selectDifficulty();
        int numberOfFields = FieldSetup.getNumberOfFields();
        String result;

        Field[][] field = new Field[numberOfFields][numberOfFields];

        for(int i = 0; i < numberOfFields; i++){
            for(int j = 0; j < numberOfFields; j++){
                field[i][j] = new Field();
            }
        }

        FieldSetup.randomizeMines(field);
        FieldSetup.setNeighbours(field);

        result = gameplay.gamePlay(field);

        if(result.equalsIgnoreCase("win")){
            System.out.println("You won!");
        }
        else{
            System.out.println("You lost!");
        }

        System.exit(0);
    }

}


