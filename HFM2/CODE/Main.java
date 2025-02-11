import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static Stage stage;

    @Override
    public void start( Stage primaryStage) throws Exception {
        stage = primaryStage;
        Parent root = FXMLLoader.load( getClass().getResource( "main.fxml"));
        Scene scene = new Scene( root);
        primaryStage.setTitle( "Text File Compression");
        primaryStage.setScene( scene);
        primaryStage.show();
    }

    public static void main( String[] args) {
        launch( args);
    }
}
