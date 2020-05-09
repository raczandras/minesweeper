package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import java.util.List;
import main.Field;
import gameplayLogic.FieldSetup;

public class gameplayController {

    @FXML
    private List<List<Button>> buttonsList;

    public void clickAction(ActionEvent actionEvent){
        Field[][] field = FieldSetup.getField();
        for( int i = 0; i < buttonsList.size(); i++){
            for( int j = 0; j< buttonsList.get(i).size(); j++){
                if( actionEvent.getSource().equals(buttonsList.get(i).get(j))){
                    buttonsList.get(i).get(j).setText(String.valueOf(field[i][j].getNeighborMines()));
                    break;
                }
            }
        }
    }

    public void forfeitAction(ActionEvent actionEvent){
        System.exit(0);
    }

    public void printField(){
        Field[][] field = FieldSetup.getField();

        for( int i = 0; i < buttonsList.size(); i++){
            for( int j = 0; j< buttonsList.get(i).size(); j++){
                if(field[i][j].isClicked() ){
                    buttonsList.get(i).get(j).setText(String.valueOf(field[i][j].getNeighborMines()));
                }
            }
        }
    }
}
