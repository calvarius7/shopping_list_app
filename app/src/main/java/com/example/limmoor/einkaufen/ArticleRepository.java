package com.example.limmoor.einkaufen;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class ArticleRepository {

    private ArticleDao mArticleDao;
    private LiveData<List<Article>> mAllArticles;

    ArticleRepository(Application application){
        ArticleDatabase db = ArticleDatabase.getDatabase(application);
        mArticleDao = db.articleDao();
        mAllArticles = mArticleDao.getAllArticles();
    }

    public LiveData<List<Article>> getAllArticles() {
        return mAllArticles;
    }

    public void insert(Article article){
        new insertAsyncTask(mArticleDao).execute(article);
    }

    public void delete(Article article){
        new deleteAsyncTask(mArticleDao).execute(article);
    }

    public void deleteAll(){
        new deleteAllAsyncTask(mArticleDao).execute();
    }
    private static class insertAsyncTask extends AsyncTask<Article, Void, Void> {
        private ArticleDao mAsyncTaskDao;

        insertAsyncTask(ArticleDao mArticleDao) {
            mAsyncTaskDao = mArticleDao;
        }

        @Override
        protected Void doInBackground(Article... articles) {
            mAsyncTaskDao.insert(articles[0]);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<Article, Void, Void> {
        private ArticleDao mAsyncTaskDao;

        deleteAsyncTask(ArticleDao mArticleDao) {
            mAsyncTaskDao = mArticleDao;
        }

        @Override
        protected Void doInBackground(Article... articles) {
            mAsyncTaskDao.delete(articles[0]);
            return null;
        }
    }

    private static class deleteAllAsyncTask extends AsyncTask<Void, Void, Void> {
        private ArticleDao mAsyncTaskDao;

        deleteAllAsyncTask(ArticleDao mArticleDao) {
            mAsyncTaskDao = mArticleDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }
}
