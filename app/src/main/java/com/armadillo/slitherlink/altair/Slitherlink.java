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

    private boolean solved;  //if this is true, any solver methods should quit.
    public String type;  // a description of the puzzle.
    public SNode root;  // the "first" node of the puzzle.
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
        type = "Altair";
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
     * a new puzzle of this depth, centered on an 8 sided node, then
     * fill it with the numbers from the list.
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
        if (type.equalsIgnoreCase("Altair")) {
            factory = new AltairNodeFactory();
            root = new SNode(8, -1, base, scale);
        } else {
            factory = new HexagonNodeFactory();
            root = new SNode(6, -1, base, scale);
        }

        factory.makePoly(root);

        int[] clues;
        if (list == null) {
            clues = new int[250];
        } else switch (depth) {
            case 2: {
                clues = fill2(list);
                break;
            }
            case 3: {
                clues = fill3(list);
                break;
            }
            case 6:
            case 7:
            case 8:
            {
                clues = fill7(list);
                break;
            }
            default: {
                clues = new int[250];
            }
        }

        root.setValue(clues[index]);
        root.setId(index++);
        grow(addNode(root), depth, clues);

        if (depth == 6) {
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
        tlist = factory.grow(temp, 1, trigger);
        DLog.d(TAG, "tlist.size() = " + tlist.size());
        queue.addAll(tlist);
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
    /**
    * This is used to fill a level 3 puzzle, that's all.  The order in 
    * which grow creates the nodes is not so easy to input from existing
    * puzzles.  This reorders that list for easy input.  Lest you think I
    * went crazy on the input, I used excel to write this.
    * @param nums List of numbers with which to fill the puzzle.
    * @return The new list of reorganized numbers. 
    */  
    public int[] fill3(int[] nums)
    {
        int[] fills = new int[45];
        int index = 0;
        for (int i = 0; i < 45; i++){
            switch (i){
    case    0   :   index = 22  ;   break;
    case    1   :   index = 23  ;   break;
    case    2   :   index = 29  ;   break;
    case    3   :   index = 28  ;   break;
    case    4   :   index = 27  ;   break;
    case    5   :   index = 21  ;   break;
    case    6   :   index = 15  ;   break;
    case    7   :   index = 16  ;   break;
    case    8   :   index = 17  ;   break;
    case    9   :   index = 30  ;   break;
    case    10  :   index = 18  ;   break;
    case    11  :   index = 36  ;   break;
    case    12  :   index = 35  ;   break;
    case    13  :   index = 34  ;   break;
    case    14  :   index = 33  ;   break;
    case    15  :   index = 26  ;   break;
    case    16  :   index = 14  ;   break;
    case    17  :   index = 8   ;   break;
    case    18  :   index = 9   ;   break;
    case    19  :   index = 10  ;   break;
    case    20  :   index = 11  ;   break;
    case    21  :   index = 31  ;   break;
    case    22  :   index = 24  ;   break;
    case    23  :   index = 37  ;   break;
    case    24  :   index = 12  ;   break;
    case    25  :   index = 19  ;   break;
    case    26  :   index = 42  ;   break;
    case    27  :   index = 41  ;   break;
    case    28  :   index = 40  ;   break;
    case    29  :   index = 44  ;   break;
    case    30  :   index = 43  ;   break;
    case    31  :   index = 39  ;   break;
    case    32  :   index = 38  ;   break;
    case    33  :   index = 32  ;   break;
    case    34  :   index = 20  ;   break;
    case    35  :   index = 25  ;   break;
    case    36  :   index = 13  ;   break;
    case    37  :   index = 7   ;   break;
    case    38  :   index = 2   ;   break;
    case    39  :   index = 3   ;   break;
    case    40  :   index = 4   ;   break;
    case    41  :   index = 0   ;   break;
    case    42  :   index = 1   ;   break;
    case    43  :   index = 5   ;   break;
    case    44  :   index = 6   ;   break;
                default: index = -1; break;
            }
            if (index == -1){
                fills[i] = -1;
            } else{
                fills[i] = nums[index];
            }
        }
        return fills;
    }//fill3()
    /**
    * This is used to fill a level 2 puzzle.  See fill7 for the scoop.
    * @param nums List of numbers with which to fill the puzzle.
    * @return The new list of reorganized numbers. 
    */  
    public int[] fill2(int[] nums)
    {
        int[] fills = new int[21];
        int index;
        for (int i = 0; i < 21; i++){
            switch (i){
                case    0   :   index = 10  ;   break;
                case    1   :   index = 11  ;   break;
                case    2   :   index = 15  ;   break;
                case    3   :   index = 14  ;   break;
                case    4   :   index = 13  ;   break;
                case    5   :   index = 9   ;   break;
                case    6   :   index = 5   ;   break;
                case    7   :   index = 6   ;   break;
                case    8   :   index = 7   ;   break;
                case    9   :   index = 16  ;   break;
                case    10  :   index = 8   ;   break;
                case    11  :   index = 20  ;   break;
                case    12  :   index = 19  ;   break;
                case    13  :   index = 18  ;   break;
                case    14  :   index = 17  ;   break;
                case    15  :   index = 12  ;   break;
                case    16  :   index = 4   ;   break;
                case    17  :   index = 0   ;   break;
                case    18  :   index = 1   ;   break;
                case    19  :   index = 2   ;   break;
                case    20  :   index = 3   ;   break;
                default: index = -1; break;
            }
            fills[i] = nums[index];
        }
        return fills;
    } // fill2()
    /**
    * This is used to fill a level 7 puzzle.  I didn't actually make
    * any puzzles that would use this.  Also, I lied about telling the
    * story here, try fill3.
    * @param nums List of numbers with which to fill the puzzle.
    * @return The new list of reorganized numbers. 
    */  
    public int[] fill7(int[] nums)
    {
        int[] fills = new int[189];
        int index;
        for (int i = 0; i < 189; i++){
            switch (i){
    case    0   :   index = 94  ;   break;
    case    1   :   index = 95  ;   break;
    case    2   :   index = 108 ;   break;
    case    3   :   index = 107 ;   break;
    case    4   :   index = 106 ;   break;
    case    5   :   index = 93  ;   break;
    case    6   :   index = 80  ;   break;
    case    7   :   index = 81  ;   break;
    case    8   :   index = 82  ;   break;
    case    9   :   index = 109 ;   break;
    case    10  :   index = 83  ;   break;
    case    11  :   index = 123 ;   break;
    case    12  :   index = 122 ;   break;
    case    13  :   index = 121 ;   break;
    case    14  :   index = 120 ;   break;
    case    15  :   index = 105 ;   break;
    case    16  :   index = 79  ;   break;
    case    17  :   index = 65  ;   break;
    case    18  :   index = 66  ;   break;
    case    19  :   index = 67  ;   break;
    case    20  :   index = 68  ;   break;
    case    21  :   index = 110 ;   break;
    case    22  :   index = 96  ;   break;
    case    23  :   index = 124 ;   break;
    case    24  :   index = 69  ;   break;
    case    25  :   index = 84  ;   break;
    case    26  :   index = 136 ;   break;
    case    27  :   index = 135 ;   break;
    case    28  :   index = 134 ;   break;
    case    29  :   index = 146 ;   break;
    case    30  :   index = 145 ;   break;
    case    31  :   index = 133 ;   break;
    case    32  :   index = 132 ;   break;
    case    33  :   index = 119 ;   break;
    case    34  :   index = 92  ;   break;
    case    35  :   index = 104 ;   break;
    case    36  :   index = 78  ;   break;
    case    37  :   index = 64  ;   break;
    case    38  :   index = 52  ;   break;
    case    39  :   index = 53  ;   break;
    case    40  :   index = 54  ;   break;
    case    41  :   index = 42  ;   break;
    case    42  :   index = 43  ;   break;
    case    43  :   index = 55  ;   break;
    case    44  :   index = 56  ;   break;
    case    45  :   index = 125 ;   break;
    case    46  :   index = 97  ;   break;
    case    47  :   index = 111 ;   break;
    case    48  :   index = 70  ;   break;
    case    49  :   index = 85  ;   break;
    case    50  :   index = 137 ;   break;
    case    51  :   index = 149 ;   break;
    case    52  :   index = 148 ;   break;
    case    53  :   index = 147 ;   break;
    case    56  :   index = 144 ;   break;
    case    58  :   index = 143 ;   break;
    case    59  :   index = 142 ;   break;
    case    60  :   index = 131 ;   break;
    case    61  :   index = 118 ;   break;
    case    62  :   index = 103 ;   break;
    case    63  :   index = 91  ;   break;
    case    64  :   index = 63  ;   break;
    case    65  :   index = 77  ;   break;
    case    66  :   index = 51  ;   break;
    case    67  :   index = 39  ;   break;
    case    68  :   index = 40  ;   break;
    case    69  :   index = 41  ;   break;
    case    70  :   index = 29  ;   break;
    case    71  :   index = 30  ;   break;
    case    72  :   index = 44  ;   break;
    case    73  :   index = 31  ;   break;
    case    74  :   index = 57  ;   break;
    case    75  :   index = 45  ;   break;
    case    76  :   index = 46  ;   break;
    case    77  :   index = 126 ;   break;
    case    78  :   index = 98  ;   break;
    case    79  :   index = 112 ;   break;
    case    80  :   index = 71  ;   break;
    case    81  :   index = 86  ;   break;
    case    82  :   index = 150 ;   break;
    case    83  :   index = 162 ;   break;
    case    84  :   index = 161 ;   break;
    case    85  :   index = 160 ;   break;
    case    55  :   index = 158 ;   break;
    case    86  :   index = 169 ;   break;
    case    54  :   index = 159 ;   break;
    case    87  :   index = 170 ;   break;
    case    88  :   index = 156 ;   break;
    case    57  :   index = 157 ;   break;
    case    89  :   index = 168 ;   break;
    case    90  :   index = 155 ;   break;
    case    91  :   index = 154 ;   break;
    case    92  :   index = 141 ;   break;
    case    93  :   index = 117 ;   break;
    case    94  :   index = 90  ;   break;
    case    95  :   index = 102 ;   break;
    case    96  :   index = 62  ;   break;
    case    97  :   index = 76  ;   break;
    case    98  :   index = 38  ;   break;
    case    99  :   index = 26  ;   break;
    case    100 :   index = 27  ;   break;
    case    101 :   index = 28  ;   break;
    case    102 :   index = 19  ;   break;
    case    103 :   index = 18  ;   break;
    case    104 :   index = 32  ;   break;
    case    105 :   index = 20  ;   break;
    case    106 :   index = 47  ;   break;
    case    107 :   index = 33  ;   break;
    case    108 :   index = 34  ;   break;
    case    109 :   index = 138 ;   break;
    case    110 :   index = 127 ;   break;
    case    111 :   index = 99  ;   break;
    case    112 :   index = 113 ;   break;
    case    113 :   index = 87  ;   break;
    case    114 :   index = 72  ;   break;
    case    115 :   index = 58  ;   break;
    case    116 :   index = 151 ;   break;
    case    117 :   index = 163 ;   break;
    case    118 :   index = 173 ;   break;
    case    119 :   index = 172 ;   break;
    case    120 :   index = 171 ;   break;
    case    121 :   index = 181 ;   break;
    case    122 :   index = 180 ;   break;
    case    123 :   index = 179 ;   break;
    case    124 :   index = 178 ;   break;
    case    125 :   index = 177 ;   break;
    case    126 :   index = 167 ;   break;
    case    127 :   index = 176 ;   break;
    case    128 :   index = 175 ;   break;
    case    129 :   index = 166 ;   break;
    case    130 :   index = 165 ;   break;
    case    131 :   index = 153 ;   break;
    case    132 :   index = 130 ;   break;
    case    133 :   index = 140 ;   break;
    case    134 :   index = 116 ;   break;
    case    135 :   index = 101 ;   break;
    case    136 :   index = 89  ;   break;
    case    137 :   index = 75  ;   break;
    case    138 :   index = 50  ;   break;
    case    139 :   index = 61  ;   break;
    case    140 :   index = 37  ;   break;
    case    141 :   index = 25  ;   break;
    case    142 :   index = 15  ;   break;
    case    143 :   index = 16  ;   break;
    case    144 :   index = 17  ;   break;
    case    145 :   index = 7   ;   break;
    case    146 :   index = 8   ;   break;
    case    147 :   index = 9   ;   break;
    case    148 :   index = 10  ;   break;
    case    149 :   index = 11  ;   break;
    case    150 :   index = 21  ;   break;
    case    151 :   index = 12  ;   break;
    case    152 :   index = 35  ;   break;
    case    153 :   index = 48  ;   break;
    case    154 :   index = 13  ;   break;
    case    155 :   index = 22  ;   break;
    case    156 :   index = 23  ;   break;
    case    157 :   index = 128 ;   break;
    case    158 :   index = 139 ;   break;
    case    159 :   index = 114 ;   break;
    case    160 :   index = 88  ;   break;
    case    162 :   index = 73  ;   break;
    case    161 :   index = 59  ;   break;
    case    163 :   index = 164 ;   break;
    case    164 :   index = 182 ;   break;
    case    165 :   index = 188 ;   break;
    case    166 :   index = 187 ;   break;
    case    168 :   index = 186 ;   break;
    case    167 :   index = 185 ;   break;
    case    169 :   index = 184 ;   break;
    case    170 :   index = 183 ;   break;
    case    171 :   index = 174 ;   break;
    case    172 :   index = 152 ;   break;
    case    173 :   index = 129 ;   break;
    case    174 :   index = 115 ;   break;
    case    176 :   index = 100 ;   break;
    case    175 :   index = 74  ;   break;
    case    177 :   index = 60  ;   break;
    case    178 :   index = 49  ;   break;
    case    179 :   index = 24  ;   break;
    case    180 :   index = 6   ;   break;
    case    181 :   index = 0   ;   break;
    case    182 :   index = 1   ;   break;
    case    184 :   index = 2   ;   break;
    case    183 :   index = 3   ;   break;
    case    185 :   index = 4   ;   break;
    case    186 :   index = 5   ;   break;
    case    187 :   index = 36  ;   break;
    case    188 :   index = 14  ;   break;
                    default: index = -1; break;
                }
            if (index == -1){
                fills[i] = -1;
            } else{
                fills[i] = nums[index];
            }
        }
        return fills;
    }// fill7()

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
