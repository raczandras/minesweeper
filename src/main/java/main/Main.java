package main;

import gameplayLogic.FieldSetup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import gameplayLogic.Printer;
import gameplayLogic.Moves;

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args){
        logger.trace("Please type in the difficulty you want. easy or normal or hard");
        FieldSetup.selectDifficulty();
        int numberOfFields = FieldSetup.getNumberOfFields();
        int numberOfMines = FieldSetup.getNumberOfMines();
        int numberOfNotClicked = 0;

        Field[][] field = new Field[numberOfFields][numberOfFields];

        for(int i = 0; i < numberOfFields; i++){
            for(int j = 0; j < numberOfFields; j++){
                field[i][j] = new Field();
            }
        }

        FieldSetup.randomizeMines(field);
        FieldSetup.setNeighbours(field);

        Printer.duringGamePrint(field, numberOfFields);

        while(true){
            numberOfNotClicked = 0;
            Moves.userMove(field, numberOfFields);

            for(int i = 0; i < numberOfFields; i++){
                for(int j = 0; j < numberOfFields; j++){
                    if( !field[i][j].isClicked() ){
                        numberOfNotClicked++;
                    }
                }
            }
            if( numberOfNotClicked == numberOfMines ){
                Printer.endgamePrint(field, numberOfFields);
                System.out.println("You won!");
                break;
            }
        }
        System.exit(0);
    }

}


