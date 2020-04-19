package core;

import java.util.ArrayList;
import java.util.List;


public class WireWorld {

    private static WireWorld instance;
    private List<Generation> generations;

    private WireWorld(){
        generations = new ArrayList<>();
    }

    public static WireWorld getInstance(){
        if(instance == null)
            instance = new WireWorld();
        return instance;
    }

    private boolean isConductor(Cell cell) {
        if (cell.getState() == CellStates.CONDUCTOR)
            return true;
        return false;
    }

    public void initialize(Generation genZero){

        if (!generations.isEmpty())
            generations.clear();

        generations.add(genZero);

    }

    private Cell[][] surroundCells(Cell[][] wwCells) {

        int height = generations.get(generations.size()-1).getRows();
        int width = generations.get(generations.size()-1).getColumns();
        Cell[][] wardedCells = new Cell[height+2][width+2];
        for (int i=0; i<height+2; i++) {
            wardedCells[i] = new Cell[width+2];
            for (int j=0; j<width+2; j++) {
                wardedCells[i][j] = new Cell(CellStates.EMPTY);
            }
        }

        for (int i=0; i<height; i++)
            for (int j = 0; j < width; j++)
                wardedCells[i+1][j+1] = wwCells[i][j];

        return wardedCells;
    }


    public void runWireWorld() {
        if(!generations.isEmpty()){
            Generation lastGen = generations.get(generations.size() - 1);
            Cell[][] lastGenBoard = (Cell[][]) lastGen.getCells();
            lastGenBoard = surroundCells(lastGenBoard);
            int height = lastGen.getRows();
            int width = lastGen.getColumns();

            Cell[][] nextGenBoard = new Cell[height][width];
            for (int r=0; r<height; r++) {
                nextGenBoard[r] = new Cell[width];
                for (int c = 0; c < width; c++) {

                    nextGenBoard[r][c] = new Cell(CellStates.EMPTY);
                    if (lastGenBoard[r+1][c+1].getState() == CellStates.ELECTRON_HEAD)
                        nextGenBoard[r][c].setState(CellStates.ELECTRON_TAIL);
                    else if (lastGenBoard[r+1][c+1].getState() == CellStates.ELECTRON_TAIL)
                        nextGenBoard[r][c].setState(CellStates.CONDUCTOR);
                    else if (isConductor(lastGenBoard[r+1][c+1])) {
                        int headCounter = 0;

                        for(int i=r; i<r+3; i++){
                            for(int j=c; j<c+3; j++)
                                if(lastGenBoard[i][j].getState()==CellStates.ELECTRON_HEAD){
                                    headCounter++;
                                }
                        }

                        if (headCounter == 1 || headCounter == 2)
                            nextGenBoard[r][c].setState(CellStates.ELECTRON_HEAD);
                        else
                            nextGenBoard[r][c].setState(CellStates.CONDUCTOR);
                    }
                }
            }
            Generation nextGen = new Generation(nextGenBoard);

            getGenerations().add(nextGen);

        }
    }

    public List<Generation> getGenerations() {
        return generations;
    }
}
