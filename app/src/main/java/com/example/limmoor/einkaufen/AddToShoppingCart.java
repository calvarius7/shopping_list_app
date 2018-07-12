package com.example.limmoor.einkaufen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

public class AddToShoppingCart extends AppCompatActivity {

    public static final String EXTRA_REPLY_ARTICLE = "com.example.limmoor.einkaufen.REPLY_ARTICLE";
    public static final String EXTRA_REPLY_PRICE = "com.example.limmoor.einkaufen.REPLY_PRICE";

    private EditText mEditWordView;
    private EditText mEditDescriptionView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_new_article);
        mEditWordView = findViewById(R.id.edit_article_name);
        mEditDescriptionView = findViewById(R.id.edit_price);

        final Button button = findViewById(R.id.button_save);
        button.setOnClickListener(view -> {
            Intent replyIntent = new Intent();
            if (TextUtils.isEmpty(mEditWordView.getText())) {
                setResult(RESULT_CANCELED, replyIntent);
            } else {
                userInputToIntent(replyIntent);
                setResult(RESULT_OK, replyIntent);
            }
            finish();
        });
    }
    private void userInputToIntent(Intent replyIntent) {
        String article = mEditWordView.getText().toString();
        Double price;
        try {
            price = Double.valueOf(mEditDescriptionView.getText().toString());
        } catch (NumberFormatException e) {
            price = 0.00;
        }
        replyIntent.putExtra(EXTRA_REPLY_ARTICLE, article);
        replyIntent.putExtra(EXTRA_REPLY_PRICE,price);
    }
}
