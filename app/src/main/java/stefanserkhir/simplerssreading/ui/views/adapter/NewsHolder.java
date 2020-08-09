package stefanserkhir.simplerssreading.ui.views.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import stefanserkhir.simplerssreading.R;
import stefanserkhir.simplerssreading.ui.views.interfaces.RepositoryItemView;

class NewsHolder extends RecyclerView.ViewHolder implements RepositoryItemView, View.OnClickListener {
    private TextView mNewsTitle;
    private TextView mNewsDate;

    public NewsHolder(@NonNull View itemView) {
        super(itemView);

        itemView.setOnClickListener(this);

        mNewsTitle = itemView.findViewById(R.id.news_title);
        mNewsDate = itemView.findViewById(R.id.news_date);
    }

    @Override
    public void onClick(View v) {
       // mActivity.startActivity(SingleNewsActivity.newIntent(mActivity, mNewsItem));
    }

    @Override
    public void setTitle(String title) {
        mNewsTitle.setText(title);
    }

    @Override
    public void setDate(String date) {
        mNewsDate.setText(date);
    }
}
