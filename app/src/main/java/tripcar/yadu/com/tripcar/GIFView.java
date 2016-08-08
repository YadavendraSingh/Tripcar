package tripcar.yadu.com.tripcar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Movie;
import android.util.AttributeSet;
import android.view.View;

import java.io.InputStream;

/**
 * Created by yadu on 27/12/15.
 */
public class GIFView extends View {
    public Movie mMovie;
    public long movieStart;

    public GIFView(Context context) {
        super(context);
        initializeView();
    }

    public GIFView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeView();
    }

    public GIFView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initializeView();
    }

    private void initializeView() {
//R.drawable.loader - our animated GIF
        InputStream is = getContext().getResources().openRawResource(+R.drawable.cargirl);
        mMovie = Movie.decodeStream(is);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.TRANSPARENT);
        super.onDraw(canvas);
        long now = android.os.SystemClock.uptimeMillis();
        if (movieStart == 0) {
            movieStart = now;
        }
        if (mMovie != null) {
            int relTime = (int) ((now - movieStart) % mMovie.duration());
            mMovie.setTime(relTime);
            //mMovie.draw(canvas, getWidth() - mMovie.width(), getHeight() - mMovie.height());
            mMovie.draw(canvas, this.getWidth()/2-20,this.getHeight()/2-40);
            this.invalidate();
        }
    }
    private int gifId;

    public void setGIFResource(int resId) {
        this.gifId = resId;
        initializeView();
    }

    public int getGIFResource() {
        return this.gifId;
    }
}