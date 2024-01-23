import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class App extends Application {

    private static Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("mainscene.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Typing Practice");
        primaryStage.setScene(new Scene(root, 1000, 600));
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image("images/icon.png"));
        primaryStage.show();
    }

    public void changeScene(String fxml) throws IOException{
        FXMLLoader sceneLoader = new FXMLLoader(getClass().getResource(fxml));
        Parent pane = sceneLoader.load();
        stage.getScene().setRoot(pane);
    }

    public void showPracticeScene(int selectedTime) throws IOException {
        FXMLLoader sceneLoader = new FXMLLoader(getClass().getResource("practicescene.fxml"));
        Parent pane = sceneLoader.load();
        stage.getScene().setRoot(pane);
        PracticeController practiceController = sceneLoader.getController();
        practiceController.setSelectedTime(selectedTime);
    }

    public static void main(String[] args) {
        launch(args);
    }
}