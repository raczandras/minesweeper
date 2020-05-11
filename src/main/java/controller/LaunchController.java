package controller;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import gameplayLogic.ManageField;
import gameResult.Leaderboard;

import java.io.IOException;

public class LaunchController {

    private static final Logger logger = LoggerFactory.getLogger(LaunchController.class);

    public void easySelected(ActionEvent actionEvent) throws IOException {
        logger.trace("The chosen difficulty is easy.");
        ManageField.initField(9,10);

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/easymode.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();

        Leaderboard.initStartingDate();

    }

    public void normalSelected(ActionEvent actionEvent) throws IOException{
        logger.trace("The chosen difficulty is normal.");
        ManageField.initField(12,20);

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/normalmode.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();

        Leaderboard.initStartingDate();
    }

    public void hardSelected(ActionEvent actionEvent) throws IOException{
        logger.trace("The chosen difficulty is hard.");
        ManageField.initField(15,40);

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/hardmode.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();

        Leaderboard.initStartingDate();
    }

    public void leaderboardSelected(ActionEvent actionEvent) throws IOException{

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/leaderboard.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
        //LeaderboardController.printLeaderboard();

    }

    public void exitSelected(ActionEvent actionEvent){
        System.exit(0);
    }
}
