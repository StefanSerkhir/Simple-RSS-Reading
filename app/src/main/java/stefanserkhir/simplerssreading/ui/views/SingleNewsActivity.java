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
    private Intent mIntent;
    private SingleNewsPresenter mPresenter;

    public static Intent newIntent(Context context, Map<KeyExtra, String> kitExtra) {
        return new Intent(context, SingleNewsActivity.class)
                .putExtra(KeyExtra.TITLE.toString(), kitExtra.get(KeyExtra.TITLE))
                .putExtra(KeyExtra.LINK.toString(), kitExtra.get(KeyExtra.LINK))
                .putExtra(KeyExtra.DATE.toString(), kitExtra.get(KeyExtra.DATE))
                .putExtra(KeyExtra.CATEGORY.toString(), kitExtra.get(KeyExtra.CATEGORY))
                .putExtra(KeyExtra.FULL_TEXT.toString(), kitExtra.get(KeyExtra.FULL_TEXT))
                .putExtra(KeyExtra.IMAGE.toString(), kitExtra.get(KeyExtra.IMAGE));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        mIntent = getIntent();

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
        startActivity(new Intent(
                Intent.ACTION_VIEW, Uri.parse(mIntent.getStringExtra(KeyExtra.LINK.toString()))));
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void updateUI() {
        mNewsTitle.setText(mIntent.getStringExtra(KeyExtra.TITLE.toString()));
        mNewsDate.setText(mIntent.getStringExtra(KeyExtra.DATE.toString()));
        mNewsFullText.setText(mIntent.getStringExtra(KeyExtra.FULL_TEXT.toString()));
        getSupportActionBar().setTitle(mIntent.getStringExtra(KeyExtra.TITLE.toString()));
        Glide.with(this)
                .load(mIntent.getStringExtra(KeyExtra.IMAGE.toString()))
                .thumbnail(Glide.with(this).load(R.drawable.loading_news))
                .into(mNewsImage);
    }

    @Override
    public void openNewScreen(Map<KeyExtra, String> kitExtra) {
        startActivity(new Intent(
                Intent.ACTION_VIEW, Uri.parse(mIntent.getStringExtra(KeyExtra.LINK.toString()))));
    }
}
