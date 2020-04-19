package gui;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable {

    CellBoard cells;

    @FXML
    AnchorPane pane1;
    @FXML
    Spinner genInput;
    @FXML
    ToggleButton endlessMode;
    @FXML
    Button run;
    @FXML
    Button pause;
    @FXML
    Slider speedSlider;
    @FXML
    Label genLabel;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        cells = new CellBoard();
        pane1.getChildren().add(cells);

        cells.getRunning().addListener(((observable, oldValue, newValue) -> {

            if(newValue==false){
                pause.setVisible(false);
                run.setVisible(true);
            }
            else {
                run.setVisible(false);
                pause.setVisible(true);
            }
        }));

        cells.getCurrent().addListener(((observable, oldValue, newValue) -> {

            Platform.runLater(new Runnable(){
                @Override
                    public void run(){
                        genLabel.setText(newValue + "");
                }
            });

        }));

        Utilities.initializeSpinner(genInput);
        genInput.valueProperty().addListener((v, oldValue, newValue)-> {
            cells.setGenNum((Integer) newValue);
        });

        endlessMode.selectedProperty().addListener((v, oldValue, newValue)->{
            cells.setEndlessMode(newValue);
        });

        speedSlider.valueProperty().addListener(((observable, oldValue, newValue) -> {

            double current = (double) newValue;

            if(current == 1)
                cells.setDelay(5000);

            if(current == 2)
                cells.setDelay(3000);

            if(current == 3)
                cells.setDelay(1000);

            if(current == 4)
                cells.setDelay(500);

            if(current == 5)
                cells.setDelay(250);

            }
        ));

    }

    @FXML
    public void stopAutomaton(){
        cells.getRunning().setValue(false);
    }

    @FXML
    public void clearTheBoard(){
        if(!cells.getRunning().getValue())
            cells.clearBoard();
    }
    @FXML
    public void runAutomaton(){

        cells.runWireWorld();

    }
    @FXML
    public void showLastGen(){
        if (!cells.getRunning().getValue())
            cells.showLast();
    }
    @FXML
    public void showNextGen(){
        if (!cells.getRunning().getValue())
            cells.showNext();
    }
}
