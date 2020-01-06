package main;

import Database.MovieDatabase;
import Models.Movie;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {

    @FXML
    private Pane mainPane;
    @FXML
    private VBox vbTable;

    private MovieDatabase db = MovieDatabase.getInstance();

    public void initTableView()
    {
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

        tableView.setOnMousePressed(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent mouseEvent)
            {
                if(mouseEvent.isPrimaryButtonDown() && mouseEvent.getClickCount() == 2)
                {
                    try
                    {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/details.fxml"));
                        Parent root = loader.load();

                        Stage stage = new Stage();
                        stage.setTitle("Szczegóły");
                        stage.setScene(new Scene(root, 600, 400));
                        stage.setResizable(false);

                        stage.initModality(Modality.WINDOW_MODAL);
                        Stage primaryStage = (Stage) mainPane.getScene().getWindow();
                        stage.initOwner(primaryStage);

                        Movie movie = tableView.getSelectionModel().getSelectedItem();

                        details.Controller controller = loader.getController();
                        controller.initData(movie);

                        stage.showAndWait();

                    } catch (IOException e)
                    {
                        e.printStackTrace();
                    }

                }
            }
        });

        tableView.setItems(getMovies());

        vbTable.getChildren().add(tableView);
    }

    public ObservableList<Movie> getMovies(){
        ObservableList<Movie> movies = FXCollections.observableArrayList();

        movies.addAll(db.selectMovies());

        return movies;
    }

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
            stage.showAndWait();

            Movie movie = controller.returnData();

            if(movie != null)
            {
                TableView<Movie> tableView = (TableView<Movie>) vbTable.getChildren().get(0);

                System.out.println(movie.toString());
                movie = db.insertMovie(movie);
                System.out.println(movie);

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
            db.deleteMovie(selectedMovies.get(0));
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
                    updatedMovie = db.updateMovie(updatedMovie);

                    Movie m = tableView.getSelectionModel().getSelectedItem();
                    m.setTitle(updatedMovie.getTitle());
                    m.setDescription(updatedMovie.getDescription());
                    m.setCast(updatedMovie.getCast());
                    m.setRating(updatedMovie.getRating());
                    tableView.refresh();

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
