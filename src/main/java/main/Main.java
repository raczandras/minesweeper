package main;

import gameplayLogic.FieldSetup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import gameplayLogic.gameplay;
import gameResult.Leaderboard;
import gameResult.Result;
import java.time.LocalDateTime;

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args){
        logger.trace("Please type in the difficulty you want. easy or normal or hard");
        FieldSetup.selectDifficulty();
        int numberOfFields = FieldSetup.getNumberOfFields();
        String end;
        LocalDateTime startingDate;
        long startTime;
        long endTime;

        Field[][] field = new Field[numberOfFields][numberOfFields];

        for(int i = 0; i < numberOfFields; i++){
            for(int j = 0; j < numberOfFields; j++){
                field[i][j] = new Field();
            }
        }

        FieldSetup.randomizeMines(field);
        FieldSetup.setNeighbours(field);
        startingDate = LocalDateTime.now();
        startTime = System.currentTimeMillis();
        end = gameplay.gamePlay(field);

        if(end.equalsIgnoreCase("win")){
            System.out.println("You won!");
            endTime = System.currentTimeMillis();
            Leaderboard.makeNewResult(startingDate, startTime, endTime);
        }
        else{
            System.out.println("You lost!");
            endTime = System.currentTimeMillis();
            Leaderboard.makeNewResult(startingDate, startTime, endTime);
        }

        System.exit(0);
    }

}


