package cl.crossline.alphabeticalorder;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

public class CustomAlphabetical extends View
{
    private float height;
    private float width;
    private Paint mPaintRect;
    private Paint mPaintLetter;
    private boolean showView;
    private int pressIndexRow;
    private int pressIndexColumn;
    private int squareColor;
    private int letterColor;
    private OnLetterClick onLetterClickListener;
    private ArrayList<String> enableList;
    private String letter;
    private Rect rect;
    private int space;

    public CustomAlphabetical(Context context)
    {
        super(context);
        init();
    }
    public CustomAlphabetical(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }
    public CustomAlphabetical(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init();
    }
    private void init()
    {
        mPaintRect = new Paint();
        mPaintRect.setStrokeWidth(3);

        mPaintLetter = new Paint();
        mPaintLetter.setStrokeWidth(3);


        showView = false;
        pressIndexRow = -1;
        pressIndexColumn = -1;

        squareColor = Color.RED;
        letterColor = Color.WHITE;

        enableList = new ArrayList<>();
        letter = "#abcdefghijklmnñopqrstuvwxyz";

        rect = new Rect();

        space = 10;

    }
    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        if(showView)
        {
            drawRect(canvas);
        }
    }

    private void drawRect(Canvas canvas)
    {
        int counter=0;
        for(int i=0;i<7;i++)
        {
            float marginTop = height * i;
            float heightRect = (height*(i+1)) - space;

            for(int x=0;x<4;x++)
            {
                putColor(x,i,letter.charAt(counter));
                mPaintLetter.setTextSize(height / 2);

                float marginLeft = (width * x) ;
                float widthRect = width*(x+1) - space;
                float withLetter = marginLeft + space;
                float heightLetter = heightRect - space * 3;

                rect.left = (int) marginLeft;
                rect.top = (int) marginTop;
                rect.right = (int) widthRect;
                rect.bottom = (int) heightRect;

                canvas.drawRect(rect, mPaintRect);
                canvas.drawText(letter, counter, ++counter, withLetter, heightLetter, mPaintLetter);
            }
        }


    }

    private void putColor(int x, int i, char letter)
    {
        if(isEnabled(letter))
        {
            if (pressIndexColumn == x && pressIndexRow == i)
            {
                mPaintRect.setColor(letterColor);
                mPaintLetter.setColor(squareColor);
            }
            else
            {
                mPaintRect.setColor(squareColor);
                mPaintLetter.setColor(letterColor);
            }
        }
        else
        {
            mPaintRect.setColor(Color.GRAY);
            mPaintLetter.setColor(Color.DKGRAY);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh)
    {
        getMeasured();
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        float downX;
        float downY;
        if(isShowView())
        {
            switch (event.getAction())
            {
                case MotionEvent.ACTION_DOWN:
                    downX = event.getX();
                    downY = event.getY();
                    for (int i = 0; i < 7; i++)
                    {
                        float marginTop = height * i;
                        for (int x = 0; x < 4; x++)
                        {
                            float marginLeft = (width * x);
                            float widthRect = ((width * (x + 1)) - 10);
                            float heightRect = (height * (i + 1)) - 10;

                            if ((downX > marginLeft && downX < widthRect) && (downY > marginTop &&
                                    downY < heightRect))
                            {
                                pressIndexRow = i;
                                pressIndexColumn = x;
                                invalidate();
                            }
                        }
                    }

                    return true;

                case MotionEvent.ACTION_UP:
                    float upX = event.getX();
                    float upY = event.getY();
                    int counter = 0;
                    for (int i = 0; i < 7; i++)
                    {
                        float marginTop = height * i;
                        float heightRect = (height*(i+1))-10;
                        for (int x = 0; x < 4; x++)
                        {
                            float marginLeft = (width * x);
                            float widthRect = ((width * (x + 1)) - 10);

                            if ((upX > marginLeft && upX < widthRect) && (upY > marginTop &&
                                    upY < heightRect) && pressIndexRow == i && pressIndexColumn == x)
                            {
                                showView = false;
                                if(onLetterClickListener!=null)
                                {
                                    char letterChar = letter.charAt(counter);
                                    onLetterClickListener.onLetterClickListener(this,letterChar,isEnabled(letterChar));
                                }
                                closeView();
                            }
                            counter++;


                        }
                    }
                    pressIndexRow = -1;
                    pressIndexColumn = -1;
                    break;
            }
        }
        invalidate();
        return false;
    }

    /**
     * Check if the letter its enable
     * @param c letter to check
     * @return true if enable
     */
    private boolean isEnabled(char c)
    {
        boolean isEnabled = false;
        for(String word : enableList)
        {
            if(word.charAt(0) == c)
            {
                isEnabled = true;
                break;
            }
        }
        return isEnabled;
    }

    private void getMeasured()
    {

        if(getResources().getConfiguration().orientation == getResources().getConfiguration().ORIENTATION_PORTRAIT)
        {
            width = getWidth()/4 ;
        }
        else
        {
            width = (getWidth()/8);

        }

        height = getHeight()/7;
    }

    public boolean isShowView()
    {
        return showView;
    }
    private void closeView()
    {
        showView = false;
    }
    /**
     * Open or Close the CustomAlphabetical View with the letters
     * @param showView true if show view
     */
    public void setShowView(boolean showView)
    {
        this.showView = showView;
        invalidate();
        requestLayout();
    }



    /**
     * Add listener for square click's
     * @param onLetterClickListener
     */
    public void setOnLetterClickListener(OnLetterClick onLetterClickListener)
    {
        this.onLetterClickListener = onLetterClickListener;
    }
    public void removeOnLetterClickListener()
    {
        this.onLetterClickListener = null;
    }

    public int getLetterColor()
    {
        return letterColor;
    }

    /**
     * Color for letters on Square
     * @param letterColor
     */
    public void setLetterColor(int letterColor)
    {
        this.letterColor = letterColor;
        invalidate();
        requestLayout();
    }

    public int getSquareColor()
    {
        return squareColor;
    }

    /**
     * Background color on square
     * @param squareColor Color
     */
    public void setSquareColor(int squareColor)
    {
        this.squareColor = squareColor;
        invalidate();
        requestLayout();
    }

    public ArrayList<String> getEnableList()
    {
        return enableList;
    }

    /**
     * Method for make letters enable
     * @param enableList List of String
     */
    public void setEnableList(ArrayList<String> enableList)
    {
        this.enableList = enableList;
        invalidate();
        requestLayout();
    }

    public void addEnableWord(String word)
    {
        enableList.add(word);
    }

    /**
     * Listener for item click
     */
    public interface OnLetterClick
    {
        public void onLetterClickListener(CustomAlphabetical view, char letterClick, boolean isEnabled);
    }
}
