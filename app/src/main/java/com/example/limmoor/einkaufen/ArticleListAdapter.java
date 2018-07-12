package com.example.limmoor.einkaufen;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ArticleListAdapter extends RecyclerView.Adapter<ArticleListAdapter.ArticleViewHolder> {

    class ArticleViewHolder extends RecyclerView.ViewHolder {
        private final TextView articleItemView;

        ArticleViewHolder(View itemView) {
            super(itemView);
            articleItemView = itemView.findViewById(R.id.textView);
        }

        void bind(final Article item, final OnItemLongClickListener listener) {
            articleItemView.setText(item.getName());
            itemView.setOnLongClickListener(view -> listener.onItemLongClick(item));
        }
    }

    public interface OnItemLongClickListener {
        boolean onItemLongClick(Article item);
    }

    private final LayoutInflater mInflater;
    private List<Article> mArticles;
    private final OnItemLongClickListener clickListener;

    ArticleListAdapter(Context context, OnItemLongClickListener listener) {
        mInflater = LayoutInflater.from(context);
        this.clickListener = listener;
    }

    @Override
    public ArticleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new ArticleViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ArticleViewHolder holder, int position) {
        Article current = mArticles.get(position);
        holder.articleItemView.setText(current.getName());
        holder.bind(current, clickListener);
    }

    void setArticles(List<Article> words) {
        mArticles = words;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return (mArticles != null) ? mArticles.size() : 0;
    }
}
