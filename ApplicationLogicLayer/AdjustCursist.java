package ApplicationLogicLayer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import Data_Access_Layer_DAL.DatabaseConnection;
import Domain.Cursist;
import Domain.GeslachtCursist;
import TestMethode.DateTools;
import TestMethode.GeslachtValidatie;
import TestMethode.MailTools;
import TestMethode.PostalCode;
import ApplicationLogicLayer.CodeAcademyMaxLouisAPP;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AdjustCursist {
    private boolean cursistBewerkt = false;

    @FXML
    private TextField land;

    @FXML
    private TextField achternaam;

    @FXML
    private Button adjustCursistButton;

    @FXML
    private Button backToOverview;

    @FXML
    private TextField email;

    @FXML
    private Label errorAdjustCursist;

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

    private Cursist selectedCursist;

    CodeAcademyMaxLouisAPP m = new CodeAcademyMaxLouisAPP();
    DatabaseConnection databaseConnection = new DatabaseConnection();

    @FXML
    void adjustCursist(ActionEvent event) throws IOException {
        System.out.println("Adjust cursist...");
        adjustCursist();

        if (cursistBewerkt) {

            m.changeScene("/FXML/CursistOverview.fxml");
        }
    }

    // Methode om cursistgegevens in te stellen
    public void setCursistData(Cursist cursist) {
        selectedCursist = cursist;


        email.setText(cursist.getEmail() != null ? cursist.getEmail() : "");
        voornaam.setText(cursist.getVoornaam() != null ? cursist.getVoornaam() : "");
        achternaam.setText(cursist.getAchternaam() != null ? cursist.getAchternaam() : "");

        LocalDate geboortedatumValue = cursist.getGeboortedatum();
        if (geboortedatumValue != null) {
            dag.setText(String.valueOf(geboortedatumValue.getDayOfMonth()));
            maand.setText(String.valueOf(geboortedatumValue.getMonthValue()));
            jaar.setText(String.valueOf(geboortedatumValue.getYear()));
        }
   

        geslacht.setText(cursist.getGeslacht() != null ? cursist.getGeslacht().toString() : "");
        straatnaam.setText(cursist.getStraatnaam() != null ? cursist.getStraatnaam() : "");
        huisnummer.setText(cursist.getHuisnummer() != null ? cursist.getHuisnummer() : "");
        postcode.setText(cursist.getPostcode() != null ? cursist.getPostcode() : "");
        woonplaats.setText(cursist.getWoonplaats() != null ? cursist.getWoonplaats() : "");
        land.setText(cursist.getLand() != null ? cursist.getLand() : "");
    }



    //Methode cursis aanpassen database
    public void adjustCursist(){
        if (selectedCursist != null) {

            String updatedEmail = email.getText();
            String updatedVoornaam = voornaam.getText();
            String updatedAchternaam = achternaam.getText();

   
            int updatedDag, updatedMaand, updatedJaar;
            try {
                updatedDag = Integer.parseInt(dag.getText());
                updatedMaand = Integer.parseInt(maand.getText());
                updatedJaar = Integer.parseInt(jaar.getText());
            } catch (NumberFormatException e) {
                errorAdjustCursist.setText("Vul geldige getallen in voor dag, maand en jaar.");
                return;  // Add this line to stop further processing
            }
            
            // Combineer dag, maand en jaar om een LocalDate-object te maken
            LocalDate updatedGeboortedatum;
            try {
                if (!DateTools.validateDate(updatedDag, updatedMaand, updatedJaar)) {
                    throw new IllegalArgumentException("Vul een geldige geboortedatum in.");
                }
            
                updatedGeboortedatum = LocalDate.of(updatedJaar, updatedMaand, updatedDag);
            
                if (updatedGeboortedatum.isAfter(LocalDate.now())) {
                    errorAdjustCursist.setText("Vul een geldige geboortedatum in.");
                    return;
                }
            } catch (IllegalArgumentException e) {
                errorAdjustCursist.setText("Vul een geldige geboortedatum in.");
                return;
            }

            GeslachtCursist updatedGeslachtEnum;
            try {
                updatedGeslachtEnum = GeslachtCursist.valueOf(geslacht.getText().toUpperCase());
            } catch (IllegalArgumentException e) {
                errorAdjustCursist.setText("Vul M, V of X in als geslacht.");
                return;
            }                

            String updatedGeslacht = geslacht.getText();
            String updatedStraatnaam = straatnaam.getText();
            String updatedHuisnummer = huisnummer.getText();
            String updatedPostcode = postcode.getText();
            String updatedWoonplaats = woonplaats.getText();
            String updatedLand = land.getText();

            // Voer de validatiechecks uit zoals in AddNewCursist
            if (updatedEmail.isEmpty() || updatedVoornaam.isEmpty() || updatedAchternaam.isEmpty()
                    || updatedGeslacht.isEmpty() || updatedStraatnaam.isEmpty() || updatedHuisnummer.isEmpty()
                    || updatedPostcode.isEmpty() || updatedWoonplaats.isEmpty() || updatedLand.isEmpty()) {
                errorAdjustCursist.setText("Vul alle velden in.");
                return;
            }

            if (!MailTools.validateMailAddress(updatedEmail)) {
                errorAdjustCursist.setText("Vul een geldig e-mailadres in.");
                return;
            }

            // Validate geslacht (M, V of X)
            if (!GeslachtValidatie.validateGeslacht(updatedGeslacht)) {
                errorAdjustCursist.setText("Vul M, V of X in als geslacht.");
                return;
            }

            // Validate postcode  (in het formaat XXXX AB)
            try {
                String formattedPostalCode = PostalCode.formatPostalCode(updatedPostcode);
            } catch (IllegalArgumentException e) {
                errorAdjustCursist.setText(e.getMessage());
                return;
            }

            updateCursistInDatabase(updatedEmail, updatedVoornaam, updatedAchternaam, updatedGeboortedatum,
            updatedGeslachtEnum, updatedStraatnaam, updatedHuisnummer, updatedPostcode, updatedWoonplaats, updatedLand);

            try {
                backToOverview(null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }




    // Methode om cursistgegevens in de database bij te werken
    private void updateCursistInDatabase(String email, String voornaam, String achternaam, LocalDate geboortedatum,
                                        GeslachtCursist geslacht, String straatnaam, String huisnummer, String postcode,
                                        String woonplaats, String land) {
        String formattedGeboortedatum = geboortedatum.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        String query = "UPDATE Cursist SET " +
                "Voornaam = '" + voornaam + "', " +
                "Achternaam = '" + achternaam + "', " +
                "Geboortedatum = '" + formattedGeboortedatum + "', " +
                "Geslacht = '" + geslacht + "', " +
                "Straatnaam = '" + straatnaam + "', " +
                "Huisnummer = '" + huisnummer + "', " +
                "Postcode = '" + postcode + "', " +
                "Woonplaats = '" + woonplaats + "', " +
                "Land = '" + land + "' " +
                "WHERE Email = '" + email + "'";


        if (databaseConnection.openConnection()) {
            try {
                int rowsAffected = databaseConnection.executeSQLUpdateStatement(query);

                if (rowsAffected > 0) {
                    cursistBewerkt = true;
                } else {
                    cursistBewerkt = false;
                }
            } finally {
                databaseConnection.closeConnection();
            }
        } else {
            System.out.println("Kon geen verbinding maken met de database.");
            cursistBewerkt = false;
        }
    }


    @FXML
    void backToOverview(ActionEvent event) throws IOException {
        m.changeScene("/FXML/CursistOverview.fxml");
    }
}