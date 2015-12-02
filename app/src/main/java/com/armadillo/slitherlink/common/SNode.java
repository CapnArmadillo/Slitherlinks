package com.armadillo.slitherlink.common;

import android.graphics.Color;
import android.graphics.Path;

import com.armadillo.common.DLog;
import com.armadillo.slitherlink.altair.SlitherlinkHelper;

import java.util.ArrayList;
//import java.awt.Graphics;
//import java.awt.Polygon;
//import java.awt.Color;
//import javax.swing.*;

/**
 * SNode is a class for the creation of Slitherlinks, based on the puzzles
 * found at http://www.krazydad.com/blog/2008/03/28/altair-slitherlinks/
 ****************************************************************************
 * @author John Pushnik
 * @version 02-14-09 Started Slitherlink Version
 * @version 02-28-09 1.0 submittal
 * @version 04-03-09 2.0 submittal
 * @version 05-13-10 2.0 resumed
 ****************************************************************************
 */
public class SNode
{
    private static final String TAG = "SNode";

    private int sides;  //number of sides for this node
    private int empty;  //number of empty sides for this node
    private int filled;  //number of filled sides for this node
    private int crossed;  //number of crossed sides for this node
    private int value;  //number of sides to be filled for this node
    private int highlight = 0;  //color for this node
    protected int magic = 0;  // just don't output this size node in testing

    private float scale = SlitherlinkHelper.SCALE;

    private Position pos;  // xy coordinates for drawing node
    private Position[] points;
    // points for drawing the corners of the polygon
    private Path path;
    private int size;  // in case I want larger nodes...
    private int dir;  // direction, 1 through 4
    private int id;  // initially for testing, used to set value from list
//    private Polygon poly;
    private int color;
    private Boolean isGrown = false;

    protected static final int BACKGROUND_COLOR = Color.WHITE;
    protected static final int INSIDE_COLOR = Color.YELLOW;
    protected static final int OUTSIDE_COLOR = Color.CYAN;
    protected static final int HIGHLIGHT_COLOR = Color.YELLOW;
    protected static final int LINE_COLOR = Color.LTGRAY;
    protected static final int TEXT_COLOR = Color.BLACK;
    protected static final int TEST_COLOR = Color.BLUE;
    private ArrayList<Edge> edges = new ArrayList<Edge>();
    private ArrayList<SNode> nodes = new ArrayList<SNode>();

    /**
     * Constructor for objects of class SNode
     */
    public SNode()
    {
        sides = 0;
        value = -1;
        pos = new Position(0,0);
    }
    /**
     * Constructor for objects of class SNode
     */
    public SNode(int s, int v, Position p)
    {
        sides = s;
        value = v;
        pos = new Position(p);
//        makePoly();
    }
    /**
     * Constructor for objects of class SNode
     */
    public SNode(int s, int v, Position p, float scaleIn)
    {
        sides = s;
        value = v;
        pos = new Position(p);
        scale = scaleIn;
//        makePoly();
    }
    /**
     * Constructor for objects of class SNode.  This one has more bells
     * and whistles, maybe a horn or siren, too.
     */
    public SNode(int s, int v, Position p, int x, int y, int d, float scaleIn)
    {
        sides = s;
        value = v;
        pos = new Position(p,new Position(x,y));
        dir = d;
        scale = scaleIn;
//        makePoly();
        DLog.v(TAG, "[size:" + size + ", center:(" + pos + "), sides:" + s + ", angle:" + d + "];");
//        if(debug){System.out.println("[self drawShapeInContext:context center:CGPointMake(" +
//            pos + ") size:size sides:" + s + " angle:" + d +"];");}
    }
    /*
     * setters and getters
     */
    public void setPosition(Position pos)
    {this.pos = pos;}
    public Position getPosition()
    {return pos;}
    public int getSides()
    {return sides;}
    public void setSides(int set)
    {sides = set;}
    public int getDir()
    {return dir;}
    public void setId(int set)
    {id = set;}
    public int getId()
    {return id;}
    public int getHighlight()
    {return highlight;}
    public void setHighlight(int set)
    {highlight = set;}
    public void toggleHighlight() {
        if (highlight == -1) {
            highlight = 0;
        } else if (highlight == 1) {
            highlight = -1;
        } else {
            highlight = 1;
        }
    }
    public void setOtherHighlight()
    {
        if (highlight != 0)
        {
            for (int i = 0; i < edges.size(); i++)
            {
                edges.get(i).setHighlight(this);
            }
        }
    }

    public int getColor(){
        if (highlight == 0){
            color = BACKGROUND_COLOR;
        } else if  (highlight < 0){
            color = INSIDE_COLOR;
        } else if  (highlight > 0){
            color = OUTSIDE_COLOR;
        }
        return color;
    }
    public int getTextColor() {
        return TEXT_COLOR;
    }

    public ArrayList<Edge> getEdges()
    {return edges;}
    public ArrayList<SNode> getNodes()
    {return nodes;}
    public int getValue()
    {return value;}
    public void setValue(int set)
    {value = set;}

    public void setEdgeValues()
    {
        Edge temp;
        int stat = 0;
        if (value == 0)
        {
            stat = -2;
        }
        if (value == sides)
        {
            stat = 2;
        }
        for (int i = 0; i < edges.size(); i++)
        {
            temp = edges.get(i);
            temp.setStatus(stat);
            edges.set(i, temp);
        }
    }
    public boolean hasEdge(Edge edge)
    {
        for (int i = 0; i < edges.size(); i++){
            if (edge.equals(edges.get(i))) return true;
        }
        return false;
    } // hasEdge()
    public Edge getEdge(Edge edge)
    {
        for (int i = 0; i < edges.size(); i++){
            if (edge.equals(edges.get(i))) return edges.get(i);
        }
        return null;
    } // getEdge()
    public Edge getEdge(int i)
    {
        return edges.get(i);
    } // getEdge()
    public boolean adjHasEdge(Edge edge)
    {
        for (int i = 0; i < nodes.size(); i++){
            if (nodes.get(i).hasEdge(edge) ) return true;
        }
        return false;
    } // adjHasEdge()
    public Edge getAdjEdge(Edge edge)
    {
        for (int i = 0; i < nodes.size(); i++){
            if (nodes.get(i).hasEdge(edge) ) return nodes.get(i).getEdge(edge);
        }
        return null;
    } // getAdjEdge()
    public boolean equalPos(SNode node)
    {
        if (pos.equals(node.pos)) return true;
        return false;
    } // equalPos()
    /**
    * Some days it's nice to rewrite the equals method.  Like when it's 
    * for a new class I wrote, and I want to control how it determines
    * equality.  If there was a class "Man", the comparison should always 
    * return true, according to the forefathers of he United States.  Now 
    * it just means they have the same position, so that nothing overlaps, 
    * and the same number of sides, just in case something else goes wrong.
    * @param node Target node.  Is it the same as this node?
    * @return The answer, true or false.
    */  
    public boolean equals(SNode node)
    {
        if (pos.equals(node.pos) && (sides == node.sides)) {
            return true;
        }
        return false;
    } // equals()
    public boolean hasNode(SNode node)
    {
        for (int i = 0; i < nodes.size(); i++){
            if (node.equals(nodes.get(i))) {
                return true;
            }
        }
        return false;
    } // hasNode()
    public void removeNode(SNode node)
    {
        for (int i = 0; i < nodes.size(); i++){
            if (node.equals(nodes.get(i))) {
                nodes.remove(i);
            }
        }
    } // removeNode()
    public SNode getNode(SNode node)
    {
        for (int i = 0; i < nodes.size(); i++){
            if (node.equals(nodes.get(i))) {
                return nodes.get(i);
            }
        }
        return null;
    } // getNode()
    /**
    * This returns the node at position i. Probably used in a loop.
    * @param i Short for index.  Specifically the index of a node.
    * @return The node for which I asked.  Handy for the solver.
    */  
    public SNode getNode(int i)
    {
        return nodes.get(i);
    } // getNode()
    public boolean adjHasNode(SNode node)
    {
        for (int i = 0; i < nodes.size(); i++){
            if (nodes.get(i).hasNode(node) ) {
                return true;
            }
        }
        return false;
    } // adjHasNode()
    /**
    * I wanted to add "getAdvNode()", it would only return nodes ending in 
    * -ly.  In this case, it gets an Adjacent node.  That seemed easier 
    * than calling it "getNeighborNode".  This one doesn't do any error 
    * checking, I only call it after using adjHasNode.  
    * @param node Target node.  I know it's in there, give it to me!
    * @return The node for which I asked.  Handy for the solver.
    */  
    public SNode getAdjNode(SNode node)
    {
        for (int i = 0; i < nodes.size(); i++){
            if (nodes.get(i).hasNode(node) ){
                return nodes.get(i).getNode(node);
            }
        }
        return null;
    } // getAdjNode()
    public void addNode(SNode node)
    {
        nodes.add(node);
    } // addEdge()
    public void addEdges()
    {
        Position p1, p2;
        p2 = new Position(pos, points[sides - 1]);
        for (int i = 0; i < sides; i++) 
        {
            p1 = p2;
            p2 = new Position(pos, points[i]);
            edges.add(new Edge(p1, p2, this));
        }
        setEdges();
    } // addEdges()
    /**
    * Seriously, why are you still reading this?  This does what it 
    * says, it sets all the edges.  Which means that the edges are then
    * aware of each other, so useful for the solver in the big class.
    */
    public void setEdges()
    {
        for (int i = 0; i < sides; i++) 
        {
            edges.get(i).setEdges();
        }
    } // setEdges()
    /**
     * Seriously, why are you still reading this?  This does what it
     * says, it sets all the edges.  Which means that the edges are then
     * aware of each other, so useful for the solver in the big class.
     */
    public void setEdges(ArrayList<Edge> edges)
    {
        this.edges = edges;
    } // setEdges()
    public void link()
    {
        SNode tI, tJ;
        for (int i = 0; i < (nodes.size() - 1); i++){
            tI = nodes.get(i);
            for (int j = 0; j < tI.nodes.size(); j++){
                tJ = tI.getNode(j);
                if (isClose(this, tJ))
                link(this, tJ);
            }
        }
    }
    public void link(SNode n1, SNode n2)
    {
        if ( !n1.hasNode(n2)){
            
            n1.addNode(n2); 
            n2.addNode(n1);
            n1.setEdges();
            n2.setEdges();
        }
    }
    /**
    * Counts the edges for this node, then sets values for empty, filled,
    * and crossed.  Useful for some of the logic.
    */
    public void count()
    {
        empty = 0;
        filled = 0;
        crossed = 0;
        for (int i = 0; i < (sides); i++){
            if (edges.get(i).getStatus() == 2)
            filled++;
            else if (edges.get(i).getStatus() == -2)
            crossed++;
            else empty++;
        }
    }
    public boolean isClose(SNode n1, SNode n2)
    {
        Position p1, p2;
        int xVal, yVal;
        p1 = n1.getPosition();
        p2 = n2.getPosition();
        xVal = (p1.getX() + p2.getX());
        yVal = (p1.getY() + p2.getY());
        if (((xVal * xVal) + (yVal * yVal)) < 2000) // 43^2 + 1
        {return true;}
        return false;
    }

    public void makePath(Position[] points) {
        this.points = points;
        makePath();
    }

    public void makePath() {
        path = new Path();
        path.setFillType(Path.FillType.EVEN_ODD);
        path.setLastPoint(points[0].getX(), points[0].getY());
        for (int i = 1; i < sides; i++) {
            path.lineTo(points[i].getX(), points[i].getY());
        }
        path.close();
        path.offset(pos.getX(), pos.getY());
    }

/**
 * from http://stackoverflow.com/questions/17569327/does-a-path-contain-a-point
 */
    public boolean contains(Position test) {
        DLog.d(TAG, "checking (" + test.toString() + ")");

        int x = test.getX() - pos.getX();
        int y = test.getY() - pos.getY();

        int ix, iy, jx, jy;

        int j = points.length - 1 ;
        boolean oddNodes = false;

        for (int i = 0; i < points.length; i++) {
            ix = points[i].getX();
            iy = points[i].getY();
            jx = points[j].getX();
            jy = points[j].getY();
            if (((iy < y && jy >= y) || (jy < y && iy >= y))
                    && (ix <= x || jx <= x)) {
                if (ix + (y - iy)
                        / (jy - iy)
                        * (jx - ix) < x) {
                    oddNodes = !oddNodes;
                }
            }
            j = i;
        }

        return oddNodes;
    }
//    public void addToPoly()
//    {
//        for (int i = 0; i < sides; i++)
//        poly.addPoint((pos.x + points[i].x), (pos.y + points[i].y));
//    } // addToPoly()
    /**
    * Draws the shape on the given graphics object.  I would feel 
    * really bad if anyone ever actually read this.
    * @param g Graphics object used to draw on the label.
    */  
//    public void draw(Graphics g)
//    {
//        if (highlight == 0){
//            g.setColor(BACKGROUND_COLOR);
//        } else if  (highlight < 0){
//            g.setColor(INSIDE_COLOR);
//        } else if  (highlight > 0){
//            g.setColor(OUTSIDE_COLOR);
//        }
//        if (sides != magic){
//            g.fillPolygon(poly);
//        }
//        g.setColor(LINE_COLOR);
//        if (sides != magic){
//            g.drawPolygon(poly);
//        }
//        g.setColor(TEXT_COLOR);
//        drawData(g);
//    } // draw()
    /**
    * Draws the data on the given graphics object.  In this case, the value
    * of the SNode.  It's the number that goes inside the shape.  Kind of 
    * the whole points of the puzzle.  Very useful if you want to solve it
    * by hand.  
    * @param g Graphics object used to draw on the label.
    */  
//    public void drawData(Graphics g)
//    {
///* */
//        String temp = toString();
//        int length = temp.length();
//        int ht = g.getFont().getSize();
//        g.drawString(temp, (pos.x - (length * ht / 4)), (pos.y + (ht / 2) - 1));
///* * /
//        String temp = ("" + pos.x);
//        int length = temp.length();
//        int ht = g.getFont().getSize();
//        g.drawString(temp, (pos.x - (length * ht / 4)), (pos.y - 1));
//        temp = ("" + pos.y);
//        length = temp.length();
//        g.drawString((temp), (pos.x - (length * ht / 4)), (pos.y + (ht) - 1));
///* */
//    } // drawData()
   /**
    * Converts int data to String for output.
    * @return Sends a string to whoever asks.  In this case, the value
    * (if it isn't -1).
    */  
    public String toString()
    {
        /* */        
        StringBuilder valStr = new StringBuilder();
        if (value == -1) {
            valStr.append("");
        } else {
            valStr.append(value);
            valStr.append(", ");
        }
        valStr.append(sides + "(" + pos.toString() + ")");
        return valStr.toString();
        /* * /        
        return pos.toString();
        /* */
    } // toString()  

    public Position[] getPoints() {
        return points;
    }

    public void setPoints(Position[] points) {
        this.points = points;
    }

    public Path getPath() {
        return path;
    }

    public int getEmpty() {
        return empty;
    }

    public int getFilled() {
        return filled;
    }

    public int getCrossed() {
        return crossed;
    }

    public int getSize() {
        return size;
    }

    public Boolean getIsGrown() {
        return isGrown;
    }

    public void setNodes(ArrayList<SNode> nodes) {
        this.nodes = nodes;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public Boolean isGrown() {
        return isGrown;
    }

    public void setIsGrown(Boolean isGrown) {
        this.isGrown = isGrown;
    }
}// Snode