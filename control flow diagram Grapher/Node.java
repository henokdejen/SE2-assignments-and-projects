/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package contrrolflowdiagram;

/**
 *
 * @author Henkok
 */
public class Node {

    private int id;
    private String statement;
    private Node trueEnd;
    private Node falseEnd;
    private boolean forLoop = false;

    public Node(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

    public Node getTrueEnd() {
        return trueEnd;
    }

    public void setTrueEnd(Node trueEnd) {
        this.trueEnd = trueEnd;
    }

    public Node getFasleEnd() {
        return falseEnd;
    }

    public void setFalseEnd(Node leftEnd) {
        this.falseEnd = leftEnd;
    }

    public boolean isForLoop() {
        return forLoop;
    }

    public void setForLoop(boolean forLoop) {
        this.forLoop = forLoop;
    }

}
