package gui;
import core.Cell;
import core.CellStates;
import core.Generation;
import core.WireWorld;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.layout.Pane;

public class CellBoard extends Pane implements Runnable {

    private RecCell[][] cells;
    private IntegerProperty current;
    private int genNum;
    private BooleanProperty running;
    private boolean endlessMode;
    private int delay;
    private WireWorld instance;
    private Thread automaton;

    public CellBoard(){

        instance = WireWorld.getInstance();
        current = new SimpleIntegerProperty(0);
        delay = 1000;
        endlessMode = false;
        running = new SimpleBooleanProperty(false);
        genNum = 10;

        cells = new RecCell[40][40];

        setWidth(600);
        setHeight(600);

        for(int i = 0; i < 40; i++)
            for(int j =0; j < 40; j++){
                cells[i][j] = new RecCell(15,0);
                int finalI = i;
                int finalJ = j;
                cells[i][j].setOnMouseClicked(e->{
                    if(!running.getValue())
                        cells[finalI][finalJ].changeState();
                });
                cells[i][j].setTranslateX(15*j);
                cells[i][j].setTranslateY(15*i);
                getChildren().add(cells[i][j]);
            }
    }

    public void runWireWorld(){

        instance.initialize(readTheBoard());

        for (int i = 0; i < genNum; i++) {
            instance.runWireWorld();
        }

        current.setValue(0);

        running.setValue(true);
        automaton = new Thread(this);
        automaton.start();
        automaton = null;
    }



    public Generation readTheBoard(){

        Cell[][] genZero = new Cell[40][40];

        for(int i=0; i < 40; i++) {

            genZero[i] = new Cell[40];

            for (int j = 0; j < 40; j++) {
                RecCell cell = cells[i][j];

                switch (cell.getWWState()) {
                    case EMPTY:
                        genZero[i][j] = new Cell(CellStates.EMPTY);
                        break;
                    case ELECTRON_HEAD:
                        genZero[i][j] = new Cell(CellStates.ELECTRON_HEAD);
                        break;
                    case ELECTRON_TAIL:
                        genZero[i][j] = new Cell(CellStates.ELECTRON_TAIL);
                        break;
                    case CONDUCTOR:
                        genZero[i][j] = new Cell(CellStates.CONDUCTOR);
                        break;
                }
            }
        }
        return new Generation(genZero);
    }

    public void clearBoard(){

        instance.getGenerations().clear();
        current.setValue(0);

        for(int i=0; i < 40; i++)
            for(int j=0; j < 40; j++)
            {
                cells[i][j].setState(0);
            }
    }

    @Override
    public void run() {
        while (running.getValue() && current.getValue() < genNum ){

            showNext();
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {

            }
        }
        running.setValue(false);
    }

    public void showNext(){

        if(instance.getGenerations().size() - 1 > current.getValue()){

            Generation nextGeneration = instance.getGenerations().get(current.getValue() +1);
            for(int i=0; i < 40; i++)
                for(int j=0; j < 40; j++)
                {
                    RecCell cell = cells[i][j];
                    Cell nextGenCell = nextGeneration.getCells()[i][j];

                    switch (nextGenCell.getState()){
                        case EMPTY:
                            cell.setState(0);
                            break;
                        case ELECTRON_HEAD:
                            cell.setState(2);
                            break;
                        case ELECTRON_TAIL:
                            cell.setState(1);
                            break;
                        case CONDUCTOR:
                            cell.setState(3);
                            break;
                    }
                }
            current.setValue(current.getValue()+1);

        }
    }

    public void showLast() {

        if (current.getValue() > 0) {

            Generation lastGeneration = instance.getGenerations().get(current.getValue() - 1);
            for (int i = 0; i < 40; i++)
                for (int j = 0; j < 40; j++) {
                    RecCell cell = cells[i][j];
                    Cell lastGenCell = lastGeneration.getCells()[i][j];

                    switch (lastGenCell.getState()) {
                        case EMPTY:
                            cell.setState(0);
                            break;
                        case ELECTRON_HEAD:
                            cell.setState(2);
                            break;
                        case ELECTRON_TAIL:
                            cell.setState(1);
                            break;
                        case CONDUCTOR:
                            cell.setState(3);
                            break;
                    }
                }
            current.setValue(current.getValue()-1);
        }
    }

    public RecCell[][] getCells(){
        return cells;
    }


    public IntegerProperty getCurrent() {
        return current;
    }

    public int getGenNum() {
        return genNum;
    }

    public void setGenNum(int genNum) {
        this.genNum = genNum;
    }

    public boolean isEndlessMode() {
        return endlessMode;
    }

    public void setEndlessMode(boolean endlessMode) {
        this.endlessMode = endlessMode;
    }

    public BooleanProperty getRunning() {
        return running;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public int getDelay() {
        return delay;
    }
}
