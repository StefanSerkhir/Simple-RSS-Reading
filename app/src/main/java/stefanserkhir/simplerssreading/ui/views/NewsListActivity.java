package stefanserkhir.simplerssreading.ui.views;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.List;

import io.realm.Realm;
import stefanserkhir.simplerssreading.data.db.model.NewsItem;
import stefanserkhir.simplerssreading.R;
import stefanserkhir.simplerssreading.ui.presenters.NewsListImpl;
import stefanserkhir.simplerssreading.ui.presenters.interfaces.NewsListPresenter;
import stefanserkhir.simplerssreading.ui.views.adapter.NewsAdapter;
import stefanserkhir.simplerssreading.ui.views.interfaces.NewsListView;


public class NewsListActivity extends AppCompatActivity implements NewsListView {

    private RecyclerView mNewsRecyclerView;
    private SwipeRefreshLayout mRefreshLayout;
    private Realm mRealm;
    private NewsListPresenter mPresenter;
    private List<String> mSetOfCategories;
    private Menu mMenu;

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

        mPresenter = new NewsListImpl().onAttachView(this);
        mPresenter.onDataRequest();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        mMenu = menu;
        return true;
    }

    MenuItem selectedItem;
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (!item.getTitle().toString().equals(getString(R.string.filter_news))) {
            Log.d("MyFilter", "Item title -> " + item.getTitle().toString());

            if (selectedItem != null) {
                selectedItem.setEnabled(true);
            }

            if (mSetOfCategories.contains(item.getTitle().toString())) {
                mPresenter.onSelectingFilters(item.getTitle().toString());
                item.setEnabled(false);
                selectedItem = item;
            } else {
                mPresenter.onDataRequest();
            }
        }

        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mRealm.close();
    }

    @Override
    public void createMenu(List<String> list) {
        if (mMenu.hasVisibleItems()) {
            mMenu.removeItem(0);
        }

        mMenu.addSubMenu(R.string.filter_news).setIcon(R.drawable.ic_filter);
        mMenu.getItem(0).setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        for (int i = 0; i < list.size(); i++) {
            mMenu.getItem(0).getSubMenu().add(0, i, i, list.get(i)); // add(groupId, itemId, order, title)
        }
        mMenu.getItem(0).getSubMenu().add(1, 0, list.size(), R.string.reset_filter);
        mMenu.getItem(0).getSubMenu().getItem(list.size()).setIcon(R.drawable.ic_reset_filter);

        MenuCompat.setGroupDividerEnabled(mMenu, true);

        mSetOfCategories = list;
    }

    @Override
    public void applyFilter() {
        // TODO Applying filters
    }

    @Override
    public void updateUI(String how) {
        // TODO Loading News List

        if (how.equals("")) {
            mNewsRecyclerView.setAdapter(new NewsAdapter(mRealm.where(NewsItem.class)
                    .findAll(), this));
        } else {
            mNewsRecyclerView.setAdapter(new NewsAdapter(mRealm.where(NewsItem.class)
                    .contains("category", how).findAll(), this));
        }
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