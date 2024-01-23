import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
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
    
    private int isPracticeStarted = 0;
    private int correctCounter = 0;
    private int countAll = 0;
    private int timer = 15;
    private int practiceTime = timer;

    ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

    ArrayList<String> words = new ArrayList<>();
    private int wordCounter = 0;

    private File saveData;

    @FXML
    private Text accuracy; 
    @FXML
    private Text correctWordsTxt;
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

        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss");
        saveData = new File("src/records/" + format.format(date).strip() + ".txt");
        
        try {
            if(saveData.createNewFile())
            {
                System.out.println("File " + saveData.getName() + "created");
            }
            else{
                System.out.println("File already exists");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void playAgainBtn(ActionEvent event) throws IOException{
        App app = new App();
        app.changeScene("mainscene.fxml");
    }

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
                correctWordsTxt.setText(String.valueOf(correctCounter));

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


    Runnable practiceLogic = new Runnable() {
        @Override
        public void run() {
            if (timer > -1) {
                timeLeft.setText(String.valueOf(timer));
                timer -= 1;
            }

            else {
                if (timer == -1) {
                    practiceTf.setDisable(true);
                    practiceTf.setText("The time is over");

                    try {
                        FileWriter myWriter = new FileWriter(saveData);
                        myWriter.write(countAll +";");
                        myWriter.write(correctCounter +";");
                        myWriter.write(String.valueOf(practiceTime));
                        myWriter.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    playAgainBtn.setVisible(true);
                    playAgainBtn.setDisable(false);
                    executor.shutdown();
                }
                timer -= 1;
            }
        }
    };


    Runnable showWrongImg = new Runnable() {
        @Override
        public void run() {
            wrongIv.setOpacity(0);
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
            }
            wrongIv.setOpacity(50);
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {          
            }
            wrongIv.setOpacity(100);
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
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
            }
            correctIv.setOpacity(50);
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
            }
            correctIv.setOpacity(100);
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
            }
            correctIv.setOpacity(0);
        }
    };
    
}
