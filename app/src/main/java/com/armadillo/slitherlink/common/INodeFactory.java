package com.armadillo.slitherlink.common;

import java.util.ArrayList;

/**
 * Created by john.pushnik on 12/1/15.
 */
public interface INodeFactory {
    ArrayList<SNode> grow(SNode node, int input, boolean trigger);
    void makePoly(SNode node);
    int[] fill(int depth, int[] nums);


}
