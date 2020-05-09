package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import java.util.List;
import main.Field;
import gameplayLogic.FieldSetup;

public class gameplayController {

    @FXML
    private List<List<Button>> buttonsList;

    public void clickAction(MouseEvent actionEvent){
        for( int i = 0; i < buttonsList.size(); i++){
            for( int j = 0; j< buttonsList.get(i).size(); j++){
                if( actionEvent.getSource().equals(buttonsList.get(i).get(j))){
                    if(actionEvent.getButton() == MouseButton.SECONDARY ){
                        if( FieldSetup.flagAField(i,j)){
                            decideState();
                        }
                    }
                    else if(FieldSetup.openAField(i,j)) {
                            decideState();
                    }
                    break;
                }
            }
        }
    }

    public void forfeitAction(ActionEvent actionEvent){
        lost();
    }

    public void decideState(){
        if( didwin() ){
            won();
        }
        else if( didlost() ){
            lost();
        }
        else{
            printState();
        }
    }

    private void printState() {
        Field[][] field = FieldSetup.getField();

        for( int i = 0; i < buttonsList.size(); i++){
            for( int j = 0; j< buttonsList.get(i).size(); j++){
                if(field[i][j].isFlagged()){
                    buttonsList.get(i).get(j).setText("F");
                }
                else if(field[i][j].isClicked() ){
                    buttonsList.get(i).get(j).setText(String.valueOf(field[i][j].getNeighborMines()));
                }
                else{
                    buttonsList.get(i).get(j).setText("");
                }
            }
        }
    }

    private boolean didlost() {
        Field[][] field = FieldSetup.getField();
        for( int i = 0; i < buttonsList.size(); i++){
            for( int j = 0; j< buttonsList.get(i).size(); j++){
                if(field[i][j].isMine() && field[i][j].isClicked()){
                    return true;
                }
            }
        }
        return false;
    }

    private boolean didwin() {
        Field[][] field = FieldSetup.getField();
        int numberOfClicked = 0;
        int numberOfMines = FieldSetup.getNumberOfMines();

        for(int i = 0; i < buttonsList.size(); i++){
            for(int j = 0; j < buttonsList.get(i).size(); j++){
                if( field[i][j].isClicked() ){
                    numberOfClicked++;
                }
            }
        }
        if( numberOfClicked == (buttonsList.size()*buttonsList.size() - numberOfMines) ){
            return true;
        }
        else {
            return false;
        }
    }

    public void lost(){
        Field[][] field = FieldSetup.getField();

        for( int i = 0; i < buttonsList.size(); i++){
            for( int j = 0; j< buttonsList.get(i).size(); j++){
                if(field[i][j].isMine()){
                    buttonsList.get(i).get(j).setText("M");
                }
                else{
                    buttonsList.get(i).get(j).setText(String.valueOf(field[i][j].getNeighborMines()));
                }
                buttonsList.get(i).get(j).setDisable(true);
            }
        }
        System.out.println("You lost!");
    }

    public void won(){
        Field[][] field = FieldSetup.getField();

        for( int i = 0; i < buttonsList.size(); i++){
            for( int j = 0; j< buttonsList.get(i).size(); j++){
                if(field[i][j].isMine()){
                    buttonsList.get(i).get(j).setText("M");
                }
                else{
                    buttonsList.get(i).get(j).setText(String.valueOf(field[i][j].getNeighborMines()));
                }
                buttonsList.get(i).get(j).setDisable(true);
            }
        }
        System.out.println("You Won");
    }
}
