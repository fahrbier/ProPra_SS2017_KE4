
package gens.randomfunctiontrees;

import java.util.ArrayList;


/**
 *
 * @author holger
 */
public class FunctionTreeNode {

    private int myDepth;
    private String functionName;
   
    private ArrayList<FunctionTreeNode> children = new ArrayList<>();

    FunctionTreeNode(int depth) {
        this.myDepth = depth;      
    }
    
    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }
    
    public String getFunctionName() {
        return this.functionName;
    }
    
    public void addChild(FunctionTreeNode child) {
        this.children.add(child); 
    }
    
    public boolean isLeaf() {
        return children.isEmpty();
    }
 
    public ArrayList<FunctionTreeNode> getChildren() {
        return this.children;
    }
    
    public int getDepth() {
        return this.myDepth;
    }
    
}
