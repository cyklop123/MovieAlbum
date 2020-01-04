package main;

import Models.Movie;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {

    @FXML
    private Pane mainPane;
    @FXML
    private VBox vbTable;

    @FXML
    void addMovie(ActionEvent event) {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edit.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Dodaj nowy film");
            stage.setScene(new Scene(root, 400, 600));
            stage.setResizable(false);

            stage.initModality(Modality.WINDOW_MODAL);
            Stage primaryStage = (Stage) mainPane.getScene().getWindow();
            stage.initOwner(primaryStage);


            edit.Controller controller = loader.getController();
            //do okna edycji
            //controller.initData(new Movie.Builder().setTitle("Testowy film").setDescription("Opis testowego filmu").addActor("Jhon","Rambo","bambo").addActor("Jhoanna","Krupa","modelka").setRating(7).build());
            stage.showAndWait();

            Movie movie = controller.returnData();

            if(movie != null)
            {
                TableView<Movie> tableView = (TableView<Movie>) vbTable.getChildren().get(0);
                tableView.getItems().add(movie);

            }

        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @FXML
    void deleteMovie(ActionEvent event) {
        TableView<Movie> tableView = (TableView<Movie>) vbTable.getChildren().get(0);

        ObservableList<Movie> allMovies, selectedMovies;
        allMovies = tableView.getItems();
        selectedMovies = tableView.getSelectionModel().getSelectedItems();

        if(selectedMovies.size() > 0)
        {
            selectedMovies.forEach(allMovies::remove);
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Uwaga!");
            alert.setHeaderText(null);
            alert.setContentText("Nie zaznaczono żadnego filmu!");

            alert.showAndWait();
        }
    }

    @FXML
    void editMovie(ActionEvent event) {
        TableView<Movie> tableView = (TableView<Movie>) vbTable.getChildren().get(0);
        Movie movie = tableView.getSelectionModel().getSelectedItem();

        if(movie != null)
        {
            try
            {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/edit.fxml"));
                Parent root = loader.load();

                Stage stage = new Stage();
                stage.setTitle("Edytuj film");
                stage.setScene(new Scene(root, 400, 600));
                stage.setResizable(false);

                stage.initModality(Modality.WINDOW_MODAL);
                Stage primaryStage = (Stage) mainPane.getScene().getWindow();
                stage.initOwner(primaryStage);


                edit.Controller controller = loader.getController();
                controller.initData(movie);
                stage.showAndWait();

                Movie updatedMovie = controller.returnData();

                if(updatedMovie != null)
                {
                    ObservableList<Movie> allMovies = tableView.getItems();

                    for (Movie m : allMovies)
                    {
                        if (m == movie)
                        {
                            m.setTitle(updatedMovie.getTitle());
                            m.setDescription(updatedMovie.getDescription());
                            m.setCast(updatedMovie.getCast());
                            m.setRating(updatedMovie.getRating());
                            tableView.refresh();
                        }
                    }
                }

            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Uwaga!");
            alert.setHeaderText(null);
            alert.setContentText("Nie zaznaczono żadnego filmu!");

            alert.showAndWait();
        }
    }

    @FXML
    void deselectItems(MouseEvent event) {
        TableView<Movie> tableView = (TableView<Movie>) vbTable.getChildren().get(0);
        tableView.getSelectionModel().clearSelection();
    }

}
