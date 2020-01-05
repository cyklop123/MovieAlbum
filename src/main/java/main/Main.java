package main;

import Models.Movie;
import com.sun.org.apache.bcel.internal.generic.MONITORENTER;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
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

        primaryStage.show();
    }



    public static void main(String[] args) {
        launch(args);
    }
}
