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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class LeaderboardController {

    @FXML
    private TextField nameTextField;

    @FXML
    private Label nameLabel;

    @FXML
    private TableView<Result> leaderboardTable;

    @FXML
    private TableColumn<Result, String> name;

    @FXML
    private TableColumn<Result, String> startingDate;

    @FXML
    private TableColumn<Result, Float> secondsTaken;

    public void makeResult(MouseEvent actionEvent) throws IOException {
        String username;

        if( nameTextField.getText().isEmpty()){
            nameLabel.setText("Username is empty! Try again!");
        }
        else{
            username = nameTextField.getText();
            Leaderboard.makeNewResult(username);

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/leaderboard.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
            fxmlLoader.<LeaderboardController>getController().printResults();
        }
    }

    public void exitSelected(ActionEvent actionEvent){
        System.exit(0);
    }

    @FXML
    public void printResults(){
        List<Result> topList = Leaderboard.getResults();

        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        startingDate.setCellValueFactory(new PropertyValueFactory<>("startingDate"));
        secondsTaken.setCellValueFactory(new PropertyValueFactory<>("secondsTaken"));

        secondsTaken.setCellFactory(column -> {
            TableCell<Result, Float> cell = new TableCell<Result, Float>() {

                @Override
                protected void updateItem(Float item, boolean empty) {
                    super.updateItem(item, empty);
                    if(empty) {
                        setText(null);
                    }
                    else {
                        setText(Float.toString(item));
                    }
                }
            };
            return cell;
        });

        startingDate.setCellFactory(column -> {
            TableCell<Result, String> cell = new TableCell<Result, String>() {

                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if(empty) {
                        setText(null);
                    }
                    else {
                        setText(item);
                    }
                }
            };
            return cell;
        });

        name.setCellFactory(column -> {
            TableCell<Result, String> cell = new TableCell<Result, String>() {

                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if(empty) {
                        setText(null);
                    }
                    else {
                        setText(item);
                    }
                }
            };
            return cell;
        });

        ObservableList<Result> observableResult = FXCollections.observableArrayList();
        observableResult.addAll(topList);

        leaderboardTable.setItems(observableResult);
    }

}
