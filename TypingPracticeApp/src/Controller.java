import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class Controller implements Initializable{

    @FXML
    private AnchorPane mainPane;

    @FXML
    private Button startBtn;

    @FXML
    private Text time;

    @FXML
    private Text total;

    @FXML
    private Label welcomeLbl;

    @FXML
    private Text wpm;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        
    }


    
}
