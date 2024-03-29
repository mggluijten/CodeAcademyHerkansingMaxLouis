package ApplicationLogicLayer;



import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import Data_Access_Layer_DAL.DatabaseConnection;
import ApplicationLogicLayer.CodeAcademyMaxLouisAPP;
import Domain.Cursist;
import Domain.Cursus;
import Domain.Inschrijving;
import Domain.Medewerker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class InschrijvingenOverzicht {



    @FXML
    private TableView<Inschrijving> inschrijvingTable;

 

    @FXML
    private Button addInschrijving;

 

    @FXML
    private Button adjustInschrijving;

    @FXML
    private Button backToMenu;

  

    @FXML
    private Label inschrijvingErrorLabel;

    @FXML
    private TableColumn<Cursus, String> inschrijvingCursusNaamColumn;

    @FXML
    private TableColumn<Cursist, String> inschrijvingEmailColumn;

    @FXML
    private TableColumn<Inschrijving, LocalDate> inschrijvingInschrijfDatumColumn;



    @FXML
    private Button removeInschrijving;

    CodeAcademyMaxLouisAPP m = new CodeAcademyMaxLouisAPP();
    DatabaseConnection databaseConnection = new DatabaseConnection();


    @FXML
    void addInschrijving(ActionEvent event) throws IOException {
        m.changeScene("/FXML/AddRegistration.fxml");
    }

 


    @FXML
    void adjustInschrijving(ActionEvent event) throws IOException {
        Inschrijving selectedInschrijving = inschrijvingTable.getSelectionModel().getSelectedItem();

        if (selectedInschrijving != null) {
            openAdjustInschrijvingForm(selectedInschrijving);
        } else {
            // Geef een foutmelding als er geen inschrijving is geselecteerd
            inschrijvingErrorLabel.setText("Selecteer een inschrijving om aan te passen.");
        }
    }

    // Methode om het aanpassingsformulier te openen met geselecteerde inschrijving
    private void openAdjustInschrijvingForm(Inschrijving selectedInschrijving) throws IOException {
        CodeAcademyMaxLouisAPP m = new CodeAcademyMaxLouisAPP();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/AdjustInschrijving.fxml"));
        Parent root = loader.load();
        AdjustInschrijving controller = loader.getController();

        controller.setInschrijvingData(selectedInschrijving);

        m.changeSceneS(root, 1280, 800);
    }



    @FXML
    void backToMenu(ActionEvent event) throws IOException {
        m.changeScene("/FXML/Menu.fxml");
    }

   

    @FXML
    void removeInschrijving(ActionEvent event) {
        Inschrijving selectedInschrijving = inschrijvingTable.getSelectionModel().getSelectedItem();
    
        if (selectedInschrijving != null) {
            deleteInschrijving(selectedInschrijving);
            databaseConnection.openConnection();
            loadInschrijvingData(); // Update de gegevens in de tabel
            databaseConnection.closeConnection();
        } else {
            inschrijvingErrorLabel.setText("Selecteer een inschrijving om te verwijderen.");
        }
    }
    
    private void deleteInschrijving(Inschrijving inschrijving) {
        // Query om inschrijving te verwijderen uit de database
        String query = "DELETE FROM Inschrijving WHERE CursusNaam = ? AND Email = ? AND CertificaatID = ?";
    
    
        if (databaseConnection.openConnection()) {
            try (Connection connection = databaseConnection.getConnection();
                 PreparedStatement statement = connection.prepareStatement(query)) {
    
                statement.setString(1, inschrijving.getCursusNaam().getCursusNaam());
                statement.setString(2, inschrijving.getEmail().getEmail());
    
                int rowsAffected = statement.executeUpdate();
    
                if (rowsAffected > 0) {
                    System.out.println("Inschrijving succesvol verwijderd.");
                } else {
                    inschrijvingErrorLabel.setText("Kon inschrijving niet verwijderen.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                databaseConnection.closeConnection();
            }
        } else {
            System.out.println("Kon geen verbinding maken met de database.");
        }
    }
    
    
    
    
    

    @FXML
    private void initialize() {
    
        if (inschrijvingTable != null) {
            inschrijvingCursusNaamColumn.setCellValueFactory(new PropertyValueFactory<>("cursusNaam"));
            inschrijvingEmailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
            inschrijvingInschrijfDatumColumn.setCellValueFactory(new PropertyValueFactory<>("inschrijfDatum"));
    
            loadInschrijvingData();
        } else {
            System.err.println("inschrijvingTable is null. Check your FXML file.");
        }
    
        // Sluit de databaseverbinding nadat beide tabellen zijn geladen
        databaseConnection.closeConnection();
    }
    
    
    

    private void loadInschrijvingData() {
        try {
            String query = "SELECT * FROM Inschrijving";
            ResultSet resultSet = databaseConnection.executeSQLSelectStatement(query);
    
            if (resultSet != null) {
                ObservableList<Inschrijving> inschrijvingen = FXCollections.observableArrayList();
    
                while (resultSet.next()) {
                    String cursusNaam = resultSet.getString("CursusNaam");
                    String email = resultSet.getString("Email");
                    java.sql.Date inschrijfDatumSQL = resultSet.getDate("InschrijfDatum");
                    LocalDate inschrijfDatum = (inschrijfDatumSQL != null) ? inschrijfDatumSQL.toLocalDate() : null;
    
                    if (cursusNaam != null && email != null && inschrijfDatum != null) {
                        Cursus cursus = new Cursus(cursusNaam);
                        Cursist cursist = new Cursist(email);
    
    
                        Inschrijving inschrijving = new Inschrijving(cursus, cursist, inschrijfDatum);
                        inschrijvingen.add(inschrijving);
                    } else {
                        System.out.println("Waarschuwing: Een of meer waarden zijn null. " +
                                "Controleer de kolomnamen in de database.");
                    }
                }
    
                inschrijvingTable.setItems(inschrijvingen);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } 
    }
    

}
