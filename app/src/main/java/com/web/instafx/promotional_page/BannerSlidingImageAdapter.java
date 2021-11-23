package com.web.instafx.promotional_page;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.web.instafx.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.jar.JarException;

public class BannerSlidingImageAdapter extends PagerAdapter {


    private JSONArray IMAGES;
    private LayoutInflater inflater;
    private AppCompatActivity context;


    public BannerSlidingImageAdapter(AppCompatActivity context, JSONArray dataAr) {
        this.context = context;
        this.IMAGES=dataAr;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return IMAGES.length();
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View imageLayout = inflater.inflate(R.layout.slidingimages_layout, view, false);

        assert imageLayout != null;
        final ImageView imageView = imageLayout
                .findViewById(R.id.image);

        try {
           // JSONObject jsonObject= (JSONObject) IMAGES.get(position);
            Log.e("Bannaer Image","Banner::"+IMAGES.getString(position));
            showImage(IMAGES.getString(position),imageView);
            view.addView(imageLayout, 0);
        }
        catch (Exception e){
            e.printStackTrace();
        }



        return imageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

    private void showImage(final String url, final ImageView header_img) {
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Glide.with(context)
                        .load(url)
                        .apply(RequestOptions.bitmapTransform(new RoundedCorners(3)))
                        .into(header_img);
            }
        });
    }
}
