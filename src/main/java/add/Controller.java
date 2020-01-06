package add;

import Models.Actor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Controller
{
    Actor retval = null;
    public Actor returnData()
    {
        return retval;
    }

    @FXML
    private TextField tfName;
    @FXML
    private TextField tfSurname;
    @FXML
    private TextField tfCharacter;
    @FXML
    private Button btnCloseWindow;

    @FXML
    void addActor(ActionEvent event) {
        String name = tfName.getText().trim();
        String surname = tfSurname.getText().trim();
        String character = tfCharacter.getText().trim();
        if(!name.equals("") && !surname.equals("") && !character.equals(""))
        {
            retval = new Actor(0, name,surname,character);

            Stage stage = (Stage) btnCloseWindow.getScene().getWindow();
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
        Stage stage = (Stage) btnCloseWindow.getScene().getWindow();
        stage.close();
    }
}
