package core;


public class Cell {

    private CellStates state;

    public Cell(CellStates state) {
        this.state = state;
    }



    public CellStates getState() {
        return state;
    }

    public void setState(CellStates ww) {
        state = ww;
    }

    public String stateToString() {

        switch(this.getState()) {
            case EMPTY: return "EMPTY";
            case CONDUCTOR: return "CONDUCTOR";
            case ELECTRON_HEAD: return "ELECTRON_HEAD";
            case ELECTRON_TAIL: return "ELECTRON_TAIL";
            default: return "WRONG";
        }
    }

}
