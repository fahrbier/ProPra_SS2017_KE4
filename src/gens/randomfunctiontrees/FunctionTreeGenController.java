package gens.randomfunctiontrees;

import general.GenController;
import general.GenModel;
import java.io.File;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

/**
 * FXML Controller class
 *
 * @author Christoph Baumhardt
 */
public class FunctionTreeGenController extends GenController {

    @FXML private TextField width;  
    @FXML private TextField height;
    @FXML private TextField seed;
    @FXML private TextField depth;
    @FXML private ComboBox<String> colorGeneration;     
    @FXML private Button openFile;  
    @FXML private CheckBox createMany;
    @FXML private TextField howManySeeds;
    @FXML private Button generateMany;
    @FXML private Button buttonGenerate;
    @FXML private CheckBox showTreeInConsole;
    
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
        width.textProperty().setValue(String.valueOf(model.getWidth()));
        height.textProperty().setValue(String.valueOf(model.getHeight()));
        seed.textProperty().setValue(String.valueOf(model.getSeed()));        
        depth.textProperty().setValue(String.valueOf(model.getDepth())); 
        
        // change model if user changes something on the view
       
        seed.focusedProperty().addListener((observableBoolean,
                oldValue, newValue) -> {
            if (!newValue){ // newValue=0 means no focus -> if no longer focused
                try {
                    String s = seed.textProperty().getValue();
                    int h = Integer.parseInt(s);
                    model.setSeed(h);
                } catch (IllegalArgumentException ex) {
                    // catches both the possible NumberFormatException from
                    // parseInt() as well as the possible IllegalArgumentExcept.
                    // from SimpleGenModel.setHeight(..)
                    
                    // display last valid value for width from model
                    seed.textProperty().setValue(
                            String.valueOf(model.getSeed()));
                    showInputAlert("Seed requires an integer value between 1"+
                            " and 32000.");
                }
            }
        });        
        
        width.focusedProperty().addListener((observableBoolean,
                oldValue, newValue) -> {
            if (!newValue){ // newValue=0 means no focus -> if no longer focused
                try {
                    String s = width.textProperty().getValue();
                    int w = Integer.parseInt(s);
                    model.setWidth(w);
                } catch (IllegalArgumentException ex) {
                    // catches both the possible NumberFormatException from
                    // parseInt() as well as the possible IllegalArgumentExcept.
                    // from SimpleGenModel.setWidth(..)
                    
                    // display last valid value for width from model
                    width.textProperty().setValue(
                            String.valueOf(model.getWidth()));
                    showInputAlert("Width requires an integer value between 1" +
                            " and 500.");
                }
            }
        });

        height.focusedProperty().addListener((observableBoolean,
                oldValue, newValue) -> {
            if (!newValue){ // newValue=0 means no focus -> if no longer focused
                try {
                    String s = height.textProperty().getValue();
                    int h = Integer.parseInt(s);
                    model.setHeight(h);
                } catch (IllegalArgumentException ex) {
                    // catches both the possible NumberFormatException from
                    // parseInt() as well as the possible IllegalArgumentExcept.
                    // from SimpleGenModel.setHeight(..)
                    
                    // display last valid value for width from model
                    height.textProperty().setValue(
                            String.valueOf(model.getHeight()));
                    showInputAlert("Heigth requires an integer value between 1"+
                            " and 500.");
                }
            }
        });

        depth.focusedProperty().addListener((observableBoolean,
                oldValue, newValue) -> {
            if (!newValue){ // newValue=0 means no focus -> if no longer focused
                try {
                    String s = depth.textProperty().getValue();
                    int h = Integer.parseInt(s);
                    model.setDepth(h);
                } catch (IllegalArgumentException ex) {
                    // catches both the possible NumberFormatException from
                    // parseInt() as well as the possible IllegalArgumentExcept.
                    // from SimpleGenModel.setHeight(..)
                    
                    // display last valid value for width from model
                    depth.textProperty().setValue(String.valueOf(model.getHeight()));
                    showInputAlert("Depth requires an integer value between 1 and 10.");
                }
            }
        });        
        
 

        createMany.selectedProperty().addListener(
            (observableBoolean, oldValue, newValue) -> {
                if (newValue) {
                    howManySeeds.setDisable(false);
                    generateMany.setVisible(true);
                    buttonGenerate.setVisible(false);
                }
                else {
                    howManySeeds.setDisable(true);
                    generateMany.setVisible(false);
                    buttonGenerate.setVisible(true);                   
                }
                System.out.println(oldValue);
                System.out.println(newValue);
            }
   
        );
        

    }
    
    @FXML
    public void generateMany(){

    }
    
    public void openFileDialog() {
    
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(openFile.getScene().getWindow());

        try {
            int seed = Integer.parseInt(file.getName().split("\\.")[0].split("rft")[0]);
            int depth = Integer.parseInt(file.getName().split("\\.")[0].split("rft")[1]);
            
            model.setSeed(seed);
            model.setDepth(depth);
            
            this.seed.textProperty().setValue(String.valueOf(model.getSeed()));        
            this.depth.textProperty().setValue(String.valueOf(model.getDepth())); 
        }
        catch (Exception ex)  {
            showInputAlert("File Name has to be in the form {$seed}rft{$depth}.png like 2411rft7.png for example.");
        }
 
    }
}
