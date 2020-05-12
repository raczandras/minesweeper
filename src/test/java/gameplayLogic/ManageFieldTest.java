package gameplayLogic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ManageFieldTest {

    @Test
    void AndOpenZerosAndGetNotCheckedIndexes(){
        ManageField.initField(2,4);
        Field[][] field = new Field[2][2];

        for(int i = 0; i < 2; i++){
            for(int j = 0; j < 2; j++){
                field[i][j] = new Field();
                field[i][j].setClicked(true);
                field[i][j].setWasChecked(true);
            }
        }
        ManageField.openZeros(0,0);
        assertArrayEquals(field, ManageField.getField());

        ManageField.initField(2,4);
        ManageField.randomizeMines();

        for(int i = 0; i < 2; i++){
            for(int j = 0; j < 2; j++){
                field[i][j].setMine(true);
                field[i][j].setNeighborMines(3);
            }
        }
        field[0][0].setWasChecked(false);
        field[0][0].setClicked(false);

        ManageField.openZeros(0,0);
        assertArrayEquals(field, ManageField.getField());

    }

    @Test
    void testSetNeighboursAndGetNeighboursIndexes() {
        Field[][] field = new Field[2][2];
        for(int i = 0; i < 2; i++){
            for(int j = 0; j < 2; j++){
                field[i][j] = new Field();
            }
        }

        ManageField.initField(2,4);
        ManageField.setNeighbours();
        assertArrayEquals(field, ManageField.getField());

        for(int i = 0; i < 2; i++){
            for(int j = 0; j < 2; j++){
                field[i][j].setMine(true);
                field[i][j].setNeighborMines(3);
            }
        }

        ManageField.randomizeMines();
        assertArrayEquals(field, ManageField.getField());
    }

    @Test
    void testFlagAField() {
        Field[][] field = new Field[2][2];
        for(int i = 0; i < 2; i++){
            for(int j = 0; j < 2; j++){
                field[i][j] = new Field();
            }
        }

        ManageField.initField(2,2);
        ManageField.flagAField(0,0);
        field[0][0].setFlagged(true);
        assertArrayEquals(field, ManageField.getField());

        field[0][0].setFlagged(false);
        ManageField.flagAField(0,0);
        assertArrayEquals(field, ManageField.getField());

        ManageField.openAField(0,0);
        ManageField.flagAField(0,0);

        for(int i = 0; i < 2; i++){
            for(int j = 0; j < 2; j++){
                field[i][j].setWasChecked(true);
                field[i][j].setClicked(true);
            }
        }
        assertArrayEquals(field, ManageField.getField());
    }

    @Test
    void testOpenAField() {
        ManageField.initField(2,4);
        assertTrue(ManageField.openAField(0,0));
        assertFalse(ManageField.openAField(0,0));

        ManageField.initField(2,4);
        ManageField.randomizeMines();
        assertTrue(ManageField.openAField(0,0));

    }


    @Test
    void testDidlost() {
        ManageField.initField(2,4);
        assertFalse(ManageField.didlost());
        ManageField.openAField(0,0);
        assertFalse(ManageField.didlost());

        ManageField.initField(2,4);
        ManageField.randomizeMines();
        assertFalse(ManageField.didlost());
        ManageField.openAField(0,0);
        assertTrue(ManageField.didlost());

    }

    @Test
    void testDidwin() {
        ManageField.initField(2,0);
        assertFalse(ManageField.didwin());

        ManageField.openAField(0,0);
        assertTrue(ManageField.didwin());

        ManageField.initField(2,1);
        ManageField.openAField(0,0);
        assertFalse(ManageField.didwin());
    }

    @Test
    void testWithinGrid() {

        ManageField.initField(9,10);
        assertFalse(ManageField.withinGrid(9, 9));
        assertFalse(ManageField.withinGrid(-1, -1));
        assertFalse(ManageField.withinGrid(-1, 0));
        assertFalse(ManageField.withinGrid(0, -1));
        assertFalse(ManageField.withinGrid(0, 9));
        assertFalse(ManageField.withinGrid(9, 0));
        assertFalse(ManageField.withinGrid(-1, 9));
        assertFalse(ManageField.withinGrid(9, -1));
        assertTrue(ManageField.withinGrid(0, 0));
        assertTrue(ManageField.withinGrid(8, 8));
    }
}