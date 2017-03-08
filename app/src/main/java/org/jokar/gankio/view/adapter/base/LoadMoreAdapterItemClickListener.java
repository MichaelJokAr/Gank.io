package org.jokar.gankio.view.adapter.base;

/**
 * Created by JokAr on 2017/3/8.
 */

public interface LoadMoreAdapterItemClickListener {
    void itemClickListener(int position);

    void loadMore();

    void footViewClick();
}
