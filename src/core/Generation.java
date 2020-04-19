package core;

public class Generation{

    private Cell[][] cells;


    public Generation (Cell[][] cells){
        this.setCells(cells);
    }

    public Cell[][] getCells() {
        return cells;
    }

    public int getRows() {
        return cells.length;
    }

    public int getColumns() {
        return cells[0].length;
    }

    public void setCells(Cell[][] cells) {
        this.cells = cells;
    }

}
