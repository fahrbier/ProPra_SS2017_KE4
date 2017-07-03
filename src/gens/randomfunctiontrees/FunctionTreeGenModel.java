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
        
        rand = new Random(34567);

        
        FunctionTreeNode rootNode = this.createFunctionTree(4);
        this.printTree(rootNode);
         
        /*
        try {
            Method method = Collection.class.getMethod("sin", double.class);
            System.out.println("-----------------------------");
            System.out.println(method.getAnnotation(ExpectedArgsLength.class).length()   );

            System.out.println(method.invoke(null, 70.0));
            System.out.println("-----------------------------");
            //method.in
            
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(FunctionTreeGenModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(FunctionTreeGenModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(FunctionTreeGenModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(FunctionTreeGenModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(FunctionTreeGenModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        */
        
        
        
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
                System.out.println ("Child " + i + " to " + functionName);
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
    
    public double calcFunctionTree(FunctionTreeNode startNode, int x, int y) {
       
        if (!startNode.isLeaf()) {
            for (int i=0; i < startNode.getChildren().size(); i++) {
                return calcFunctionTree(startNode.getChildren().get(i), x,  y);
            }      
        }
        
        return 0;
        
    }
    
    
   
    
}
