package ApplicationLogicLayer;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import Data_Access_Layer_DAL.DatabaseConnection;
import ApplicationLogicLayer.CodeAcademyMaxLouisAPP;
import Domain.Cursist;
import Domain.GeslachtCursist;

public class CursistenOverzicht {
    @FXML
    private Label aantalLabel;
    

    @FXML
    private Label naamMedewerkerText2;

    @FXML
    private Label beoordelingText2;
    
    @FXML
    private Label cursusNaamText2;

    @FXML
    private Label naamMedewerkerText1;

    @FXML
    private Label beoordelingText1;
    
    @FXML
    private Label cursusNaamText1;

    @FXML
    private Label naamMedewerkerText3;

    @FXML
    private Label beoordelingText3;
    
    @FXML
    private Label cursusNaamText3;

    @FXML
    private Label naamMedewerkerText4;

    @FXML
    private Label beoordelingText4;
    
    @FXML
    private Label cursusNaamText4;

    @FXML
    private Label errorOverview;

    @FXML
    private ScrollBar Scrollwheel;

    @FXML
    private PieChart piechartGender;

    @FXML
    private ChoiceBox<GeslachtCursist> choiceGender;

    private GeslachtCursist[] gender = {GeslachtCursist.M, GeslachtCursist.V, GeslachtCursist.X};

    @FXML
    private ChoiceBox<String> choiceCursist;

    @FXML
    private TableView<Cursist> tableView;

    @FXML
    private TableColumn<Cursist, String> AchternaamCursist;

    @FXML
    private Button BewerkenButton;

    @FXML
    private Button BijwerkenButton;

    @FXML
    private TableColumn<Cursist, String> EmailCursist;

    @FXML
    private TableColumn<Cursist, LocalDate> GeboortedatumCursist;

    @FXML
    private TableColumn<Cursist, GeslachtCursist> GeslachCursist;

    @FXML
    private TableColumn<Cursist, String> HuisnummerCursist;

    @FXML
    private TableColumn<Cursist, String> LandCursist;

    @FXML
    private TableColumn<Cursist, Double> PostcodeCursist;

    @FXML
    private TableColumn<Cursist, String> StraatnaamCursist;

    @FXML
    private TableColumn<Cursist, String> VoornaamCursist;

    @FXML
    private TableColumn<Cursist, String> WoonplaatsCurist;

    @FXML
    private Button ToevoegenButton;

    @FXML
    private Button VerwijderenButton;
    
    @FXML
    private Label achternaamCursistLabel;
 
    @FXML
    private Label emailCursistLabel;

    @FXML
    private Label voornaamCursistLabel;

    @FXML
    private Label beoordelingLabel1;

    @FXML
    private Label beoordelingLabel2;

    @FXML
    private Label beoordelingLabel3;

    @FXML
    private Label beoordelingLabel4;

    @FXML
    private Label cursusNaamLabel1;

    @FXML
    private Label cursusNaamLabel2;

    @FXML
    private Label cursusNaamLabel3;

    @FXML
    private Label cursusNaamLabel4;
    
    @FXML
    private Label naamMedewerkerLabel1;

    @FXML
    private Label naamMedewerkerLabel2;

    @FXML
    private Label naamMedewerkerLabel3;

    @FXML
    private Label naamMedewerkerLabel4;

    CodeAcademyMaxLouisAPP m = new CodeAcademyMaxLouisAPP();
    DatabaseConnection databaseConnection = new DatabaseConnection();


    @FXML
    void addCursist(ActionEvent event) throws IOException {
        m.changeScene("/FXML/AddCursist.fxml");
    }

    @FXML
    void backToMenu(ActionEvent event) throws IOException {
        m.changeScene("/FXML/MenuScreen.fxml");
    }

    @FXML
    void AlterCursist(ActionEvent event) throws IOException {

        Cursist selectedCursist = tableView.getSelectionModel().getSelectedItem();
    
        if (selectedCursist != null) {
            openAlterCursistForm(selectedCursist);
        } else {
            errorOverview.setText("Selecteer een cursist om aan te passen.");
        }
    }

    // Opent form met geselecteerde cursist
    private void openAlterCursistForm(Cursist selectedCursist) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/AlterCursist.fxml"));
        Parent root = loader.load();
        AdjustCursist controller = loader.getController();  
    
        controller.setCursistData(selectedCursist);
    
        m.changeSceneS(root, 1280, 800);
    }
    

    //Methode om geselecteerde cursist te verwijderen
    @FXML
    void deleteCursist(ActionEvent event) throws SQLException {
        Cursist selectedCursist = tableView.getSelectionModel().getSelectedItem();

        if (selectedCursist != null) {
            deleteCursist(selectedCursist);
            fillTableView(); 
        } else {
            errorOverview.setText("Selecteer een cursist om te verwijderen.");
        }
    }

    private void deleteCursist(Cursist cursist) {
        String query = "DELETE FROM Cursist WHERE Email = '" + cursist.getEmail() + "'";

        if (databaseConnection.openConnection()) {
            try {

                int rowsAffected = databaseConnection.executeSQLUpdateStatement(query);

                if (rowsAffected > 0) {
                } else {
                    errorOverview.setText("Kon cursist niet verwijderen.");
                }
            } finally {

                databaseConnection.closeConnection();
            }
        } else {
            System.out.println("Kon geen verbinding maken met de database.");
        }
    }

    @FXML
    private void initialize() throws SQLException {
        initTableView();
        initChoiceBox();
        initCursistChoiceBox();
        setupCursistChoiceListener();
    }

    private void fillTableView() throws SQLException {
        if (databaseConnection.openConnection()) {
            try {
                String query = "SELECT * FROM Cursist";
                ResultSet resultSet = databaseConnection.executeSQLSelectStatement(query);
    
                if (resultSet != null) {
                    ObservableList<Cursist> cursistenList = FXCollections.observableArrayList();
    
                    while (resultSet.next()) {
                        String email = resultSet.getString("Email");
                        String voornaam = resultSet.getString("Voornaam");
                        String achternaam = resultSet.getString("Achternaam");
                    
                        // Gebruik getTimestamp voor datumkolommen (kon anders de gegevens niet ophalen)
                        Timestamp timestamp = resultSet.getTimestamp("Geboortedatum");
                        LocalDate geboortedatum = timestamp.toLocalDateTime().toLocalDate();

                        String geslachtStr = resultSet.getString("Geslacht");
                        GeslachtCursist geslacht = GeslachtCursist.valueOf(geslachtStr); // Converteer de string naar het Geslacht-enum
                    
                        String straatnaam = resultSet.getString("Straatnaam");
                        String huisnummer = resultSet.getString("Huisnummer");
                        String postcode = resultSet.getString("Postcode");
                        String woonplaats = resultSet.getString("Woonplaats");
                        String land = resultSet.getString("Land");
                    

                        Cursist cursist = new Cursist(email, voornaam, achternaam, geboortedatum, geslacht, straatnaam, huisnummer, postcode, woonplaats, land);
                    

                        cursistenList.add(cursist);
                    
                    }
    

                    tableView.setItems(cursistenList);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                databaseConnection.closeConnection();
            }
        }
    }

    @FXML void initTableView() throws SQLException{
        EmailCursist.setCellValueFactory(new PropertyValueFactory<>("email"));
        VoornaamCursist.setCellValueFactory(new PropertyValueFactory<>("voornaam"));
        AchternaamCursist.setCellValueFactory(new PropertyValueFactory<>("achternaam"));
        GeboortedatumCursist.setCellValueFactory(new PropertyValueFactory<>("geboortedatum"));
        GeslachCursist.setCellValueFactory(new PropertyValueFactory<>("geslacht"));
        StraatnaamCursist.setCellValueFactory(new PropertyValueFactory<>("straatnaam"));
        HuisnummerCursist.setCellValueFactory(new PropertyValueFactory<>("huisnummer"));
        PostcodeCursist.setCellValueFactory(new PropertyValueFactory<>("postcode"));
        WoonplaatsCurist.setCellValueFactory(new PropertyValueFactory<>("woonplaats"));
        LandCursist.setCellValueFactory(new PropertyValueFactory<>("land"));
    
        fillTableView();
    }

    @FXML
    private void initChoiceBox() {
        choiceGender.setItems(FXCollections.observableArrayList(gender));
        choiceGender.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            
        });
    }

    
                


    //Methode om totale cursisten per geslacht te berekenen
    private int getTotalCursisten(GeslachtCursist geslacht) {
        int totalCursisten = 0;
        if (databaseConnection.openConnection()) {
            try {
                String query = "SELECT COUNT(*) FROM Inschrijving " +
                        "JOIN Cursist ON Inschrijving.Email = Cursist.Email " +
                        "WHERE Cursist.Geslacht = '" + geslacht + "'";
                ResultSet resultSet = databaseConnection.executeSQLSelectStatement(query);
                if (resultSet != null && resultSet.next()) {
                    totalCursisten = resultSet.getInt(1);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                databaseConnection.closeConnection();
            }
        }
        return totalCursisten;
    }

    //Haalt de cursist naam op uit de database
    private ObservableList<String> getCursistNameFromDatabase() {
        ObservableList<String> cursistNameList = FXCollections.observableArrayList();

        try {
            if (databaseConnection.openConnection()) {
                String query = "SELECT DISTINCT Cursist.Voornaam, Cursist.Achternaam FROM Cursist " +
                "JOIN Inschrijving ON Cursist.Email = Inschrijving.Email";
 
                ResultSet resultSet = databaseConnection.executeSQLSelectStatement(query);

                while (resultSet.next()) {
                    String voornaam = resultSet.getString("Voornaam");
                    String achternaam = resultSet.getString("Achternaam");
                    String cursistNaam = voornaam + " " + achternaam;
                    cursistNameList.add(cursistNaam);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            databaseConnection.closeConnection();
        }

        return cursistNameList;
    }

    //Methode om de cursistnamelist in de choicebox te weergeven
    private void initCursistChoiceBox() {
        ObservableList<String> cursistNames = getCursistNameFromDatabase();
        choiceCursist.setItems(cursistNames);
    }

    //Methode om te kijken welke naam wordt geselecteerd in de choicebox
    private void setupCursistChoiceListener() {
        choiceCursist.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                displaySelectedCursistData(newValue);
            } catch (SQLException | IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
                e.printStackTrace();
            }
        });
    }

    private void displaySelectedCursistData(String cursistNaam) throws SQLException, IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
        // Label reset 2 zodat info niet zichtbaar blijft na wisselen van account
        beoordelingLabel1.setVisible(false);
        beoordelingLabel1.setText("");
        cursusNaamLabel1.setVisible(false);
        cursusNaamLabel1.setText("");
        naamMedewerkerLabel1.setVisible(false);
        naamMedewerkerLabel1.setText("");
    
        // Label reset 2 zodat info niet zichtbaar blijft na wisselen van account
        beoordelingLabel2.setVisible(false);
        beoordelingLabel2.setText("");
        cursusNaamLabel2.setVisible(false);
        cursusNaamLabel2.setText("");
        naamMedewerkerLabel2.setVisible(false);
        naamMedewerkerLabel2.setText("");
    
        // Label reset 3 zodat info niet zichtbaar blijft na wisselen van account
        beoordelingLabel3.setVisible(false);
        beoordelingLabel3.setText("");
        cursusNaamLabel3.setVisible(false);
        cursusNaamLabel3.setText("");
        naamMedewerkerLabel3.setVisible(false);
        naamMedewerkerLabel3.setText("");

        // Label reset 4 zodat info niet zichtbaar blijft na wisselen van account
        beoordelingLabel4.setVisible(false);
        beoordelingLabel4.setText("");
        cursusNaamLabel4.setVisible(false);
        cursusNaamLabel4.setText("");
        naamMedewerkerLabel4.setVisible(false);
        naamMedewerkerLabel4.setText("");
    
        if (cursistNaam != null && !cursistNaam.isEmpty()) {
            if (databaseConnection.openConnection()) {
                try {
                    // Query om voornaam, achternaam en e-mail op te halen
                    String cursistQuery = "SELECT * FROM Cursist WHERE Voornaam = ?";
                    PreparedStatement cursistStatement = databaseConnection.getConnection().prepareStatement(cursistQuery);
                    cursistStatement.setString(1, cursistNaam.split(" ")[0]);
                    ResultSet cursistResultSet = cursistStatement.executeQuery();
    
                    if (cursistResultSet != null && cursistResultSet.next()) {
                        voornaamCursistLabel.setText(cursistResultSet.getString("Voornaam"));
                        achternaamCursistLabel.setText(cursistResultSet.getString("Achternaam"));
                        emailCursistLabel.setText(cursistResultSet.getString("Email"));
    
                        
                    }
                } finally {
                    databaseConnection.closeConnection();
                }
            }
        }
    }
    
    
} 


