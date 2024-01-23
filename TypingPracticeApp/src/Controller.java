import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.FieldPosition;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.event.ActionEvent;
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
    private Label usernameLbl;

    @FXML
    private Text wpm;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        File userFile = new File("username.txt");
        if(userFile.length() != 0)
        {
            Scanner reader = null;
            try {
                reader = new Scanner(userFile);
            } catch (Exception e) {
            }
            String username = reader.nextLine();
            usernameLbl.setText("Hi " + username + "!");
        }
        
        int[] statistics = FileHandler.sumUpNumbers("src/records");
        total.setText(String.valueOf(statistics[0]));
        wpm.setText(String.valueOf(Math.round(statistics[1] * 60.0/statistics[2])));
        time.setText(String.valueOf(statistics[2]));
    }

    public void startTyping(ActionEvent event) throws IOException{
        App app = new App();

        File newFile = new File("username.txt");
        if(newFile.length() == 0)
        {
            try {
                app.changeScene("CreateAccount.fxml");
                
            } catch (Exception e) {
            }
        }
        else
        {
            app.changeScene("practicescene.fxml");
        }
    } 
}
