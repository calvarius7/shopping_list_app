package com.example.limmoor.einkaufen;

import android.app.AlertDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private ArticleViewModel mArticleViewModel;
    static final int ADDED_TO_CAR_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initArticleList();
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> goToNewArticleActivity());
    }

    private void initArticleList() {
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final ArticleListAdapter articleListAdapter = new ArticleListAdapter(this, this::onClickDeleteArticle);
        recyclerView.setAdapter(articleListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mArticleViewModel = ViewModelProviders.of(this).get(ArticleViewModel.class);

        mArticleViewModel.getAllArticles().observe(this, articleListAdapter::setArticles);

        mArticleViewModel.getAllArticles().observe(this, this::showTotal);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private boolean onClickDeleteArticle(Article item) {
        AlertDialog.Builder builder = getDialogBuilder(R.string.delete_msg);
        builder.setPositiveButton(android.R.string.yes, (dialog, whichButton) -> delete(item)).show();
        return true;
    }

    public void onClickDeleteAll(MenuItem item) {
        AlertDialog.Builder builder = getDialogBuilder(R.string.delete_msg_all);
        builder.setPositiveButton(android.R.string.yes, (dialog, whichButton) -> deleteAll()).show();
    }

    private AlertDialog.Builder getDialogBuilder(int deleteMsg) {
        return new AlertDialog.Builder(this)
                .setTitle(getString(R.string.delete_confirm_head))
                .setMessage(getString(deleteMsg))
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setNegativeButton(android.R.string.no, null);
    }

    private void delete(Article item) {
        mArticleViewModel.delete(item);
        Toast.makeText(MainActivity.this, "So sad", Toast.LENGTH_SHORT).show();
    }

    private void deleteAll() {
        mArticleViewModel.deleteAll();
        Toast.makeText(MainActivity.this, "Time to say goodbye", Toast.LENGTH_SHORT).show();

    }

    private void showTotal(List<Article> articleList) {
        TextView totalView = findViewById(R.id.sumField);
        String stringTotal = new ShoppingCartTotal(articleList).getTotalAsCurrency();
        String total = getResources().getString(R.string.total) + " " + stringTotal;
        totalView.setText(total);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADDED_TO_CAR_REQUEST_CODE && resultCode == RESULT_OK) {
            addArticle(data);
        } else {
            showError();
        }
    }

    private void addArticle(Intent data) {
        String name = data.getStringExtra(AddToShoppingCart.EXTRA_REPLY_ARTICLE);
        Double price = data.getDoubleExtra(AddToShoppingCart.EXTRA_REPLY_PRICE, 0.00);
        Article article = new Article(0, name, price);

        mArticleViewModel.insert(article);
    }

    private void showError() {
        Toast.makeText(getApplicationContext(), "LOL! Leider zu dummm um zwei Werte richtig einzugegeben", Toast.LENGTH_SHORT).show();
    }


    private void goToNewArticleActivity() {
        Intent intent = new Intent(getApplicationContext(), AddToShoppingCart.class);
        startActivityForResult(intent, ADDED_TO_CAR_REQUEST_CODE);
    }
}
