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
    private Label certificaatLabel;

    @FXML
    private Label certificaatLabelTwee;

    @FXML
    private Label certificaatLabelDrie;

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

    private void labelsVanCursussen() {
        if (connection.openConnection()) {
            try {
                String queryCursus = "Select TOP (3) CursusNaam, Count(*) as aantal_certificaten " +
                        "FROM Inschrijving " +
                        "Where Beoordeling >= 5.5 " +
                        "Group by CursusNaam " +
                        "Order by aantal_certificaten DESC";

                ResultSet resultSet = connection.executeSQLSelectStatement(queryCursus);

                // Maak arrays om de gegevens op te slaan
                String[] cursusNamen = new String[3];
                int[] certificaten = new int[3];

                int index = 0;
                while (resultSet.next() && index < 3) {
                    cursusNamen[index] = resultSet.getString("CursusNaam");
                    certificaten[index] = resultSet.getInt("aantal_certificaten");
                    index++;
                }

                // Vul de labels in op basis van de juiste volgorde
                cursusNaamLabel.setText(cursusNamen[0]);
                certificaatLabel.setText(certificaten[0] + " behaalde certificaten");

                cursusNaamLabelNummerTwee.setText(cursusNamen[1]);
                certificaatLabelTwee.setText(certificaten[1] + " behaalde certificaten");

                cursusNaamLabelNummerDrie.setText(cursusNamen[2]);
                certificaatLabelDrie.setText(certificaten[2] + " behaalde certificaten");

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                connection.closeConnection();
            }
        }
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
                ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                        new PieChart.Data(cursusNamenWebcast[0], aantalBekeken[0]),
                        new PieChart.Data(cursusNamenWebcast[1], aantalBekeken[1]),
                        new PieChart.Data(cursusNamenWebcast[2], aantalBekeken[2]));
                pieChartWebcasts.setData(pieChartData);
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                connection.closeConnection();
            }
        }
    }
}