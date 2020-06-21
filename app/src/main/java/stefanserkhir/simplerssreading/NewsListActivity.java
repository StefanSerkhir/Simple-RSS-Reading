package stefanserkhir.simplerssreading;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class NewsListActivity extends AppCompatActivity {
    private static final String TAG = "NewsListActivity";

    private RecyclerView mNewsRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private SwipeRefreshLayout mRefreshLayout;
    private List<NewsItem> mNewsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);

        mNewsRecyclerView = findViewById(R.id.news_recycler_view);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mNewsRecyclerView.setLayoutManager(mLinearLayoutManager);


        mRefreshLayout = findViewById(R.id.news_container);
        mRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mRefreshLayout.setOnRefreshListener(() -> {mRefreshLayout.setRefreshing(false);});

        new FetchNewsTask().execute();
    }

    private class FetchNewsTask extends AsyncTask<Void, Void, List<NewsItem>> {

        @Override
        protected List<NewsItem> doInBackground(Void... voids) {
            return new NewsFetcher().getURLInputStream("https://www.vesti.ru/vesti.rss");
        }

        @Override
        protected void onPostExecute(List<NewsItem> newsItems) {
            mNewsList = newsItems;
        }
    }
}
