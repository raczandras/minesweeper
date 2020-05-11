package controller;

import gameResult.Leaderboard;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.List;
import gameplayLogic.Field;
import gameplayLogic.ManageField;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameplayController {

    private static final Logger logger = LoggerFactory.getLogger(GameplayController.class);

    @FXML
    private List<List<Button>> buttonsList;

    @FXML
    private Button continueButton;

    @FXML
    private Button forfeitButton;

    public void clickAction(MouseEvent actionEvent){
        for( int i = 0; i < buttonsList.size(); i++){
            for( int j = 0; j< buttonsList.get(i).size(); j++){
                if( actionEvent.getSource().equals(buttonsList.get(i).get(j))){
                    if(actionEvent.getButton() == MouseButton.SECONDARY ){
                        if( ManageField.flagAField(i,j)){
                            decideState();
                        }
                    }
                    else if(ManageField.openAField(i,j)) {
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

    public void continueAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/aftergame.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();

    }

    public void decideState(){
        if( ManageField.didwin() ){
            won();
        }
        else if( ManageField.didlost() ){
            lost();
        }
        else{
            printState();
        }
    }

    private void printState() {
        Field[][] field = ManageField.getField();

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

    public void lost(){
        logger.trace("The player lost!");
        Field[][] field = ManageField.getField();
        forfeitButton.setVisible(false);
        continueButton.setVisible(true);

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
        Leaderboard.setDidwin(false);
    }

    public void won(){
        logger.trace("The player won!");
        Field[][] field = ManageField.getField();
        forfeitButton.setVisible(false);
        continueButton.setVisible(true);

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
        Leaderboard.setDidwin(true);
    }
}
