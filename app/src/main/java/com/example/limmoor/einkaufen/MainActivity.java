package com.example.limmoor.einkaufen;

import android.app.AlertDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.Currency;
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

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final ArticleListAdapter articleListAdapter = new ArticleListAdapter(this, this::deleteArticle);
        recyclerView.setAdapter(articleListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mArticleViewModel = ViewModelProviders.of(this).get(ArticleViewModel.class);

        mArticleViewModel.getAllArticles().observe(this, articleListAdapter::setArticles);

        mArticleViewModel.getAllArticles().observe(this, this::showTotal);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> goToNewArticleActivity());

    }

    private boolean deleteArticle(Article item) {
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.delete_confirm_head))
                .setMessage(getString(R.string.delete_msg))
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, (dialog, whichButton) -> delete(item))
                .setNegativeButton(android.R.string.no, null).show();
        return true;
    }

    private void delete(Article item) {
        mArticleViewModel.delete(item);
        Toast.makeText(MainActivity.this, "So sad", Toast.LENGTH_SHORT).show();
    }

    private void showTotal(List<Article> articleList) {
        TextView total = findViewById(R.id.sumField);

        double sum = getSum(articleList);

        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.getDefault());

        String stringTotal = getResources().getString(R.string.total) + format.format(sum);
        total.setText(stringTotal);
    }

    private double getSum(List<Article> articleList) {
        double sum = 0.00;
        if (articleList != null && articleList.size() > 0) {
            for (Article item : articleList) {
                sum += item.getPrice();
            }
        }
        return sum;
    }

    private void goToNewArticleActivity() {

        Intent intent = new Intent(getApplicationContext(), AddToShoppingCart.class);
        startActivityForResult(intent, ADDED_TO_CAR_REQUEST_CODE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADDED_TO_CAR_REQUEST_CODE && resultCode == RESULT_OK) {
            addArticle(data);
        } else {
            showError();
        }
    }

    private void showError() {
        Toast.makeText(getApplicationContext(), "LOL! Leider zu dummm um zwei Werte richtig einzugegeben", Toast.LENGTH_SHORT).show();
    }

    private void addArticle(Intent data) {
        String name = data.getStringExtra(AddToShoppingCart.EXTRA_REPLY_ARTICLE);
        Double price = data.getDoubleExtra(AddToShoppingCart.EXTRA_REPLY_PRICE, 0.00);
        Article article = new Article(0, name, price);
        mArticleViewModel.insert(article);
    }

    public void deleteAll(MenuItem item) {
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.delete_confirm_head))
                .setMessage(getString(R.string.delete_msg_all))
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, (dialog, whichButton) -> mArticleViewModel.deleteAll())
                .setNegativeButton(android.R.string.no, null).show();

    }
}
