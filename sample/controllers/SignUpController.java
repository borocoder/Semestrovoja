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

public class SignUpController {

    @FXML
    private Button createAccount;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField firstnameField;

    @FXML
    private TextField lastnameField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField usernameField;

    @FXML
    private Text signMessage;

    @FXML
    void initialize() {
        createAccount.setOnAction(actionEvent -> signUpNewUser());
    }

    private void signUpNewUser() {
        DatabaseHandler databaseHandler = new DatabaseHandler();

        String firstname = firstnameField.getText();
        String lastname = lastnameField.getText();
        String username = usernameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();

        if (firstname.isEmpty() || lastname.isEmpty() || username.isEmpty() || email.isEmpty())
            signMessage.setText("Fields should not be empty!");
        else if(password.isEmpty())
            signMessage.setText("Password must not be empty!");
        else {
            User user = new User(firstname,lastname, username,email,password);
            databaseHandler.signUpUser(user);

            createAccount.setOnAction(actionEvent -> {
                createAccount.getScene().getWindow().hide();
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
            });
        }
    }
}