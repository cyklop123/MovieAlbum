package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Movie Album");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.setResizable(false);

        main.Controller controller = loader.getController();
        controller.initTableView();

        System.err.println(
                com.sun.javafx.runtime.VersionInfo.getRuntimeVersion());

        primaryStage.show();
    }



    public static void main(String[] args) {
        launch(args);
    }
}
