package ApplicationLogicLayer;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Data_Access_Layer_DAL.DatabaseConnection;
import ApplicationLogicLayer.CodeAcademyMaxLouisAPP;
import Domain.Medewerker;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;


public class LogIn {
    private DatabaseConnection connection;

    @FXML
    private Button turnOffButton;

    @FXML
    private TextField email;

    @FXML
    private Label fouteLogin;

    @FXML
    private Button loginButton;

    @FXML
    private ImageView logoCodecademy;

    @FXML
    private PasswordField wachtwoord;

    @FXML
    void userTurnOff(ActionEvent event) {
        Platform.exit();
    }


    public LogIn() {
        connection = new DatabaseConnection();
        connection.openConnection(); 
    }

    @FXML
    public void userLogin(ActionEvent event) throws IOException {
        checkLogin();
    }

    private void checkLogin() throws IOException {
        CodeAcademyMaxLouisAPP m = new CodeAcademyMaxLouisAPP();
    
        String inputEmail = email.getText().trim();
        String inputWachtwoord = wachtwoord.getText().trim();
    
        DatabaseConnection connection = new DatabaseConnection();
    
        try {
            if (connection.openConnection()) {
                try (PreparedStatement preparedStatement = connection.getConnection().prepareStatement(
                        "SELECT * FROM Medewerker WHERE Email = ? AND Wachtwoord = ?")) {
                    preparedStatement.setString(1, inputEmail);
                    preparedStatement.setString(2, inputWachtwoord);
    
                    try (ResultSet resultSet = preparedStatement.executeQuery()) {
                        if (resultSet.next()) {
                            String naamMedewerker = resultSet.getString("NaamMedewerker");
                            fouteLogin.setText("Succesvol ingelogd");
                            Medewerker ingelogdeMedewerker = new Medewerker(naamMedewerker);
                            CodeAcademyMaxLouisAPP.setIngelogdeMedewerker(ingelogdeMedewerker);
                            m.changeScene("/FxML/MenuScreen.fxml");
                        } else if (inputEmail.isEmpty() || inputWachtwoord.isEmpty()) {
                            fouteLogin.setText("Vul email en/of wachtwoord in.");
                        } else {
                            fouteLogin.setText("Gebruikersnaam en/of wachtwoord zijn onjuist.");
                        }
                    }
                } catch (SQLException e) {
                    System.out.println("Databasefout: " + e.getMessage());
                }
            } else {
                System.out.println("Kon geen verbinding maken met de database.");
            }
        } finally {
            connection.closeConnection();
        }
    }         
}




