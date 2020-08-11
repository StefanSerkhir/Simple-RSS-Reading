package stefanserkhir.simplerssreading.ui.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.util.Map;

import stefanserkhir.simplerssreading.core.KeyExtra;
import stefanserkhir.simplerssreading.R;
import stefanserkhir.simplerssreading.ui.views.interfaces.SingleNewsView;

public class SingleNewsActivity extends AppCompatActivity implements SingleNewsView {

    private TextView mNewsTitle;
    private TextView mNewsDate;
    private TextView mNewsFullText;
    private ImageView mNewsImage;

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

        Intent intent = getIntent();
        mNewsTitle = findViewById(R.id.news_title);
        mNewsTitle.setText(intent.getStringExtra(KeyExtra.TITLE.toString()));

        mNewsDate = findViewById(R.id.news_date);
        mNewsDate.setText(intent.getStringExtra(KeyExtra.DATE.toString()));

        mNewsFullText = findViewById(R.id.news_full_text);
        mNewsFullText.setText(intent.getStringExtra(KeyExtra.FULL_TEXT.toString()));
        getSupportActionBar().setTitle(intent.getStringExtra(KeyExtra.TITLE.toString()));

        mNewsImage = findViewById(R.id.news_image);
        Glide.with(this)
                .load(intent.getStringExtra(KeyExtra.IMAGE.toString()))
                .thumbnail(Glide.with(this).load(R.drawable.loading_news))
                .into(mNewsImage);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.news_item, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void goBack() {

    }

    @Override
    public void updateUI() {

    }

    @Override
    public void toggleOn() {

    }

    @Override
    public void openNewScreen(Map<KeyExtra, String> kitExtra) {

    }

    @Override
    public void showError(String message) {

    }
}
