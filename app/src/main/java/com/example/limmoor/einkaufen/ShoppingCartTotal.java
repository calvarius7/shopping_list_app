package com.example.limmoor.einkaufen;

import android.support.annotation.NonNull;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class ShoppingCartTotal {

    private List<Article> articles;
    private double total;

    ShoppingCartTotal(List<Article> articles) {
        this.articles = articles;
    }

    @NonNull
    String getTotalAsCurrency() {
        getSum();

        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.getDefault());

        return format.format(total);
    }

    private void getSum() {
        double sum = 0.00;
        if (articles != null && articles.size() > 0) {
            for (Article item : articles) {
                sum += item.getPrice();
            }
        }
        total = sum;
    }
}
