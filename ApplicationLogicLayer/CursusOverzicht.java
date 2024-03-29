package ApplicationLogicLayer;

import java.io.IOException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;

import Data_Access_Layer_DAL.DatabaseConnection;
import ApplicationLogicLayer.CodeAcademyMaxLouisAPP;
import Domain.Cursus;
import Domain.Module;
import Domain.Niveau;
import Domain.Status;
import Domain.Webcast;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class CursusOverzicht {

    @FXML
    private Button addButton;

    @FXML
    private Button ajustButton;

    @FXML
    private TableColumn<Cursus, String> cursusAanbevolenColumn;

    @FXML
    private TableView<Cursus> cursusAanbevolenTable;

    @FXML
    private ChoiceBox<String> cursusChoicebox;

    @FXML
    private TableView<Cursus> cursusTable;

    @FXML
    private Button deleteButton;

    @FXML
    private Button detailsButton;

    @FXML
    private Label errorLabel;

    @FXML
    private TableColumn<Cursus, String> introductietekstColumn;

    @FXML
    private Button menuButton;

    @FXML
    private TableColumn<Module, String> moduleBeschrijvingColumn;

    @FXML
    private TableColumn<Module, String> moduleContactpersoonEmailColumn;

    @FXML
    private TableColumn<Module, String> moduleContactpersoonColumn;

    @FXML
    private TableColumn<Module, LocalDate> moduleDatumColumn;

    @FXML
    private TableColumn<Module, Integer> moduleIDColumn;

    @FXML
    private TableColumn<Module, String> moduleNaamColumn;

    @FXML
    private TableColumn<Module, Status> moduleStatusColumn;

    @FXML
    private TableView<Module> moduleTable;

    @FXML
    private TableColumn<Module, String> moduleTitelColumn;

    @FXML
    private TableColumn<Module, Double> moduleVersieColumn;

    @FXML
    private TableColumn<Module, String> moduleVolgnummerColumn;

    @FXML
    private TableColumn<Cursus, String> naamColumn;

    @FXML
    private TableColumn<Cursus, Niveau> niveauColumn;

    @FXML
    private TableColumn<Cursus, String> onderwerpColumn;

    @FXML
    private TableColumn<Webcast, String> webcastBeschrijvingColumn;

    @FXML
    private TableColumn<Webcast, LocalDate> webcastDatumColumn;

    @FXML
    private TableColumn<Webcast, String> webcastNaamColumn;

    @FXML
    private TableColumn<Webcast, String> webcastOrganisatieColumn;

    @FXML
    private TableColumn<Webcast, String> webcastSprekerColumn;

    @FXML
    private TableColumn<Webcast, Status> webcastStatusColumn;

    @FXML
    private TableView<Webcast> webcastTable;

    @FXML
    private TableColumn<Webcast, Time> webcastTijdsduurColumn;

    @FXML
    private TableColumn<Webcast, String> webcastTitelColumn;

    @FXML
    private TableColumn<Webcast, String> webcastURLColumn;

    CodeAcademyMaxLouisAPP m = new CodeAcademyMaxLouisAPP();
    DatabaseConnection connection = new DatabaseConnection();

   

    @FXML
    void toCursusAndWebcasts(ActionEvent event) throws IOException {
        m.changeScene("/FXML/Topthree.fxml");
    
    }

    @FXML
    void toCursusDetails(ActionEvent event) throws IOException {
        Cursus selectedCursus = cursusTable.getSelectionModel().getSelectedItem();

        if (selectedCursus != null) {
            openCursusDetails(selectedCursus);
        } else {
            errorLabel.setText("Selecteer een cursus om de details te bekijken!");
        }
    }

   
    //Methode om de CursusDetails te openen
    private void openCursusDetails(Cursus selectedCursus) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/CursusOverview.fxml"));  
        Parent root = loader.load();
        CursusDetails controller = loader.getController();
    
        controller.setCursusNaam(selectedCursus.getCursusNaam());
    
        m.changeSceneS(root, 1280, 800);  
    }

    

    private void tableViewCursus() {
        try {
    
            if (connection.openConnection()) {
                String query = "SELECT * FROM Cursus";
                
    
                try (ResultSet resultSet = connection.executeSQLSelectStatement(query)) {
                    if (resultSet != null) {
                        ObservableList<Cursus> cursusList = FXCollections.observableArrayList();
    
                        while (resultSet.next()) {
                            String cursusname = resultSet.getString("CursusNaam");
                            String cursusOnderwerp = resultSet.getString("Onderwerp");
                            String cursusIntroductie = resultSet.getString("IntroductieTekst");
                            Niveau cursusNiveau = Niveau.valueOf(resultSet.getString("NiveauAanduiding"));

                            Cursus cursus = new Cursus(cursusname, cursusOnderwerp, cursusIntroductie, cursusNiveau);    
                            cursusList.add(cursus);
                        }
    
                        cursusTable.setItems(cursusList);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void tableViewModule() {
        try {
            DatabaseConnection connection = new DatabaseConnection();
    
            if (connection.openConnection()) {
                String query = "SELECT CursusNaam, PublicatieDatum, Status, Titel, Beschrijving, Versie, Volgnummer, ModuleID, NaamContactPersoon, EmailContactPersoon FROM ContentItem WHERE ModuleID IS NOT NULL";
    
                try (ResultSet resultSet = connection.executeSQLSelectStatement(query)) {
    
                    ObservableList<Module> moduleList = FXCollections.observableArrayList();
    
                    while (resultSet.next()) {
                        int moduleID = resultSet.getInt("ModuleID");
                        String moduleName = resultSet.getString("CursusNaam");
                        Date moduleDate = resultSet.getDate("PublicatieDatum");
                        Status moduleStatus = Status.valueOf(resultSet.getString("Status"));
                        String moduleTitle = resultSet.getString("Titel");
                        String moduleDescription = resultSet.getString("Beschrijving");
                        double moduleVersion = resultSet.getDouble("Versie");
                        int moduleVolgnumber = resultSet.getInt("Volgnummer");
                        String modulePerson = resultSet.getString("NaamContactPersoon");
                        String moduleEmail = resultSet.getString("EmailContactPersoon");
    
                        Cursus cursusname = new Cursus(moduleName);
                        Module modules = new Module(moduleID, cursusname, moduleDate, moduleStatus, moduleTitle, moduleDescription, moduleVersion, moduleVolgnumber, modulePerson, moduleEmail);
                        moduleList.add(modules);
                        
                    }
    
                    moduleTable.setItems(moduleList);
    
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Haalt de cursus naam op uit de database
    private ObservableList<String> getCursusNameFromDatabase() {
        ObservableList<String> cursusNameList = FXCollections.observableArrayList();

        try {
            if (connection.openConnection()) {
                String query = "SELECT CursusNaam FROM Cursus";
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

    @FXML
    private void cursusSelected() {
        String selectedCursusNaam = cursusChoicebox.getValue();

        if (selectedCursusNaam != null && !selectedCursusNaam.isEmpty()) {
            loadAanbevolenCursussen(selectedCursusNaam);
        }
    }

    private void loadAanbevolenCursussen(String cursusNaam) {
        try {
            if (connection.openConnection()) {
                ObservableList<Cursus> aanbevolenCursussenList = FXCollections.observableArrayList();
    
                // Aanbevolen cursussen + CursusInteressant worden geselecteerd in de tabel
                String query1 = "SELECT CursusNaam AS cursusAanbevolen " +
                        "FROM Cursus " +
                        "WHERE CursusInteressant = '" + cursusNaam + "' AND CursusNaam != '" + cursusNaam + "';";
    
                try (ResultSet resultSet1 = connection.executeSQLSelectStatement(query1)) {
                    while (resultSet1.next()) {
                        String cursusAanbevolenNaam = resultSet1.getString("cursusAanbevolen");
                        if (!aanbevolenCursussenList.contains(new Cursus(cursusAanbevolenNaam))) {
                            aanbevolenCursussenList.add(new Cursus(cursusAanbevolenNaam));
                        }
                    }
                }
    
                // Selecteert alleen de cursusInteressant 
                String query2 = "SELECT CursusInteressant " +
                        "FROM Cursus " +
                        "WHERE CursusNaam = '" + cursusNaam + "';";
    
                try (ResultSet resultSet2 = connection.executeSQLSelectStatement(query2)) {
                    while (resultSet2.next()) {
                        String cursusInteressantNaam = resultSet2.getString("CursusInteressant");
                        if (!aanbevolenCursussenList.contains(new Cursus(cursusInteressantNaam))) {
                            aanbevolenCursussenList.add(new Cursus(cursusInteressantNaam));
                        }
                    }
                }
    
                cursusAanbevolenTable.setItems(aanbevolenCursussenList);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    

    private void tableViewWebcasts() {
    try {
        if (connection.openConnection()) {
            String query = "SELECT CursusNaam, PublicatieDatum, Status, Titel, Beschrijving, NaamSpreker, OrganisatieNaam, WebcastURL, Tijdsduur FROM ContentItem WHERE ModuleID IS NULL";

            try (ResultSet resultSet = connection.executeSQLSelectStatement(query)) {

                ObservableList<Webcast> webcastList = FXCollections.observableArrayList();

                while (resultSet.next()) {
                    String webcastCursusName = resultSet.getString("CursusNaam");
                    Date webcastPublicatieDatum = resultSet.getDate("PublicatieDatum");
                    Status webcastStatus = Status.valueOf(resultSet.getString("Status"));
                    String webcastTitel = resultSet.getString("Titel");
                    String webcastBeschrijving = resultSet.getString("Beschrijving");
                    String webcastNaamSpreker = resultSet.getString("NaamSpreker");
                    String webcastOrganisatieNaam = resultSet.getString("OrganisatieNaam");
                    String webcastURL = resultSet.getString("WebcastURL");
                    Time webcastTijdsduur = resultSet.getTime("Tijdsduur");

                  
                    Cursus cursusname = new Cursus(webcastCursusName);
                    
                    Webcast webcast = new Webcast(cursusname, webcastPublicatieDatum, webcastStatus, webcastTitel, webcastBeschrijving, webcastNaamSpreker, webcastOrganisatieNaam, webcastURL, webcastTijdsduur);

                    
                    webcastList.add(webcast);
                }

                webcastTable.setItems(webcastList);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void initialize() {
        //Cursus
        naamColumn.setCellValueFactory(new PropertyValueFactory<>("cursusNaam"));
        onderwerpColumn.setCellValueFactory(new PropertyValueFactory<>("onderwerp"));
        introductietekstColumn.setCellValueFactory(new PropertyValueFactory<>("introductie"));
        niveauColumn.setCellValueFactory(new PropertyValueFactory<>("niveau"));
    
        tableViewCursus();

        //Aanbevolen Cursussen
        cursusAanbevolenColumn.setCellValueFactory(new PropertyValueFactory<>("cursusNaam"));
        cursusChoicebox.setItems((getCursusNameFromDatabase()));
        cursusChoicebox.setOnAction(event -> cursusSelected());

        //Module
        moduleIDColumn.setCellValueFactory(new PropertyValueFactory<>("moduleID"));
        moduleNaamColumn.setCellValueFactory(new PropertyValueFactory<>("cursusNaam"));
        moduleDatumColumn.setCellValueFactory(new PropertyValueFactory<>("publicatiedatum"));
        moduleStatusColumn.setCellValueFactory(new PropertyValueFactory<>("Status"));
        moduleTitelColumn.setCellValueFactory(new PropertyValueFactory<>("titel"));
        moduleBeschrijvingColumn.setCellValueFactory(new PropertyValueFactory<>("beschrijving"));
        moduleVersieColumn.setCellValueFactory(new PropertyValueFactory<>("versie"));
        moduleVolgnummerColumn.setCellValueFactory(new PropertyValueFactory<>("volgnummer"));
        moduleContactpersoonColumn.setCellValueFactory(new PropertyValueFactory<>("naamContactpersoon"));
        moduleContactpersoonEmailColumn.setCellValueFactory(new PropertyValueFactory<>("emailContactpersoon"));


        tableViewModule();

        //Webcast
        webcastNaamColumn.setCellValueFactory(new PropertyValueFactory<>("cursusNaam"));
        webcastDatumColumn.setCellValueFactory(new PropertyValueFactory<>("publicatiedatum"));
        webcastStatusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        webcastTitelColumn.setCellValueFactory(new PropertyValueFactory<>("titel"));
        webcastBeschrijvingColumn.setCellValueFactory(new PropertyValueFactory<>("beschrijving"));
        webcastSprekerColumn.setCellValueFactory(new PropertyValueFactory<>("naamSpreker"));
        webcastOrganisatieColumn.setCellValueFactory(new PropertyValueFactory<>("organisatieNaam"));
        webcastURLColumn.setCellValueFactory(new PropertyValueFactory<>("webcastURL"));
        webcastTijdsduurColumn.setCellValueFactory(new PropertyValueFactory<>("tijdsduur"));

        tableViewWebcasts();
    }

}