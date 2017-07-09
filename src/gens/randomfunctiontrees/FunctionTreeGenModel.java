package gens.randomfunctiontrees;

import general.GenModel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Random;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;



/**
 *
 * @author Christoph Baumhardt
 */
public class FunctionTreeGenModel extends GenModel {

    @Override
    public String getGenName() {
        return "Randon Function Tree Generator";
    }
   
    /**
     * Define some boundaries for the fields of this model
     * 
     */
    public static final int MAX_SEED = 32000;
    public static final int MAX_MORE_SEED = 500;
    public static final int MAX_HEIGHT = 500;
    public static final int MAX_WIDTH = 500;
    public static final int MAX_DEPTH = 10;
  
    public FunctionTreeGenModel(Random rand, int width, int height, int seed, int depth, int thisManySeeds, boolean showTreeInConsole, String colorGeneration) {
       
        this.rand = rand;
        this.width = width;
        this.height = height;
        this.seed = seed;
        this.depth = depth;
        this.thisManySeeds = thisManySeeds;
        this.showTreeInConsole = showTreeInConsole;
        this.colorGeneration = colorGeneration;
        
    }
    
    private Random rand;
    
    private int width; 
    private int height;
    private int seed;
    private int depth;
    
    private int thisManySeeds;
    private boolean showTreeInConsole;
    
    private String colorGeneration;
    
    
    public FunctionTreeGenModel() {
        width = 150;
        height = 150;
        seed = 2411;
        depth = 7;
        thisManySeeds = 1;
        showTreeInConsole = false;
        colorGeneration = "HSB - H randomized";
    }
    
    public int getWidth() {
        return width;
    }
    
    public int getHeight() {
        return height;
    }
    
    public int getSeed() {
        return seed;
    }    

    public int getDepth() {
        return depth;
    }  
    
    public int getThisManySeeds() {
        return thisManySeeds;
    }
    
    public boolean getShowTreeInCosole() {
        return showTreeInConsole;
    }
    
    public String getColorGeneration() {
        return colorGeneration;
    }
    
    public void setWidth(int value) {
        if (value >  0 && value <= FunctionTreeGenModel.MAX_WIDTH) {
            width = value;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void setHeight(int value) {
        if (value >  0 && value <= FunctionTreeGenModel.MAX_HEIGHT) {
            height = value;
        } else {
            throw new IllegalArgumentException();
        }
    }    
 
    public void setSeed(int value) {
        if (value >  0 && value <= FunctionTreeGenModel.MAX_SEED) {
            seed = value;
        } else {
            throw new IllegalArgumentException();
        }
    }   
    
    public void setThisManySeeds(int value) {
        /**
         * Don't watch only the range, make also sure, that we stay in the boundaries for the seed itself
         */
        if ( (value >  0 && value <= FunctionTreeGenModel.MAX_MORE_SEED) && seed + value <= FunctionTreeGenModel.MAX_SEED) {
            thisManySeeds = value;
        } else {
            throw new IllegalArgumentException();
        }
    }       

    public void setDepth(int value) {
        if (value >  0 && value <= FunctionTreeGenModel.MAX_DEPTH) {
            depth = value;
        } else {
            throw new IllegalArgumentException();
        }
    }  
    
    public void setColorGeneration(String value) {
        colorGeneration = value;
    }  
    
    public void setTreeInConsole(boolean value) {
        showTreeInConsole = value;
    }
    
    @Override
    public void generate() {     

        setGenState("Creating new canvas...");
        canvas = new Canvas(width, height);
        /**
         * The filename contains the seed and the depth which both can 
         * be used to create the same random tree again.
         * "rft" is the split mark and at the same time to recocnize "my" files
         */
        filename = this.seed + "rft" + this.depth + "rft" + this.colorGeneration; 
        
        setGenState("Filling image background...");
        GraphicsContext gc = canvas.getGraphicsContext2D();
        
        rand = new Random(this.seed);

        
        FunctionTreeNode rootNode = FunctionTree.createFunctionTree(depth, rand);
        
        if (this.showTreeInConsole) {
            rootNode.print();
        }    
        
        for (int x=0; x<width; x++) {
            for (int y=0; y<height; y++) {
                double[] args = {(double)x/(double)width, (double)y/(double)height};
                gc.getPixelWriter().setColor(x,y,this.getColor(rootNode, args));
            }
        }

        setGenState("Drawing random arts...");
  
    }
    
    public void createMany() {
        
        for (int i=0; i < this.thisManySeeds; i++) {
            rand = new Random(this.seed+i);
            canvas = new Canvas(width, height);
            GraphicsContext gc = canvas.getGraphicsContext2D();
            FunctionTreeNode rootNode = FunctionTree.createFunctionTree(depth, rand);
            
            for (int x=0; x<width; x++) {
                for (int y=0; y<height; y++) {
                    double[] args = {(double)x/(double)width, (double)y/(double)height};
                    gc.getPixelWriter().setColor(x,y,this.getColor(rootNode, args));
                }
            }
                
            this.saveImage(String.valueOf(this.seed+i) + "rft" + String.valueOf(depth));
        
            
        }
    }
    
    private Color getColor(FunctionTreeNode rootNode, double[] args) {
        
        Color c;
        
        double componentPercent = FunctionTree.calcFunctionTree(rootNode, args); //-- a value between 0..1
        double hsbComponentAngle = componentPercent * 360;
        int rgbComponent = (int)Math.round(componentPercent * 255);
        
        switch (this.colorGeneration) {
            default:
            case "HSB - H randomized" :
                c = Color.hsb(hsbComponentAngle, 1, 1);
                break;
            case "HSB - S randomized" :
                c = Color.hsb(1, componentPercent, 1);
                break;
            case "HSB - B randomized" :
                c = Color.hsb(1, 1, componentPercent);
                break;
            case "RGB - R randomized" :                     
                c = Color.rgb(rgbComponent, 255, 255);
                break;
            case "RGB - G randomized" :
                c = Color.rgb(255, rgbComponent, 255);
                break;
            case "RGB - B randomized" :
                c = Color.rgb(255, 255, rgbComponent);
                break;                         
        }
        
        return c;
    }

}