/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package contrrolflowdiagram;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

/**
 *
 * @author Henkok
 * 
 * assumption: there is only one branching key word in a single line and only one statement per line and vice versa
 */
public class Parser {
    private String filePath;
    private String[] branchingWords = {"if", "else if", "for", "while", "break", "continue"};    
    private Node startingNode = new Node(0);
    private Node lastNode = startingNode;
    private Node lastOpenedIf = null;
    private Node lastBranching = null;
    private boolean isAddToRight = true;
    
    private Stack<Node> foundBranchings = new Stack();
    private Stack<Node> nodesToAddLast = new Stack();
    
//    ArrayList<Integer> foundBranchings = new ArrayList<>();
    
    public Parser(String filePath) {
        this.filePath = filePath;
    }
    
    public ArrayList<String> readFileAsLines() throws FileNotFoundException, IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        ArrayList<String> lines = new ArrayList();
               
        String line;
        while ((line = reader.readLine()) != null) {
            lines.add(line);
        }        
        return lines;
    }
    
    public void parseAndCreateNodes(ArrayList<String> lines) {
        
        String line;
        for (int i = 0; i < lines.size(); i++) {
            line = lines.get(i);            
            Node currentNode = new Node(i + 1);
            
            String wordFound = checkIfbranchingWord(line);
            if (wordFound != null) {
                if (wordFound.equals("continue") || wordFound.equals("break")) {
                    currentNode.setStatement(line);
                    continue;
                }
                handleBranchingLines(currentNode, line, wordFound);                                                
            }
            else {
                handleNonBranchingLines(line, currentNode);
            }
        }
    }
    
    public String checkIfbranchingWord(String line) {
        for (String word: branchingWords) {
            if (line.contains(word.trim())) {
                return word;
            }
        }
       return null;
    }
    
    
    public void handleBranchingLines(Node node, String line, String wordFound) {
        /*
            this method will findout conditioning expression and if if it is for loop
            it find out initiliaser, condition, and updating statesments. it will add 
            updating statemetn to stack to be added at the end of branching...
        */
        
        
        lastBranching = node;
        
        // if { is started for the branching
        if (line.contains("{")) {
            foundBranchings.push(node);
            lastBranching = null;
        }
        
        // finding out expression        
        String expression = line.substring(
                line.indexOf("("), 
                line.lastIndexOf(")"));

        if (wordFound.equals("for")) {
            String[] tokens = expression.split(";");  
            
            // loop counter initiliaser for (int i = 0;fafasf; fsagfasg)
            node.setStatement(tokens[0]);
            addNodeToNodeTree(node);
            
            // conditioning node
            Node condNode = new Node(node.getId() + 1);                        
            condNode.setStatement(tokens[1]);
            addNodeToNodeTree(condNode);
            lastOpenedIf = condNode;
            
            nodesToAddLast.push(new Node(-1));
            return;
            // i dont know what to de with updating expression
        }
        if (wordFound.equals("if")) lastOpenedIf = node;
        
        node.setStatement(expression);
        addNodeToNodeTree(node);
                
    }
    
    // other side branching hasn't be done
    
    public void handleNonBranchingLines(String line, Node node) {             
        if (line.contains(";")){
            node.setStatement(line);
            addNodeToNodeTree(node);
        }
        
        if (line.contains("{") && lastBranching != null) {
            foundBranchings.push(lastBranching);
        }
                
        if (line.contains("}" ) && lastBranching != null) {
            if (foundBranchings.pop().isForLoop()) {
                addNodeToNodeTree(nodesToAddLast.pop());
                isAddToRight = false;
                lastNode = lastOpenedIf;
            }
        }  
        
        if (line.contains("else")) {
            isAddToRight = false;
            lastNode = lastOpenedIf;
        }
    }

    private void addNodeToNodeTree(Node node) {
        if (isAddToRight) {
            lastNode.setTrueEnd(node);
        }        
        else {
            lastNode.setFalseEnd(node);
            isAddToRight = true;
        }
        lastNode = node;
    }
}
