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
        //setUserAgentStylesheet(STYLESHEET_MODENA);
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("battleField.fxml"));
        primaryStage.setTitle("葫芦娃vs妖精");
        primaryStage.setScene(new Scene(root, 1200.0D, 800.0D)); //设置初始的窗口大小
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
