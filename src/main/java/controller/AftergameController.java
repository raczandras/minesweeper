package controller;

import gameResult.Leaderboard;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class AftergameController {

    private static final Logger logger = LoggerFactory.getLogger(AftergameController.class);

    @FXML
    private TextField nameTextField;

    @FXML
    private Label nameLabel;

    public void makeResult(MouseEvent actionEvent) throws IOException {
        String username;

        if( nameTextField.getText().isEmpty()){
            nameLabel.setText("Username is empty! Try again!");
        }
        else{
            username = nameTextField.getText();
            logger.trace("The player's name is: {}",username);
            Leaderboard.makeNewResult(username);

            logger.trace("Loading the leaderboard...");
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/leaderboard.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        }
    }
}
