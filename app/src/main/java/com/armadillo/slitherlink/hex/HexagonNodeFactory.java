package com.armadillo.slitherlink.hex;

import com.armadillo.common.DLog;
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
        ArrayList<SNode> nodeList = new ArrayList<SNode>();
        int shape = 6;
        int x = 0;
        int y = -36;

        Position pos = node.getPosition();
        float scale = node.getScale();

        for (int i = 0; i <= input; i++) {
            y += 36;
            temp = new SNode(shape, input, pos, (int) (x * scale), (int) (y * scale), 0, scale);
            if (!temp.equals(node)) {
                makePoly(temp);
//                temp.addEdges();
//            temp.link();
                temp.setIsGrown(true);
                nodeList.add(temp);
            }

            nodeList.addAll(grow1(temp, input + i));
        }
        for (int j = input - 1; j >= 0; j--) {
            x += 30;
            y += 18;
            temp = new SNode(shape, input, pos, (int) (x * scale), (int) (y * scale), 0, scale);
            makePoly(temp);
//            temp.addEdges();
//            temp.link();
            temp.setIsGrown(true);
            nodeList.add(temp);

            nodeList.addAll(grow1(temp, input + j));
        }
        node.setIsGrown(true);
        return nodeList;
    } // grow()

    public ArrayList<SNode> grow1(SNode node, int input)
    {
        int shape = 6;
        int x = 30;
        int y = -18;
        Position pos = node.getPosition();
        float scale = node.getScale();

        ArrayList<SNode> nodeList = new ArrayList<SNode>();
        SNode temp;
        for (int i = 1; i <= input; i++) {
            temp = new SNode(shape, input, pos, (int) (i * x * scale), (int) (i * y * scale), 0, scale);
            makePoly(temp);
//            temp.addEdges();
//            temp.link();
            temp.setIsGrown(true);
            nodeList.add(temp);
        }
        return nodeList;

    }
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

        points[1] = new Position(-10, -18, scale);
        points[2] = new Position(10, -18, scale);
        points[3] = new Position(20, 0, scale);
        points[4] = new Position(10, 18, scale);
        points[5] = new Position(-10, 18, scale);
        points[0] = new Position(-20, 0, scale);

        node.makePath(points);
//        node.addEdges();
    } // makePoly()

    @Override
    public int[] fill(int depth, int[] nums){
        int[] clues;
        if (nums == null) {
            clues = fill(depth);
        } else {
            clues = nums;
        }
//        switch (depth) {
//            case 1: {
//                clues = fill1(clues);
//                break;
//            }
//            case 2: {
//                clues = fill2(clues);
//                break;
//            }
//            case 3: {
//                clues = fill3(clues);
//                break;
//            }
////            case 6:
////            case 7:
////            case 8:
////            {
////                clues = fill7(nums);
////                break;
////            }
//            default: {
//                clues = fill(depth);
//            }
//        }
        for (int i = 0; i < clues.length; i++) {
            DLog.v(TAG, "clue[" + i + "] = " + clues[i]);
        }
        return clues;
    }

    /**
     * This is used to fill a sample puzzle.  See fill7 for the scoop.
     * @param depth Size of the list with which to fill the puzzle.
     * @return The new list of reorganized numbers.
     */
    public int[] fill(int depth)
    {
        int size = 3 * depth * (depth + 1) + 1; // from http://oeis.org/A003215
        int[] fills = new int[size];
        for (int i = 0; i < size; i++){
            fills[i] = i;
        }
        return fills;
    } // fill(depth)
    /**
     * This is used to fill a level 1 puzzle.  See fill7 for the scoop.
     * @param nums List of numbers with which to fill the puzzle.
     * @return The new list of reorganized numbers.
     */
    public int[] fill1(int[] nums)
    {
        int size = 7;
        int[] fills = new int[size];
        int index;
        for (int i = 0; i < size; i++){
            switch (i){
                case    0   :   index = 3  ;   break;
                case    1   :   index = 5  ;   break;
                case    2   :   index = 6  ;   break;
                case    3   :   index = 4  ;   break;
                case    4   :   index = 1  ;   break;
                case    5   :   index = 0   ;   break;
                case    6   :   index = 2   ;   break;
                default: index = -1; break;
            }
            fills[i] = nums[index];
        }
        return fills;
    } // fill1()
    /**
     * This is used to fill a level 2 puzzle.  See fill7 for the scoop.
     * @param nums List of numbers with which to fill the puzzle.
     * @return The new list of reorganized numbers.
     */
    public int[] fill2(int[] nums)
    {
        int size = 19;
        int[] fills = new int[size];
        int index;
        for (int i = 0; i < size; i++){
            switch (i){
                case    0   :   index = 9  ;   break;
                case    1   :   index = 13  ;   break;
                case    2   :   index = 14  ;   break;
                case    3   :   index = 10  ;   break;
                case    4   :   index = 5  ;   break;
                case    5   :   index = 4   ;   break;
                case    6   :   index = 8   ;   break;
                case    7   :   index = 16   ;   break;
                case    8   :   index = 17   ;   break;
                case    9   :   index = 12  ;   break;
                case    10  :   index = 18   ;   break;
                case    11  :   index = 15  ;   break;
                case    12  :   index = 11  ;   break;
                case    13  :   index = 6  ;   break;
                case    14  :   index = 2  ;   break;
                case    15  :   index = 1  ;   break;
                case    16  :   index = 0   ;   break;
                case    17  :   index = 3   ;   break;
                case    18  :   index = 7   ;   break;
                default: index = -1; break;
            }
            fills[i] = nums[index];
        }
        return fills;
    } // fill2()
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
        int size = 37;
        int[] fills = new int[size];
        int index;
        for (int i = 0; i < size; i++){
            switch (i){
                case    0   :   index = 18  ;   break;
                case    1   :   index = 24  ;   break;
                case    2   :   index = 25  ;   break;
                case    3   :   index = 19  ;   break;
                case    4   :   index = 12  ;   break;
                case    5   :   index = 11  ;   break;
                case    6   :   index = 17  ;   break;
                case    7   :   index = 29  ;   break;
                case    8   :   index = 30  ;   break;
                case    9   :   index = 23  ;   break;
                case    10  :   index = 31  ;   break;
                case    11  :   index = 26  ;   break;
                case    12  :   index = 20  ;   break;
                case    13  :   index = 13  ;   break;
                case    14  :   index = 7  ;   break;
                case    15  :   index = 6  ;   break;
                case    16  :   index = 5  ;   break;
                case    17  :   index = 10   ;   break;
                case    18  :   index = 16   ;   break;
                case    19  :   index = 33  ;   break;
                case    20  :   index = 34  ;   break;
                case    21  :   index = 28  ;   break;
                case    22  :   index = 35  ;   break;
                case    23  :   index = 22  ;   break;
                case    24  :   index = 36  ;   break;
                case    25  :   index = 32  ;   break;
                case    26  :   index = 27  ;   break;
                case    27  :   index = 21  ;   break;
                case    28  :   index = 14  ;   break;
                case    29  :   index = 8  ;   break;
                case    30  :   index = 3  ;   break;
                case    31  :   index = 2  ;   break;
                case    32  :   index = 1  ;   break;
                case    33  :   index = 0  ;   break;
                case    34  :   index = 4  ;   break;
                case    35  :   index = 9  ;   break;
                case    36  :   index = 15  ;   break;
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

}// HexagonNodeFactory