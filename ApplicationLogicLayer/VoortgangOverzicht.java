package ApplicationLogicLayer;
 
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

import Data_Access_Layer_DAL.DatabaseConnection;
import ApplicationLogicLayer.CodeAcademyMaxLouisAPP;
import TestMethode.NumericRangeTools;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
 
public class VoortgangOverzicht {
    private String currentSelectedCursist;
    private String currentSelectedCursus; 
    private String previousSelectedCursist;
    private String previousSelectedCursus;
    private String selectCursusNameWithModuleID;
 
    @FXML
    private Label achternaamLabel;
 
    @FXML
    private Label cursusNaamLabel;
 
    @FXML
    private Label errorLabel;
 
    @FXML
    private Label inschrijfdatumLabel;
 
    @FXML
    private Button menuButton;
 
    @FXML
    private PieChart piechartModule;
 
    @FXML
    private PieChart piechartPercentage;
 
    @FXML
    private ChoiceBox<String> selectCursist;
 
    @FXML
    private ChoiceBox<String> selectCursus;
 
    @FXML
    private ChoiceBox<String> selectCursusWithModuleID;
 
    @FXML
    private Label voornaamLabel;
 
    CodeAcademyMaxLouisAPP m = new CodeAcademyMaxLouisAPP();
    DatabaseConnection connection = new DatabaseConnection();

    @FXML
    void backToMenu(ActionEvent event) throws IOException {
        m.changeScene("/FXML/Menu.fxml");
    }
 
    @FXML
    private void initialize() {
        currentSelectedCursist = null;
        currentSelectedCursus = null;

        updatePieChartForSelectedCursistAndCursus();
    
        // Emails in de choicebox
        ObservableList<String> selectCursistEmail = getCursistEmailFromDatabase();
        selectCursist.setItems(selectCursistEmail);

        // Geeft voor- en achternaam weer
        selectCursist.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Nieuwe voornaam wordt in de label gezet
                updateLabelsForSelectedCursist(newValue);

                // Cursusnamen in de choicebox op basis van de geselecteerde cursist
                ObservableList<String> selectCursusName = getCursusNameFromDatabase(newValue);
                selectCursus.setItems(selectCursusName);
            }
        });

        // Geeft voor- en achternaam weer
        selectCursist.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (!Objects.equals(newValue, previousSelectedCursist) && selectCursus.getValue() != null) {
                System.out.println("Updating pie chart for cursist and cursus.");
                updatePieChart(newValue, selectCursus.getValue());
                previousSelectedCursist = newValue;
            }
        });

        selectCursus.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Geeft de nieuwe cursusnaam weer
                updateLabelsForSelectedCursus(newValue);

            }

        });

        // Piechart wordt hier geüpdatet bij de geselecteerde cursus en cursist
        selectCursist.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (!Objects.equals(newValue, previousSelectedCursist) && selectCursus.getValue() != null) {
                updatePieChart(newValue, selectCursus.getValue());
                previousSelectedCursist = newValue;
            }
        });

        selectCursus.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (!Objects.equals(newValue, previousSelectedCursus) && selectCursist.getValue() != null) {
                updatePieChartForSelectedCursistAndCursus();
                previousSelectedCursus = newValue;
            }
        });



        //Voor de totale lengte in de choicebox en voor de piechartModule
        selectCursusWithModuleID.setItems(getCursusNameWithModuleIDFromDatabase()); 

        selectCursusWithModuleID.setOnAction(event -> {
            String selectedCursusModuleID = selectCursusWithModuleID.getValue();
            updatePieChartModule(selectedCursusModuleID);
        });
    }
      

    private void updateLabelsForSelectedCursist(String selectedCursistEmail) {
        String[] names = getCursistNameFromDatabase(selectedCursistEmail);
        if (names.length == 3) {
            voornaamLabel.setText(names[0]);
            achternaamLabel.setText(names[1]);
            inschrijfdatumLabel.setText(names[2]);
    
            // Reset de geselecteerde cursus wanneer een nieuwe cursist wordt geselecteerd
            selectCursus.setValue(null);
        }
    }
    

    private void updateLabelsForSelectedCursus(String selectedCursusName) {
        String[] names = getCursusFromDatabase(selectedCursusName);
        if (names.length == 1) {
            cursusNaamLabel.setText(names[0]);

        }
    }

    private ObservableList<String> getCursistEmailFromDatabase() {
        ObservableList<String> cursistenList = FXCollections.observableArrayList();

        try {
            if (connection.openConnection()) {
                String query = "SELECT DISTINCT Email FROM Inschrijving " + "ORDER BY Email;";
                ResultSet resultSet = connection.executeSQLSelectStatement(query);

                while (resultSet.next()) {
                    String cursist = resultSet.getString("Email");
                    cursistenList.addAll(cursist);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.closeConnection();
        }

        return cursistenList;
    }

    private String[] getCursistNameFromDatabase(String selectedCursistEmail) {
        String[] names = new String[3];

        try {
            if (connection.openConnection()) {
                String query = "SELECT Cursist.Voornaam, Cursist.Achternaam, Inschrijving.InschrijfDatum FROM Cursist "
                        + "JOIN Inschrijving ON Inschrijving.Email = Cursist.Email WHERE Cursist.Email = '"
                        + selectedCursistEmail + "'";

                ResultSet resultSet = connection.executeSQLSelectStatement(query);

                if (resultSet.next()) {
                    names[0] = resultSet.getString("Voornaam");
                    names[1] = resultSet.getString("Achternaam");
                    names[2] = resultSet.getString("InschrijfDatum");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.closeConnection();
        }

        return names;
    }

    private ObservableList<String> getCursusNameFromDatabase(String selectedCursistEmail) {
        ObservableList<String> cursusList = FXCollections.observableArrayList();

        try {
            if (connection.openConnection()) {
                String query = "SELECT DISTINCT Cursus.CursusNaam FROM Cursus " +
                        "JOIN Inschrijving ON Cursus.CursusNaam = Inschrijving.CursusNaam " +
                        "WHERE Inschrijving.Email = '" + selectedCursistEmail + "'";
                ResultSet resultSet = connection.executeSQLSelectStatement(query);

                while (resultSet.next()) {
                    String cursus = resultSet.getString("CursusNaam");
                    cursusList.add(cursus);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.closeConnection();
        }

        return cursusList;
    }

    private String[] getCursusFromDatabase(String selectedCursusName) {
        String[] names = new String[1];

        try {
            if (connection.openConnection()) {
                // Correcte SQL-query zonder overbodige quote aan het einde
                String query = "SELECT CursusNaam FROM ContentItem WHERE CursusNaam = '" + selectedCursusName + "' AND ModuleID IS NOT NULL AND CursusNaam IS NOT NULL";
                ResultSet resultSet = connection.executeSQLSelectStatement(query);

                if (resultSet.next()) {
                    names[0] = resultSet.getString("CursusNaam");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.closeConnection();
        }

        return names;
    }

    // De piechart wordt bijgewerkt
    private void updatePieChart(String selectedCursistEmail, String selectedCursusName) {
        if (isModuleIDNull(selectedCursusName)) {
            // Verberg de piechart omdat het een webcast is en ModuleID null is
            if (piechartPercentage != null) {
                piechartPercentage.setData(null);
                errorLabel.setText("Geen informatie beschikbaar!");
            }
            return;
        }
    
        int percentage = getVoortgangPercentageFromDatabase(selectedCursistEmail, selectedCursusName);
    
        if (NumericRangeTools.isValidPercentage(percentage)) {
            String formattedPercentage = String.format("%.2f", (double) percentage);
    
            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                    new PieChart.Data("Voortgang\nPercentage: " + formattedPercentage + "%", percentage),
                    new PieChart.Data("Nog te doen\nPercentage: " + String.format("%.2f", 100.0 - percentage) + "%",
                            100 - percentage));
    
            if (piechartPercentage != null) {
                piechartPercentage.setData(pieChartData);
                errorLabel.setText("");
            }

        } else {
            if (piechartPercentage != null) {
                piechartPercentage.setData(null);
                errorLabel.setText("Geen informatie beschikbaar!");
            }
        }
    }
    
    //Checkt of de moduleID Null is in de database
    //Count wordt gebruikt om te bepalen of er ten minste één rij is waarvoor de cursus een webcast is en/of ModuleID null is
    private boolean isModuleIDNull(String selectedCursusName) {
    
        try {
            if (connection.openConnection()) {
                String query = "SELECT COUNT(*) AS count FROM ContentItem " +
                               "WHERE CursusNaam = '" + selectedCursusName + "' " +
                               "AND ModuleID IS NULL";
    
                ResultSet resultSet = connection.executeSQLSelectStatement(query);
    
                if (resultSet.next()) {
                    int count = resultSet.getInt("count");
                    return count > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.closeConnection();
        }
    
        return false;
    }
    

    private void updatePieChartForSelectedCursistAndCursus() {
        String selectedCursist = selectCursist.getValue();
        String selectedCursus = selectCursus.getValue();
    
        if (selectedCursist != null && selectedCursus != null) {
            resetPieChart();
            updatePieChart(selectedCursist, selectedCursus);
        }
    }


    private void resetPieChart() {
        if (piechartPercentage != null) {
            System.out.println("Resetting pie chart.");
            ObservableList<PieChart.Data> emptyData = FXCollections.observableArrayList();
            piechartPercentage.setData(emptyData);
            errorLabel.setText("");
        } else {
            System.out.println("PieChart is null!");
        }
    }
    

    // Voortgangspercentage wordt hier uit de database gehaald
    private int getVoortgangPercentageFromDatabase(String selectedCursistEmail, String selectedCursusName) {
        int percentage = 0;

        try {
            if (connection.openConnection()) {
                String query = "SELECT Voortgang.Percentage " +
                            "FROM Voortgang " +
                            "JOIN ContentItem ON Voortgang.ContentItemID = ContentItem.ContentItemID " +
                            "JOIN Cursus ON ContentItem.CursusNaam = Cursus.CursusNaam " +
                            "JOIN Inschrijving ON Voortgang.Email = Inschrijving.Email " +
                            "WHERE Cursus.CursusNaam =  '" + selectedCursusName + "' " +
                            "AND Voortgang.Email =  '" + selectedCursistEmail + "'";

                ResultSet resultSet = connection.executeSQLSelectStatement(query);

                if (resultSet.next()) {
                    percentage = resultSet.getInt("Percentage");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.closeConnection();
        }

        return percentage;
    }

    private ObservableList<String> getCursusNameWithModuleIDFromDatabase() {
        ObservableList<String> cursusNameList = FXCollections.observableArrayList();
        DatabaseConnection connection = new DatabaseConnection();
    
        try {
            if (connection.openConnection()) {
                String query = "SELECT CursusNaam FROM ContentItem WHERE ModuleID IS NOT NULL";
                ResultSet resultSet = connection.executeSQLSelectStatement(query);
    
                while (resultSet.next()) {
                    String cursusNaam = resultSet.getString("CursusNaam");
                     cursusNameList.add(cursusNaam);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.closeConnection();
        }
    
            return cursusNameList;
        }
    
    
    private void updatePieChartModule(String selectedCursusModuleID) {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
    
        try {
            if (connection.openConnection()) {
                String query = "SELECT ContentItem.ModuleID, AVG(Voortgang.Percentage) AS GemiddeldeVoortgangPercentage " +
                        "FROM ContentItem " +
                        "JOIN Inschrijving ON Inschrijving.CursusNaam = ContentItem.CursusNaam " +
                        "JOIN Voortgang ON Voortgang.ContentItemID = ContentItem.ContentItemID " +
                        "WHERE ContentItem.ModuleID IS NOT NULL AND ContentItem.CursusNaam = '" + selectedCursusModuleID + "' " +
                        "GROUP BY ContentItem.ModuleID";
        
                ResultSet resultSet = connection.executeSQLSelectStatement(query);
        
                while (resultSet.next()) {
                    double gemiddeldeVoortgangPercentage = resultSet.getDouble("GemiddeldeVoortgangPercentage");
        
                    double remainingPercentage = 100 - gemiddeldeVoortgangPercentage;
    
                    String labelAvg = String.format("Gemiddelde: %.2f%%", gemiddeldeVoortgangPercentage);
                   String labelRemaining = String.format("Overig: %.2f%%", remainingPercentage);
    
                    pieChartData.add(new PieChart.Data(labelAvg, gemiddeldeVoortgangPercentage));
                    pieChartData.add(new PieChart.Data(labelRemaining, remainingPercentage));
                }
        
                piechartModule.setData(pieChartData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
  
}
