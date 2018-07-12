package com.example.limmoor.einkaufen;

import org.junit.Before;
import org.junit.Test;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

public class ShoppingCartTotalTest {

    private ShoppingCartTotal shoppingCartTotal;
    private Article article;
    private List<Article> articles;
    private final NumberFormat format = NumberFormat.getCurrencyInstance(Locale.getDefault());

    @Before
    public void setUp() {
        articles = new ArrayList<>();
        article = new Article(0, "Name", 10.00);
        articles.add(article);
    }

    @Test
    public void oneArticleTotal(){
        shoppingCartTotal = new ShoppingCartTotal(articles);
        String total = format.format(10.00);
        String result = shoppingCartTotal.getTotalAsCurrency();

        assertEquals(total,result );
    }
    @Test
    public void twoArticlesTotal(){
        articles.add(article);
        shoppingCartTotal = new ShoppingCartTotal(articles);
        String total = format.format(20.00);
        String result = shoppingCartTotal.getTotalAsCurrency();

        assertEquals(total,result );
    }

    @Test
    public void zeroArticlesTotal(){
        articles.clear();
        shoppingCartTotal = new ShoppingCartTotal(articles);
        String total = format.format(0.00);
        String result = shoppingCartTotal.getTotalAsCurrency();

        assertEquals(total,result );
    }
    @Test
    public void nullArticlesTotal(){
        shoppingCartTotal = new ShoppingCartTotal(null);
        String total = format.format(0.00);
        String result = shoppingCartTotal.getTotalAsCurrency();

        assertEquals(total,result );
    }
}