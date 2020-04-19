package gui;

import core.CellStates;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;

public class RecCell extends Rectangle {

    private static Color[] states = {Color.BLACK, Color.BLUE, Color.RED, Color.YELLOW};
    private double stroke;

    public RecCell(double dim, int index){

        super(dim, dim);

        setStroke(Color.GRAY);
        stroke = 0.01*dim;
        setStrokeWidth(stroke);
        setState(index);
        setStrokeType(StrokeType.INSIDE);

        setOnMouseEntered(e-> {
            setStrokeWidth(stroke*2);
            setStroke(Color.ORANGE);

        });

        setOnMouseExited(e-> {
            setStrokeWidth(stroke);
            setStroke(Color.GRAY);

        });

    }

    public void setState(int i){
        try {
            setFill(states[i]);
        }catch (IndexOutOfBoundsException e){

        }

    }

    public void changeState(){

        for(int i=0; i < states.length; i++) {
            if ((getState() == states[i]) && i!=3){
                setState(i+1);
                break;
            }
            if ((getState() == states[i]) && i==3){
                setState(0);
                break;
            }
        }
    }


    public Color getState(){
        return (Color) getFill();
    }

    public CellStates getWWState() {

        if (getFill() == Color.BLACK)
            return CellStates.EMPTY;

        if (getFill() == Color.BLUE)
            return CellStates.ELECTRON_HEAD;

        if (getFill() == Color.RED)
            return CellStates.ELECTRON_TAIL;

        if (getFill() == Color.YELLOW)
            return CellStates.CONDUCTOR;

        return null;
    }
}