package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import java.util.List;

public class gameplayController {

    @FXML
    private List<List<Button>> buttonsList;

    public void clickAction(ActionEvent actionEvent){
        for( int i = 0; i < buttonsList.size(); i++){
            for( int j = 0; j< buttonsList.get(i).size(); j++){
                if( actionEvent.getSource().equals(buttonsList.get(i).get(j))){
                    buttonsList.get(i).get(j).setText("K");
                    break;
                }
            }
        }
    }

    public void forfeitAction(ActionEvent actionEvent){
        System.exit(0);
    }
}
