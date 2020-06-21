package stefanserkhir.simplerssreading;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

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

        int width = mNewsTitle.getWidth();

        mNewsImage = findViewById(R.id.news_image);
        Picasso.get()
                .load(intent.getStringExtra(KEY_IMAGE))
                .placeholder(R.drawable.placeholder_news)
                .into(mNewsImage);
        mNewsImage.setMinimumHeight((int)(width * 1.7f));
    }
}
