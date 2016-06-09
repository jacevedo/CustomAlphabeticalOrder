package cl.crossline.alphabeticalorder;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * Created by jacevedo on 3/2/16.
 */
public class AlphabeticalListView extends ListView
{
    public AlphabeticalListView(Context context)
    {
        super(context);
    }

    public AlphabeticalListView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public AlphabeticalListView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public AlphabeticalListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes)
    {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void setAdapter(ListAdapter adapter)
    {
        super.setAdapter(adapter);
    }
}
