package details;

import Models.Actor;
import Models.Movie;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Controller
{
    @FXML
    private Pane detailsPane;
    @FXML
    private Label lTitle;
    @FXML
    private Label lDescription;
    @FXML
    private VBox vbCast;
    @FXML
    private Label lRating;
    @FXML
    private Button btnOk;

    @FXML
    public void clickOk(ActionEvent actionEvent)
    {
        Stage stage = (Stage) detailsPane.getScene().getWindow();
        stage.close();
    }

    public void initData(Movie movie)
    {
        lTitle.setText(movie.getTitle());
        lDescription.setText(movie.getDescription());
        lRating.setText(movie.getRating()+"/10");

        for(Actor actor: movie.getCast())
        {
            Label row = new Label(actor.getCharacter()+" - "+actor.getName()+" "+actor.getSurname());
            row.setPrefWidth(530.);
            vbCast.getChildren().add(row);
        }
        btnOk.setDefaultButton(true);
    }
}
