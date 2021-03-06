package com.skycaster.new_hacks.customized;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;

/**
 * 创建者     $Author$
 * 创建时间   2018/4/23 11:32
 * 描述	      ${TODO}
 * <p>
 * 更新者     $Author$
 * 更新时间   $Date$
 * 更新描述   ${TODO}
 */

public class BezierView extends View {
    private Paint mLinePainter;
    private Paint mKnotPainter;
    private ArrayList<Point> mPoints=new ArrayList<>();
    private Path mPath;

    public BezierView(Context context) {
        this(context,null);
    }

    public BezierView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BezierView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //init paint that draws bezier lines
        mLinePainter=new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePainter.setStyle(Paint.Style.STROKE);
        mLinePainter.setColor(Color.BLUE);

        //init paint that draws circles in each knot
        mKnotPainter =new Paint(Paint.ANTI_ALIAS_FLAG);
        mKnotPainter.setStyle(Paint.Style.FILL);
        mKnotPainter.setColor(Color.RED);

        //add emulated points
        mPoints.add(new Point(100,200));
        mPoints.add(new Point(150,300));
        mPoints.add(new Point(170,150));
        mPoints.add(new Point(200,100));
        mPoints.add(new Point(250,350));

        //init bezier path
        mPath=new Path();
        for(int i=0;i<mPoints.size()-1;i++){
//            mPath.reset();
            Point start = mPoints.get(i);
            Point end = mPoints.get(i+1);
            mPath.moveTo(start.x,start.y);
            int xcp1=(start.x+end.x)>>1;
            int ycp1=start.y;
            int xcp2=xcp1;
            int ycp2=end.y;
            mPath.cubicTo(xcp1,ycp1,xcp2,ycp2,end.x,end.y);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(mPath,mLinePainter);
        drawKnots(canvas);
    }

    private void drawKnots(Canvas canvas) {
        for(Point p:mPoints){
            canvas.drawCircle(p.x,p.y,10, mKnotPainter);
        }
    }
}
