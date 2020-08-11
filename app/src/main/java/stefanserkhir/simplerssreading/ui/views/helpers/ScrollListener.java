package stefanserkhir.simplerssreading.ui.views.helpers;

import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ScrollListener extends RecyclerView.OnScrollListener {
    private LinearLayoutManager mLinearLayoutManager;
    private ProgressBar mScrollProgress;

    public ScrollListener(RecyclerView.LayoutManager linearLayoutManager, ProgressBar scrollProgress) {
        mLinearLayoutManager = (LinearLayoutManager) linearLayoutManager;
        mScrollProgress = scrollProgress;
        mScrollProgress.setMax(mLinearLayoutManager.getItemCount());
    }

    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        mScrollProgress.setProgress(mLinearLayoutManager.findLastVisibleItemPosition() + 1);
    }
}
