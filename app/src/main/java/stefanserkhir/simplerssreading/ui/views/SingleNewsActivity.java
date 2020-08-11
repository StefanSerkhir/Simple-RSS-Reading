package stefanserkhir.simplerssreading.ui.views;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.util.Map;

import stefanserkhir.simplerssreading.core.KeyExtra;
import stefanserkhir.simplerssreading.R;
import stefanserkhir.simplerssreading.ui.presenters.SingleNewsPresenterImpl;
import stefanserkhir.simplerssreading.ui.presenters.interfaces.SingleNewsPresenter;
import stefanserkhir.simplerssreading.ui.views.interfaces.SingleNewsView;

public class SingleNewsActivity extends AppCompatActivity implements SingleNewsView {
    private TextView mNewsTitle;
    private TextView mNewsDate;
    private TextView mNewsFullText;
    private ImageView mNewsImage;
    private SingleNewsPresenter mPresenter;

    public static Intent newIntent(Context context, Map<KeyExtra, String> kitExtra) {
        SingleNewsPresenterImpl.setKitExtra(kitExtra);
        return new Intent(context, SingleNewsActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        mNewsTitle = findViewById(R.id.news_title);
        mNewsDate = findViewById(R.id.news_date);
        mNewsFullText = findViewById(R.id.news_full_text);
        mNewsImage = findViewById(R.id.news_image);

        mPresenter = new SingleNewsPresenterImpl();
        mPresenter.onAttachView(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.news_item, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(mPresenter.getLink())));
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void updateUI() {
        mNewsTitle.setText(mPresenter.getTitle());
        mNewsDate.setText(mPresenter.getDate());
        mNewsFullText.setText(mPresenter.getFullText());
        getSupportActionBar().setTitle(mPresenter.getTitle());
        getSupportActionBar().setSubtitle(mPresenter.getCategory());
        Glide.with(this)
                .load(mPresenter.getImage())
                .thumbnail(Glide.with(this).load(R.drawable.loading_news))
                .into(mNewsImage);
    }

    @Override
    public void openNewScreen(Map<KeyExtra, String> kitExtra) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(mPresenter.getLink())));
    }
}
