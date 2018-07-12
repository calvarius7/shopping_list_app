package com.example.limmoor.einkaufen;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static junit.framework.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class ArticleDaoTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private ArticleDao mArticleDao;
    private ArticleDatabase mDb;

    @Before
    public void setUp() {
        Context context = InstrumentationRegistry.getTargetContext();

        mDb = Room.inMemoryDatabaseBuilder(context, ArticleDatabase.class).allowMainThreadQueries().build();
        mArticleDao = mDb.articleDao();
    }

    @After
    public void closeDB(){
        mDb.close();
    }

    @Test
    public void insertAndGetWord() throws InterruptedException {
        Article article = new Article(0, "Article", 0.00);

        mArticleDao.insert(article);

        List<Article> allWords = LiveDataTestUtil.getValue(mArticleDao.getAllArticles());

        assertEquals(allWords.get(0).getName(), article.getName());
        assertEquals(allWords.get(0).getPrice(), article.getPrice());
    }

    @Test
    public void insertAndDelete() throws InterruptedException {
        Article article = new Article(0, "Article", 0.00);

        mArticleDao.insert(article);
        List<Article> allWords = LiveDataTestUtil.getValue(mArticleDao.getAllArticles());

        mArticleDao.delete(allWords.get(0));

        allWords = LiveDataTestUtil.getValue(mArticleDao.getAllArticles());

        assertEquals(0, allWords.size());
    }

    @Test
    public void deleteAll() throws InterruptedException {
        Article article = new Article(0, "Article", 0.00);

        mArticleDao.insert(article);
        mArticleDao.deleteAll();
        List<Article> allWords = LiveDataTestUtil.getValue(mArticleDao.getAllArticles());

        assertEquals(0, allWords.size());
    }

    @Test
    public void deleteAllEmpty() throws InterruptedException {

        mArticleDao.deleteAll();
        List<Article> allWords = LiveDataTestUtil.getValue(mArticleDao.getAllArticles());

        assertEquals(0, allWords.size());
    }
}