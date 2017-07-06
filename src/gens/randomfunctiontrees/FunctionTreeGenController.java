package gens.randomfunctiontrees;

import gens.basicexample1.*;
import general.GenController;
import general.GenModel;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Christoph Baumhardt
 */
public class FunctionTreeGenController extends GenController {

    @FXML private TextField textFieldWidth;  
    @FXML private TextField textFieldHeight;
    @FXML private TextField textFieldSeed;
    @FXML private TextField textFieldDepth;
    
    FunctionTreeGenModel model;

    
    @Override
    public GenModel getModel() {
        return model;
    }
    
    /**
     * This automatically called method creates a new SimpleGenModel and 
     * links it with its view, so that changes on the view get reflected in the
     * model (if they are allowed in the model). 
     * 
     * Note that the view does not get updated if the model is changed from
     * anywhere else besides the very view.
     * 
     */      
    @Override
    public void initialize() {
        super.initialize(); // activate buttonGenerate on Enter
        
        model = new FunctionTreeGenModel();
        
        // display values from model
        textFieldWidth.textProperty().setValue(
                String.valueOf(model.getWidth()));
        textFieldHeight.textProperty().setValue(
                String.valueOf(model.getHeight()));
        textFieldSeed.textProperty().setValue(
                String.valueOf(model.getSeed()));        
        textFieldDepth.textProperty().setValue(
                String.valueOf(model.getDepth())); 
        
        // change model if user changes something on the view
       
        textFieldSeed.focusedProperty().addListener((observableBoolean,
                oldValue, newValue) -> {
            if (!newValue){ // newValue=0 means no focus -> if no longer focused
                try {
                    String s = textFieldSeed.textProperty().getValue();
                    int h = Integer.parseInt(s);
                    model.setSeed(h);
                } catch (IllegalArgumentException ex) {
                    // catches both the possible NumberFormatException from
                    // parseInt() as well as the possible IllegalArgumentExcept.
                    // from SimpleGenModel.setHeight(..)
                    
                    // display last valid value for width from model
                    textFieldSeed.textProperty().setValue(
                            String.valueOf(model.getSeed()));
                    showInputAlert("Seed requires an integer value between 1"+
                            " and 32000.");
                }
            }
        });        
        
        textFieldWidth.focusedProperty().addListener((observableBoolean,
                oldValue, newValue) -> {
            if (!newValue){ // newValue=0 means no focus -> if no longer focused
                try {
                    String s = textFieldWidth.textProperty().getValue();
                    int w = Integer.parseInt(s);
                    model.setWidth(w);
                } catch (IllegalArgumentException ex) {
                    // catches both the possible NumberFormatException from
                    // parseInt() as well as the possible IllegalArgumentExcept.
                    // from SimpleGenModel.setWidth(..)
                    
                    // display last valid value for width from model
                    textFieldWidth.textProperty().setValue(
                            String.valueOf(model.getWidth()));
                    showInputAlert("Width requires an integer value between 1" +
                            " and 3000.");
                }
            }
        });

        textFieldHeight.focusedProperty().addListener((observableBoolean,
                oldValue, newValue) -> {
            if (!newValue){ // newValue=0 means no focus -> if no longer focused
                try {
                    String s = textFieldHeight.textProperty().getValue();
                    int h = Integer.parseInt(s);
                    model.setHeight(h);
                } catch (IllegalArgumentException ex) {
                    // catches both the possible NumberFormatException from
                    // parseInt() as well as the possible IllegalArgumentExcept.
                    // from SimpleGenModel.setHeight(..)
                    
                    // display last valid value for width from model
                    textFieldHeight.textProperty().setValue(
                            String.valueOf(model.getHeight()));
                    showInputAlert("Heigth requires an integer value between 1"+
                            " and 3000.");
                }
            }
        });

        textFieldDepth.focusedProperty().addListener((observableBoolean,
                oldValue, newValue) -> {
            if (!newValue){ // newValue=0 means no focus -> if no longer focused
                try {
                    String s = textFieldDepth.textProperty().getValue();
                    int h = Integer.parseInt(s);
                    model.setDepth(h);
                } catch (IllegalArgumentException ex) {
                    // catches both the possible NumberFormatException from
                    // parseInt() as well as the possible IllegalArgumentExcept.
                    // from SimpleGenModel.setHeight(..)
                    
                    // display last valid value for width from model
                    textFieldDepth.textProperty().setValue(
                            String.valueOf(model.getHeight()));
                    showInputAlert("Depth requires an integer value between 1"+
                            " and 10.");
                }
            }
        });        
        
// NOTE: The view does not reflect changes to the model that are done
        //       outside the given view.
    }

}
