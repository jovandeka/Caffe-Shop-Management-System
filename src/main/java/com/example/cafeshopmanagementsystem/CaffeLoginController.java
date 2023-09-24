package com.example.cafeshopmanagementsystem;

import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class CaffeLoginController {
    @FXML
    private Hyperlink si_forgotPass;

    @FXML
    private Button si_loginBtn;

    @FXML
    private AnchorPane si_loginForm;

    @FXML
    private PasswordField si_password;

    @FXML
    private TextField si_username;

    @FXML
    private Button side_createBtn;

    @FXML
    private Button side_alreadyHave;

    @FXML
    private AnchorPane side_form;

    @FXML
    private TextField su_answer;

    @FXML
    private PasswordField su_password;

    @FXML
    private ComboBox<?> su_question;

    @FXML
    private Button su_signupBtn;

    @FXML
    private AnchorPane su_signupForm;

    @FXML
    private TextField su_username;

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;

    private Alert alert;

    public void regBtn(){
        if(su_username.getText().isEmpty() || su_password.getText().isEmpty() || su_question.getSelectionModel().getSelectedItem() == null || su_answer.getText().isEmpty()){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText("");
            alert.setContentText("Please fill all blank fields.");
            alert.showAndWait();
        } else {
            String regData = "INSERT INTO employee (username, password, question, answer) " + "VALUES (?,?,?,?)";
            connect = database.conectDB();
            try{
                prepare = connect.prepareStatement(regData);
                prepare.setString(1, su_username.getText());
                prepare.setString(2, su_password.getText());
                prepare.setString(3, (String)su_question.getSelectionModel().getSelectedItem());
                prepare.setString(4, su_answer.getText());

                prepare.executeUpdate();

                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText("");
                alert.setContentText("Successfully registered account.");
                alert.showAndWait();

                su_username.setText("");
                su_password.setText("");
                su_question.getSelectionModel().clearSelection();
                su_answer.setText("");

                TranslateTransition slider = new TranslateTransition();

                slider.setNode(side_form);
                slider.setToX(0);
                slider.setDuration(Duration.seconds(.5));

                slider.setOnFinished((ActionEvent e) ->{
                    side_alreadyHave.setVisible(false);
                    side_createBtn.setVisible(true);
                });

                slider.play();

            }catch (Exception e){e.printStackTrace();}
        }
    }

    private final String[] questionList = {"What is your favorite color?", "What is your favorite food?", "What is your birth date?"};
    public void regLquestionList(){
        List<String> listQ = new ArrayList<>();
        for(String data: questionList){
            listQ.add(data);
        }
        ObservableList listData = FXCollections.observableArrayList(listQ);

        su_question.setItems(listData);
    }

    public void switchForm(ActionEvent event){
        TranslateTransition slider = new TranslateTransition();

        if(event.getSource() == side_createBtn){
            slider.setNode(side_form);
            slider.setToX(300);
            slider.setDuration(Duration.seconds(.5));

            slider.setOnFinished((ActionEvent e) ->{
                side_alreadyHave.setVisible(true);
                side_createBtn.setVisible(false);

                regLquestionList();
            });

            slider.play();
        } else if (event.getSource() == side_alreadyHave) {
            slider.setNode(side_form);
            slider.setToX(0);
            slider.setDuration(Duration.seconds(.5));

            slider.setOnFinished((ActionEvent e) ->{
                side_alreadyHave.setVisible(false);
                side_createBtn.setVisible(true);
            });

            slider.play();
        }

    }
}