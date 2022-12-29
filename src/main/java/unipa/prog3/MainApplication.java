package unipa.prog3;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import unipa.prog3.view.controller.MainController;

import java.io.IOException;

public class MainApplication extends Application {
    private static Stage stage;
    private static MainController mainController;

    @Override
    public void start(Stage stage) throws IOException {
        MainApplication.stage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/unipa/prog3/main.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Progetto Programmazione 3");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public static Stage getStage() {
        return stage;
    }

    public static MainController getMainController() {
        return mainController;
    }

    public static void setMainController(MainController mainController) {
        MainApplication.mainController = mainController;
    }
}