package com.armadillo.slitherlink.altair; /**
 * Slitherlink is a class for the creation of Slitherlinks, based on the puzzles
 * found at http://www.krazydad.com/blog/2008/03/28/altair-slitherlinks/
 * This class will represent the entire puzzle, and should use the SNode class
 * to represent individual cells.  The logic for solving the puzzle will be
 * contained here, while individual methods for accessing and assessing the 
 * data will be in SNode.
 */

import com.armadillo.common.DLog;
import com.armadillo.slitherlink.common.Edge;
import com.armadillo.slitherlink.common.INodeFactory;
import com.armadillo.slitherlink.common.Position;
import com.armadillo.slitherlink.common.SNode;
import com.armadillo.slitherlink.common.SlitherlinkHelper;
import com.armadillo.slitherlink.hex.HexagonNodeFactory;

import java.util.*;

/**
 * @author John Pushnik
 * @version 02-14-09 Started Slitherlink
 * @version 02-28-09 1.0 submittal
 * @version 04-03-09 2.0 submittal
 * @version 05-13-10 2.0 resumed
 * @version 11-16-15 3.0 resumed for Android
 */
public class Slitherlink
{
    private static final String TAG = "Slitherlink";
    public static final String TYPE_ALTAIR = "Altair";
    public static final String TYPE_HEXAGON = "Hexagon";

    private boolean solved;  //if this is true, any solver methods should quit.
    public String type;  // a description of the puzzle.
    public Position base;  

    private INodeFactory factory;

    protected int index = 0;
//    protected int indexE = 0;
    public static int size = SlitherlinkHelper.SIZE, maxSolve = SlitherlinkHelper.MAX_SOLVE;
    private float scale = SlitherlinkHelper.SCALE;
    private ArrayList<SNode> nodes = new ArrayList<SNode>();
    // List of node objects drawn first in the window
    private ArrayList<Edge> edges = new ArrayList<Edge>();
    // List of edge objects drawn second in the window
//    protected ArrayList<Polygon> polys = new ArrayList<Polygon>();
    protected STable rules;
//    protected int[] list = new int[250];
    /**
     * solve() To find the solution for the given puzzle,
     * and update the display.  Calls solveNode() and solveEdge().
     * @return boolean    Is the puzzle solved?
     */
    public boolean solve() throws Exception
    {
        SNode tempN;
        rules = new STable();

      for (int h = 0; h < maxSolve; h++){
        for (int j = 0; j < nodes.size(); j++){
            tempN = nodes.get(j);
            rules.check(tempN);
            tempN.setOtherHighlight();
        }
      }
      return false;
    } // solve

    /**
     * Constructor for objects of class Slitherlink, used to make a new blank puzzle.
     */
    public Slitherlink()
    {
        type = TYPE_ALTAIR;
        solved = false;
    }
    /**
     * Constructor for objects of class Slitherlink, used to make a new blank puzzle.
     * @param  type Which puzzle should I make?
     */
    public Slitherlink(String type)
    {
        this.type = type;
        solved = false;
    }
    /**
     * Constructor for objects of class Slitherlink, used to make
     * a new puzzle with base at the given position.
     * @param  type Which puzzle should I make?
     * @param  start Where should I put this?
     */
    public Slitherlink(String type, Position start)
    {
        this.type = type;
        solved = false;
        base = start;
    }
    /**
     * Constructor for objects of class Slitherlink, used to make 
     * a new puzzle of this depth, centered on an 8 sided node.
     * @param  type Which puzzle should I make?
     * @param  depth How deep do you want to go?
     */
    public Slitherlink(String type, int depth)
    {
        this.type = type;
        setup(depth, null);

        for (int i = 0; i < edges.size(); i++){
            edges.get(i).setEdges();
        }
    }//Slitherlink(depth)
    /**
     * Constructor for objects of class Slitherlink, used to make
     * a new puzzle of this depth, centered on an 8 sided node.
     * @param  type Which puzzle should I make?
     * @param  depth How deep do you want to go?
     * @param scaleIn the size of the puzzle.
     */
    public Slitherlink(String type, int depth, float scaleIn)
    {
        this.type = type;
        scale = scaleIn;
        setup(depth, null);

        for (int i = 0; i < edges.size(); i++){
            edges.get(i).setEdges();
        }
    }//Slitherlink(depth)
    /**
     * Constructor for objects of class Slitherlink, used to make 
     * a new puzzle of this depth, centered on an 8 sided node, then
     * fill it with the numbers from the list.
     * @param  type Which puzzle should I make?
     * @param depth How deep do you want to go?
     * @param listIn Use these to fill the puzzle.
     */
    public Slitherlink(String type, int depth, int[] listIn)  throws Exception
    {
        this.type = type;
        setup(depth, listIn);

        for (int h = 0; h < maxSolve; h++){
//            solve();
        }
    }//Slitherlink(depth, listIn)
    /**
     * Constructor for objects of class Slitherlink, used to make
     * a new puzzle of this depth, centered on an 8 sided node, then
     * fill it with the numbers from the list.
     * @param  type Which puzzle should I make?
     * @param depth How deep do you want to go?
     * @param listIn Use these to fill the puzzle.
     * @param scaleIn the size of the puzzle.
     */
    public Slitherlink(String type, int depth, int[] listIn, float scaleIn)  throws Exception
    {
        this.type = type;
        scale = scaleIn;
        setup(depth, listIn);

        for (int h = 0; h < maxSolve; h++){
//            solve();
        }
    }//Slitherlink(depth, listIn)

    public void setup(int depth, int[] list) {
        DLog.d(TAG, "setup for type " + type);
        solved = false;
        int factor = (depth + 2) * (int)(size * scale);
        base = new Position(factor,factor);
        SNode root;
        if (type.equalsIgnoreCase(TYPE_ALTAIR)) {
            factory = new AltairNodeFactory();
            root = new SNode(8, -1, base, scale);
        } else {
            factory = new HexagonNodeFactory();
            root = new SNode(6, -1, base, scale);
        }

        factory.makePoly(root);

        int[] clues = factory.fill(depth, list);

        root.setValue(clues[index]);
        root.setId(index++);
        grow(addNode(root), depth, clues);

        if (type.equalsIgnoreCase(TYPE_ALTAIR) && depth == 6) {
            add6(clues);
        }
    }
    /**
     * Method for building a Slitherlink puzzle of a given depth.  
     * @param  start The node from which to start building.
     * @param  count How deep to build the puzzle.
     * @param  nums The list from which to fill the puzzle.
     * @return null No return parameters
     */
    public void grow(SNode start, int count, int[] nums)
    {
        Queue<SNode> queue = new LinkedList<SNode>();
        Queue<Edge> edgeQ = new LinkedList<Edge>();
        ArrayList<SNode> tlist;
        SNode temp = start;
        Edge tempE;
//        int countG, countE;
        boolean trigger = true;
        if (count > 0) {
            tlist = factory.grow(temp, 1, trigger);
            DLog.d(TAG, "tlist.size() = " + tlist.size());
            queue.addAll(tlist);
        }
        int max = queue.size();
        for (int h = 1; h <= count; h++ ){
            if (h == count) {
                trigger = false;
            }
            for (int i = 0; i < max; i++)
            {
                DLog.d(TAG, "grow: h = " + h + ", i = " + i + ", max = " + max);
                temp = queue.remove();
                boolean hasNode = hasNode(temp);
                if (temp.getIsGrown() || hasNode) {
                    DLog.d(TAG, "temp.isGrown = " + temp.getIsGrown() + ", hasNode = " + hasNode);
                } else {
                    temp.setValue(nums[index]);
                    temp.setId(index++);
                    addNode(temp);
                    tlist = factory.grow(temp, h,trigger);
                    queue.addAll(tlist);
//                    countG = queue.size();
                }
            }
            max = queue.size();
        }
        for (int j = 0; j < max; j++)
        {
            temp = queue.remove();
//                    countG = queue.size();
            if (hasNode(temp)) {
            } else {
                temp.link();
                temp.setEdges();
//                edgeQ.addAll(temp.edges);
//                countE = edgeQ.size();
                temp.setValue(nums[index]);
                temp.setId(index++);
                addNode(temp);
            }
        }
/* */       
        queue.addAll(nodes);
        max = queue.size();
        for (int j = 0; j < max; j++)
        {
            temp = queue.remove();
            temp.link();
            temp.setEdges();
            edgeQ.addAll(temp.getEdges());
//                    countE = edgeQ.size();
        }
/* */
        max = edgeQ.size();
        for (int k = 0; k < max; k++)
        {
            tempE = edgeQ.remove();
//            countE = edgeQ.size();
            if (hasLine(tempE)) {
            } else {
                addEdge(tempE);
            }
        }
    } //grow()
    public SNode addNode(SNode temp)
   {
       if (temp != null) {
           DLog.d(TAG, "added node " + nodes.size() + ", " + temp.getSides() + "(" + temp.getPosition().toString() + ")");
           nodes.add(temp);
       }
        return temp;
   }
    public SNode replaceNode(SNode temp)
    {
        if (temp != null) {
            nodes.set(nodes.indexOf(temp), temp);
            DLog.d(TAG, "replaced node " + temp.getValue() + ", " + temp.getSides() + "(" + temp.getPosition().toString() + ")");
        }
        return temp;
    }
    public boolean hasNode(SNode temp) {
        return nodes.contains(temp);
    }
    public boolean hasLine(Edge temp) {
        return edges.contains(temp);
    }

    public void add6(int[] list)
    {
        Queue<Edge> edgeQ = new LinkedList<Edge>();
        ArrayList<SNode> tlist = new ArrayList<SNode>();
        SNode temp;
        Edge tempE;

        grow(nodes.get(110),1,list);
        grow(nodes.get(111),1,list);
        grow(nodes.get(114),1,list);
        grow(nodes.get(117),1,list);
        grow(nodes.get(119),1,list);
        grow(nodes.get(122),1,list);
        grow(nodes.get(124),1,list);
        grow(nodes.get(127),1,list);
        grow(nodes.get(129),1,list);
        grow(nodes.get(131),1,list);
        grow(nodes.get(134),1,list);
        grow(nodes.get(136),1,list);
        grow(nodes.get(139),1,list);
        grow(nodes.get(141),1,list);
        grow(nodes.get(143),1,list);
        grow(nodes.get(146),1,list);
        grow(nodes.get(148), 1, list);
        grow(nodes.get(151), 1, list);
        grow(nodes.get(152), 1, list);
        grow(nodes.get(155),1,list);
        
        tlist.addAll(nodes);
        tlist.get(113).link(tlist.get(113),tlist.get(114));
        tlist.get(113).link(tlist.get(113),tlist.get(162));
        tlist.get(162).link(tlist.get(160),tlist.get(162));
        tlist.get(168).link(tlist.get(166),tlist.get(168));
        tlist.get(176).link(tlist.get(174),tlist.get(176));
        tlist.get(184).link(tlist.get(182),tlist.get(184));

/* */          
        for (int i = 0; i < tlist.size(); i ++){
            temp = tlist.get(i);
            temp.link();
            temp.setEdges();
            edgeQ.addAll(temp.getEdges());
            addNode(temp);
        }
        int max = edgeQ.size();
        for (int k = 0; k < max; k++)
        {
            tempE = edgeQ.remove();
            if (hasLine(tempE)) {
            } else {
                addEdge(tempE);
            }
        }
/* */        
    }//add6()
   public void addNodes(ArrayList<SNode> input, ArrayList<SNode> temp)
   {
          temp.addAll(input);
   }
   public void addNode(SNode input, ArrayList<SNode> temp)
   {
          temp.add(input);
   }
   public void addNodes(ArrayList<SNode> input)
   {
       SNode temp;
       int length = input.size();
       for (int i = 0; i < length; i++)
       {
           temp = input.get(i);
           if (temp != null) {
               nodes.add(temp);
           }
       }
   }
   public void addEdge(Edge temp)
   {
           if (temp != null) {
               edges.add(temp);
           }
   }
   public void addEdges(ArrayList<Edge> input)
   {
       Edge temp;
       int length = input.size();
       for (int i = 0; i < length; i++)
       {
           temp = input.get(i);
           if (temp != null) {
               edges.add(temp);
           }
       }
   }

    public Edge replaceEdge(Edge temp)
    {
        if (temp != null) {
            edges.set(edges.indexOf(temp), temp);
            DLog.d(TAG, "replaced edge " + temp.getStatus() + ", (" + temp.getPosition().toString() + ")");
        }
        return temp;
    }

    public boolean solve1()
    {
        SNode temp;
        Edge tempE;
        boolean drawn;
/*
 *  node logic here
 */
        for (int i = 0; i < nodes.size(); i++){
            temp = nodes.get(i);
            drawn = solveNode(temp);
            temp.setOtherHighlight();
        }
/*
 *  edge logic here
 */
        for (int j = 0; j < edges.size(); j++){
            tempE = edges.get(j);
            drawn = solveEdge(tempE);
        }
        return false;
    } // solve
    /**
     * solveNodePro() Procedurally find the solution for the given node. 
     * @param  temp The current node upon which to work.
     * @return boolean    Is this node solved? 
     */
    public boolean solveNodePro(SNode temp)
    {
//        if (temp.getValue() == -1) return false;
        SNode temp2;
        Edge tempE, tempE2, tempE3;
        boolean drawn;
        int max;
/*
 *  node logic here
 */
            drawn = false;
            temp.count();
            if (temp.getFilled() + temp.getCrossed() == temp.getSides())
            return true;
            if (temp.getFilled() == temp.getValue()){
                    for (int i = 0; i < temp.getEdges().size(); i++){
                        tempE = temp.getEdges().get(i);
                        if (tempE.getStatus() == 0)
                        tempE.setStatus(-2);
                    }
                return true;
            }
            if (temp.getCrossed() == temp.getSides() - temp.getValue()){
                    for (int i = 0; i < temp.getEdges().size(); i++){
                        tempE = temp.getEdges().get(i);
                        if (tempE.getStatus() == 0)
                        tempE.setStatus(2);
                    }
                return true;
            }
/* */
            /* if outside edge, check for extreme values*/
            int temp3 = temp.getSides() - temp.getValue();
/* */
            if (temp3 > temp.getNodes().size()){
                for (int j = 0; j < temp.getEdges().size(); j++){
                    tempE = temp.getEdges().get(j);
                    temp2 = tempE.otherNode(temp);
                    if (temp2 == null){
                        tempE.setStatus(-2);
                    }
                }
            }
/* */
            else 
/* */
            if (temp.getValue() > temp.getNodes().size()){
                for (int j = 0; j < temp.getEdges().size(); j++){
                    tempE = temp.getEdges().get(j);
                    temp2 = tempE.otherNode(temp);
                    if (temp2 == null){
                        tempE.setStatus(2);
                    }
                }
            }
/* */         
            else 
/* */             
            if ((temp.getSides() - temp.getNodes().size())
                > (temp.getValue() - temp.getFilled()) ){
                for (int j = 0; j < temp.getEdges().size(); j++){
                    tempE = temp.getEdges().get(j);
                    temp2 = tempE.otherNode(temp);
                    if (temp2 == null){
                        tempE.setStatus(-2);
                    }
                }
            }
/* */
            else if ((temp.getValue() - temp.getFilled()) == 2){
                for (int j = 0; j < temp.getEdges().size(); j++){
                    tempE = temp.getEdges().get(j);
                    temp2 = tempE.otherNode(temp);
                    if (temp2 != null){
                        if (temp2.getValue() == 1){
                            tempE.setStatus(-2);
                        } 
                    }
                }
                drawn = true;
            }
/* */
            
        return drawn;
    } // solveNodePro()
    /**
     * solveNode() To find the solution for the given node. 
     * Node logic goes here.  All the tricky things like preventing snowmen
     * and filling edges around large numbers happens here.  
     * @param  temp The current node upon which to work.
     * @return boolean    Is this node solved? 
     */
    public boolean solveNode(SNode temp)
    {
//        if (temp.getValue() == -1) return false;
        SNode temp2;
        Edge tempE, tempE2, tempE3;
        boolean drawn;
        int max;
/*
 *  node logic here
 */
            drawn = false;
            temp.count();
            if (temp.getFilled() + temp.getCrossed() == temp.getSides())
            return true;
            if (temp.getFilled() == temp.getValue()){
                    for (int i = 0; i < temp.getEdges().size(); i++){
                        tempE = temp.getEdges().get(i);
                        if (tempE.getStatus() == 0)
                        tempE.setStatus(-2);
                    }
                
//                return true;
            }
            if (temp.getCrossed() == temp.getSides() - temp.getValue()){
                    for (int i = 0; i < temp.getEdges().size(); i++){
                        tempE = temp.getEdges().get(i);
                        if (tempE.getStatus() == 0)
                        tempE.setStatus(2);
                    }
                
//                return true;
            }
/* */
            if (temp.getEmpty() == 2 && temp.getValue() > 0){
                tempE2 = temp.getEdges().get(temp.getEdges().size() - 1);
                for (int j = 0; j < temp.getEdges().size(); j++){
                    tempE = tempE2;
                    tempE2 = temp.getEdges().get(j);
                    if ((tempE.getStatus() == 0) && (tempE2.getStatus() == 0)){
                        tempE3 = tempE.otherEdge(tempE2);
                        if (tempE3 != null) tempE3.setStatus(2);
                    }
                }
            }
/* */
            switch (temp.getValue()){
                case 0: {
                    for (int j = 0; j < temp.getEdges().size(); j++){
                        tempE = temp.getEdges().get(j);
                        tempE.setAll(-2);
                        temp2 = tempE.otherNode(temp);
                        if (temp2 != null)
                        switch ((temp2.getSides() - temp2.getValue())){
                            case 3: {
                    for (int k = 0; k < temp2.getEdges().size(); k++){
                        tempE2 = temp2.getEdges().get(k);
                        if (tempE2.getStatus() == 0) tempE2.setStatus(2);
                    }
                            }break;
                        }
                    }
                    drawn = true;
                }
                break;
                case 1: {
                    for (int j = 0; j< temp.getEdges().size(); j++){
                        tempE = temp.getEdges().get(j);
                        if (tempE.nodesEqual()
//                          || (tempE.otherNode(temp) == null)
                          ){
                            tempE.setStatus(-2);
                        }
                    }
                    drawn = true;
                }
                break;
                case 2: {
                    for (int j = 0; j < temp.getEdges().size(); j++){
                        tempE = temp.getEdges().get(j);
                        temp2 = tempE.otherNode(temp);
                        if (temp2 != null){
                            switch (temp2.getValue()){
                                case 1: {tempE.setStatus(-2);} break;
                                default: break;
                            } 
                        }
                    }
                    drawn = true;
                }
                break;
/* */
                case 3: {
                    if ((temp.getNodes().size() == temp.getValue())
                        &&(temp.getSides() == temp.getValue() * 2 ))
                    for (int j = 0; j < temp.getEdges().size(); j++){
                        tempE = temp.getEdges().get(j);
                        temp2 = tempE.otherNode(temp);
                        if (temp2 != null){
                            switch (temp2.getSides() -temp2.getValue()){
                                case 1: {tempE.setStatus(2);} break;
                                default: break;
                            } 
                        }
                    }
                    drawn = true;
                }
                break;
/* */
                default : 
                break;
            }
            /* if outside edge, check for extreme values*/
            int temp3 = temp.getSides() - temp.getValue();
/* */
            if (temp3 > temp.getNodes().size()){
                for (int j = 0; j < temp.getEdges().size(); j++){
                    tempE = temp.getEdges().get(j);
                    temp2 = tempE.otherNode(temp);
                    if (temp2 == null){
                        tempE.setStatus(-2);
                    }
                }
            }
/* */
            else 
/* */
            if (temp.getValue() > temp.getNodes().size()){
                for (int j = 0; j < temp.getEdges().size(); j++){
                    tempE = temp.getEdges().get(j);
                    temp2 = tempE.otherNode(temp);
                    if (temp2 == null){
                        tempE.setStatus(2);
                    }
                }
            }
/* */         
            else 
/* */             
            if ((temp.getSides() - temp.getNodes().size())
                > (temp.getValue() - temp.getFilled()) ){
                for (int j = 0; j < temp.getEdges().size(); j++){
                    tempE = temp.getEdges().get(j);
                    temp2 = tempE.otherNode(temp);
                    if (temp2 == null){
                        tempE.setStatus(-2);
                    }
                }
            }
            else if ((temp.getValue() - temp.getFilled()) == 2){
                for (int j = 0; j < temp.getEdges().size(); j++){
                    tempE = temp.getEdges().get(j);
                    temp2 = tempE.otherNode(temp);
                    if (temp2 != null){
                        if (temp2.getValue() == 1){
                            tempE.setStatus(-2);
                        } 
                    }
                }
                drawn = true;
            }
/* */
            if (!drawn)
            if (temp.getValue() != -1)
            switch (temp3){
                case 0: {
                    for (int j = 0; j< temp.getEdges().size(); j++){
                        tempE = temp.getEdges().get(j);
                        tempE.setStatus(2);
                    drawn = true;
                    }
                }
                break;
                case 1: {
                    max = temp.getEdges().size();
                    for (int j = 0; j < max; j++){
                        tempE = temp.getEdges().get(j);
                        temp2 = tempE.otherNode(temp);
                  if (temp2 != null) {
                     if ((temp2.getSides() - temp2.getValue()) == 1){
                     // s shape, to prevent a snowman
                        tempE.setStatus(2);
                        for (int k = 2; k < max - 1; k++){
                            tempE2 = temp.getEdges().get((k + j) % max);
                            tempE2.setStatus(2);}
                        } else if (temp2.getValue() == 1){
                            tempE.setStatus(2);
                        }
                    }
                     drawn = true;
                    }
                }
                break;
                default : {
                }
                break;
            }
            
        return drawn;
    } // solveNode
    /**
     * solveEdge() To find the solution for the given edge. 
     * @param  temp The current Edge upon which to work.
     * @return boolean    Is this edge solved? 
     */
    public boolean solveEdge(Edge temp)
    {
        Edge temp2;
        boolean drawn;
        int stat = temp.getStatus();
/*
 *  edge logic here
 */
        {
            switch (stat){
                case 0: {
                    if (temp.getEdge21() != null){
                        if (temp.getEdge11().getStatus() != stat)
                            {if (temp.getEdge11().getStatus() == temp.getEdge21().getStatus())
                                {temp.setStatus(-2);}
                        else if (temp.getEdge11().getStatus() == -temp.getEdge21().getStatus())
                            {temp.setStatus(2);} 
                            }
                    }
                    if (temp.getEdge22() != null){
                        if (temp.getEdge12().getStatus() != stat)
                            {if (temp.getEdge12().getStatus() == temp.getEdge22().getStatus())
                                {temp.setStatus(-2);}
                        else if (temp.getEdge12().getStatus() == -temp.getEdge22().getStatus())
                            {temp.setStatus(2);} 
                            }
                    }
                    if (temp.getEdge21() == null && temp.getEdge22() == null){
                        if (temp.getEdge11().getStatus() == temp.getEdge12().getStatus())
                            {temp.setStatus(temp.getEdge11().getStatus());}
                    } 
                }
                break;
                default : {
                    if (temp.getEdge21() != null){
                        if (temp.getEdge11().getStatus() == stat){
                            temp.getEdge21().setStatus(-2);
//                        } else if (temp.edge11.status == -stat){
//                            temp.edge21.setStatus(2);
                        }
                        if (temp.getEdge21().getStatus() == stat){
                            temp.getEdge11().setStatus(-2);
//                        } else if (temp.edge21.status == -stat){
//                            temp.edge11.setStatus(2);
                        }
                    }
                    if (temp.getEdge22() != null){
                        if (temp.getEdge12().getStatus() == stat){
                            temp.getEdge22().setStatus(-2);
//                        } else if (temp.edge12.status == -stat){
//                            temp.edge22.setStatus(2);
                        }
                        if (temp.getEdge22().getStatus() == stat){
                            temp.getEdge12().setStatus(-2);
//                        } else if (temp.edge22.status == -stat){
//                            temp.edge12.setStatus(2);
                        }
                    }
                }
                break;
            }
        }
        return false;
    } // solve

    public ArrayList<SNode> getNodes() {
        return nodes;
    }

    public void setNodes(ArrayList<SNode> nodes) {
        this.nodes = nodes;
    }

    public ArrayList<Edge> getEdges() {
        return edges;
    }

    public void setEdges(ArrayList<Edge> edges) {
        this.edges = edges;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }
}
