import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/** main
 * @author sconboy
 * @version 1.0
 *
 * starts the program
 */
public class main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("TimelineViewerGUI.fxml"));
        primaryStage.setTitle("Timeline Viewer");
        primaryStage.setScene(new Scene(root, 600, 250));
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
