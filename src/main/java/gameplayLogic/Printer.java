package gameplayLogic;

public class Printer {

    public static void endgamePrint( Field[][] field, int numberOfFields){
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

    public static void duringGamePrint( Field[][] field, int numberOfFields){
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
                else if(field[i][j].isClicked() && field[i][j].isMine() ){
                    System.out.print("M ");
                }
                else if(field[i][j].isClicked()){
                    System.out.print(field[i][j].getNeighborMines()+" ");
                }
                else{
                    System.out.print("X ");
                }
            }
            System.out.println("");
        }
    }
}
