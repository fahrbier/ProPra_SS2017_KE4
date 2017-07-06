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
        width = 200;
        height = 200;
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

        setGenState("Filling image background...");
        GraphicsContext gc = canvas.getGraphicsContext2D();
        
        rand = new Random(this.seed);

        
        FunctionTreeNode rootNode = this.createFunctionTree(this.depth);
        //this.printTree(rootNode);
        
        for (int x=0; x<width; x++) {
            for (int y=0; y<height; y++) {
                
                Color c = new Color(
                        this.calcFunctionTree(rootNode, (double)x/(double)width, (double)y/(double)height),
                        this.calcFunctionTree(rootNode, (double)x/(double)width, (double)y/(double)height),
                        1,
                        1.0);
                
                gc.getPixelWriter().setColor(x,y,c);
            }
        }

        setGenState("Drawing fancy arts...");
     
    }
    
    private FunctionTreeNode createFunctionTree(int depth) {
        
        FunctionTreeNode node = new FunctionTreeNode(depth);
          
        String functionName = Collection.getRandomFunctionName(rand);
        node.setFunctionName(functionName);
        
        int amountChildren = 0;
        
        try {
            /**
             * Read Annotation to find out how many parameters/arguments
             * are expected. This determines the amount of children added
             * unless we're at the desired depth already
             */
            Method method = Collection.class.getMethod(functionName, double[].class);
            amountChildren = method.getAnnotation(ExpectedArgsLength.class).length();
        
        
        } catch (NoSuchMethodException | SecurityException ex) {
            Logger.getLogger(FunctionTreeGenModel.class.getName()).log(Level.SEVERE, null, ex);
        } 
         
        if (depth > 1) {
            
            for (int i=0; i < amountChildren; i++) {
                node.addChild(createFunctionTree(depth-1));
            }

        }                 
        return node;
    } 
    
    private void printTree(FunctionTreeNode startNode) {

        System.out.println(treeDepthForOutput);
        if (!startNode.isLeaf()) {
            treeDepthForOutput += 1;
            for (int i=0; i < startNode.getChildren().size(); i++) {
                printTree(startNode.getChildren().get(i));
            }      
        }
    
    }
    
    private double calcFunctionTree(FunctionTreeNode startNode, double x, double y) {
       
        if (!startNode.isLeaf()) {
            for (int i=0; i < startNode.getChildren().size(); i++) {
                return calcFunctionTree(startNode.getChildren().get(i), x,  y);
            }      
        }
          
        /**
         * Put always both parameter into the args array - unary functions which expects only one
         * value will pick it from double[0] and ignore the remaning values
         */
        double[] args = new double[] { x, y };
       
        try {
            Method method = Collection.class.getMethod(startNode.getFunctionName(), double[].class);
            double value = Double.valueOf(method.invoke(null, args).toString());
            return value > 1 ? 1 : value;
            
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(FunctionTreeGenModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return 0.0;
        
    }
    
    
   
    
}
