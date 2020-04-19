package gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.util.StringConverter;

public class Utilities {



    public static boolean isInteger(String s){

        if(s.isEmpty()) return false;

        for(int i = 0; i < s.length(); i++){
            if(Character.digit(s.charAt(i), 10) < 0) return false;
        }
        return true;
    }


    public static void initializeSpinner(Spinner spinner){


        SpinnerValueFactory<Integer> factory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 200, 10);

        spinner.setValueFactory(factory);
        spinner.setEditable(true);

        factory.setConverter(new ToIntConverter());
        spinner.getEditor().setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

                String text = spinner.getEditor().getText();
                if (isInteger(text)) {

                    StringConverter<Integer> converter = spinner.getValueFactory().getConverter();
                    Integer value = converter.fromString(text);

                    spinner.getValueFactory().setValue(value);

                } else {
                    spinner.getValueFactory().setValue(1);
                    spinner.getValueFactory().setValue(10);
                }

            }
        });
    }


    public static class ToIntConverter extends StringConverter<Integer>{

            @Override
            public String toString(Integer object) {
                return object + "";
            }

            @Override
            public Integer fromString(String string) {
                return Integer.parseInt(string);
            }


    }
}
