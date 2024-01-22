import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class PracticeController implements Initializable{
    
    @FXML
    private Text accuracy; 
    @FXML
    private Text wpm;
    @FXML
    private Text timeLeft;

    @FXML
    private ImageView wrongIv;
    @FXML
    private ImageView correctIv;

    @FXML
    private Text currentWordTxt;
    @FXML
    private Text nextWordTxt;

    @FXML
    private AnchorPane mainPane;

    @FXML
    private Button playAgainBtn;

    @FXML
    private TextField practiceTf;


    @FXML
    void playAgainBtn(ActionEvent event) throws IOException{
        App app = new App();
        app.changeScene("mainscene.fxml");

    }

    Runnable showWrongImg = new Runnable() {
        @Override
        public void run() {
            wrongIv.setOpacity(0);
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            wrongIv.setOpacity(50);
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            wrongIv.setOpacity(100);
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            wrongIv.setOpacity(0);
        }
    };

    Runnable showCorrectImg = new Runnable() {
        @Override
        public void run() {
            correctIv.setOpacity(100);
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            correctIv.setOpacity(50);
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            correctIv.setOpacity(100);
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            correctIv.setOpacity(0);
        }
    };

    private int isPracticeStarted = 0;
    private int correctCounter = 0;
    private int countAll = 0;

    @FXML
    void startPractice(KeyEvent event) {
        if(isPracticeStarted == 0)
        {
            isPracticeStarted = 1;
            executor.scheduleAtFixedRate(practiceLogic, 0, 1, TimeUnit.SECONDS);
        }

        if (event.getCode().equals(KeyCode.ENTER) && practiceTf.getText().strip() != "") {

            String userWord = practiceTf.getText().strip();
            String realWord = currentWordTxt.getText();
            countAll++;

            if (userWord.equals(realWord)) {
                correctCounter++;
                wpm.setText(String.format("%.1f", correctCounter * 60.0 / timeSpent));


                Thread t = new Thread(showCorrectImg);
                t.start();

            }
            else {
                Thread t = new Thread(showWrongImg);
                t.start();
            }
            practiceTf.setText("");
            accuracy.setText(String.valueOf(Math.round((correctCounter*1.0/countAll)*100)));
            currentWordTxt.setText(words.get(wordCounter));
            nextWordTxt.setText(words.get(wordCounter+1));
            wordCounter++;
        }

    }

    ArrayList<String> words = new ArrayList<>();
    private int wordCounter = 0;

    public void addWords()
    {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader("wordsLevel1.txt"));
            String line = reader.readLine();
            while (line != null) {
                words.add(line);
                line = reader.readLine();
            }
            reader.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int timer = 60;
    private int timeSpent = 0;

    ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        playAgainBtn.setVisible(false);
        playAgainBtn.setDisable(true);
        timeLeft.setText(String.valueOf(timer));
        addWords();
        Collections.shuffle(words);
        currentWordTxt.setText(words.get(wordCounter));
        nextWordTxt.setText(words.get(wordCounter + 1));
        wordCounter++;

    }

    Runnable practiceLogic = new Runnable() {
        @Override
        public void run() {
            if (timer > -1) {
                timeLeft.setText(String.valueOf(timer));
                timer -= 1;
                timeSpent++;
            }

            else {
                if (timer == -1) {
                    practiceTf.setDisable(true);
                    practiceTf.setText("The time is over");

                    // try {
                    //     FileWriter myWriter = new FileWriter(saveData);
                    //     myWriter.write(countAll +";");
                    //     myWriter.write(counter +";");
                    //     myWriter.write(String.valueOf(countAll-counter));
                    //     myWriter.close();
                    // } catch (IOException e) {
                    //     e.printStackTrace();
                    // }

                    playAgainBtn.setVisible(true);
                    playAgainBtn.setDisable(false);
                    executor.shutdown();
                }
                timer -= 1;
            }
        }
    };

    
}
