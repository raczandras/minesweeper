package controller;

import gameResult.Leaderboard;
import gameResult.Result;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

public class LeaderboardController {
    private static final Logger logger = LoggerFactory.getLogger(LeaderboardController.class);

    @FXML
    private TableView<Result> leaderboardTable;

    @FXML
    private TableColumn<Result, String> name;

    @FXML
    private TableColumn<Result, String> startingDate;

    @FXML
    private TableColumn<Result, Float> secondsTaken;

    public void exitSelected(ActionEvent actionEvent){
        System.exit(0);
    }

    public void easyLeaderboard(ActionEvent actionEvent) {
        printLeaderboard("easy");
    }

    public void normalLeaderboard(ActionEvent actionEvent) {
        printLeaderboard("normal");
    }

    public void hardLeaderboard(ActionEvent actionEvent) {
        printLeaderboard("hard");
    }

    public void mainmenuSelected(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/launch.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void printLeaderboard( String difficulty){
        logger.trace("Printing the results onto the leaderboard.");
        List<Result> topList = Leaderboard.getResults(difficulty);

        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        startingDate.setCellValueFactory(new PropertyValueFactory<>("startingDate"));
        secondsTaken.setCellValueFactory(new PropertyValueFactory<>("secondsTaken"));

        ObservableList<Result> observableResult = FXCollections.observableArrayList();
        observableResult.addAll(topList);

        leaderboardTable.setItems(observableResult);
    }
}
