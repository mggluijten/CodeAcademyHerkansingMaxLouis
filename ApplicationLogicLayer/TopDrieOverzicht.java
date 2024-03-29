package ApplicationLogicLayer;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import Data_Access_Layer_DAL.DatabaseConnection;
import ApplicationLogicLayer.CodeAcademyMaxLouisAPP;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class TopDrieOverzicht {


    @FXML
    private Label cursusNaamLabel;

    @FXML
    private Label cursusNaamLabelNummerDrie;

    @FXML
    private Label cursusNaamLabelNummerTwee;

    @FXML
    private Label cursistLabel;

    @FXML
    private Label cursistLabelDrie;

    @FXML
    private Label cursistLabelTwee;

    @FXML
    private Button cursusOverzichtButton;

    @FXML
    private Button menuButton;

    @FXML
    private Label webcastLabel;

    @FXML
    private Label webcastLabelDrie;

    @FXML
    private Label webcastLabelTwee;

    @FXML
    private PieChart pieChartWebcasts;

    CodeAcademyMaxLouisAPP m = new CodeAcademyMaxLouisAPP();
    DatabaseConnection connection = new DatabaseConnection();

    @FXML
    void backToCursusOverzicht(ActionEvent event) throws IOException {
        m.changeScene("/FXML/CursusOverview.fxml");

    }

    @FXML
    void backToMenu(ActionEvent event) throws IOException {

        m.changeScene("/FXML/MenuScreen.FXML");

    }

    @FXML
    void initialize() {
        labelsVanWebcast();
        piechartVanWebcast();
    }

  

    private void labelsVanWebcast() {
        if (connection.openConnection()) {
            try {
                String queryWebcast = "SELECT TOP (3) Inschrijving.CursusNaam, Count(*) as meest_bekeken " +
                        "FROM Inschrijving " +
                        "JOIN ContentItem ON ContentItem.CursusNaam = Inschrijving.CursusNaam " +
                        "WHERE ContentItem.ModuleID IS NULL " +
                        "GROUP BY Inschrijving.CursusNaam " +
                        "ORDER BY meest_bekeken DESC";

                ResultSet resultSet = connection.executeSQLSelectStatement(queryWebcast);

                // Maak arrays om de gegevens op te slaan
                String[] cursusNamenWebcast = new String[3];
                int[] aantalBekeken = new int[3];

                int index = 0;
                while (resultSet.next() && index < 3) {
                    cursusNamenWebcast[index] = resultSet.getString("CursusNaam");
                    aantalBekeken[index] = resultSet.getInt("meest_bekeken");
                    index++;
                }

                // Vul de labels in op basis van de juiste volgorde
                webcastLabel.setText(cursusNamenWebcast[0]);
                cursistLabel.setText(aantalBekeken[0] + " keer bekeken");

                webcastLabelTwee.setText(cursusNamenWebcast[1]);
                cursistLabelTwee.setText(aantalBekeken[1] + " keer bekeken");

                webcastLabelDrie.setText(cursusNamenWebcast[2]);
                cursistLabelDrie.setText(aantalBekeken[2] + " keer bekeken");

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                connection.closeConnection();
            }
        }
    }
    

    private void piechartVanWebcast() {
        if (connection.openConnection()) {
            try {
                // Your SQL query remains unchanged.
                String queryWebcast = "SELECT TOP (3) Inschrijving.CursusNaam, Count(*) as meest_bekeken " +
                        "FROM Inschrijving " +
                        "JOIN ContentItem ON ContentItem.CursusNaam = Inschrijving.CursusNaam " +
                        "WHERE ContentItem.ModuleID IS NULL " +
                        "GROUP BY Inschrijving.CursusNaam " +
                        "ORDER BY meest_bekeken DESC";
    
                ResultSet resultSet = connection.executeSQLSelectStatement(queryWebcast);
    
                // Preparing data for the pie chart.
                ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
    
                int index = 0;
                while (resultSet.next() && index < 3) {
                    // Fetch the name and the view count.
                    String cursusNaam = resultSet.getString("CursusNaam");
                    int aantalBekeken = resultSet.getInt("meest_bekeken");
    
                    // Add the fetched data as a new pie chart data point.
                    // Note: You can modify the string format as needed to display additional details.
                    pieChartData.add(new PieChart.Data(String.format("%d. %s (%d views)", index + 1, cursusNaam, aantalBekeken), aantalBekeken));
                    index++;
                }
    
                // Set the prepared data to the pie chart.
                pieChartWebcasts.setData(pieChartData);
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                connection.closeConnection();
            }
        }
    }
    
}