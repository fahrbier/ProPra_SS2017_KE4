
package gens.randomfunctiontrees;

import java.util.ArrayList;


/**
 *
 * @author holger
 */
public class FunctionTreeNode {

    private final int myDepth;
    private String functionName;
   
    private final ArrayList<FunctionTreeNode> children = new ArrayList<>();

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
    
    /**
     * .print() section ispired by
     * https://stackoverflow.com/questions/4965335/how-to-print-binary-tree-diagram
     */
    public void print() {
        print("", true);
    }

    private void print(String prefix, boolean isTail) {
        System.out.println(prefix + (isTail ? "└── " : "├── ") + functionName);
        for (int i = 0; i < children.size() - 1; i++) {
            children.get(i).print(prefix + (isTail ? "    " : "│   "), false);
        }
        if (children.size() > 0) {
            children.get(children.size() - 1)
                    .print(prefix + (isTail ?"    " : "│   "), true);
        }
    }    
    
}
