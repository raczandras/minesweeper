package main;

import minefieldSetup.Setup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import gameplayLogic.Printer;
import gameplayLogic.Moves;

public class Main {

    private static Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args){
        logger.trace("Please type in the difficulty you want. easy or normal or hard");
        Setup.selectDifficulty();
        int numberOfFields = Setup.getNumberOfFields();

        Field[][] field = new Field[numberOfFields][numberOfFields];

        for(int i = 0; i < numberOfFields; i++){
            for(int j = 0; j < numberOfFields; j++){
                field[i][j] = new Field();
            }
        }

        Setup.randomizeMines(field);
        Setup.setNeighbours(field);

        Printer.duringGamePrint(field, numberOfFields);

        while(true){
            Moves.userMove(field, numberOfFields);
        }
    }

}


