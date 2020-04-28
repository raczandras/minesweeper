package main;

@lombok.Data
public class Field {

    private boolean isClicked = false;
    private boolean isMine = false;
    private boolean isFlagged = false;
    private int neighborMines = 0;
    private boolean wasChecked = false;

    public static int[][] getNeighboursIndexes(int row, int col, int numberOfFields){
        int[][] neighbours;
        int rowcount = 0;

        for(int colNum = col - 1; colNum <= (col + 1); colNum++  ) {
            for (int rowNum = row - 1; rowNum <= (row + 1); rowNum++  ) {
                if(!((colNum == col) && (rowNum == row))) {
                    if(withinGrid (colNum, rowNum, numberOfFields)) {
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
                    if(withinGrid (colNum, rowNum, numberOfFields)) {
                        neighbours[rowcount][0] = rowNum;
                        neighbours[rowcount][1] = colNum;
                        rowcount++;
                    }
                }
            }
        }

        return neighbours;
    }

    public static boolean withinGrid(int colNum, int rowNum, int numberOfFields) {

        if((colNum < 0) || (rowNum <0) ) {
            return false;
        }
        if((colNum >= numberOfFields) || (rowNum >= numberOfFields)) {
            return false;
        }
        return true;
    }

}
