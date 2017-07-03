/*
 * The MIT License
 *
 * Copyright 2017 holger.
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
package gens.randomfunctiontrees.functioncollections;


import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Random;


/**
 *
 * @author holger
 */
public class Collection {
    
    public static double avg(double a, double b) {
        return (a+b)/2;
    } 

    public static double min(double a, double b) {
        return (a>=b)?b:a;
    }     
    
    public static double max(double a, double b) {
        return (a>=b)?a:b;
    } 
    
    public static double sin(double a) {
        return Math.sin(a);
    }    

    public static double cos(double a) {
        return Math.cos(a);
    }  
    
    public static double tan(double a) {
        return Math.tan(a);
    }  
    
    public static double plusOne(double a) {
        return a + 1.0;
    }       
   
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
            int countParams = methlist[i].getParameterCount();
            if ( ! "getRandomFunctionName".equals(name)) {
                filteredMethlist.add(String.valueOf(countParams) + "," + name);
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
