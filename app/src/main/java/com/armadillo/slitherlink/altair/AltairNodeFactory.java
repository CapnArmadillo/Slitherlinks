package com.armadillo.slitherlink.altair;

import com.armadillo.slitherlink.common.INodeFactory;
import com.armadillo.slitherlink.common.Position;
import com.armadillo.slitherlink.common.SNode;

import java.util.ArrayList;

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
public class AltairNodeFactory implements INodeFactory
{
    private static final String TAG = "AltairNodeFactory";

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
        int shape = 0;
        int ang = 0;
        int x = 0;
        int y = 0;
        int xSign = 1;
        int ySign = 1;   // for changing the sign of x and y based on dir

        int sides = node.getSides();
        int dir = node.getDir();
        Position pos = node.getPosition();
        float scale = node.getScale();

        for (int i = 1; i <= sides; i++)
        {
            switch (sides){ // the number of sides of the node to be grown
                case 4:
                shape = 7;
                switch (i){
                    case 1:
                        x = 20;
                        y = 20;
                    break;
                    case 2:
                        x = -20;
                        y = 20;
                    break;
                    case 3:
                        x = -20;
                        y = -20;
                    break;
                    case 4:
                        x = 20;
                        y = -20;
                    break;
                }
                    temp = new SNode(shape, input, pos, (int)(x * scale), (int)(y * scale), i, scale);
                break; // case 4
                case 5:
                switch (i){
                    case 1:
                        shape = 8;
                        switch (dir % 4){
                            case 1:
                                x = -38;
                                y = 0;
                            break;
                            case 2:
                                x = 0;
                                y = -38;
                            break;
                            case 3:
                                x = 38;
                                y = 0;
                            break;
                            case 0:
                                x = 0;
                                y = 38;
                            break;
                        }
                    break;
                    case 2:
                        shape = 6;
                        ang = dir;
                        switch (dir % 4){
                            case 1:
                                x = -8;
                                y = 30;
                            break;
                            case 2:
                                x = -30;
                                y = -8;
                            break;
                            case 3:
                                x = 8;
                                y = -30;
                            break;
                            case 0:
                                x = 30;
                                y = 8;
                            break;
                        }
                    break;
                    case 3:
                        shape = 7;
                        ang = dir + 1;
                        switch (dir % 4){
                            case 1:
                                x = 28;
                                y = 20;
                            break;
                            case 2:
                                x = -20;
                                y = 28;
                            break;
                            case 3:
                                x = -28;
                                y = -20;
                            break;
                            case 0:
                                x = 20;
                                y = -28;
                            break;
                        }
                    break;
                    case 4:
                        shape = 7;
                        ang = dir + 2;
                        switch (dir % 4){
                            case 1:
                                x = 28;
                                y = -20;
                            break;
                            case 2:
                                x = 20;
                                y = 28;
                            break;
                            case 3:
                                x = -28;
                                y = 20;
                            break;
                            case 0:
                                x = -20;
                                y = -28;
                            break;
                        }
                    break;
                    case 5:
                        shape = 6;
                        ang = dir + 3;
                        switch (dir % 4){
                            case 1:
                                x = -8;
                                y = -30;
                            break;
                            case 2:
                                x = 30;
                                y = -8;
                            break;
                            case 3:
                                x = 8;
                                y = 30;
                            break;
                            case 0:
                                x = -30;
                                y = 8;
                            break;
                        }
                    break;
                }
                    temp = new SNode(shape, input, pos, (int)(x * scale), (int)(y * scale), ang, scale);
                break;  // case 5
                case 6:
                        switch (dir % 4){
                            case 1:
                                ang = dir;
                                xSign = -1;
                                ySign = -1;
                            break;
                            case 2:
                                ang = dir;
                                xSign = 1;
                                ySign = -1;
                            break;
                            case 3:
                                ang = dir;
                                xSign = 1;
                                ySign = 1;
                            break;
                            case 0:
                                ang = dir;
                                xSign = -1;
                                ySign = 1;
                            break;
                        }
                switch (i){
                    case 1:
                        shape = 8;
                        x = 30;
                        y = 30;
                    break;
                    case 2:
                        switch (dir % 2){
                            case 1:
                                ang = dir;
                                    x = -8;
                                    y = 30;
                            break;
                            case 0:
                                ang = dir;
                                    x = 30;
                                    y = -8;
                            break;
                        }
                        shape = 5;
                    break;
                    case 3:
                        shape = 7;
                        switch (dir % 2){
                            case 1:
                                ang = dir;
                                    x = -36;
                                    y = 10;
                            break;
                            case 0:
                                ang = dir;
                                    x = 10;
                                    y = -36;
                            break;
                        }
                                ang = ang + 1;
                    break;
                    case 4:
                        shape = 6;
                                ang = dir + 2;
                        x = -26;
                        y = -26;
                    break;
                    case 5:
                        shape = 7;
                        switch (dir % 2){
                            case 1:
                                ang = dir;
                                    x = 10;
                                    y = -36;
                            break;
                            case 0:
                                ang = dir;
                                    x = -36;
                                    y = 10;
                            break;
                        }
                                ang = ang + 3;
                    break;
                    case 6:
                        switch (dir % 2){
                            case 1:
                                ang = dir;
                                    x = 30;
                                    y = -8;
                            break;
                            case 0:
                                ang = dir;
                                    x = -8;
                                    y = 30;
                            break;
                        }
                        shape = 5;
                                ang = ang + 1;
                    break;
                }
                    temp = new SNode(shape, input, pos, xSign * (int)(x * scale), ySign * (int)(y * scale), ang, scale);
                break; // case 6
                case 7:
                        switch (dir % 4){
                            case 1:
                                ang = dir;
                                xSign = -1;
                                ySign = -1;
                            break;
                            case 2:
                                ang = dir;
                                xSign = 1;
                                ySign = -1;
                            break;
                            case 3:
                                ang = dir;
                                xSign = 1;
                                ySign = 1;
                            break;
                            case 0:
                                ang = dir;
                                xSign = -1;
                                ySign = 1;
                            break;
                        }
                switch (i){
                    case 1:
                        shape = 6;
                        switch (dir % 2){
                            case 1:
                                ang = dir;
                                    x = -10;
                                    y = -36;
                            break;
                            case 0:
                                ang = dir;
                                    x = -36;
                                    y = -10;
                            break;
                        }
                        ang = ang + 3;
                    break;
                    case 2:
                        switch (dir % 2){
                            case 1:
                                ang = dir;
                                    x = 20;
                                    y = -28;
                            break;
                            case 0:
                                ang = dir;
                                    x = -28;
                                    y = 20;
                            break;
                        }
                        shape = 5;
                        ang = ang + 3;
                    break;
                    case 3:
                        switch (dir % 2){
                            case 1:
                                ang = dir;
                                    x = 0;
                                    y = 40;
                            break;
                            case 0:
                                ang = dir;
                                    x = 40;
                                    y = 0;
                            break;
                        }
                        ang = ang + 3;
                        shape = 7;
                    break;
                    case 4:
                        shape = 4;
                        x = 20;
                        y = 20;
                    break;
                    case 5:
                        switch (dir % 2){
                            case 1:
                                ang = dir;
                                    x = 40;
                                    y = 0;
                            break;
                            case 0:
                                ang = dir;
                                    x = 0;
                                    y = 40;
                            break;
                        }
                        ang = ang + 1;
                        shape = 7;
                    break;
                    case 6:
                        shape = 5;
                        switch (dir % 2){
                            case 1:
                                ang = dir;
                                    x = -28;
                                    y = 20;
                            break;
                            case 0:
                                ang = dir;
                                    x = 20;
                                    y = -28;
                            break;
                        }
                    ang = ang + 2;
                    break;
                    case 7:
                        shape = 6;
                        switch (dir % 2){
                            case 1:
                                ang = dir;
                                    x = -36;
                                    y = -10;
                            break;
                            case 0:
                                ang = dir;
                                    x = -10;
                                    y = -36;
                            break;
                        }
                        ang = ang + 1;
                    break;
                }
                    temp = new SNode(shape, input, pos, xSign * (int)(x * scale),
                        ySign * (int)(y * scale), ang % 4, scale);
                break; // case 7
                case 8:
                switch (i){
                    case 1:
                        shape = 5;
                        ang = 1;
                        x = 38;
                        y = 0;
                    break;
                    case 2:
                        shape = 6;
                        ang = 1;
                        x = 30;
                        y = 30;
                    break;
                    case 3:
                        shape = 5;
                        ang = 2;
                        x = 0;
                        y = 38;
                    break;
                    case 4:
                        shape = 6;
                        ang = 2;
                        x = -30;
                        y = 30;
                    break;
                    case 5:
                        shape = 5;
                        ang = 3;
                        x = -38;
                        y = 0;
                    break;
                    case 6:
                        shape = 6;
                        ang = 3;
                        x = -30;
                        y = -30;
                    break;
                    case 7:
                        shape = 5;
                        ang = 4;
                        x = 0;
                        y = -38;
                    break;
                    case 8:
                        shape = 6;
                        ang = 4;
                        x = 30;
                        y = -30;
                    break;
                }
                    temp = new SNode(shape, input, pos, (int)(x * scale), (int)(y * scale), ang, scale);
                break;  // case 8
                default:
                    temp = null;
                break;
            }
            if (temp != null) {
                makePoly(temp);
            }
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
        int dir = node.getDir();
        float scale = node.getScale();

        Position[] points = new Position[sides];
        int xSign = 1;
        int ySign = 1;   // for changing the sign of x and y based on dir
        switch (sides){
            case 8:
                points[1] = new Position(24,10, scale);
                points[2] = new Position(10,24, scale);
                points[3] = new Position(-10,24, scale);
                points[4] = new Position(-24,10, scale);
                points[5] = new Position(-24,-10, scale);
                points[6] = new Position(-10,-24, scale);
                points[7] = new Position(10,-24, scale);
                points[0] = new Position(24,-10, scale);
                break;
            case 7:
                switch (dir % 4){
                    case 1:
                        xSign = 1;
                        ySign = 1;
                    break;
                    case 2:
                        xSign = -1;
                        ySign = 1;
                    break;
                    case 3:
                        xSign = -1;
                        ySign = -1;
                    break;
                    case 0:
                        xSign = 1;
                        ySign = -1;
                    break;
                }
                points[1] = new Position(xSign * 16, ySign * 16, scale);
                points[2] = new Position(xSign * -4, ySign * 22, scale);
                points[3] = new Position(xSign * -20, ySign * 12, scale);
                points[4] = new Position(xSign * -20, ySign * -6, scale);
                points[5] = new Position(xSign * -6, ySign * -20, scale);
                points[6] = new Position(xSign * 12, ySign * -20, scale);
                points[0] = new Position(xSign * 22, ySign * -4, scale);
                break;
            case 6:
                switch (dir % 2){
                    case 1:
                        ySign = 1;
                    break;
                    case 0:
                        ySign = -1;
                    break;
                }
                points[1] = new Position(14, ySign * -14, scale);
                points[2] = new Position(20, ySign * 6, scale);
                points[3] = new Position(6, ySign * 20, scale);
                points[4] = new Position(-14, ySign * 14, scale);
                points[5] = new Position(-20, ySign * -6, scale);
                points[0] = new Position(-6, ySign * -20, scale);
                break;
            case 5:
                switch (dir % 4){
                    case 1:
                        xSign = 1;
                    break;
                    case 2:
                        ySign = 1;
                    break;
                    case 3:
                        xSign = -1;
                    break;
                    case 0:
                        ySign = -1;
                    break;
                }
                switch (dir % 2){
                    case 1:
                points[1] = new Position(xSign * 16, 0, scale);
                points[2] = new Position(xSign * 6, 16, scale);
                points[3] = new Position(xSign * -14, 10, scale);
                points[4] = new Position(xSign * -14, -10, scale);
                points[0] = new Position(xSign * 6, -16, scale);
                    break;
                    case 0:
                points[1] = new Position(0, ySign * 16, scale);
                points[2] = new Position(-16, ySign * 6, scale);
                points[3] = new Position(-10, ySign * -14, scale);
                points[4] = new Position(10, ySign * -14, scale);
                points[0] = new Position(16, ySign * 6, scale);
                    break;
                }
                break;
            case 4:
                points[1] = new Position(14,0, scale);
                points[2] = new Position(0,14, scale);
                points[3] = new Position(-14,0, scale);
                points[0] = new Position(0,-14, scale);
                break;
            default : ;
        }
        node.makePath(points);
        node.addEdges();
    } // makePoly()

}// Snode