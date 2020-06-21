package stefanserkhir.simplerssreading;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import io.realm.Realm;
import io.realm.RealmResults;


public class NewsListActivity extends AppCompatActivity {

    private RecyclerView mNewsRecyclerView;
    private SwipeRefreshLayout mRefreshLayout;
    private String SELECTED_FILTER = "";
    private Realm mRealm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);

        Realm.init(this);
        mRealm = Realm.getDefaultInstance();

        mNewsRecyclerView = findViewById(R.id.news_recycler_view);
        mNewsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mNewsRecyclerView.setAdapter(new NewsAdapter(mRealm.where(NewsItem.class).findAll()));

        mRefreshLayout = findViewById(R.id.news_container);
        mRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mRefreshLayout.setRefreshing(true);
        mRefreshLayout.setOnRefreshListener(() -> new FetchNewsTask().execute());

        new FetchNewsTask().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activitiy_news_list, menu);

        MenuCompat.setGroupDividerEnabled(menu, true);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        final String CURRENT_CATEGORY = SELECTED_FILTER;
        switch (item.getItemId()) {
            case R.id.category_politics:
                SELECTED_FILTER = getString(R.string.politics);
                break;
            case R.id.category_society:
                SELECTED_FILTER = getString(R.string.society);
                break;
            case R.id.category_sport:
                SELECTED_FILTER = getString(R.string.sport);
                break;
            case R.id.category_in_the_world:
                SELECTED_FILTER = getString(R.string.in_the_world);
                break;
            case R.id.category_incidents:
                SELECTED_FILTER = getString(R.string.incidents);
                break;
            case R.id.reset_filter:
                mNewsRecyclerView.setAdapter(new NewsAdapter(mRealm.where(NewsItem.class)
                                .findAll()));
                SELECTED_FILTER = "";
                getSupportActionBar().setSubtitle(SELECTED_FILTER);
                Toast.makeText(this, getString(R.string.you_reseted_filter),
                                                            Toast.LENGTH_SHORT).show();
                return true;
            default:
                return true;
        }
        if (!SELECTED_FILTER.equals(CURRENT_CATEGORY)) {
            getSupportActionBar().setSubtitle(SELECTED_FILTER);
            Toast.makeText(this, getString(R.string.you_selected_categoty,
                    SELECTED_FILTER), Toast.LENGTH_SHORT).show();
            mNewsRecyclerView.setAdapter(new NewsAdapter(mRealm.where(NewsItem.class)
                                .contains("category", SELECTED_FILTER).findAll()));
        }
        return true;
    }
    private class NewsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mNewsTitle;
        private TextView mNewsDate;
        private NewsItem mNewsItem;

        public NewsHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);

            mNewsTitle = itemView.findViewById(R.id.news_title);
            mNewsDate = itemView.findViewById(R.id.news_date);
        }

        public void bindNews(NewsItem newsItem) {
            mNewsTitle.setText(newsItem.getTitle());
            mNewsDate.setText(newsItem.getDate());
            mNewsItem = newsItem;
        }

        @Override
        public void onClick(View v) {
            startActivity(NewsActivity.newIntent(NewsListActivity.this, mNewsItem));
        }
    }

    private class NewsAdapter extends RecyclerView.Adapter<NewsHolder> {
        private RealmResults<NewsItem> mNewsList;

        public NewsAdapter(RealmResults<NewsItem> newsList) {
            mNewsList = newsList;
        }

        @NonNull
        @Override
        public NewsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new NewsHolder(getLayoutInflater().inflate(R.layout.news_item, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull NewsHolder newsHolder, int position) {
            newsHolder.bindNews(mNewsList.get(position));
        }

        @Override
        public int getItemCount() {
            return mNewsList.size();
        }
    }

    private class FetchNewsTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            new NewsFetcher().fetchNews("https://www.vesti.ru/vesti.rss");
            return null;
        }

        @Override
        protected void onPostExecute(Void voidParam) {
            if (SELECTED_FILTER.equals("")) {
                mNewsRecyclerView.setAdapter(new NewsAdapter(mRealm.where(NewsItem.class)
                                    .findAll()));
            } else {
                mNewsRecyclerView.setAdapter(new NewsAdapter(mRealm.where(NewsItem.class)
                                    .contains("category", SELECTED_FILTER).findAll()));
            }
            mRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mRealm.close();
    }
}
