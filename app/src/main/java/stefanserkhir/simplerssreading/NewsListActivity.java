package stefanserkhir.simplerssreading;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;


public class NewsListActivity extends AppCompatActivity {
    private static final String TAG = "NewsListActivity";

    private RecyclerView mNewsRecyclerView;
    private SwipeRefreshLayout mRefreshLayout;
    private List<NewsItem> mNewsList = new ArrayList<>();
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

        RealmResults<NewsItem> realmResults = mRealm.where(NewsItem.class).findAll();
        mNewsRecyclerView.setAdapter(new NewsAdapter(mRealm.copyFromRealm(realmResults)));

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
        final String CATEGORY_POLITICS = "Политика";
        final String CATEGORY_SOCIETY = "Общество";
        final String CATEGORY_SPORT = "Спорт";
        final String CATEGORY_IN_THE_WORLD = "В мире";
        final String CATEGORY_INCIDENTS = "Происшествия";

        final String CURRENT_CATEGORY = SELECTED_FILTER;
        switch (item.getItemId()) {
            case R.id.category_politics:
                SELECTED_FILTER = CATEGORY_POLITICS;
                getSupportActionBar().setSubtitle(getString(R.string.politics));
                Toast.makeText(this, getString(R.string.you_selected_categoty,
                                        getString(R.string.politics)), Toast.LENGTH_SHORT).show();
                break;
            case R.id.category_society:
                SELECTED_FILTER = CATEGORY_SOCIETY;
                getSupportActionBar().setSubtitle(getString(R.string.society));
                Toast.makeText(this, getString(R.string.you_selected_categoty,
                                        getString(R.string.society)), Toast.LENGTH_SHORT).show();
                break;
            case R.id.category_sport:
                SELECTED_FILTER = CATEGORY_SPORT;
                getSupportActionBar().setSubtitle(getString(R.string.sport));
                Toast.makeText(this, getString(R.string.you_selected_categoty,
                                        getString(R.string.sport)), Toast.LENGTH_SHORT).show();
                break;
            case R.id.category_in_the_world:
                SELECTED_FILTER = CATEGORY_IN_THE_WORLD;
                getSupportActionBar().setSubtitle(getString(R.string.in_the_world));
                Toast.makeText(this, getString(R.string.you_selected_categoty,
                                        getString(R.string.in_the_world)), Toast.LENGTH_SHORT).show();
                break;
            case R.id.category_incidents:
                SELECTED_FILTER = CATEGORY_INCIDENTS;
                getSupportActionBar().setSubtitle(getString(R.string.incidents));
                Toast.makeText(this, getString(R.string.you_selected_categoty,
                                        getString(R.string.incidents)), Toast.LENGTH_SHORT).show();
                break;
            case R.id.reset_filter:
                mNewsRecyclerView.setAdapter(new NewsAdapter(mNewsList));
                SELECTED_FILTER = "";
                getSupportActionBar().setSubtitle("");
                Toast.makeText(this, getString(R.string.you_reseted_filter),
                                                            Toast.LENGTH_SHORT).show();
                return true;
            default:
                return true;
        }
        if (!SELECTED_FILTER.equals(CURRENT_CATEGORY)) {
            mNewsRecyclerView.setAdapter(new NewsAdapter(setFilterNews(SELECTED_FILTER)));
        }
        return true;
    }

    private List<NewsItem> setFilterNews(String filter) {
        List<NewsItem> filteredNewsList = new ArrayList<>();
        for (NewsItem newsItem : mNewsList) {
            if (newsItem.getCategory().equals(filter)) {
                filteredNewsList.add(newsItem);
            }
        }
        return filteredNewsList;
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
            newsHolder.bindNews(mNewsList.get(position));
        }

        @Override
        public int getItemCount() {
            return mNewsList.size();
        }
    }

    private class FetchNewsTask extends AsyncTask<Void, Void, List<NewsItem>> {

        @Override
        protected List<NewsItem> doInBackground(Void... voids) {
            return new NewsFetcher().fetchNews("https://www.vesti.ru/vesti.rss");
        }

        @Override
        protected void onPostExecute(List<NewsItem> newsItems) {
            RealmResults<NewsItem> realmResults = mRealm.where(NewsItem.class).findAll();
            Log.i(TAG, "Ream size = " + realmResults.size());
            mNewsList = newsItems;
            Log.i(TAG, "ArrayList size = " + mNewsList.size());
            if (SELECTED_FILTER.equals("")) {
                mNewsRecyclerView.setAdapter(new NewsAdapter(mNewsList));
            } else {
                mNewsRecyclerView.setAdapter(new NewsAdapter(setFilterNews(SELECTED_FILTER)));
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
