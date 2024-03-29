package ApplicationLogicLayer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import Data_Access_Layer_DAL.DatabaseConnection;
import ApplicationLogicLayer.CodeAcademyMaxLouisAPP;
import Domain.Inschrijving;
import TestMethode.DateTools;

public class AdjustInschrijving {

    @FXML
    private Label achternaamCursistLabel;

    @FXML
    private Button addInschrijvingButton;

    @FXML
    private Button backToOverview;

    @FXML
    private ChoiceBox<String> choiceCursus;

    @FXML
    private ChoiceBox<String> choiceEmail;

    @FXML
    private Label cursusNaamLabel;

    @FXML
    private TextField dag;

    @FXML
    private Label emailCursistLabel;

    @FXML
    private Label errorAdjustInschrijving;

    @FXML
    private Label inschrijfDatumLabel;

    @FXML
    private TextField jaar;

    @FXML
    private TextField maand;

    @FXML
    private Label voornaamCursistLabel;

    private DatabaseConnection databaseConnection;

    private Inschrijving selectedInschrijving;
    



    @FXML
    void adjustInschrijving(ActionEvent event) throws IOException {
        if (selectedInschrijving != null) {
            String selectedCursus = choiceCursus.getValue();
            String selectedEmail = choiceEmail.getValue();


            int day, month, year;

            try {
                day = Integer.parseInt(dag.getText());
                month = Integer.parseInt(maand.getText());
                year = Integer.parseInt(jaar.getText());
            } catch (NumberFormatException e) {
                errorAdjustInschrijving.setText("Vul geldige getallen in voor dag, maand en jaar.");
                return;
            }
            
            // Inschrijfdatum 1 maken
            LocalDate inschrijfDatum;

            try {
                // Gebruik DateTools om de geldigheid van de inschrijfdatum te controleren
                if (!DateTools.validateAndCheckFutureDate(year, month, day)) {
                    errorAdjustInschrijving.setText(" " + "Ongeldige inschrijfdatum. \n Datum moet vandaag of in de toekomst liggen.");
                    return;
                }

                inschrijfDatum = LocalDate.of(year, month, day);
            } catch (IllegalArgumentException e) {
                errorAdjustInschrijving.setText("Vul een geldige inschrijfdatum in.");
                return;
            }
            
            
            // Validatie checks 
            if (selectedCursus == null || selectedEmail == null || dag.getText().isEmpty() || maand.getText().isEmpty() || jaar.getText().isEmpty()) {
                errorAdjustInschrijving.setText("Vul alle velden in.");
                return;
            }

            if (!validateInput(selectedCursus, selectedEmail,  day, month, year)) {
                return;
            }

            updateInschrijvingInDatabase(selectedCursus, selectedEmail, inschrijfDatum);

            try {
                backToOverview(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
        

        }
    }
       

    private void updateInschrijvingInDatabase(String selectedCursus, String selectedEmail, LocalDate inschrijfDatum) {
        DatabaseConnection dbConnection = new DatabaseConnection();
        try {
            String updateQuery = "UPDATE Inschrijving SET CursusNaam = ?, Email = ?, InschrijfDatum = ? ";
            PreparedStatement preparedStatement = dbConnection.prepareStatement(updateQuery);
            preparedStatement.setString(1, selectedCursus);
            preparedStatement.setString(2, selectedEmail);
            preparedStatement.setObject(3, inschrijfDatum);           

    
            int rowsAffected = preparedStatement.executeUpdate();
    
            if (rowsAffected > 0) {
                // Succesvol aangepast
            } else {
                errorAdjustInschrijving.setText("Fout bij aanpassen inschrijving. \n Controleer de invoer.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            errorAdjustInschrijving.setText("Fout bij aanpassen inschrijving. \n Controleer de invoer.");
        } finally {
            dbConnection.closeConnection();
        }
    }
    
    
    @FXML
    void backToOverview(ActionEvent event) throws IOException {
        CodeAcademyMaxLouisAPP m = new CodeAcademyMaxLouisAPP();
        m.changeScene("/FXML/Registrations.fxml");
    }


    public void setInschrijvingData(Inschrijving inschrijving) {
        selectedInschrijving = inschrijving;
    

        choiceCursus.setItems(FXCollections.observableArrayList(getCursusOptions(inschrijving.getEmail().getEmail())));
        choiceCursus.setValue(inschrijving.getCursusNaam().getCursusNaam());
    

        choiceEmail.setValue(inschrijving.getEmail().getEmail());
    
    

        dag.setText(String.valueOf(inschrijving.getInschrijfDatum().getDayOfMonth()));
        maand.setText(String.valueOf(inschrijving.getInschrijfDatum().getMonthValue()));
        jaar.setText(String.valueOf(inschrijving.getInschrijfDatum().getYear()));
    }

    private boolean validateInput(String selectedCursus, String selectedEmail,int day, int month, int year) {
        if (selectedCursus == null || selectedCursus.trim().isEmpty()) {
            errorAdjustInschrijving.setText("Selecteer een cursus.");
            return false;
        }

        if (selectedEmail == null || selectedEmail.trim().isEmpty()) {
            errorAdjustInschrijving.setText("Selecteer een e-mailadres.");
            return false;
        }

        if (day < 1 || day > 31 || month < 1 || month > 12 || year < 1900) {
            errorAdjustInschrijving.setText("Voer een geldige datum in.");
            return false;
        }

        return true;
    }

    //Cursus options uit database
    private ObservableList<String> getCursusOptions(String selectedEmail) {
        DatabaseConnection dbConnection = new DatabaseConnection();
        ObservableList<String> cursusOptions = FXCollections.observableArrayList();

        try {
            String query = "SELECT CursusNaam FROM Cursus " +
                    "WHERE CursusNaam NOT IN " +
                    "(SELECT CursusNaam FROM Inschrijving WHERE Email = ?)";
            PreparedStatement preparedStatement = dbConnection.prepareStatement(query);
            preparedStatement.setString(1, selectedEmail);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String cursusNaam = resultSet.getString("CursusNaam");
                cursusOptions.add(cursusNaam);
            }

            // Voeg de geselecteerde cursusnaam als eerste toe
            if (selectedInschrijving != null && selectedInschrijving.getCursusNaam() != null) {
                String geselecteerdeCursusNaam = selectedInschrijving.getCursusNaam().getCursusNaam();
                cursusOptions.add(0, geselecteerdeCursusNaam);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbConnection.closeConnection();
        }

        return cursusOptions;
    }

    // E-mail opties uit de database
    private ObservableList<String> getEmailOptionsFromDatabase() {
        DatabaseConnection dbConnection = new DatabaseConnection();
        ObservableList<String> emailOptions = FXCollections.observableArrayList();

        try {
            String query = "SELECT DISTINCT Email FROM Inschrijving";
            ResultSet resultSet = dbConnection.executeSQLSelectStatement(query);

            while (resultSet.next()) {
                String email = resultSet.getString("Email");
                emailOptions.add(email);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbConnection.closeConnection();
        }

        return emailOptions;
    }

    
    

    @FXML
    void initialize() {
        databaseConnection = new DatabaseConnection();

        ObservableList<String> emailOptions = getEmailOptionsFromDatabase();
        choiceEmail.setItems(emailOptions);

        if (selectedInschrijving != null) {
            if (selectedInschrijving.getEmail() != null && selectedInschrijving.getEmail().getEmail() != null) {
                choiceEmail.setValue(selectedInschrijving.getEmail().getEmail());
            }

            choiceEmail.setDisable(false);
            choiceEmail.setVisible(true);


            choiceEmail.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    choiceCursus.setItems(FXCollections.observableArrayList(getCursusOptions(newValue)));
                    setSelectedValues(newValue);

        }});
        }
    

        // Schakel de ChoiceBox voor email uit
        choiceEmail.setDisable(true);
        choiceEmail.setStyle("-fx-opacity: 3; -fx-text-fill: black;");

        databaseConnection.closeConnection();
    }

    private void setSelectedValues(String email) {
        ObservableList<String> cursusOptions = getCursusOptions(email);
        if (!cursusOptions.isEmpty()) {
            choiceCursus.setItems(cursusOptions);
            choiceCursus.setValue(cursusOptions.get(0));
        }
    
}
}
