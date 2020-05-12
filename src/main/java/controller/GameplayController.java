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

    public void forfeitAction(ActionEvent actionEvent){
        logger.trace("The user forfeited the game.");
        lost();
    }

    public void continueAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/aftergame.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void clickedAField(MouseEvent actionEvent){
        for( int i = 0; i < buttonsList.size(); i++){
            for( int j = 0; j< buttonsList.get(i).size(); j++){
                if( actionEvent.getSource().equals(buttonsList.get(i).get(j))){
                    if(actionEvent.getButton() == MouseButton.SECONDARY ){
                         ManageField.flagAField(i,j);
                            decideState();
                    }
                    else if(ManageField.openAField(i,j)) {
                            decideState();
                    }
                    break;
                }
            }
        }
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

    public void won(){
        logger.trace("The player won!");
        forfeitButton.setVisible(false);
        continueButton.setVisible(true);
        printEndState();
        Leaderboard.setDidwin(true);
    }

    public void lost(){
        logger.trace("The player lost!");
        forfeitButton.setVisible(false);
        continueButton.setVisible(true);
        printEndState();
        Leaderboard.setDidwin(false);
    }

    public void printEndState(){
        Field[][] field = ManageField.getField();

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
}
