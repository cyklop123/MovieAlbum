package main;

import Models.Movie;
import com.sun.org.apache.bcel.internal.generic.MONITORENTER;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/main.fxml"));
        primaryStage.setTitle("Movie Album");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.setResizable(false);

        VBox vbTable = (VBox) root.lookup("#vbTable");
        TableView<Movie> tableView = new TableView<>();

        TableColumn<Movie, String> titleColumn = new TableColumn<>("Tytul");
        titleColumn.setPrefWidth(152.);
        titleColumn.setResizable(false);
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<Movie, String> descriptionColumn = new TableColumn<>("Opis");
        descriptionColumn.setPrefWidth(209.);
        descriptionColumn.setResizable(false);
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        TableColumn<Movie, Integer> ratingColumn = new TableColumn<>("Ocena");
        ratingColumn.setPrefWidth(62.);
        ratingColumn.setResizable(false);
        ratingColumn.setCellValueFactory(new PropertyValueFactory<>("rating"));

        tableView.getColumns().addAll(titleColumn,descriptionColumn,ratingColumn);
        tableView.setItems(getMovies());

        vbTable.getChildren().add(tableView);

        primaryStage.show();
    }

    public ObservableList<Movie> getMovies(){
        ObservableList<Movie> movies = FXCollections.observableArrayList();

        movies.add(new Movie.Builder().setTitle("Testowy film").setDescription("Opis testowego filmu").addActor("Jhon","Rambo","bambo").addActor("Jhoanna","Krupa","modelka").setRating(7).build());

        return movies;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
