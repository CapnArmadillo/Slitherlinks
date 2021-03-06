package com.armadillo.slitherlink.common;

import android.graphics.Color;

import java.util.*;
//import java.awt.*;
//import java.awt.Polygon;
//import javax.swing.*;

/****************************************************************************
 * Edge Class
 ****************************************************************************
 * Class Summary:
 * This class is used to create a line between 2 nodes in the picture panel.
 * These two points can be specified in 3 ways: by (x, y) coordinates, by 
 * Position of each one, or by specifying two SNode objects between which 
 * a line should be drawn.
 ****************************************************************************
 * @author John Pushnik
 * @version 02-14-09 Started Slitherlink Version
 * @version 02-28-09 1.0 submittal
 * @version 04-03-09 2.0 submittal
 * @version 05-13-10 2.0 resumed
 ****************************************************************************/
public class Edge 
{
    private Position pos;   // useful place to put a label, the midpoint
    private Position pos1;  // first endpoint
    private Position pos2;  // second endpoint
    private SNode node1 = null;  // connected nodes
    private SNode node2 = null;  // connected nodes
    private Edge edge11 = null;  // edge connected to pos1 and node1
    private Edge edge12 = null;  // edge connected to pos1 and node2
    private Edge edge21 = null;  // edge connected to pos2 and node1
    private Edge edge22 = null;  // edge connected to pos2 and node2
    private int status = -1;
    private int nodeCount;
    private int edgeCount;
    private int id;
    private int color;
    protected static final int LINE_ON_COLOR = Color.BLUE;
    protected static final int LINE_OFF_COLOR = Color.LTGRAY;
    protected static final int LINE_INVALID_COLOR = Color.RED;
    protected static final int TEST_COLOR = Color.YELLOW;
    protected static final int TEXT_COLOR = Color.RED;
    protected static final int CLEAR_COLOR = Color.TRANSPARENT;
    protected static final int WHITE_COLOR = Color.WHITE;

//    protected Position[] point;
//    private Polygon poly;

   /**
    * Creates a new Edge object from two Positions.
    * @param input1 Position at which Edge should start.
    * @param input2 Position at which Edge should terminate.
    */
    public Edge(Position input1, Position input2)
    {
        pos1 = input1;
        pos2 = input2;
        pos = new Position((pos1.getX() + pos2.getX())/2,(pos1.getY() + pos2.getY())/2);
    }
    public Edge(Position input1, Position input2, SNode node)
    {
        pos1 = input1;
        pos2 = input2;
        pos = new Position((pos1.getX() + pos2.getX())/2,(pos1.getY() + pos2.getY())/2);
        node1 = node;
    }
    public Edge(Position input1, Position input2, SNode nod1, SNode nod2)
    {
        pos1 = input1;
        pos2 = input2;
        pos = new Position((pos1.getX() + pos2.getX())/2,(pos1.getY() + pos2.getY())/2);
        node1 = nod1;
        node2 = nod2;
    }
    public Edge(SNode nod1, SNode nod2)
    {
        node1 = nod1;
        node2 = nod2;
//        pos1 = input1;
//        pos2 = input2;
//        pos = new Position((pos1.getX() + pos2.getX())/2,(pos1.getY() + pos2.getY())/2);

//        setEdges();
    }

    public Edge setStatus(int stat)
    {
        status = stat;
        return this;
    }
    public Edge setID(int input)
    {
        id = input;
        return this;
    }
    public void toggleStatus() {
        if (status == 1) {
            status = 0;
        } else if (status == 0){
            status = -1;
        } else {
            status = 1;
        }
    }
    public int getColor(){
        if (status == 0){
            color = LINE_INVALID_COLOR;
        } else if  (status < 0){
            color = LINE_OFF_COLOR;
        } else if  (status > 0){
            color = LINE_ON_COLOR;
        }
        return color;
    }
    public int getTextColor(){
        if (status == 0){
            color = TEXT_COLOR;
        } else {
            color = WHITE_COLOR;
        }
        return color;
    }
    public boolean showText(){
        return status == 0;
    }


    public ArrayList<Edge> setAll(int stat)
    {
        ArrayList<Edge> edges = new ArrayList<Edge>();
        status = stat;
            edges.add(edge11.setStatus(stat));
            edges.add(edge12.setStatus(stat));
        if (edge21 != null)
            edges.add(edge21.setStatus(stat));
        if (edge22 != null)
            edges.add(edge22.setStatus(stat));
        return edges;
            
    }
    public void setEdges()
    {
        ArrayList<Edge> temp;
        ArrayList<SNode> nodes;
        SNode tnode;
        Edge line = null;
        if (node1 == null) {
            return;
        }//if
            temp = node1.getEdges();
            for (int i = 0; i < temp.size(); i++){
                line = temp.get(i);
                switch (touches(line)){
                    case 1: edge11 = line; break;
                    case 2: edge12 = line; break;
//                    case 3: node1.edges.set(i,line); break;
                    default: break;
                }//switch
            }//for
        node1.setEdges(temp);
        if (node2 == null) {
            nodes = node1.getNodes();
            for (int i = 0; i < nodes.size(); i++){
                tnode = nodes.get(i);
                if (tnode.hasEdge(this)){
                    node2 = tnode;
//                    node2.link();
                    break;
                }
            }//for
        }//if
        if (node2 != null) {
            temp= node2.getEdges();
            for (int i = 0; i < temp.size(); i++){
                line = temp.get(i);
                switch (touches(line)){
                    case 1: {
                        edge21 = line; 
                    }
                    break;
                    case 2: {
                        edge22 = line; 
                    }
                    break;
                    case 3: {
                        temp.set(i,this);
                    }
                    break;
                    default: break;
                }//switch
            }//for
            node2.setEdges(temp);
        }//if
        edgeCount = 0;
        if (edge11 != null) edgeCount++;
        if (edge12 != null) edgeCount++;
        if (edge21 != null) edgeCount++;
        if (edge22 != null) edgeCount++;
        nodeCount = 0;
        if (node1 != null) nodeCount++;
        if (node2 != null) nodeCount++;
        
    }//setEdges
    public Edge otherEdge(Edge edge)
    {
        if (null == edge) return null;
        if (edge.equals(edge11)) return edge21;
        if (edge.equals(edge21)) return edge11;
        if (edge.equals(edge12)) return edge22;
        if (edge.equals(edge22)) return edge12;
        return null;
    }
    public void setNode1(SNode node)
    {node1 = node;}
    public void setNode2(SNode node)
    {node2 = node;}
    public void setHighlight(SNode node)
    {
        if (status != 0){
            int high = node.getHighlight();
            if (high != 0){
                SNode other = otherNode(node);
                if (other != null) {
                    if (status > 1){
                        other.setHighlight( 0 - high );
                    }else if (status < 0){
                        other.setHighlight( high );
                    }
                }
            }
        }
    }
    public SNode otherNode(SNode node)
    {
        if (node.equals(node1)) return node2;
        return node1;
    }
    public SNode getNode1()
    {return node1;}
    public SNode getNode2()
    {return node2;}
    public SNode getNode3()
    {return edge11.otherNode(node1);}
    public SNode getNode4()
    {return edge12.otherNode(node1);}
    public Edge getEdge1()
    {return edge11;}
    public Edge getEdge2()
    {return edge12;}
    public Edge getEdge3()
    {return edge21;}
    public Edge getEdge4()
    {return edge22;}
    public Position getPos1()
    {return pos1;}
    public Position getPos2()
    {return pos2;}
    public Position getPosition()
    {return pos;}
    public int getStatus()
    {return status;}
    public boolean nodesEqual()
    {
        if (node2 == null) return false;
        if (node1.getValue()==node2.getValue()) return true;
        return false;
    }
    public boolean equals(Edge input)
    {
        if (input == null) return false;
        if (input.getPosition().equals(pos)) return true;
        return false;
    }
    public int touches(Edge input)
    {
        int stat = 0;
        if (input.getPos1().equals(pos1) || input.getPos2().equals(pos1)){
            stat = 1;
        }
        if (input.getPos1().equals(pos2) || input.getPos2().equals(pos2)){
            stat = stat + 2;
        }
        return stat;
    }
//    public Polygon getPolygon(){
//        return poly;
//    }
//    public void makePoly()
//    {
//        poly = new Polygon();
//        if (null == node2){
//            points[0] = pos;
//        } else {
//        points[0] = node2.getPosition();
//        }
//        points[1] = pos1;
//        points[2] = node1.getPosition();
//        points[3] = pos2;
//        for (int i = 0; i < 4; i++)
//        poly.addPoint((pos.getX() + points[i].getX()), (pos.getY() + points[i].getY()));
//    } // makePoly()

//    /**
//    * Draws the edge on the given graphics object.
//    * @param g Graphics object used to draw on the label.
//    */
//    public void draw(Graphics g)
//    {
///*
//        if (nodeCount == 1){g.setColor(TEXT_COLOR);
//        }else{g.setColor(LINE_OFF_COLOR);}
//            Position t1 = node1.getPosition();
//            g.drawLine(pos.getX(), pos.getY(), t1.getX(), t1.getY());
///*
//            if (node2 != null){
//            g.setColor(LINE_OFF_COLOR);
//            Position t2 = node2.getPosition();
//            g.drawLine(pos.getX(), pos.getY(), t2.getX(), t2.getY());
//        }// else
//
///* */
//        int ht = g.getFont().getSize();
//
//        if (status > 1){
//            g.setColor(LINE_ON_COLOR);
//            g.drawLine(pos1.getX(), pos1.getY(), pos2.getX(), pos2.getY());
//        }else if (status == 1){g.setColor(TEST_COLOR);
//        }else{g.setColor(LINE_OFF_COLOR);}
//            g.drawLine(pos1.getX(), pos1.getY(), pos2.getX(), pos2.getY());
//        if (status < 0){
//            g.setColor(TEXT_COLOR);
////            g.drawLine(pos1.getX(), pos1.getY(), pos2.getX(), pos2.getY());
//            g.drawString("X", (pos.getX() - (ht / 4)), (pos.getY() + (ht / 2) - 1));
//        }
///*
//        if (null != node2) {
//            Position p1 = node2.getPosition();
//            g.drawLine(pos.getX(), pos.getY(), p1.getX(), p1.getY());
//        }
///* * /
//        if (null != edge21) {
//            Position p1 = edge21.getPosition();
//            g.drawLine(pos.getX(), pos.getY(), p1.getX(), p1.getY());
//        }
//        if (null != edge22) {
//            Position p1 = edge22.getPosition();
//            g.drawLine(pos.getX(), pos.getY(), p1.getX(), p1.getY());
//        }
///* * /
//        StringBuilder idStr = new StringBuilder();
//        idStr.append(id);
////        if (edge21 == null) idStr.append("," + 1);
////        if (edge22 == null) idStr.append("," + 2);
//        g.drawString(idStr.toString(), (pos.getX() - (ht / 4)), (pos.getY() + (ht / 2) - 1));
////        System.out.println(idStr);
///*
//        Position t1 = new Edge(node1.getPosition(), pos).pos;
//        StringBuilder nodStr = new StringBuilder();
//        nodStr.append(nodeCount);
//
//        g.drawString(nodStr.toString(), (t1.getX() - (ht / 4)), (t1.getY() + (ht / 2) - 1));
///* */
//     }
    @Override
    public String toString() {
        return pos1.toString() + ", " + pos2.toString();
    }

    public Position getPos() {
        return pos;
    }

    public void setPos(Position pos) {
        this.pos = pos;
    }

    public void setPos1(Position pos1) {
        this.pos1 = pos1;
    }

    public void setPos2(Position pos2) {
        this.pos2 = pos2;
    }

    public Edge getEdge11() {
        return edge11;
    }

    public void setEdge11(Edge edge11) {
        this.edge11 = edge11;
    }

    public Edge getEdge12() {
        return edge12;
    }

    public void setEdge12(Edge edge12) {
        this.edge12 = edge12;
    }

    public Edge getEdge21() {
        return edge21;
    }

    public void setEdge21(Edge edge21) {
        this.edge21 = edge21;
    }

    public Edge getEdge22() {
        return edge22;
    }

    public void setEdge22(Edge edge22) {
        this.edge22 = edge22;
    }

    public int getNodeCount() {
        return nodeCount;
    }

    public void setNodeCount(int nodeCount) {
        this.nodeCount = nodeCount;
    }

    public int getEdgeCount() {
        return edgeCount;
    }

    public void setEdgeCount(int edgeCount) {
        this.edgeCount = edgeCount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
} /* End Edge */
