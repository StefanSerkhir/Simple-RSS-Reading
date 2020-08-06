package stefanserkhir.simplerssreading.repository.remote;

import android.app.Activity;
import android.os.AsyncTask;

import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import io.realm.Realm;
import stefanserkhir.simplerssreading.repository.db.model.NewsItem;
import stefanserkhir.simplerssreading.repository.NewsFetcher;
import stefanserkhir.simplerssreading.ui.views.adapter.NewsAdapter;

public class FetchNewsTask extends AsyncTask<Void, Void, Void> {
    private final String mSelectedFilter;
    private final RecyclerView mNewsRecyclerView;
    private final Realm mRealm;
    private final SwipeRefreshLayout mRefreshLayout;
    private final Activity mActivity;

    public FetchNewsTask(String filter, RecyclerView newsRecyclerView,
                         Realm realm, SwipeRefreshLayout refreshLayout, Activity activity) {
        mSelectedFilter = filter;
        mNewsRecyclerView = newsRecyclerView;
        mRealm = realm;
        mRefreshLayout = refreshLayout;
        mActivity = activity;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        new NewsFetcher().fetchNews("https://www.vesti.ru/vesti.rss");
        return null;
    }

    @Override
    protected void onPostExecute(Void voidParam) {
        if (mSelectedFilter.equals("")) {
            mNewsRecyclerView.setAdapter(new NewsAdapter(mRealm.where(NewsItem.class)
                                .findAll(), mActivity));
        } else {
            mNewsRecyclerView.setAdapter(new NewsAdapter(mRealm.where(NewsItem.class)
                                .contains("category", mSelectedFilter).findAll(), mActivity));
        }
        mRefreshLayout.setRefreshing(false);
    }
}