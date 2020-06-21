package stefanserkhir.simplerssreading;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class NewsActivity extends AppCompatActivity {
    private static final String KEY_TITLE = "newsTitle";
    private static final String KEY_CATEGORY = "newsCategory";
    private static final String KEY_DATE = "newsDate";
    private static final String KEY_IMAGE = "newsImage";
    private static final String KEY_FULL_TEXT = "newsFullText";

    private TextView mNewsTitle;
    private TextView mNewsDate;
    private TextView mNewsFullText;
    private ImageView mNewsImage;

    public static Intent newIntent(Context context, NewsItem newsItem) {
        return new Intent(context, NewsActivity.class)
                .putExtra(KEY_TITLE, newsItem.getTitle())
                .putExtra(KEY_CATEGORY, newsItem.getCategory())
                .putExtra(KEY_DATE, newsItem.getDate())
                .putExtra(KEY_IMAGE, newsItem.getImage())
                .putExtra(KEY_FULL_TEXT, newsItem.getFullText());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        Intent intent = getIntent();
        mNewsTitle = findViewById(R.id.news_title);
        mNewsTitle.setText(intent.getStringExtra(KEY_TITLE));

        mNewsDate = findViewById(R.id.news_date);
        mNewsDate.setText(intent.getStringExtra(KEY_DATE));

        mNewsFullText = findViewById(R.id.news_full_text);
        mNewsFullText.setText(intent.getStringExtra(KEY_FULL_TEXT));

        mNewsImage = findViewById(R.id.news_image);
        Glide.with(this)
                .load(intent.getStringExtra(KEY_IMAGE))
                .thumbnail(Glide.with(this).load(R.drawable.loading_news))
                .crossFade()
                .into(mNewsImage);
    }
}
