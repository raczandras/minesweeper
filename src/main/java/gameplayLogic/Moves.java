package gameplayLogic;
import main.Field;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Moves {
    private static Scanner sc = new Scanner(System.in);

    public static void userMove(Field[][] field, int numberOfFields){

        try{
            String move;
            System.out.println("Do you want to open, or flag a field, or forfeit?");
            move = sc.nextLine();

            if( move.equalsIgnoreCase("forfeit")){
                System.out.println("You lost!");
                sc.close();
                System.exit(0);
            }
            else if( move.equalsIgnoreCase("open") ){
                openAField(field, numberOfFields);
            }
            else if( move.equalsIgnoreCase("flag")){
                flagAField(field, numberOfFields);
            }
            else{
                System.out.println("Invalid input");
                userMove(field,numberOfFields);
            }


        }catch(InputMismatchException ex ){
            System.out.println("Invalid input");
            sc.nextLine();
            userMove(field, numberOfFields);

        }
    }

    public static void flagAField( Field[][] field, int numberOfFields) {
        int rownum;
        int colnum;

        try{
            System.out.println("Please type in a row number you want to flag.");
            rownum = sc.nextInt();

            System.out.println("Please type in a column number you want to flag.");
            colnum = sc.nextInt();

            if( rownum < 0 || rownum > field.length-1 || colnum < 0 || colnum > field.length-1){
                System.out.println("Invalid input.");
                flagAField(field, numberOfFields);
                return;
            }

            if( field[rownum][colnum].isFlagged()){
                System.out.println("This field is already flagged. Choose an other field.");
                flagAField(field, numberOfFields);
            }
            else if( field[rownum][colnum].isClicked()){
                System.out.println("This field is already opened. Choose an other field.");
                flagAField(field, numberOfFields);
            }
            else{
                field[rownum][colnum].setFlagged(true);
                sc.nextLine();
            }

        }catch(InputMismatchException ex ){
            System.out.println("Invalid input");
            sc.nextLine();
            flagAField(field, numberOfFields);

        }
    }

    public static void openAField(Field[][] field, int numberOfFields){
        int rownum;
        int colnum;

        try {
            System.out.println("Please type in a row number you want to open.");
            rownum = sc.nextInt();

            System.out.println("Please type in a column number you want to open.");
            colnum = sc.nextInt();

            if( rownum < 0 || rownum > field.length-1 || colnum < 0 || colnum > field.length-1){
                System.out.println("Invalid input.");
                openAField(field, numberOfFields);
            }
            else if( field[rownum][colnum].isClicked()){
                System.out.println("This field is already Clicked. Choose an other field.");
                openAField(field, numberOfFields);
            }
            else {
                field[rownum][colnum].setClicked(true);
                field[rownum][colnum].setFlagged(false);
                field[rownum][colnum].setWasChecked(true);

                if( field[rownum][colnum].getNeighborMines() == 0){
                    openZeros(field, rownum, colnum, numberOfFields);
                }
                sc.nextLine();
            }
        }
        catch(InputMismatchException ex ){
            System.out.println("Invalid input");
            sc.nextLine();
            openAField(field, numberOfFields);
        }
    }

    private static void openZeros(Field[][] field, int rownum, int colnum, int numberOfFields) {

        int[][] neighbours = getNotCheckedIndexes(field, rownum, colnum,numberOfFields);

        for (int[] neighbour : neighbours) {

            int rowindex = neighbour[0];
            int colindex = neighbour[1];

            if (!field[rowindex][colindex].isClicked()) {
                field[rowindex][colindex].setClicked(true);
            }

            if (field[rowindex][colindex].getNeighborMines() == 0) {
                openZeros(field, rowindex, colindex, numberOfFields);
            }
        }
    }


    private static int[][] getNotCheckedIndexes(Field[][] field, int row, int col, int numberOfFields){
        int[][] neighbours;
        int rowcount = 0;

        for(int colNum = col - 1; colNum <= (col + 1); colNum++  ) {
            for (int rowNum = row - 1; rowNum <= (row + 1); rowNum++  ) {
                if(!((colNum == col) && (rowNum == row))) {
                    if(Field.withinGrid (colNum, rowNum, numberOfFields) && !field[colNum][rowNum].isWasChecked() ) {
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
                    if(Field.withinGrid (colNum, rowNum, numberOfFields) && !field[colNum][rowNum].isWasChecked() ) {
                        neighbours[rowcount][0] = rowNum;
                        neighbours[rowcount][1] = colNum;
                        field[colNum][rowNum].setWasChecked(true);
                        rowcount++;
                    }
                }
            }
        }
        return neighbours;
    }
}
