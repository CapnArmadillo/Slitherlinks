package com.armadillo.slitherlink.hex;

import com.armadillo.slitherlink.common.INodeFactory;
import com.armadillo.slitherlink.common.Position;
import com.armadillo.slitherlink.common.SNode;

import java.util.ArrayList;

/**
 * SNode is a class for the creation of Honeycomb Slitherlinks, based on the puzzles
 * found at http://www.krazydad.com/blog/2008/03/28/altair-slitherlinks/
 ****************************************************************************
 * @author John Pushnik
 * @version 02-14-09 Started Slitherlink Version
 * @version 02-28-09 1.0 submittal
 * @version 04-03-09 2.0 submittal
 * @version 05-13-10 2.0 resumed
 ****************************************************************************
 */
public class HexagonNodeFactory implements INodeFactory
{
    private static final String TAG = "HexagonNodeFactory";

    /**
    * Used for automatic construction of the puzzle with very little input.
    * Well, other than that input.  And a boolean.  Do you think 
    * programmers make soup with boolean cubes?  This is where the 
    * heavy lifting occurs in creating the puzzle.  Well, that and the 
    * method makePoly().  That does the actual drawing, but this one
    * tells it where to start drawing, and is twice as long.
    * @param input How deep is this node?  Probably doesn't matter.
    * @param trigger If this horse says nay, don't make any more nodes.
    * @return An ArrayList of the nodes built with this method.  
    */
    @Override
    public ArrayList<SNode> grow(SNode node, int input, boolean trigger)
    {
        SNode temp;
        ArrayList<SNode> newList = new ArrayList<SNode>();
        int shape = 6;
        int x;
        int y;
        int xSign = 1;
        int ySign = 1;   // for changing the sign of x and y based on dir

        int sides = 6;
        Position pos = node.getPosition();
        float scale = node.getScale();

        for (int i = 1; i <= sides; i++)
        {
            switch (i % 3){
                case 1:
                    x = 0;
                    y = 34;
                    break;
                default:
                    x = 30;
                    y = 17;
                    break;
            }

            switch (i){
                case 3:
                    ySign = -1;
                    break;
                case 4:
                    xSign = -1;
                    ySign = -1;
                    break;
                case 5:
                    xSign = -1;
                    ySign = -1;
                    break;
                case 6:
                    xSign = -1;
                    break;
            }

            temp = new SNode(shape, input, pos, xSign * (int)(x * scale), ySign * (int)(y * scale), 0, scale);
            makePoly(temp);

            if (node.hasNode(temp))  {
                node.link();
            } else if (node.adjHasNode(temp)) {
                temp = node.getAdjNode(temp);
                node.link(node, temp);
//                    temp.setEdges();
            } else if (trigger){
                node.link(node, temp);
                temp.setEdges();
                temp.link();
                newList.add(temp);
            }
        }
        node.setIsGrown(true);
        node.setNodes(newList);
        return newList;
    } // grow()
    /**
    * Used to calculate the corners of each node.  Don't let the grow method
    * steal my glory, I do all the work around here!  Most of that code 
    * originally came from here!  Copycat.
    * And I don't answer to anyone!
    */  
    public void makePoly(SNode node)
    {
        int sides = node.getSides();
        float scale = node.getScale();

        Position[] points = new Position[sides];

        points[1] = new Position(-10, -17, scale);
        points[2] = new Position(10, -17, scale);
        points[3] = new Position(20, 0, scale);
        points[4] = new Position(10, 17, scale);
        points[5] = new Position(-10, 17, scale);
        points[0] = new Position(-20, 0, scale);

        node.makePath(points);
        node.addEdges();
    } // makePoly()

}// Snode