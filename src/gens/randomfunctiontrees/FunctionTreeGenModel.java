package gens.randomfunctiontrees;

import general.GenModel;

import gens.randomfunctiontrees.functioncollections.Collection;
import gens.randomfunctiontrees.functioncollections.ExpectedArgsLength;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;


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
    
    private int treeDepthForOutput = 1;
    
    public FunctionTreeGenModel() {
        width = 5;
        height = 5;
        seed = 2411;
        depth = 4;
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
        /*
        canvas.setOnMouseMoved((MouseEvent event) -> {
            System.out.println(event.getSceneX());
            System.out.println(event.getSceneY());
        });
        */
        setGenState("Filling image background...");
        GraphicsContext gc = canvas.getGraphicsContext2D();
        
        rand = new Random(this.seed);

        
        FunctionTreeNode rootNode = FunctionTree.createFunctionTree(depth, rand);
        
        //-- print the tree to the console for debug resons
        rootNode.print();
        
        for (int x=0; x<width; x++) {
            for (int y=0; y<height; y++) {
                double[] args = {(double)x/(double)width, (double)y/(double)height};
                Color c = Color.hsb(FunctionTree.calcFunctionTree(rootNode, args) * 360, 1, 1);
                gc.getPixelWriter().setColor(x,y,c);
            }
        }

        setGenState("Drawing random arts...");
  
    }

}