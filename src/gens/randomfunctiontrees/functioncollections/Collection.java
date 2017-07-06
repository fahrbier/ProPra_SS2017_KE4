package gens.randomfunctiontrees.functioncollections;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Random;

/**
 * A collection of static methods to put randomly into a function tree
 * @author holger
 */
public class Collection {
    
    @ExpectedArgsLength(length=2)
    public static double avg(double[] args) {
        return (args[0]+args[1])/2;
    }
    /*
    @ExpectedArgsLength(length=2)
    public static double mul(double[] args) {
        return args[0]*args[1];
    }  

    @ExpectedArgsLength(length=2)
    public static double exp(double[] args) {
        return Math.abs(Math.pow(args[0], args[1])) > 1.0 ? 1.0 : Math.abs(Math.pow(args[0], args[1]));
    }      
    
    
    @ExpectedArgsLength(length=2)
    public static double min(double[] args) {
        return (args[0]>=args[1])?args[1]:args[0];
    }     
    
    @ExpectedArgsLength(length=2)
    public static double max(double[] args) {
        return (args[0]>=args[1])?args[0]:args[1];
    } 
    */
    @ExpectedArgsLength(length=1)
    public static double sin(double[] args) {
        return Math.abs(Math.sin(Math.toRadians(args[0]) * 360 ));
    }    

    @ExpectedArgsLength(length=1)
    public static double cos(double[] args) {
        return Math.abs(Math.cos(Math.toRadians(args[0]) * 360 ));
    }  
    
    @ExpectedArgsLength(length=1)
    public static double tan(double[] args) {
        return Math.abs(Math.tan(Math.toRadians(args[0]) * 360 ));
    }  
    
    /*
    @ExpectedArgsLength(length=1)
    public static double plusOne(double[] args) {
        return args[0] + 1.0;
    }       
    */
    public static String getRandomFunctionName(Random rand) {
        /**
         * Using reflection to find out all methods of this collection here.
         * This convenience function getRandomFunction() is of course also
         * in the list of the "reflected" methods. And since this function
         * is useless for the function tree, let's filter it out from the
         * pool of functions from which we pick one at random
         */
        
        Method methlist[];
        methlist = Collection.class.getDeclaredMethods();
        ArrayList<String> filteredMethlist = new ArrayList<>();
        for (int i=0; i<methlist.length; i++) {
            String name = methlist[i].getName();
            
            if ( ! "getRandomFunctionName".equals(name)) { //-- 2do: das mal auch noch via annotationen loesen
                filteredMethlist.add(name);
            }
        }
        
        /**
         * Determine a random number in the boundaries Min-Max
         * using the pattern
         * Min + (int)(Math.random() * ((Max - Min) + 1))
         * inspired by https://stackoverflow.com/questions/363681
        */
        
        int min = 0;
        int max = filteredMethlist.size()-1;
        int randomNum = rand.nextInt((max - min) + 1) + min;
           
        return  filteredMethlist.get(randomNum);
    }
}
