package com.example.cafeshopmanagementsystem;

import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class CaffeLoginController {
    @FXML
    private TextField fp_answer;

    @FXML
    private Button fp_back;

    @FXML
    private Button fp_proceedBtn;

    @FXML
    private ComboBox<?> fp_question;

    @FXML
    private TextField fp_username;

    @FXML
    private AnchorPane fp_questionForm;

    @FXML
    private Button np_back;

    @FXML
    private Button np_changePassBtn;

    @FXML
    private PasswordField np_confirmPassword;

    @FXML
    private AnchorPane np_newPassForm;

    @FXML
    private PasswordField np_newPassword;

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
    private Button side_alreadyHave;

    @FXML
    private Button side_createBtn;

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

    public void loginBtn(){
        if(si_username.getText().isEmpty() || si_password.getText().isEmpty()){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText("");
            alert.setContentText("Please fill-in the blank fields.");
            alert.showAndWait();
        } else {
            String selectData = "SELECT username,password FROM employee WHERE username = ? AND password = ?";
            connect = database.conectDB();
            try{
                prepare = connect.prepareStatement(selectData);
                prepare.setString(1, si_username.getText());
                prepare.setString(2, si_password.getText());

                result = prepare.executeQuery();

                if(result.next()){

                    data.username = si_username.getText();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText("");
                    alert.setContentText("Successfully logged in.");
                    alert.showAndWait();

                    Parent root = FXMLLoader.load(getClass().getResource("main-form.fxml"));

                    Stage stage = new Stage();
                    Scene scene = new Scene(root);

                    stage.setTitle("Caffe Shop Management System");
                    stage.setMinWidth(1100);
                    stage.setMinHeight(600);

                    stage.setScene(scene);
                    stage.show();

                    si_loginBtn.getScene().getWindow().hide();

                }else{
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText("");
                    alert.setContentText("Incorrect Username/Password.");
                    alert.showAndWait();
                }
            }catch (Exception e){e.printStackTrace();}
        }
    }
    public void regBtn(){
        if(su_username.getText().isEmpty() || su_password.getText().isEmpty() || su_question.getSelectionModel().getSelectedItem() == null || su_answer.getText().isEmpty()){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText("");
            alert.setContentText("Please fill-in the blank fields.");
            alert.showAndWait();
        } else {
            String regData = "INSERT INTO employee (username, password, question, answer, date) " + "VALUES (?,?,?,?,?)";
            connect = database.conectDB();
            try{

                String checkUsername = "SELECT username FROM employee WHERE username = '" + su_username.getText() + "'";

                prepare = connect.prepareStatement(checkUsername);
                result = prepare.executeQuery();

                if(result.next()){
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText("");
                    alert.setContentText("Username '" + su_username.getText() + "' is already taken.");
                    alert.showAndWait();
                }else if(su_password.getText().length() < 8){
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText("");
                    alert.setContentText("Password must be at least 8 characters long.");
                    alert.showAndWait();
                }else {
                    prepare = connect.prepareStatement(regData);
                    prepare.setString(1, su_username.getText());
                    prepare.setString(2, su_password.getText());
                    prepare.setString(3, (String)su_question.getSelectionModel().getSelectedItem());
                    prepare.setString(4, su_answer.getText());

                    Date date = new Date();
                    java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                    prepare.setString(5, String.valueOf(sqlDate));
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
                }

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

    public void switchForgotPass(){
        fp_questionForm.setVisible(true);
        si_loginForm.setVisible(false);

        forgotPassQuestionList();
    }

    public void proceedBtn(){
        if(fp_username.getText().isEmpty() || fp_question.getSelectionModel().getSelectedItem() == null || fp_answer.getText().isEmpty()){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText("");
            alert.setContentText("Please fill-in the blank fields.");
            alert.showAndWait();
        }else{
            String selectData = "SELECT username, question, answer FROM employee WHERE username=? AND question=? AND answer=?";
            connect = database.conectDB();

            try{
                prepare = connect.prepareStatement(selectData);
                prepare.setString(1, fp_username.getText());
                prepare.setString(2, (String)fp_question.getSelectionModel().getSelectedItem());
                prepare.setString(3, fp_answer.getText());

                result = prepare.executeQuery();

                if(result.next()){
                    np_newPassForm.setVisible(true);
                    fp_questionForm.setVisible(false);
                }else{
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText("");
                    alert.setContentText("Incorrect information.");
                    alert.showAndWait();
                }
            }catch (Exception e){e.printStackTrace();}

        }
    }

    public void changePassBtn(){
        if(np_newPassword.getText().isEmpty() || np_confirmPassword.getText().isEmpty()){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText("");
            alert.setContentText("Please fill-in the blank fields.");
            alert.showAndWait();
        }else if(np_newPassword.getText().length() < 8){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText("");
            alert.setContentText("New password must be at least 8 characters long.");
            alert.showAndWait();
        }else{
            if(np_newPassword.getText().equals(np_confirmPassword.getText())){
                String getDate = "SELECT date FROM employee WHERE username = '"
                        + fp_username.getText() +"'";

                connect = database.conectDB();

                try {
                    prepare = connect.prepareStatement(getDate);
                    result = prepare.executeQuery();

                    String date ="";
                    if(result.next()){
                        date = result.getString("date");
                    }

                    String updatePass = "UPDATE employee SET password = '"
                            + np_newPassword.getText() +"', question ='"
                            + fp_question.getSelectionModel().getSelectedItem() +"', answer = '"
                            + fp_answer.getText() +"', date = '"
                            + date +"' WHERE username = '"
                            + fp_username.getText() +"'";

                    prepare = connect.prepareStatement(updatePass);
                    prepare.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("INFORMATION Message");
                    alert.setHeaderText("");
                    alert.setContentText("Successfully changed password.");
                    alert.showAndWait();

                    si_loginForm.setVisible(true);
                    np_newPassForm.setVisible(false);

                    np_confirmPassword.setText("");
                    np_newPassword.setText("");
                    fp_question.getSelectionModel().clearSelection();
                    fp_answer.setText("");
                    fp_username.setText("");

                }catch (Exception e){e.printStackTrace();}
            }else{
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText("");
                alert.setContentText("Confirmation password doesn't match.");
                alert.showAndWait();
            }
        }
    }

    public void backToLoginForm(){
        si_loginForm.setVisible(true);
        fp_questionForm.setVisible(false);
    }
    public void backToQuestionForm(){
        fp_questionForm.setVisible(true);
        np_newPassForm.setVisible(false);
    }

    public void forgotPassQuestionList(){
        List<String> listQ = new ArrayList<>();
        for(String data: questionList){
            listQ.add(data);
        }
        ObservableList listData = FXCollections.observableArrayList(listQ);
        fp_question.setItems(listData);
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

                fp_questionForm.setVisible(false);
                si_loginForm.setVisible(true);
                np_newPassForm.setVisible(false);

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

                fp_questionForm.setVisible(false);
                si_loginForm.setVisible(true);
                np_newPassForm.setVisible(false);
            });

            slider.play();
        }

    }
}