package ApplicationLogicLayer;

import java.io.IOException;
import java.time.LocalDate;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import Data_Access_Layer_DAL.DatabaseConnection;
import ApplicationLogicLayer.CodeAcademyMaxLouisAPP;
import TestMethode.DateTools;
import TestMethode.GeslachtValidatie;
import TestMethode.MailTools;
import TestMethode.PostalCode;

public class AddNewCursist {

    @FXML
    private TextField land;

    @FXML
    private TextField achternaam;

    @FXML
    private Button addCursistButton;

    @FXML
    private Button backToOverview;

    @FXML
    private TextField email;

    @FXML
    private TextField dag;

    @FXML
    private TextField jaar;

    @FXML
    private TextField maand;

    @FXML
    private TextField geslacht;

    @FXML
    private TextField huisnummer;

    @FXML
    private TextField postcode;

    @FXML
    private TextField straatnaam;

    @FXML
    private TextField voornaam;

    @FXML
    private TextField woonplaats;

    @FXML
    private Label errorAddCursist;

    private boolean cursistToegevoegd = false;

    CodeAcademyMaxLouisAPP m = new CodeAcademyMaxLouisAPP();
    DatabaseConnection databaseConnection = new DatabaseConnection();

    @FXML
    void addNewCursist(ActionEvent event) throws IOException {
        System.out.println("Adding new cursist...");
        addCursist();

        if (cursistToegevoegd) {
            m.changeScene("/FXML/CursistenOverzicht.fxml");
        }
    }

    @FXML
    void backToOverview(ActionEvent event) throws IOException {
        m.changeScene("/FXML/CursistOverview.fxml");
    }

    @FXML
    public void addCursist() {
        String userEmail = email.getText();
        String userVoornaam = voornaam.getText();
        String userAchternaam = achternaam.getText();
        String userGeslacht = geslacht.getText();
        String userStraatnaam = straatnaam.getText();
        String userHuisnummer = huisnummer.getText();
        String userPostcode = postcode.getText();
        String userWoonplaats = woonplaats.getText();
        String userLand = land.getText();

        if (userEmail.isEmpty() || userVoornaam.isEmpty() || userAchternaam.isEmpty()
                || userGeslacht.isEmpty() || userStraatnaam.isEmpty() || userHuisnummer.isEmpty()
                || userPostcode.isEmpty() || userWoonplaats.isEmpty() || userLand.isEmpty()) {
            errorAddCursist.setText("Vul alle velden in.");
            return;
        }

        // Validate email
        if (!MailTools.validateMailAddress(userEmail)) {
            errorAddCursist.setText("Vul een geldig e-mailadres in.");
            return;
        }

        // Validate geslacht (M, V of X)
        if (!GeslachtValidatie.validateGeslacht(userGeslacht)) {
            errorAddCursist.setText("Vul M, V of X in als geslacht.");
            return;
        }

        // Validate postcode (in het formaat XXXX AB)
        try {
            String formattedPostalCode = PostalCode.formatPostalCode(userPostcode);
        } catch (IllegalArgumentException e) {
            errorAddCursist.setText(e.getMessage());
            return;
        }

        int updatedDag, updatedMaand, updatedJaar;
        try {
            updatedDag = Integer.parseInt(dag.getText());
            updatedMaand = Integer.parseInt(maand.getText());
            updatedJaar = Integer.parseInt(jaar.getText());
        } catch (NumberFormatException e) {
            errorAddCursist.setText("Vul geldige getallen in voor dag, maand en jaar.");
            return;
        }

        if (updatedDag == 0 || updatedMaand == 0 || updatedJaar == 0) {
            errorAddCursist.setText("Vul alle velden van de geboortedatum in.");
            return;
        }

        // 3 velden tot in geboortedatum maken
        LocalDate userGeboortedatum;
        try {
            if (!DateTools.validateDate(updatedDag, updatedMaand, updatedJaar)) {
                throw new IllegalArgumentException("Vul een geldige geboortedatum in.");
            }

            userGeboortedatum = LocalDate.of(updatedJaar, updatedMaand, updatedDag);

            if (userGeboortedatum.isAfter(LocalDate.now())) {
                errorAddCursist.setText("Vul een geldige geboortedatum in.");
                return;
            }
        } catch (IllegalArgumentException e) {
            errorAddCursist.setText("Vul een geldige geboortedatum in.");
            return;
        }

        // Insert statement
        String query = "INSERT INTO Cursist (email, voornaam, achternaam, geboortedatum, geslacht, straatnaam, huisnummer, postcode, woonplaats, land) "
                + "VALUES ('" + userEmail + "', '" + userVoornaam + "', '" + userAchternaam + "', '" + userGeboortedatum
                + "', '"
                + userGeslacht + "', '" + userStraatnaam + "', '" + userHuisnummer + "', '" + userPostcode + "', '"
                + userWoonplaats + "', '" + userLand + "')";

        if (databaseConnection.openConnection()) {
            int rowsAffected = databaseConnection.executeSQLUpdateStatement(query);

            if (rowsAffected > 0) {
                System.out.println("Cursist added successfully!");
                cursistToegevoegd = true;
            } else {
                System.out.println("Failed to add cursist.");
                cursistToegevoegd = false;
            }

            databaseConnection.closeConnection();
        } else {
            System.out.println("Failed to open database connection.");
            cursistToegevoegd = false;
        }
    }
}
