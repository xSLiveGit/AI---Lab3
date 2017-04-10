import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import GUIController.GUIController;
public class Main extends Application {

    public static void main(String[] argv) {
        // STOPSHIP: 4/10/2017
        launch(argv);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("MainWindows.fxml"));
        GUIController guiController = loader.getController();
        System.out.print(guiController);
//        guiController.initDesgin();
        BorderPane pane = loader.load();
        Scene scene = new Scene(pane);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }
}
