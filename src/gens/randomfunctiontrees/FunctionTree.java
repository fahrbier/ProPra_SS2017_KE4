package gens.randomfunctiontrees;

import gens.randomfunctiontrees.functioncollections.Collection;
import gens.randomfunctiontrees.functioncollections.ExpectedArgsLength;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author holger
 */
public class FunctionTree {
    /**
     * Static function to build up a tree of a given depth
     * 
     * @param depth
     * @param rand
     * @return 
     */
    
    public static FunctionTreeNode createFunctionTree(int depth, Random rand) {
        
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
                node.addChild(createFunctionTree(depth-1, rand));
            }

        }                 
        return node;
    } 
    
    /**
     * Static function to walk recursevly down the tree with the args and then calculate using the functions in the nodes on the way back up
     * @param startNode
     * @param args You can always put all parameters into the args array - the different functions (unary, binary etc.) pick only the amount of parameters they need 
     * @return 
     */
    
    public static double calcFunctionTree(FunctionTreeNode startNode, double[] args) {
       
        if (!startNode.isLeaf()) {
            for (int i=0; i < startNode.getChildren().size(); i++) {
                return calcFunctionTree(startNode.getChildren().get(i), args);
            }      
        }
       
        try {
            Method method = Collection.class.getMethod(startNode.getFunctionName(), double[].class);
            double value = Double.valueOf(method.invoke(null, args).toString());
            return value;
            
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(FunctionTreeGenModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //- in doubt, result 0
        return 0.0;
        
    }
}
