package com.example.mvvmretrofit.base;

import androidx.databinding.BindingAdapter;
import androidx.databinding.BindingConversion;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BaseBindingAdapter {

    @SuppressWarnings("unchecked")
    @BindingAdapter(value = {"itemView", "items","itemAnimator","itemDecor"}, requireAll = false)
    public static <T> void setAdapter(final RecyclerView recyclerView, BaseItemView<T> arg, final List<T> items, RecyclerView.ItemAnimator animator, RecyclerView.ItemDecoration decor) {
        if (arg == null) {
            throw new IllegalArgumentException("itemView must not be null");
        }
        BaseBindingRecyclerViewAdapter<T> adapter = new BaseBindingRecyclerViewAdapter<>(arg);
        if (items!=null)adapter.setItems(items);
        if (animator!=null)recyclerView.setItemAnimator(animator);
        if (decor!=null)recyclerView.addItemDecoration(decor);
        recyclerView.setAdapter(adapter);

    }

    @BindingAdapter("layoutManager")
    public static void setLayoutManager(RecyclerView recyclerView, BaseLayoutManager.LayoutManagerFactory layoutManagerFactory) {
        recyclerView.setLayoutManager(layoutManagerFactory.create(recyclerView));
    }

    @BindingConversion
    public static BaseItemView toItemViewArg(BaseItemView.ItemView itemView) {
        return BaseItemView.Companion.of(itemView);
    }

    @BindingConversion
    public static BaseItemView toItemViewArg(BaseItemView.ItemViewSelector<?> selector) {
        return BaseItemView.Companion.of(selector);
    }

}
