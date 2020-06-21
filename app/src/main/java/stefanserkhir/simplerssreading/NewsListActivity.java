package stefanserkhir.simplerssreading;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
        mRefreshLayout.setOnRefreshListener(() -> {
            new FetchNewsTask().execute();
        });

        new FetchNewsTask().execute();
    }

    private class NewsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mNewsTitle;
        private TextView mNewsDate;
        private int mNewsPosition;

        public NewsHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);

            mNewsTitle = itemView.findViewById(R.id.news_title);
            mNewsDate = itemView.findViewById(R.id.news_date);
        }

        public void bindNews(NewsItem newsItem, int newsPosition) {
            mNewsTitle.setText(newsItem.getTitle());
            mNewsDate.setText(newsItem.getDate());
            mNewsPosition = newsPosition;
        }

        @Override
        public void onClick(View v) {
            startActivity(NewsActivity.newIntent(NewsListActivity.this,
                                                mNewsList.get(mNewsPosition)));
        }
    }

    private class NewsAdapter extends RecyclerView.Adapter<NewsHolder> {
        private List<NewsItem> mNewsList;

        public NewsAdapter(List<NewsItem> newsList) {
            mNewsList = newsList;
        }

        @NonNull
        @Override
        public NewsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new NewsHolder(getLayoutInflater().inflate(R.layout.news_item, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull NewsHolder newsHolder, int position) {
            Log.i(TAG, "URL => " + mNewsList.get(position).getImage());
            newsHolder.bindNews(mNewsList.get(position), position);
        }

        @Override
        public int getItemCount() {
            return mNewsList.size();
        }
    }

    private class FetchNewsTask extends AsyncTask<Void, Void, List<NewsItem>> {

        @Override
        protected List<NewsItem> doInBackground(Void... voids) {
            return new NewsFetcher().getURLInputStream("https://www.vesti.ru/vesti.rss");
        }

        @Override
        protected void onPostExecute(List<NewsItem> newsItems) {
            mNewsList = newsItems;
            mNewsRecyclerView.setAdapter(new NewsAdapter(mNewsList));
            mRefreshLayout.setRefreshing(false);
        }
    }
}
