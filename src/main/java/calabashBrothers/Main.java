package calabashBrothers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @ Author     ：Young
 * @ Date       ：Created in 18:29 2018/12/6
 * @ Description：用于重构Javafx的入口
 */

public class Main extends Application {

    public void start(Stage primaryStage) throws Exception {
        System.out.println(getClass().getClassLoader().getResource("battleField.fxml"));
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("battleField.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 600.0D, 500.0D));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
