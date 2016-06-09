package cl.crossline.alphabeticalorder;

import android.content.Context;
import android.database.DataSetObserver;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.WrapperListAdapter;

import java.util.ArrayList;

/**
 * Created by jacevedo on 3/2/16.
 */
public class AlphabeticalAdapter implements WrapperListAdapter
{
    private ListAdapter adapter;
    private Context mContext;
    private char previousLetter;

    public AlphabeticalAdapter(Context context, ListAdapter adapter)
    {
        this.adapter = adapter;
        this.mContext = context;
        previousLetter = '\0';
    }

    @Override
    public ListAdapter getWrappedAdapter()
    {
        return adapter;
    }

    @Override
    public boolean areAllItemsEnabled()
    {
        return false;
    }

    @Override
    public boolean isEnabled(int position)
    {
        return adapter.isEnabled(position);
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer)
    {
        adapter.registerDataSetObserver(observer);
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer)
    {
        adapter.unregisterDataSetObserver(observer);
    }

    @Override
    public int getCount()
    {
        return adapter.getCount();
    }

    @Override
    public Object getItem(int position)
    {
        return adapter.getItem(position);
    }

    @Override
    public long getItemId(int position)
    {
        return adapter.getItemId(position);
    }

    @Override
    public boolean hasStableIds()
    {
        return adapter.hasStableIds();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        int itemViewType = getItemViewType(position);
        if(itemViewType == 0)
        {
            previousLetter = adapter.getItem(position).toString().charAt(0);
            Button button = new Button(mContext);
            button.setText(adapter.getItem(position).toString());
            return button;
        }
        else
        {
            TextView textView = new TextView(mContext);
            textView.setText(adapter.getItem(position).toString());
            return textView;
        }
    }

    @Override
    public int getItemViewType(int position)
    {
        if(adapter.getItem(position).toString().charAt(0) != previousLetter)
        {
            previousLetter = adapter.getItem(position).toString().charAt(0);
            return 0;
        }
        else
        {
            return 1;
        }
    }

    @Override
    public int getViewTypeCount()
    {
        return 2;
    }

    @Override
    public boolean isEmpty()
    {
        return adapter.isEmpty();
    }

    public float getWidthScreenOnDP()
    {
        DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();

        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        return dpWidth;
    }
}
