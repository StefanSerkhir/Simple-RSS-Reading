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
    }

    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        if (dy == 0) {
            int progress = mLinearLayoutManager.getItemCount() -
                    mLinearLayoutManager.findLastVisibleItemPosition() - 1;
            progress = progress == 0 ? 1 : progress;
            mScrollProgress.setMax(progress);
        }
        mScrollProgress.setProgress(mScrollProgress.getMax() == 1 ?
                1 : mLinearLayoutManager.findFirstVisibleItemPosition());
    }
}