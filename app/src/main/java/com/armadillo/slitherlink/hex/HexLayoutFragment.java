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
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.armadillo.common.DLog;
import com.armadillo.slitherlink.R;
import com.armadillo.slitherlink.common.Edge;
import com.armadillo.slitherlink.common.Position;
import com.armadillo.slitherlink.common.SNode;
import com.armadillo.slitherlink.altair.Slitherlink;
import com.armadillo.slitherlink.common.SlitherlinkHelper;

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

    private RadioButton depth0, depth1, depth2, depth3, depth4, depth5, depth6;
    private Spinner depth;

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

//        depth = (Spinner)view.findViewById(R.id.depth);
        depth0 = (RadioButton)view.findViewById(R.id.depth0);
        depth1 = (RadioButton)view.findViewById(R.id.depth1);
        depth2 = (RadioButton)view.findViewById(R.id.depth2);
        depth3 = (RadioButton)view.findViewById(R.id.depth3);
        depth4 = (RadioButton)view.findViewById(R.id.depth4);
        depth5 = (RadioButton)view.findViewById(R.id.depth5);
        depth6 = (RadioButton)view.findViewById(R.id.depth6);

        setListener(depth0);
        setListener(depth1);
        setListener(depth2);
        setListener(depth3);
        setListener(depth4);
        setListener(depth5);
        setListener(depth6);

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
//                slitherlink = new Slitherlink(Slitherlink.TYPE_HEXAGON, 2, SlitherlinkHelper.alt31, 1.5f);
//                slitherlink = new Slitherlink(Slitherlink.TYPE_HEXAGON, 1, 2.0f);
                slitherlink = new Slitherlink(Slitherlink.TYPE_HEXAGON, 1, 1.5f);
//                slitherlink = new Slitherlink(Slitherlink.TYPE_HEXAGON, 1, SlitherlinkHelper.hex10, 1.5f);
//                slitherlink = new Slitherlink(Slitherlink.TYPE_HEXAGON, 1, SlitherlinkHelper.hex11, 1.5f);
//                slitherlink = new Slitherlink(Slitherlink.TYPE_HEXAGON, 2, 2.0f);
//                slitherlink = new Slitherlink(Slitherlink.TYPE_HEXAGON, 2, SlitherlinkHelper.hex20, 1.5f);
//                slitherlink = new Slitherlink(Slitherlink.TYPE_HEXAGON, 2, 1.5f);
//                slitherlink = new Slitherlink(Slitherlink.TYPE_HEXAGON, 3, SlitherlinkHelper.hex30, 1.5f);
//                slitherlink = new Slitherlink(Slitherlink.TYPE_HEXAGON, 3, 1.5f);
//                slitherlink = new Slitherlink(Slitherlink.TYPE_HEXAGON, 4, SlitherlinkHelper.hex40, 1.5f);
//                slitherlink = new Slitherlink(Slitherlink.TYPE_HEXAGON, 4, 1.5f);

                nodes = slitherlink.getNodes();
                edges = slitherlink.getEdges();
            }
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
//            canvas.drawText("" + node.getId(), pos.getX() - xTextOffset, pos.getY() + yTextOffset, paint);
        }
    }

    public void setListener(CompoundButton button) {
        button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    String text = buttonView.getText().toString();
                    if (text != null) {
                        int depth = Integer.parseInt(text);
                        slitherlink = new Slitherlink(Slitherlink.TYPE_HEXAGON, depth, 1.5f);

                        nodes = slitherlink.getNodes();
                        edges = slitherlink.getEdges();

                        if (treeLoaded && canvas != null) {
                            canvas.drawColor(getResources().getColor(R.color.white));
                            fillNodes();
                            fillEdges();
                        }
                    }
                }
            }
        });
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
