package com.example.movie.adapter;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movie.R;
import com.example.movie.ui.callbacks.BaseInterface;
import com.google.android.material.button.MaterialButton;

import java.util.List;

import static com.example.movie.utils.Constants.IMAGE_URL;

public class BindingAdapter {

    @SuppressWarnings("ALL")
    @androidx.databinding.BindingAdapter(value = {"layoutFile", "listener", "listData"}, requireAll = false)
    public static <E> void bindAdapter(RecyclerView recyclerView, int id, BaseInterface listener, List<E> list) {
        if (recyclerView.getAdapter() == null) {
            recyclerView.setAdapter(new BaseAdapter(list, id, listener));
        } else {
            ((BaseAdapter) recyclerView.getAdapter()).setListener(listener);
            ((BaseAdapter) recyclerView.getAdapter()).setData(list);
        }
    }

    @SuppressWarnings("unused")
    @androidx.databinding.BindingAdapter("bind:setViewVisibility")
    public static void showHide(MaterialButton btn, boolean enabled) {
        enableDisableView(btn, !enabled);
    }

    public static void enableDisableView(View view, boolean enabled) {
        view.setEnabled(enabled);
        if (view instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) view;

            for (int idx = 0; idx < group.getChildCount(); idx++) {
                enableDisableView(group.getChildAt(idx), enabled);
            }
        }
    }

    @androidx.databinding.BindingAdapter(value = {"image", "placeholder"}, requireAll = false)
    public static void setImage(ImageView image, String url, Drawable placeHolder) {
        String posterUrl = IMAGE_URL + url;
        if (!url.isEmpty()) {

            Glide.with(image.getContext()).load(posterUrl).centerCrop()
                    .placeholder(R.drawable.ic_film)
                    .into(image);
        } else {
            image.setImageDrawable(placeHolder);
        }
    }

    @androidx.databinding.BindingAdapter(value = {"detailImage", "placeholder"}, requireAll = false)
    public static void setMainImage(ImageView image, String url, Drawable placeHolder) {
        String posterUrl = IMAGE_URL + url;
        if (!url.isEmpty()) {
            Glide.with(image.getContext()).load(posterUrl).centerCrop()
                    .placeholder(R.drawable.ic_film)
                    .into(image);
        } else {
            image.setImageDrawable(placeHolder);
        }
    }
}
