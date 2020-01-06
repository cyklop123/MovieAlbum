package edit;

import Models.Actor;
import Models.Movie;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class Controller {

    boolean editing = false;
    Movie retval = null;
    int id_movie = 0;

    @FXML
    private Label lWindowName;
    @FXML
    private Pane editPane;
    @FXML
    private TextField tfTitle;
    @FXML
    private TextArea taDescription;
    @FXML
    private VBox vbCast;
    @FXML
    private Slider slRating;
    @FXML
    private Button btnAddActor;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnCancel;

    public void initData(Movie movie)
    {
        editing = true;
        lWindowName.setText("Edytuj film");

        tfTitle.setText(movie.getTitle());
        taDescription.setText(movie.getDescription());
        for(Actor a: movie.getCast())
            this.addCastToList(a);
        slRating.setValue(movie.getRating());
        id_movie = movie.getId_movie();
    }

    public Movie returnData()
    {
        return retval;
    }

    private void addCastToList(Actor actor)
    {
        Label data = new Label();
        data.setPrefWidth(289.);
        data.setMaxWidth(289.);
        Label X = new Label();
        X.setPrefWidth(22.);
        X.setAlignment(Pos.CENTER);
        HBox row = new HBox(data,X);
        vbCast.getChildren().add(row);

        row.getProperties().put("id_actor", actor.getId_actor());
        row.getProperties().put("name",actor.getName());
        row.getProperties().put("surname",actor.getSurname());
        row.getProperties().put("character",actor.getCharacter());

        data.setText(actor.getCharacter()+" - "+actor.getName()+" "+actor.getSurname());
        X.setText("X");

        X.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent mouseEvent)
            {
                removeCastFromList(row);
            }
        });

    }

    private void removeCastFromList(HBox el)
    {
        vbCast.getChildren().remove(el);
    }

    @FXML
    void addActor(ActionEvent event) {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/add.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Dodaj nowego aktora do obsady");
            stage.setScene(new Scene(root, 400, 330));
            stage.setResizable(false);

            stage.initModality(Modality.WINDOW_MODAL);
            Stage primaryStage = (Stage) editPane.getScene().getWindow();
            stage.initOwner(primaryStage);

            add.Controller controller = loader.getController();

            stage.showAndWait();

            Actor actor = controller.returnData();

            if(actor != null)
            {
                this.addCastToList(actor);
            }

        } catch (IOException e)
        {
            e.printStackTrace();
        }

    }
    @FXML
    void addMovie(ActionEvent event) {

        int castSize = vbCast.getChildren().size();

        if(!tfTitle.getText().equals("") && !taDescription.getText().equals("") && castSize > 0)
        {
            ArrayList<Actor> actors = new ArrayList<>();
            for(Node actor: vbCast.getChildren())
            {
                HBox row = (HBox) actor;

                int id_actor = (int) row.getProperties().get("id_actor");
                String name = (String) row.getProperties().get("name");
                String surname = (String) row.getProperties().get("surname");
                String character = (String) row.getProperties().get("character");

                actors.add(new Actor(id_actor, name.trim(), surname.trim(), character.trim()));

            }

            retval = new Movie.Builder().
                    setId_movie(id_movie).
                    setTitle(tfTitle.getText().trim()).
                    setDescription(taDescription.getText().trim()).
                    setCast(actors).
                    setRating((int) Math.round(slRating.getValue())).
                    build();

            Stage stage = (Stage) editPane.getScene().getWindow();
            stage.close();

        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Uwaga!");
            alert.setHeaderText(null);
            alert.setContentText("Uzupełnij wszystkie pola!");

            alert.showAndWait();
        }
    }
    @FXML
    void closeWindow(ActionEvent event) {
        Stage stage = (Stage) editPane.getScene().getWindow();
        stage.close();
    }
}
