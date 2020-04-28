package gameplayLogic;
import main.Field;
import minefieldSetup.Setup;
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

    private static void flagAField( Field[][] field, int numberOfFields) {
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
                Printer.duringGamePrint(field, numberOfFields);
                sc.nextLine();
            }

        }catch(InputMismatchException ex ){
            System.out.println("Invalid input");
            sc.nextLine();
            flagAField(field, numberOfFields);

        }
    }

    private static void openAField(Field[][] field, int numberOfFields){
        int rownum;
        int colnum;
        int fieldsLeft = numberOfFields * numberOfFields;

        try {
            System.out.println("Please type in a row number you want to open.");
            rownum = sc.nextInt();

            System.out.println("Please type in a column number you want to open.");
            colnum = sc.nextInt();

            if( rownum < 0 || rownum > field.length-1 || colnum < 0 || colnum > field.length-1){
                System.out.println("Invalid input.");
                openAField(field, numberOfFields);
                return;
            }
            else if( field[rownum][colnum].isClicked()){
                System.out.println("This field is already Clicked. Choose an other field.");
                openAField(field, numberOfFields);
                return;
            }
            else{
                field[rownum][colnum].setClicked(true);
                field[rownum][colnum].setFlagged(false);
                Printer.duringGamePrint(field, numberOfFields);
                sc.nextLine();
                fieldsLeft--;
            }

            if( field[rownum][colnum].isMine()){
                System.out.println("You lost!");
                sc.close();
                System.exit(0);
            }
            else if( fieldsLeft == Setup.getNumberOfMines() ){
                System.out.println("You won!");
                sc.close();
                System.exit(0);
            }


        }
        catch(InputMismatchException ex ){
            System.out.println("Invalid input");
            sc.nextLine();
            openAField(field, numberOfFields);
        }
    }
}
