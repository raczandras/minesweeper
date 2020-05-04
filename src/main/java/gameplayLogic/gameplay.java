package gameplayLogic;
import main.Field;

public class gameplay {
    private static String result;

    public static String gamePlay(Field[][] field){

        int numberOfFields = FieldSetup.getNumberOfFields();

        Printer.duringGamePrint(field, numberOfFields);

        while ( true ) {
            Moves.userMove(field, numberOfFields);
            if(didEnd(field)){
                break;
            }
            Printer.duringGamePrint(field, numberOfFields);
        }
        return result;
    }

    private static boolean didEnd(Field[][] field){
        int numberOfFields = FieldSetup.getNumberOfFields();
        int numberOfMines = FieldSetup.getNumberOfMines();
        int numberOfClicked = 0;

        for(int i = 0; i < numberOfFields; i++){
            for(int j = 0; j < numberOfFields; j++){
                if( field[i][j].isClicked() && field[i][j].isMine() ){
                    Printer.endgamePrint(field, numberOfFields);
                    result = "lose";
                    return true;
                }
                if( field[i][j].isClicked() ){
                    numberOfClicked++;
                }
            }
        }

        System.out.println(numberOfClicked + " = " + (numberOfFields*numberOfFields - numberOfMines) );
        if( numberOfClicked == (numberOfFields*numberOfFields - numberOfMines) ){
            Printer.endgamePrint(field, numberOfFields);
            result = "win";
            return true;
        }
        return false;
    }
}
