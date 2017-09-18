package com.wfq.graph.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.wfq.graph.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by weifuqing on 2017/7/18 0018.
 */

public class BrokenLine extends View {

    private Paint paint_horizontal_line, paint_vertical_line, paint_horizontal_text,
            paint_vertical_text, paint_broken_line, paint_vertical_unit;

    private int color_verticalLine = 0xffaaaaaa; // 灰色
    private int color_horizontalLine = 0xffaaaaaa; // 灰色
    private int color_broken = 0xff20C420; // 绿色
    private int color_verticalText = 0xff222222; // 黑色
    private int color_verticalUnit = 0xff222222; // 黑色
    private int color_horizontalText = 0xff222222; // 黑色

    private Bitmap bitmap_point;

    private List<Integer> broken_list;
    private List<String> horizontal_list;
    private List<Integer> vertical_list;
    private String vertical_unit;

    private int space_left;//左边边距
    private int space_bottom;//下边边距
    private int space_top;//上边距（单位）
    private int space_right;//右边距
    private int width,height;
    private float tb; //作为标准长度

    private Context mContext;

    public BrokenLine(Context context) {
        this(context,null);
    }

    public BrokenLine(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BrokenLine(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initPaint();
    }

    private void initPaint(){

        tb = getResources().getDimension(R.dimen.historyscore_tb);
        bitmap_point = BitmapFactory.decodeResource(getResources(),R.drawable.point_green);
        space_bottom  = space_top = (int) tb*3;
        space_right = (int) (tb*3);
        space_left = (int) (tb*4);

        paint_broken_line = new Paint();//折线
        paint_broken_line.setColor(color_broken);
        paint_broken_line.setStrokeWidth(tb*0.1f);
        paint_broken_line.setStyle(Paint.Style.STROKE);
        paint_broken_line.setAntiAlias(true);

        paint_horizontal_line = new Paint();//底线
        paint_horizontal_line.setColor(color_horizontalLine);
        paint_horizontal_line.setStrokeWidth(tb*0.1f);

        paint_horizontal_text = new Paint();//底部文字
        paint_horizontal_text.setStrokeWidth(tb*0.1f);
        paint_horizontal_text.setColor(color_horizontalText);
        paint_horizontal_text.setTextSize(tb*1.2f);

        paint_vertical_line = new Paint();//竖线
        paint_vertical_line.setStrokeWidth(tb*0.1f);
        paint_vertical_line.setStyle(Paint.Style.STROKE);
        paint_vertical_line.setColor(color_verticalLine);

        paint_vertical_text = new Paint();//左侧文字
        paint_vertical_text.setStrokeWidth(tb*0.1f);
        paint_vertical_text.setColor(color_verticalText);
        paint_vertical_text.setTextSize(tb*1.2f);

        paint_vertical_unit = new Paint();//左侧单位
        paint_vertical_unit.setStrokeWidth(tb*0.1f);
        paint_vertical_unit.setColor(color_verticalUnit);
        paint_vertical_unit.setTextSize(tb*1.2f);

    }

    public void init(List<Integer> broken_list, List<Integer> vertical_list, List<String> horizontal_list, String vertical_unit){
        Collections.sort(vertical_list);
        Collections.reverse(vertical_list);//vertical_list从大到小排列
        this.broken_list = broken_list;
        this.vertical_list = vertical_list;
        this.horizontal_list = horizontal_list;
        this.vertical_unit = "("+vertical_unit+")";
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(broken_list==null||broken_list.size()==0){
            return;
        }
        drawLine(canvas);
        drawBrokenLine(canvas);
        drawText(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getMeasuredWidth();
        height = getMeasuredHeight();
    }

    private void drawLine(Canvas canvas){
        int space = (width-space_left-space_right)/(broken_list.size()-1);
        for (int i=0;i<broken_list.size();i++){
            Path path = new Path();
            path.moveTo(space_left+space*i,space_top/3);
            path.lineTo(space_left+space*i,height-space_bottom);
            DashPathEffect effects = new DashPathEffect(new float[] { tb*0.3f, tb*0.3f,tb*0.3f, tb*0.3f}, 0);
            paint_vertical_line.setPathEffect(effects);
            canvas.drawPath(path,paint_vertical_line);
//            canvas.drawLine(space_left+space*i,space_top/3,space_left+space*i,height-space_bottom,paint_vertical_line);
        }
        canvas.drawLine(space_left,height-space_bottom,width-space_right,height-space_bottom,paint_horizontal_line);

    }

    private void drawBrokenLine(Canvas canvas){
        List<Point> points = new ArrayList<>();
        int max = height - space_top - space_bottom;
        int space = (width-space_left-space_right)/(broken_list.size()-1);


        for(int i=0;i<broken_list.size();i++){
            int progress = broken_list.get(i)*max/vertical_list.get(0);
            points.add(new Point(space_left+space*i,height-space_bottom-progress));
        }
        Path path_broken = new Path();
        for (int j=0;j<points.size();j++){
            Point point = points.get(j);
            if(j==0){
                path_broken.moveTo(point.getX(),point.getY());
            }else {
                path_broken.lineTo(point.getX(),point.getY());
            }
//            canvas.drawBitmap(bitmap_point,point.getX()-bitmap_point.getWidth() / 2,
//                    point.getY()-bitmap_point.getHeight() / 2,null);
            paint_broken_line.setStyle(Paint.Style.FILL_AND_STROKE);
            canvas.drawCircle(point.getX(),point.getY(),tb*0.2f,paint_broken_line);
        }
        paint_broken_line.setStyle(Paint.Style.STROKE);
        canvas.drawPath(path_broken,paint_broken_line);
    }

    private void drawText(Canvas canvas){
        int space_horizontal = (width-space_left-space_right)/(horizontal_list.size()-1);
        int space_vertical = (height-space_bottom-space_top)/(vertical_list.size()-1);
        for (int i=0;i<horizontal_list.size();i++){
            canvas.drawText(horizontal_list.get(i),space_left+space_horizontal*i-tb*1.8f,height,paint_horizontal_text);
        }
        for (int j=0;j<vertical_list.size();j++){
            canvas.drawText(vertical_list.get(j)+"",space_left/4,space_top+space_vertical*j+tb*1f,paint_vertical_text);
        }
        canvas.drawText(vertical_unit,0,space_top-tb*1f,paint_vertical_unit);
    }

    class Point{

        int x;
        int y;

        Point(int x,int y){
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }
    }
}
