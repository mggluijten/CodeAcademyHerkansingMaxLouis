
package ApplicationLogicLayer;

import java.io.IOException;

import ApplicationLogicLayer.CodeAcademyMaxLouisAPP;
import Domain.Medewerker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class Menu {

    @FXML
    private Button VoortgangButton;

    @FXML
    private Button CursistenoverzichtButton;

    @FXML
    private Button CursusoverzichtButton;

    @FXML
    private Button InschrijvingButton;

    @FXML
    private Label NaamMedewerkerLabel;

    @FXML
    private Button logUitButton;

    @FXML
    private ImageView logoCodecademy;

    CodeAcademyMaxLouisAPP m = new CodeAcademyMaxLouisAPP();


    @FXML
    void userInschrijving(ActionEvent event) throws IOException {
        m.changeScene("/FXML/Registrations.fxml");
    }

    @FXML
    void userVoortgang(ActionEvent event) throws IOException {
        m.changeScene("/FXML/ProgressOverview.fxml");

    }


    @FXML
    void userCursistoverzicht(ActionEvent event) throws IOException {
        m.changeScene("/FXML/CursistOverview.fxml");

    }

    @FXML
    void userCursusoverzicht(ActionEvent event) throws IOException {
        m.changeScene("/FXML/CursusOverview.fxml");

    }


    @FXML
    void userLogOut(ActionEvent event) throws IOException {
        m.changeScene("/FXML/LoginScreen.fxml");

    }


    @FXML
    private void initialize() {
        Medewerker ingelogdeMedewerker = CodeAcademyMaxLouisAPP.getIngelogdeMedewerker();
        if (ingelogdeMedewerker != null) {
            String medewerkerNaam = ingelogdeMedewerker.getNaamMedewerker();
            NaamMedewerkerLabel.setText(medewerkerNaam);
        }
    }

}



