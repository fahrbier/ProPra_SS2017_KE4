/*
 * The MIT License
 *
 * Copyright 2017 Christoph Baumhardt.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package gens.randomfunctiontrees;

import general.GenModel;
import gens.randomfunctiontrees.functioncollections.Binaries;
import gens.randomfunctiontrees.functioncollections.Collection;
import gens.randomfunctiontrees.functioncollections.Unaries;
import java.io.PrintStream;
import java.util.Random;
import java.util.function.DoubleConsumer;

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
    
    
    
    public FunctionTreeGenModel() {
        width = 600;
        height = 400;
    }
    
    public int getWidth() {
        return width;
    }
    
    public int getHeight() {
        return height;
    }

    public void setWidth(int value) {
        if (value >  0 && value <= 3000) {
            width = value;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void setHeight(int value) {
        if (value >  0 && value <= 3000) {
            height = value;
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
        
        rand = new Random(1234);
        //System.out.println(Unaries.getRandomFunctionName(rand));
       // System.out.println(Unaries.getRandomFunctionName(rand));
       // System.out.println(Binaries.getRandomFunctionName(rand));
       // System.out.println(Binaries.getRandomFunctionName(rand));
       // System.out.println(Unaries.getRandomFunctionName(rand));
        
        FunctionTreeNode rootNode = this.createTree(4);
        this.printTree(rootNode);
                
        for (int x=0; x<width; x++) {
            for (int y=0; y<height; y++) {
                
                Color c = new Color( 
                        Math.abs(
                            Math.sin(
                                Math.toRadians(
                                    ((double)x/(double)width) * 360
                                )        
                            )
                        ) ,
                        Math.abs(
                            Math.cos(
                                Math.toRadians(
                                    ((double)x/(double)height) * 360
                                )        
                            )
                        ) ,
                        1,
                        1.0);
                
                gc.getPixelWriter().setColor(x,y,c);
            }
        }

        setGenState("Drawing blue circle...");
     
    }
    
    private FunctionTreeNode createTree(int depth) {
        FunctionTreeNode node = new FunctionTreeNode(depth);
        
 
        String functionName = Collection.getRandomFunctionName(rand);
        node.setFunctionName(functionName);
        String[] tmp = functionName.split(",");
        //System.out.println ("Created node with value " + functionName + "["+depth+"]");

        
        if (depth > 1) {
            node.addChild(createTree(depth-1));
            System.out.println ("Child 1 to " + functionName);
            if (tmp[0].equals("2")) {
                node.addChild(createTree(depth-1));
                System.out.println ("Child 2 to " + functionName);
            }

        }                
    

        return node;
    } 
    
    public void printTree(FunctionTreeNode startNode) {
       
        if (!startNode.isLeaf()) {
            for (int i=0; i < startNode.getChildren().size(); i++) {
                printTree(startNode.getChildren().get(i));
            }      
        }
        System.out.println(startNode.getFunctionName() + "[" +startNode.getDepth() + "]");
    
    }
    
    
   
    
}
