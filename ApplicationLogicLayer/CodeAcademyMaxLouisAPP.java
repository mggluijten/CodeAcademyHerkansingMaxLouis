package ApplicationLogicLayer;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import Domain.Medewerker;

public class CodeAcademyMaxLouisAPP extends Application{
    public static void main(String[] args) {
    launch(args);
}

    private static Stage stg;
    private static Medewerker ingelogdeMedewerker;

    // Screensize
    private static final int SCREEN_WIDTH = 1280;
    private static final int SCREEN_HEIGHT = 800;



    public static Medewerker getIngelogdeMedewerker() {
        return ingelogdeMedewerker;
    }

    public static void setIngelogdeMedewerker(Medewerker medewerker) {
        ingelogdeMedewerker = medewerker;
    }


    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/LoginScreen.fxml"));
            primaryStage.setResizable(false);
            Parent root = loader.load();
 
            Scene scene = new Scene(root);
 
            primaryStage.setTitle("Max Luijten 2215302");
            primaryStage.setScene(scene);
            
            primaryStage.show();
            
            primaryStage.setWidth(SCREEN_WIDTH);
            primaryStage.setHeight(SCREEN_HEIGHT);
 
            stg = primaryStage;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
 
    public void changeScene(String fxmlPath) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(CodeAcademyMaxLouisAPP.class.getResource(fxmlPath));
        Parent root = loader.load(); 
        System.out.println("Laden van: " + fxmlPath);
        // Controleer of stg niet null is
        if (stg != null) {
            stg.setWidth(SCREEN_WIDTH);
            stg.setHeight(SCREEN_HEIGHT);

            if (stg.getScene() == null) {
                Scene scene = new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT);
                stg.setScene(scene);
            } else {
                stg.getScene().setRoot(root);
            }
        } else {
            System.out.println("De Stage (stg) is niet geïnitialiseerd.");
        }
    }

    public void changeSceneS(Parent root, int width, int height) {
        // Controleer of stg niet null is
        if (stg != null) {
            stg.setWidth(width);
            stg.setHeight(height);
    
            if (stg.getScene() == null) {
                Scene scene = new Scene(root, width, height);
                stg.setScene(scene);
            } else {
                stg.getScene().setRoot(root);
            }
        } else {
            System.out.println("De Stage (stg) is niet geïnitialiseerd.");
        }
    }
}