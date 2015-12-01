package com.armadillo.slitherlink.hex;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.armadillo.common.DLog;
import com.armadillo.slitherlink.R;
import com.armadillo.slitherlink.altair.Edge;
import com.armadillo.slitherlink.altair.Position;
import com.armadillo.slitherlink.altair.SNode;
import com.armadillo.slitherlink.altair.Slitherlink;
import com.armadillo.slitherlink.altair.SlitherlinkHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by john.pushnik on 11/16/15.
 */
public class HexLayoutFragment extends Fragment {
    private static final String TAG = "HexLayoutFragment";

    private View view;
    private ImageView image;
    private TextView coordinates, nearest;
    private ToggleButton clickMode;

    private Slitherlink slitherlink;
    private ArrayList<SNode> nodes;
    private ArrayList<Edge> edges;
    private LayoutInflater inflater;

    private boolean treeLoaded = false;
    private Canvas canvas;
    private Paint paint;

    private int fontSize = 13;
    private int xTextOffset = 3;
    private int yTextOffset = 6;

    public HexLayoutFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DLog.i(TAG, "onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        DLog.i();
        this.inflater = inflater;
        view = inflater.inflate(R.layout.fragment_hex, container, false);
        view.getViewTreeObserver().addOnGlobalLayoutListener(globalLayoutListener);

        image = (ImageView)view.findViewById(R.id.image);
        setOnTouchListenerForView(image);
        coordinates = (TextView)view.findViewById(R.id.coordinates);
        nearest = (TextView)view.findViewById(R.id.nearest);

        clickMode = (ToggleButton)view.findViewById(R.id.clickMode);

        return view;
    }

    private ViewTreeObserver.OnGlobalLayoutListener globalLayoutListener =  new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {
            DLog.i();

            int width = image.getWidth();
            if (treeLoaded) {
                return;
            } else if (width > 0) {
                if (slitherlink != null) {
                    treeLoaded = true;

                    Bitmap bitmap = Bitmap.createBitmap(image.getWidth(), image.getHeight(), Bitmap.Config.ARGB_8888);
                    canvas = new Canvas(bitmap);
                    image.setImageBitmap(bitmap);

                    paint = new Paint();
                    paint.setStrokeWidth(2);

                    fillNodes();
                    fillEdges();
                }
                if (android.os.Build.VERSION.SDK_INT >= 16) {
                    view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    view.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        DLog.i(TAG, "onResume");

        treeLoaded = false;

        try {
            if (slitherlink == null) {
//                try {
//                    Position base = new Position(130,130);
//                    slitherlink = new Slitherlink();
//                    int xMax = 5;
//                    int off = 200;
//                    int[] clues;
//                    for (int tsize = 0; tsize < xMax; tsize++){
//                        for (int ang = 0; ang < 3; ang++){
//                            slitherlink.index = 0;
//                            clues = slitherlink.fill2(SlitherlinkHelper.sl2[tsize + ang * xMax]);
//                            slitherlink.root = new
//                                    SNode(8,-1,base,off * tsize, off * ang, ang + 1, 2.0f);
//                            slitherlink.root.setValue(clues[slitherlink.index]);
//                            slitherlink.root.setHighlight(2);
//                            slitherlink.root.setId(slitherlink.index++);
//                            slitherlink.grow(slitherlink.addNode(slitherlink.root),2,clues);
//                        }
//                    }
//                    for (int h = 0; h < Slitherlink.maxSolve; h++){
//                        slitherlink.solve();
//                    }
//                } catch (Exception e) {
//                }

                slitherlink = new Slitherlink(3, SlitherlinkHelper.sl31, 1.5f);
//                slitherlink = new Slitherlink(2, SlitherlinkHelper.sl20, 2.0f);
//                slitherlink = new Slitherlink(2, SlitherlinkHelper.sl21, 2.0f);
//                slitherlink = new Slitherlink(2, SlitherlinkHelper.sl22, 2.0f);
//                slitherlink = new Slitherlink(2, SlitherlinkHelper.sl23, 2.0f);
//                slitherlink = new Slitherlink(2, SlitherlinkHelper.sl24, 2.0f);
//                slitherlink = new Slitherlink(2, SlitherlinkHelper.sl25, 2.0f);
//                slitherlink = new Slitherlink(2, SlitherlinkHelper.sl26, 2.0f);
//                slitherlink = new Slitherlink(2, SlitherlinkHelper.sl27, 2.0f);
//                slitherlink = new Slitherlink(2, SlitherlinkHelper.sl28, 2.0f);
//                slitherlink = new Slitherlink(2, SlitherlinkHelper.sl29, 2.0f);
//                slitherlink = new Slitherlink(6, SlitherlinkHelper.sl71, 1.0f);
//                slitherlink = new Slitherlink(6, SlitherlinkHelper.sl72, 1.0f);
//                slitherlink = new Slitherlink(6, SlitherlinkHelper.sl73, 1.0f);
//                slitherlink = new Slitherlink(6, SlitherlinkHelper.sl74, 1.0f);
                nodes = slitherlink.getNodes();
                edges = slitherlink.getEdges();
            }
//            fillNodes();
//            board.requestLayout();
        } catch (Exception e) {

        }

    }

    public void fillNodes() {
        for (SNode node : nodes) {
            if (node != null) {
                drawNode(node);
            }
        }

    }

    public void fillEdges() {
        for (Edge edge : edges) {
            if (edge != null) {
                drawEdge(edge);
            }
        }
    }

    public void drawEdge(Edge edge) {
        DLog.d(TAG, edge.toString());
        paint.setColor(edge.getColor());
        canvas.drawLine(edge.getPos1().getX(), edge.getPos1().getY(), edge.getPos2().getX(), edge.getPos2().getY(), paint);
        if (edge.showText()) {
            Position pos = edge.getPosition();
            paint.setColor(edge.getTextColor());
            canvas.drawText("X", pos.getX() - xTextOffset, pos.getY() + yTextOffset, paint);
        }
    }

    public void drawEdges(Edge[] edges) {
        for (Edge edge : edges) {
            if (edge != null) {
                drawEdge(edge);
            }
        }
    }

    public void drawEdges(ArrayList<Edge> edges) {
        for (Edge edge : edges) {
            drawEdge(edge);
        }
    }

    public void drawNode(SNode node) {
        if (node == null) {
            DLog.d(TAG, "node is null");
            return;
        }
        DLog.d(TAG, node.toString());
        paint.setColor(node.getColor());
        canvas.drawPath(node.getPath(), paint);
        if (node.getValue() != -1) {
            paint.setColor(node.getTextColor());
            paint.setTextSize(fontSize);
            Position pos = node.getPosition();
            canvas.drawText("" + node.getValue(), pos.getX() - xTextOffset, pos.getY() + yTextOffset, paint);
//            canvas.drawText("" + node.getId() + "," + node.getValue(), pos.getX() - xTextOffset, pos.getY() + yTextOffset, paint);
        }
    }

    public void setOnTouchListenerForView(View view) {
        view.setOnTouchListener(
                new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (event.getAction() == MotionEvent.ACTION_UP) {
                            int x = (int) event.getX();
                            int y = (int) event.getY();
                            coordinates.setText("Touch coordinates : (" + x + "," + y + ")");
                            if (slitherlink != null) {
                                if (clickMode.isChecked()) {
                                    SNode near = findNearestNode(x, y);
                                    if (near != null) {
                                        nearest.setText("Nearest node = " + near.toString());
                                        near.toggleHighlight();
                                        drawNode(near);
                                        drawEdges(near.getEdges());
                                        slitherlink.replaceNode(near);
                                    }
                                } else {
                                    Edge near = findNearestEdge(x, y);
                                    if (near != null) {
                                        nearest.setText("Nearest edge = " + near.toString());
                                        near.toggleStatus();
                                        if (!near.showText()) {
                                            Position pos = near.getPosition();
                                            paint.setColor(near.getTextColor());
                                            canvas.drawText("X", pos.getX() - xTextOffset, pos.getY() + yTextOffset, paint);
                                        }

                                        SNode node1 = near.getNode1();
                                        if (node1 != null) {
                                            drawNode(node1);
                                            drawEdges(node1.getEdges());
                                        }
                                        SNode node2 = near.getNode2();
                                        if (node2 != null) {
                                            drawNode(node2);
                                            drawEdges(node2.getEdges());
                                        }
                                    }
                                }
                            }
                        }
                        return true;
                    }
                });
    }

    public SNode findNearestNode(int x, int y){
        final Position point = new Position(x,y);
        Collections.sort(nodes, new Comparator<SNode>() {
            @Override
            public int compare(SNode lhs, SNode rhs) {
                float left = lhs.getPosition().getDist(point);
                float right = rhs.getPosition().getDist(point);
                if (left < right) {
                    return -1;
                } else if (left > right) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });
        SNode temp = nodes.get(0);
        if (temp.contains(point)) {
            return temp;
        } else {
            for (SNode neighbor : temp.getNodes()) {
                if (neighbor.contains(point)) {
                    return neighbor;
                }
            }
        }
        return null;
    }

    public Edge findNearestEdge(int x, int y){
        final Position point = new Position(x,y);
        Collections.sort(edges, new Comparator<Edge>() {
            @Override
            public int compare(Edge lhs, Edge rhs) {
                float left = lhs.getPosition().getDist(point);
                float right = rhs.getPosition().getDist(point);
                if (left < right) {
                    return -1;
                } else if (left > right) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });
        return edges.get(0);
    }

}
