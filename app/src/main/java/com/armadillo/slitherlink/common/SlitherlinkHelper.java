package com.armadillo.slitherlink.common;

/**
 * Created by john.pushnik on 11/18/15.
 */
public class SlitherlinkHelper {

    public static float SCALE = 1.5f;
    public static int SIZE = 40;
    public static int MAX_SOLVE = 10;

    public static int[] alt00 = {0,0,0,0,
            0,0,0,0,0,
            0,0,0,
            0,0,0,0,0,
            0,0,0,0};
    public static int[] alt10 = {-1, 0,-1, 0,
            0,-1,-1,-1,-1,
            -1,-1,-1,
            -1,-1,-1,-1, 0,
            0,-1, 0,-1};
    public static int[] alt11 = { 5,-1,-1, 5,
            -1,-1, 2,-1,-1,
            2, 0, 2,
            -1,-1, 2,-1,-1,
            5,-1,-1, 5};
    public static int[] alt12 = {-1,-1,-1,-1,
            0,-1,-1,-1,0,
            -1, 8,-1,
            0,-1,-1,-1,0,
            -1,-1,-1,-1};
    public static int[] alt12a = {-1, 0,-1, 0,
            0,-1,-1,-1,-1,
            1,-1,-1,
            -1,-1,-1,-1, 0,
            0,-1, 0,-1};
    public static int[] alt13 = {-1,-1,-1,-1,
            -1,-1, 0,-1,-1,
            4, 5, 4,
            -1, 4,-1, 4,-1,
            -1,-1,-1,-1};
    public static int[] alt20 = { 5,-1,-1, 5,
            -1,-1, 2,-1,-1,
            2, 0, 2,
            -1,-1, 2,-1,-1,
            5,-1,-1, 5};
    public static int[] alt21 = {1,6,5,4,
            0,2,3,4,5,
            2,5,4,
            2,2,0,2,5,
            4,3,4,3};
    public static int[] alt22 = {3,6,2,1,
            6,3,4,5,2,
            4,4,4,
            3,4,4,3,6,
            5,3,6,3};
    public static int[] alt23 = {5,5,2,1,
            3,5,3,4,5,
            3,4,1,
            5,4,3,2,4,
            5,2,6,3};
    public static int[] alt24 = {5,5,5,4,
            2,3,3,4,5,
            2,7,3,
            6,5,2,5,5,
            4,4,4,4};
    public static int[] alt25 = {4,3,6,4,
            4,3,3,4,5,
            4,7,3,
            5,4,3,4,5,
            4,5,5,4};
    public static int[] alt26 = {5,3,6,3,
            4,4,3,3,4,
            2,7,1,
            4,2,2,2,4,
            4,4,5,3};
    public static int[] alt27 = {4,3,5,4,
            4,4,3,3,4,
            4,5,3,
            4,3,3,5,5,
            4,4,5,4};
    public static int[] alt28 = {5,3,6,3,
            4,4,3,3,5,
            2,7,2,
            4,2,2,2,4,
            4,4,4,4};
    public static int[] alt29 = {4,5,3,5,
            4,3,3,4,4,
            2,7,2,
            4,2,2,2,5,
            4,4,5,3};
    public static int[][] alt2 =
            {alt00, alt10, alt12, alt12a, alt13,
                    alt21, alt22, alt23, alt24, alt25,
                    alt26, alt11, alt27, alt28, alt29};
    public static int[] alt31 =
            {4,4,
                    7,4,0,4,6,
                    4,3,2,2,2,2,
                    5,3,5,3,5,3,3,
                    2,4,4,3,3,
                    3,5,4,3,5,3,6,
                    4,3,3,3,3,4,
                    3,3,3,4,7,
                    3,6};
    public static int[] alt71 =
            {3,4,3,6,-1,4,
                    -1,5,3,4,3,5,-1,5,5,
                    4,3,-1,4,5,-1,-1,-1,-1,
                    5,3,-1,6,3,3,4,-1,-1,-1,-1,2,-1,
                    -1,-1,-1,-1,5,4,4,3,-1,-1,-1,5,
                    -1,-1,3,-1,4,3,-1,5,-1,-1,3,
                    4,2,6,-1,2,-1,4,2,-1,4,5,4,2,2,
                    3,-1,2,2,-1,4,3,-1,-1,-1,4,4,2,0,4,
                    -1,-1,2,-1,3,-1,-1,-1,-1,-1,1,
                    6,4,-1,5,-1,2,4,2,-1,5,5,3,-1,2,-1,
                    3,4,-1,-1,-1,5,-1,1,-1,-1,-1,6,5,5,
                    -1,-1,-1,-1,2,0,-1,-1,-1,-1,2,
                    -1,-1,5,-1,5,-1,-1,4,-1,-1,3,3,
                    -1,-1,-1,2,-1,-1,1,-1,5,-1,-1,-1,4,
                    3,0,-1,3,2,3,-1,-1,4,
                    -1,-1,4,-1,-1,2,-1,-1,4,
                    -1,5,-1,4,3,4};
    public static int[] alt72 =
            {-1,-1,-1,-1,3,-1,
                    3,2,2,3,4,3,-1,-1,-1,
                    4,-1,2,-1,-1,-1,-1,2,-1,
                    4,3,4,5,4,-1,-1,2,5,-1,4,3,-1,
                    5,-1,-1,3,4,4,3,3-1,-1,-1,-1,2,
                    3,3,3,3,-1,3,1,6,3,-1,3,
                    3,2,5,-1,-1,-1,3,-1,-1,-1,4,-1,3,-1,
                    3,-1,-1,3,-1,4,4,2,-1,4,-1,2,2,-1,-1,
                    -1,6,2,-1,4,-1,-1,-1,-1,4,3,
                    -1,2,-1,-1,4,4,-1,-1,4,5,5,5,3,-1,-1,
                    2,0,-1,-1,4,2,-1,-1,3,-1,-1,-1,6,-1,
                    -1,-1,-1,4,2,3,2,-1,-1,2,-1,
                    4,5,-1,-1,-1,4,-1,2,2,-1,-1,3,
                    2,-1,4,3,4,-1,4,5,-1,5,-1,2,-1,
                    -1,-1,-1,-1,-1,1,-1,-1,-1,
                    5,4,-1,2,0,-1,2,5,-1,
                    -1,3,3,4,4,4};
    public static int[] alt73 =
            {2,-1,-1,6,-1,-1,
                    5,-1,0,2,3,-1,-1,-1,-1,
                    -1,-1,-1,-1,3,3,-1,0,-1,
                    5,4,5,-1,-1,5,-1,-1,-1,2,-1,-1,-1,
                    -1,-1,-1,4,4,-1,-1,-1,-1,5,-1,4,
                    4,-1,-1,5,-1,3,-1,-1,2,3,4,
                    -1,4,3,2,-1,3,-1,2,1,-1,3,-1,6,-1,
                    -1,3,-1,-1,-1,-1,5,-1,0,2,-1,5,-1,-1,-1,
                    2,4,2,-1,-1,5,-1,-1,-1,-1,1,
                    3,-1,3,-1,-1,5,-1,-1,4,-1,-1,1,-1,0,-1,
                    4,3,-1,-1,-1,-1,6,-1,-1,-1,-1,3,-1,-1,
                    3,2,-1,-1,-1,-1,4,-1,-1,3,-1,
                    5,5,-1,2,-1,-1,-1,-1,1,2,-1,4,
                    5,-1,3,3,-1,4,3,5,-1,2,4,-1,5,
                    -1,-1,-1,-1,4,-1,-1,4,-1,
                    5,3,4,-1,-1,4,-1,3,5,
                    3,3,6,-1,5,-1};
    public static int[] alt74 =
            {4,3,5,-1,-1,2,
                    -1,-1,-1,2,-1,-1,2,3,5,
                    5,1,-1,-1,-1,3,-1,-1,-1,
                    -1,3,-1,-1,5,-1,2,4,-1,6,4,3,-1,
                    5,5,2,-1,-1,-1,-1,-1,-1,-1,5,-1,
                    3,-1,4,-1,-1,3,-1,3,3,3,4,
                    -1,-1,4,4,-1,4,3,4,3,-1,-1,5,-1,3,
                    -1,-1,-1,-1,4,5,4,-1,5,3,5,-1,3,-1,5,
                    -1,6,-1,2,3,-1,-1,3,-1,-1,-1,
                    -1,4,3,-1,5,3,3,3,3,4,4,4,3,-1,4,
                    -1,4,2,4,-1,4,4,5,4,-1,4,-1,5,-1,
                    -1,1,-1,-1,-1,-1,-1,4,4,-1,-1,
                    6,-1,5,2,2,3,2,-1,1,3,4,-1,
                    5,-1,-1,-1,-1,2,-1,-1,-1,5,-1,-1,-1,
                    -1,4,3,-1,-1,4,3,2,-1,
                    -1,-1,-1,3,1,-1,2,-1,-1,
                    -1,-1,-1,-1,2,-1};
    public static int[][] alt7a = {alt71, alt72, alt73, alt74};

    public static int[] hex10 = {0,1,
                                2,3,4,
                                5,6};

    public static int[] hex11 = {5,4,
                                6,0,3,
                                1,2};

    public static int[] hex20 = {0,1,2,
                               3,4,5,6,
                              7,8,9,10,11,
                               12,13,14,15,
                                16,17,18};

    public static int[] hex21 = {16,15,14,
                               17,5,4,13,
                              18,6,0,3,12,
                                9,1,2,11,
                                 7,8,10};

}