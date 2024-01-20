import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class Controller {

    @FXML
    private Label textLbl;

    @FXML
    private TextField tfType;

    @FXML
    void onBtnClick(ActionEvent event) {
        String text = tfType.getText();
        textLbl.setText(text);
    }

}
