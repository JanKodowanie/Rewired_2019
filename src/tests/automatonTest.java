package tests;

import core.Cell;
import core.CellStates;
import core.Generation;
import core.WireWorld;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class automatonTest {

    WireWorld automaton;

    @Test
    public void cellStatesShouldMatch(){

        automaton = WireWorld.getInstance();

        Cell[][] cells = new Cell[3][3];

        for(int i=0; i<3; i++){
            cells[i] = new Cell[3];
            for (int j=0; j<3; j++){
                cells[i][j] = new Cell(CellStates.EMPTY);
            }
        }

        cells[0][0].setState(CellStates.ELECTRON_HEAD);
        cells[0][2].setState(CellStates.CONDUCTOR);
        cells[1][0].setState(CellStates.CONDUCTOR);
        cells[1][1].setState(CellStates.CONDUCTOR);
        cells[2][0].setState(CellStates.ELECTRON_TAIL);
        cells[2][1].setState(CellStates.ELECTRON_HEAD);

        Generation genZero = new Generation(cells);

        automaton.initialize(genZero);
        automaton.runWireWorld();
        Generation newGen = automaton.getGenerations().get(1);

        assertEquals(CellStates.ELECTRON_TAIL, newGen.getCells()[0][0].getState());
        assertEquals(CellStates.EMPTY, newGen.getCells()[0][1].getState());
        assertEquals(CellStates.CONDUCTOR, newGen.getCells()[0][2].getState());
        assertEquals(CellStates.ELECTRON_HEAD, newGen.getCells()[1][0].getState());
        assertEquals(CellStates.ELECTRON_HEAD, newGen.getCells()[1][1].getState());
        assertEquals(CellStates.EMPTY, newGen.getCells()[1][2].getState());
        assertEquals(CellStates.CONDUCTOR, newGen.getCells()[2][0].getState());
        assertEquals(CellStates.ELECTRON_TAIL, newGen.getCells()[2][1].getState());
        assertEquals(CellStates.EMPTY, newGen.getCells()[2][2].getState());


    }




}
