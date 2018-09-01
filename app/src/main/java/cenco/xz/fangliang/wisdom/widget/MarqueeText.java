package cenco.xz.fangliang.wisdom.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;


public class MarqueeText extends View implements Runnable {
    private int x; // 当前滚动的位置
    private boolean isStop = false;
    private int textWidth;
    private int textHeight;
    private boolean isMeasure = false;
    private MarqueeListener listener;

    private Paint paint;
    private String text;


    public MarqueeText(Context context) {
        super(context);
        init();
    }


    public MarqueeText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    public MarqueeText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize(16);
    }

    public void setListener(MarqueeListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
// TODO Auto-generated method stub
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(widthMeasureSpec,50);

    }


    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);

        if (text==null){
            return;
        }

        if (!isMeasure) {
            getTextWidth();// 文字宽度只需要获取一次就可以了
            x = this.getWidth();
            isMeasure = true;
        }

        int y = (getHeight()-textHeight)/2+textHeight;

        canvas.drawText(text.toString(),x,y,paint);

    }

    public void setText(String  text){
        isMeasure = false;
        this.text = text;
    }


    private void getTextWidth() {

        if (text==null){
            return;
        }
        textWidth = (int) paint.measureText(text);

        Paint.FontMetricsInt metrics = paint.getFontMetricsInt();
        textHeight = metrics.descent - metrics.ascent;
    }




    @Override
    public void run() {

        x-=2;
        if (textWidth+x<=0){
            x = getWidth();
            if (listener!=null){
                listener.onMarqueeNext();
            }
        }
        invalidate();
        postDelayed(this, 1);

//        currentScrollX += 2;// 滚动速度.+号表示往左边-
//        scrollTo(currentScrollX, 0);
//        if (isStop) {
//            return;
//        }
//        if (getScrollX() >= (textWidth)) {
//            currentScrollX = -(this.getWidth());// 当前出现的位置
//        }
//        postDelayed(this, 1);
    }


    // 开始滚动
    public void startScroll() {
        isStop = false;
        this.removeCallbacks(this);
        post(this);
    }


    // 停止滚动
    public void stopScroll() {
        isStop = true;
        this.removeCallbacks(this);
    }



    public void setTextSize(int textSize) {
        paint.setTextSize(textSize);
    }

    public void setTextColor(int textColor) {
        paint.setColor(textColor);
    }

    public interface MarqueeListener{
        void onMarqueeNext();

    }

}