package main;

import fieldSetup.Setup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

    private static Logger logger = LoggerFactory.getLogger(Main.class);
    private static int numberOfFields;


    public static void main(String[] args){
        logger.trace("Please type in the difficulty you want. easy or normal or hard");
        Setup.selectDifficulty();
        numberOfFields = Setup.getNumberOfFields();

        Field[][] field = new Field[numberOfFields][numberOfFields];

        for(int i = 0; i < numberOfFields; i++){
            for( int j = 0; j < numberOfFields; j++){
                field[i][j] = new Field();
            }
        }

        Setup.randomizeMines(field);
        Setup.setNeighbours(field);

        print(field);

    }

  private static void print( Field[][] field){
        int rownum = 0;
        int colnum = 0;

        System.out.print("  ");

        for( int i = 0; i < numberOfFields; i++){
            if(colnum <= 10) {
                System.out.print(" " + colnum);
            }
            else{
                System.out.print(colnum);
            }
            colnum++;
        }

      System.out.print("\n  ");

      for( int i = 0; i < numberOfFields*2; i++){
          System.out.print("_");
      }
      System.out.println("");

        for(int i = 0; i< numberOfFields; i++){
            if(rownum < 10){
                System.out.print(rownum+ " |");
            }
            else {
                System.out.print(rownum+"|");
            }
            rownum++;

            for( int j = 0; j< numberOfFields; j++){
                if(field[i][j].isMine()) {
                    System.out.print("M ");
                }
                else{
                    System.out.print(field[i][j].getNeighborMines()+" ");
                }
            }
            System.out.println("");
        }
  }

    private static void correctPrint( Field[][] field){
        int rownum = 0;
        int colnum = 0;

        System.out.print("  ");

        for( int i = 0; i < numberOfFields; i++){
            if(colnum <= 10) {
                System.out.print(" " + colnum);
            }
            else{
                System.out.print(colnum);
            }
            colnum++;
        }

        System.out.print("\n  ");

        for( int i = 0; i < numberOfFields*2; i++){
            System.out.print("_");
        }
        System.out.println("");

        for(int i = 0; i< numberOfFields; i++){
            if(rownum < 10){
                System.out.print(rownum+ " |");
            }
            else {
                System.out.print(rownum+"|");
            }
            rownum++;

            for( int j = 0; j< numberOfFields; j++){
                if(field[i][j].isFlagged()){
                    System.out.print("L ");
                }
                else if(field[i][j].isClicked()){
                    System.out.print(field[i][j].getNeighborMines());
                }
                else{
                    System.out.print("X ");
                }
            }
            System.out.println("");
        }
    }

}


