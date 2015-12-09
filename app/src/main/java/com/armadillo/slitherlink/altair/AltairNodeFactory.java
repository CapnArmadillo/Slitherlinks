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

    @Override
    public int[] fill(int depth, int[] nums){
        int[] clues;
        if (nums == null) {
            clues = new int[250];
        } else switch (depth) {
            case 2: {
                clues = fill2(nums);
                break;
            }
            case 3: {
                clues = fill3(nums);
                break;
            }
            case 6:
            case 7:
            case 8:
            {
                clues = fill7(nums);
                break;
            }
            default: {
                clues = new int[250];
            }
        }
        return clues;
    }

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

}// AltairNodeFactory