package stefanserkhir.simplerssreading.ui.views;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import io.realm.Realm;
import stefanserkhir.simplerssreading.repository.db.model.NewsItem;
import stefanserkhir.simplerssreading.R;
import stefanserkhir.simplerssreading.repository.remote.FetchNewsTask;
import stefanserkhir.simplerssreading.ui.views.adapter.NewsAdapter;


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
        mNewsRecyclerView.setAdapter(new NewsAdapter(mRealm.where(NewsItem.class).findAll(), this));

        mRefreshLayout = findViewById(R.id.news_container);
        mRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mRefreshLayout.setRefreshing(true);
        mRefreshLayout.setOnRefreshListener(() -> new FetchNewsTask(SELECTED_FILTER,
                mNewsRecyclerView, mRealm, mRefreshLayout, this).execute());

        new FetchNewsTask(SELECTED_FILTER, mNewsRecyclerView, mRealm, mRefreshLayout, this).execute();
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
                                .findAll(), this));
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
                                .contains("category", SELECTED_FILTER).findAll(), this));
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mRealm.close();
    }
}
