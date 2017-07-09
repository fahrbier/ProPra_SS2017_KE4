package gens.randomfunctiontrees;

import general.GenModel;
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
   
    private Random rand;
    
    private int width; 
    private int height;
    private int seed;
    private int depth;
    
    private int thisManySeeds;
    private boolean showTreeInConsole;
    
    private final int treeDepthForOutput = 1;
    
    public FunctionTreeGenModel() {
        width = 5;
        height = 5;
        seed = 2411;
        depth = 4;
        thisManySeeds = 1;
        showTreeInConsole = false;
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
    
    public void setWidth(int value) {
        if (value >  0 && value <= 500) {
            width = value;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void setHeight(int value) {
        if (value >  0 && value <= 500) {
            height = value;
        } else {
            throw new IllegalArgumentException();
        }
    }    
 
    public void setSeed(int value) {
        if (value >  0 && value <= 32000) {
            seed = value;
        } else {
            throw new IllegalArgumentException();
        }
    }     

    public void setDepth(int value) {
        if (value >  0 && value <= 10) {
            depth = value;
        } else {
            throw new IllegalArgumentException();
        }
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
        filename = this.seed + "rft" + this.depth; 
        
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
                Color c = Color.hsb(FunctionTree.calcFunctionTree(rootNode, args) * 360, 1, 1);
                gc.getPixelWriter().setColor(x,y,c);
            }
        }

        setGenState("Drawing random arts...");
  
    }
    
    public void createMany() {
        
        
        for (int i=0; i<500; i++) {
            rand = new Random(this.seed+i);
            canvas = new Canvas(width, height);
            GraphicsContext gc = canvas.getGraphicsContext2D();
            FunctionTreeNode rootNode = FunctionTree.createFunctionTree(depth, rand);
            
            for (int x=0; x<width; x++) {
                for (int y=0; y<height; y++) {
                    double[] args = {(double)x/(double)width, (double)y/(double)height};
                    Color c = Color.hsb(FunctionTree.calcFunctionTree(rootNode, args) * 360, 1, 1);
                    gc.getPixelWriter().setColor(x,y,c);
            }
                
            this.saveImage(String.valueOf(this.seed+i) + "rft" + String.valueOf(depth));
        }
            
        }
    }

}