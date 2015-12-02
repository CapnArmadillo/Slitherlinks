package com.armadillo.slitherlink.altair;

import android.test.ActivityTestCase;

import com.armadillo.slitherlink.common.Position;
import com.armadillo.slitherlink.common.SNode;

/**
 * Created by john.pushnik on 11/16/15.
 */
public class TestAltair extends ActivityTestCase {
    public static int size = 50, maxSolve = 10;

    @Override
    public void setUp() throws Exception {
        super.setUp();

    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
    }

    public void testAndroidTestCaseSetupProperly() {
        assertTrue(false);
    }

    public void testS3() {
        try {
            Slitherlink s3 = new Slitherlink(3, SlitherlinkHelper.sl31);
        } catch (Exception e) {

        }
    }

    public void testS17() {
        try {
            Slitherlink s17[] = new Slitherlink[4];
            for (int j = 0; j < 4; j++)
            {
                s17[j] = new Slitherlink(6, SlitherlinkHelper.sl7a[j]);
            }
        } catch (Exception e) {

        }
    }

    public void testS10() {
        try {
            Slitherlink s10 = new Slitherlink(3);
            s10.base = new Position(130,130);
            int xMax = 5;
            int off = 200;
            int[] clues = new int[21];
            for (int tsize = 0; tsize < xMax; tsize++){
                for (int ang = 0; ang < 3; ang++){
                    s10.index = 0;
                    clues = s10.fill2(SlitherlinkHelper.sl2[tsize + ang * xMax]);
                    s10.root = new
                            SNode(8,-1,s10.base,off * tsize, off * ang, ang + 1, 2.0f);
                    s10.root.setValue(clues[s10.index]);
                    s10.root.setHighlight(2);
                    s10.root.setId(s10.index++);
//                    s10.addNode(s10.root);
                    s10.grow(s10.addNode(s10.root),2,clues);
                }
            }
            for (int h = 0; h < maxSolve; h++){
                s10.solve();
            }
        } catch (Exception e) {

        }
    }

}
