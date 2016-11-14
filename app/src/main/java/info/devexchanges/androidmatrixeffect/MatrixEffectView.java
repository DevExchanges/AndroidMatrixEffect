package info.devexchanges.androidmatrixeffect;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.Random;

public class MatrixEffectView extends View {

    private static Random random;
    private int width, height;
    private Canvas canvas;
    private Bitmap canvasBmp;
    private int fontSize = 40;
    private int columnSize;
    private char[] chars = "ABCDEFGHIJKLMNOPQRSTUVWSYZabcdefghijklmnopqrstuvwyz".toCharArray();
    private int[] txtPosByColumn;
    private Paint paintTxt, paintBg, paintBgBmp, paintInitBg;

    public MatrixEffectView(Context context, AttributeSet attrs) {
        super(context, attrs);
        random = new Random();

        paintTxt = new Paint();
        paintTxt.setStyle(Paint.Style.FILL);
        paintTxt.setColor(Color.GREEN);
        paintTxt.setTextSize(fontSize);

        paintBg = new Paint();
        paintBg.setColor(Color.BLACK);
        paintBg.setAlpha(5);
        paintBg.setStyle(Paint.Style.FILL);

        paintBgBmp = new Paint();
        paintBgBmp.setColor(Color.BLACK);

        paintInitBg = new Paint();
        paintInitBg.setColor(Color.BLACK);
        paintInitBg.setAlpha(255);
        paintInitBg.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;

        canvasBmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(canvasBmp);
        canvas.drawRect(0, 0, width, height, paintInitBg);
        columnSize = width / fontSize;

        txtPosByColumn = new int[columnSize + 1];

        for (int x = 0; x < columnSize; x++) {
            txtPosByColumn[x] = random.nextInt(height / 2) + 1;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(canvasBmp, 0, 0, paintBgBmp);
        drawCanvas();
        invalidate();
    }

    private void drawText() {
        for (int i = 0; i < txtPosByColumn.length; i++) {
            canvas.drawText("" + chars[random.nextInt(chars.length)], i * fontSize, txtPosByColumn[i] * fontSize, paintTxt);

            if (txtPosByColumn[i] * fontSize > height && Math.random() > 0.975) {
                txtPosByColumn[i] = 0;
            }
            txtPosByColumn[i]++;
        }
    }

    private void drawCanvas() {
        canvas.drawRect(0, 0, width, height, paintBg);
        drawText();
    }
}