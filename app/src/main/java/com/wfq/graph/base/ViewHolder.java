package com.wfq.graph.base;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

/**
 * Created by dell on 2016/12/16.
 */
public class ViewHolder {

    View itemView;
    public ViewHolder(View itemView){
        this.itemView = itemView;
    }

    public View getItemView() {
        return itemView;
    }

    public void setText(int id, String text){
        ((TextView)itemView.findViewById(id)).setText(text);
    }

    public void setText(int id,SpannableString text){
        ((TextView)itemView.findViewById(id)).setText(text);
    }


    public void setTextColor(int id,int color){
        ((TextView)itemView.findViewById(id)).setTextColor(color);
    }

    public void setType(int id, Typeface typeface){
        ((TextView)itemView.findViewById(id)).setTypeface(typeface);
    }

    public void setTextAndType(int id, String text, Typeface face){
        setText(id,text);
        setType(id,face);
    }

    public void setImageBitmap(int id, Bitmap bitmap){
        ((ImageView)itemView.findViewById(id)).setImageBitmap(bitmap);
    }

    public void setImageResource(int id,int resId){
        ((ImageView)itemView.findViewById(id)).setImageResource(resId);
    }

    public void setImageUrl(Activity activity,int id, String url){
        ImageView imageView = (ImageView) itemView.findViewById(id);
        Glide.with(activity).load(url).into(imageView);
    }

    public void setImageUrl(Activity activity,int id, String url,int errorResId){
        ImageView imageView = (ImageView) itemView.findViewById(id);
        Glide.with(activity).load(url).error(errorResId).into(imageView);
    }

    public void setImageUrl(Fragment fragment, int id, String url){
        ImageView imageView = (ImageView) itemView.findViewById(id);
        Glide.with(fragment).load(url).into(imageView);
    }

    public void setImageUrl(Fragment fragment, int id, String url, int errorResId){
        ImageView imageView = (ImageView) itemView.findViewById(id);
        Glide.with(fragment).load(url).error(errorResId).into(imageView);
    }

    public void setImageUrl(Context context, int id, String url){
        ImageView imageView = (ImageView) itemView.findViewById(id);
        Glide.with(context).load(url).into(imageView);
    }

    public void setImageUrl(Context context,int id, String url,int errorResId){
        ImageView imageView = (ImageView) itemView.findViewById(id);
        Glide.with(context).load(url).placeholder(errorResId).error(errorResId).into(imageView);
    }

    public void setBackgroundResource(int id,int resId){
        itemView.findViewById(id).setBackgroundResource(resId);
    }

    public void setOnClickListener(int id, View.OnClickListener onClickListener){
        itemView.findViewById(id).setOnClickListener(onClickListener);
    }

    public void setVisibility(int id,int type){
        itemView.findViewById(id).setVisibility(type);
    }

    public void setChecked(int id,boolean isCheck){
        ((CheckBox)itemView.findViewById(id)).setChecked(isCheck);
    }

    public void setOnCheckedChangeListener(int id, CompoundButton.OnCheckedChangeListener listener){
        ((CheckBox)itemView.findViewById(id)).setOnCheckedChangeListener(listener);
    }

    public <T extends View> T getView(int id){
        return (T) itemView.findViewById(id);
    }
}
