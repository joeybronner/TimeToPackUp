package com.timetopackup.app.tools;

import com.timetopackup.app.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter 
{
    private Context context;

    Integer[] imageIDs =
    	{
            R.drawable.im0,
            R.drawable.im1,
            R.drawable.im2,
            R.drawable.im3,
            R.drawable.im4,
            R.drawable.im5,
            R.drawable.im6,
            R.drawable.im7,
            R.drawable.im8,
            R.drawable.im9,
            R.drawable.im10,
            R.drawable.im11,
            R.drawable.im12,
            R.drawable.im13,
            R.drawable.im14,
            R.drawable.im15,
            R.drawable.im16,
            R.drawable.im17,
            R.drawable.im18,
            R.drawable.im19,
            R.drawable.im20,
            R.drawable.im21,
            R.drawable.im22,
            R.drawable.im23,
            R.drawable.im24,
            R.drawable.im25,
            R.drawable.im26,
            R.drawable.im27,
            R.drawable.im28,
            R.drawable.im29,
            R.drawable.im30,
            R.drawable.im31,
            R.drawable.im32,
            R.drawable.im33,
            R.drawable.im34,
            R.drawable.im35,
            R.drawable.im36,
            R.drawable.im37,
            R.drawable.im38,
            R.drawable.im39,
            R.drawable.im40,
            R.drawable.im41,
            R.drawable.im42,
            R.drawable.im43,
            R.drawable.im44,
            R.drawable.im45,
            R.drawable.im46,
            R.drawable.im47,
            R.drawable.im48,
            R.drawable.im49,
            R.drawable.im50,
            R.drawable.im51,
            R.drawable.im52,
            R.drawable.im53,
            R.drawable.im54,
            R.drawable.im55,
            R.drawable.im56,
            R.drawable.im57,
            R.drawable.im58,
            R.drawable.im59,
            R.drawable.im60,
            R.drawable.im61,
            R.drawable.im62,
            R.drawable.im63,
            R.drawable.im64,
            R.drawable.im65,
            R.drawable.im66,
            R.drawable.im67,
            R.drawable.im68,
            R.drawable.im69,
            R.drawable.im70,
            R.drawable.im71,
            R.drawable.im72,
            R.drawable.im73,
            R.drawable.im74,
            R.drawable.im75
    	};

    public ImageAdapter(Context c) 
    {
        context = c;
    }

    //---returns the number of images---
    @Override
	public int getCount()
    {
        return imageIDs.length;
    }

    //---returns the ID of an item--- 
    @Override
	public Object getItem(int position)
    {
        return position;
    }

    @Override
	public long getItemId(int position)
    {
        return position;
    }

    //---returns an ImageView view---
    @Override
	public View getView(int position, View convertView, ViewGroup parent)
    {
        ImageView imageView;
        if (convertView == null)
        {
            imageView = new ImageView(context);
            imageView.setLayoutParams(new GridView.LayoutParams(100, 100));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(5, 5, 5, 5);
        }
        else
        {
            imageView = (ImageView) convertView;
        }
        imageView.setImageResource(imageIDs[position]);
       
        return imageView;
	}
}
