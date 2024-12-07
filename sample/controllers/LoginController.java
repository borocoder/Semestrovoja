package sample.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sample.DatabaseHandler;
import sample.User;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {

    @FXML
    private Button signUpButton;

    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button authButton;

    @FXML
    private Text loginMessage;

    @FXML
    void initialize() {
        authButton.setOnAction(actionEvent -> {
            String loginText = loginField.getText();
            String passwordText = passwordField.getText();

            loginUser(loginText,passwordText);
        });

        signUpButton.setOnAction(event -> {
            signUpButton.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/sample/scenes/signUp.fxml"));

            try{
                loader.load();
            }catch (IOException e){
                e.printStackTrace();
            }

            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.showAndWait();
        });
    }

    private void loginUser(String loginText, String passwordText) {
        DatabaseHandler databaseHandler = new DatabaseHandler();
        User user = new User();
        user.setUsername(loginText);
        user.setPassword(passwordText);

        ResultSet resultSet = databaseHandler.getUSer(user);

        int counter = 0;

        try {
            while (resultSet.next())
                counter++;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (counter >= 1){
            authButton.getScene().getWindow().hide();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/sample/scenes/home.fxml"));

            try{
                loader.load();
            }catch (IOException e){
                e.printStackTrace();
            }

            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.showAndWait();
        }
        else if(loginText.equals("") || passwordText.equals(""))
            loginMessage.setText("Login or password is empty!");
        else
            loginMessage.setText("Login or password is incorrect!");
    }
}
