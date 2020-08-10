package stefanserkhir.simplerssreading.ui.views;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.List;

import io.realm.Realm;
import stefanserkhir.simplerssreading.R;
import stefanserkhir.simplerssreading.ui.presenters.NewsListImpl;
import stefanserkhir.simplerssreading.ui.presenters.interfaces.NewsListPresenter;
import stefanserkhir.simplerssreading.ui.views.adapter.NewsAdapter;
import stefanserkhir.simplerssreading.ui.views.helpers.MenuHolder;
import stefanserkhir.simplerssreading.ui.views.interfaces.NewsListView;


public class NewsListActivity extends AppCompatActivity implements NewsListView {

    private RecyclerView mNewsRecyclerView;
    private SwipeRefreshLayout mRefreshLayout;
    private Realm mRealm;
    private NewsListPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);

        Realm.init(this);
        mRealm = Realm.getDefaultInstance();

        mNewsRecyclerView = findViewById(R.id.news_recycler_view);
        mNewsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mRefreshLayout = findViewById(R.id.news_container);
        mRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mRefreshLayout.setOnRefreshListener(() -> mPresenter.onDataRequest());

        mPresenter = new NewsListImpl();
        mPresenter.onAttachView(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuHolder.init(menu);
        mPresenter.onMenuRequest();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        MenuHolder.selectItem(item);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mRealm.close();
    }

    @Override
    public void createMenu(List<String> list) {
        MenuHolder.createMenu(list);
    }

    @Override
    public void applyFilter() {
        // TODO Applying filters
    }

    @Override
    public void updateUI() {
        Log.d("MyFilter", "updateUI");
        mNewsRecyclerView.setAdapter(new NewsAdapter(mPresenter, getLayoutInflater()));
        mRefreshLayout.setRefreshing(false);
    }

    @Override
    public void toggleOn() {
        // TODO Switching on/off Loading bar
    }

    @Override
    public void openNewScreen() {
        // TODO Open Single News Activity
    }

    @Override
    public void showError() {
        // TODO Showing error if was problem
    }
}