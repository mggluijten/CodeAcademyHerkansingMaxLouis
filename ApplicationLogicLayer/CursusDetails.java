package ApplicationLogicLayer;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import Data_Access_Layer_DAL.DatabaseConnection;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class CursusDetails {
    @FXML
    private Button backToCursusOverzichtButton;

    @FXML
    private Button backToMenuButton;

    @FXML
    private Label behaaldeCerticaatLabel;

    @FXML
    private NumberAxis yAxis;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private Label cursusNameLabel;

    @FXML
    private Label totaalCursistenLabel;

    CodeAcademyMaxLouisAPP m = new CodeAcademyMaxLouisAPP();
    DatabaseConnection connection = new DatabaseConnection();

    @FXML
    void backToCursusOverzicht(ActionEvent event) throws IOException {
        m.changeScene("/FXML/CursusOverview.fxml");
    }

    @FXML
    void backToMenu(ActionEvent event) throws IOException {
        m.changeScene("/FXML/MenuScreen.fxml");
    }

    public void setCursusNaam(String cursusNaam) {
        
        cursusNameLabel.setText(cursusNaam);
        updateBarChart(cursusNaam);
        setLabels(cursusNaam);
    }

    @FXML
    public void setLabels(String cursusNaam) {
           
    }
    
    public void updateBarChart(String cursusNaam) {
    
        try {
            if (connection.openConnection()) {
                String query = "SELECT Beoordeling, COUNT(*) as Cursisten " +
                                "FROM Inschrijving " +
                                "WHERE CursusNaam = '" + cursusNaam + "' " +
                                "GROUP BY Beoordeling";
    
                ResultSet resultSet = connection.executeSQLSelectStatement(query);
    
                // Maakt elke keer een nieuwe BarChart.Series aan voor elke Beoordeling
                XYChart.Series<String, Number> series = new XYChart.Series<>();
                series.setName("Aantal cursisten");
    
                // Voeg data toe aan de series en bereken de hoogte van de staven
                while (resultSet.next()) {
                    double beoordeling = resultSet.getDouble("Beoordeling");
    
                    // Bereken de hoogte van de staaf op basis van de beoordeling
                    double hoogte = beoordeling;
    
                    // Maakt elke keer een nieuwe XYChart.Series aan voor elke Beoordeling
                    series.getData().add(new XYChart.Data<>(String.valueOf(beoordeling), hoogte));
                }
    
                // Maak een lijst van kleuren die je wilt gebruiken voor elke beoordeling
                List<String> kleuren = Arrays.asList(
                        "#214157", "#2A5375", "#326C93", "#3B86B1", "#43A0CF",
                        "#4CB9ED", "#74C4F7", "#9CCFF2", "#C4DAFD", "#ECE8FF"
                );

               

                // Stelt de kleuren in voor elke bar in de serie
                int index = 0;
                for (XYChart.Data<String, Number> data : series.getData()) {
                    String kleur = kleuren.get(index % kleuren.size());
                    data.getNode().setStyle("-fx-bar-fill: " + kleur + ";");
                    index++;
                }

                
                // Stelt de y-as eigenschappen in
                yAxis.setAutoRanging(false);
                yAxis.setLowerBound(1);
                yAxis.setUpperBound(10);
                yAxis.setTickUnit(1);
                
              
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.closeConnection();
}
}
    
}
