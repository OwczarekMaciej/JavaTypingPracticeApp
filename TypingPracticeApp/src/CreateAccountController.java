import java.io.FileWriter;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class CreateAccountController {

    @FXML
    private AnchorPane mainPane;

    @FXML
    private Button setNameBtn;

    @FXML
    private TextField usernameTf;

    @FXML
    public void setUsername(ActionEvent event) throws IOException{
        String username = usernameTf.getText().strip();
        FileWriter fw = new FileWriter("username.txt");
        fw.write(username);
        fw.close();

        App app = new App();
        app.changeScene("mainscene.fxml");
    }

}
