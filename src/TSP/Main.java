package TSP;

import TSP.api.Views;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static Stage stage;
    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        Views views = new Views();
        primaryStage.setScene(new Scene(views.getParent()));
        views.handleEvents();
        primaryStage.setOnCloseRequest(event -> System.exit(0));
        primaryStage.show();
    }
}
