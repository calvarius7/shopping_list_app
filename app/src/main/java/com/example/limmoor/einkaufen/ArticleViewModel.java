package com.example.limmoor.einkaufen;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

public class ArticleViewModel extends AndroidViewModel{

    private ArticleRepository mRepository;

    private LiveData<List<Article>> mAllArticles;

    public ArticleViewModel(Application application){
        super(application);
        mRepository = new ArticleRepository(application);
        mAllArticles = mRepository.getAllArticles();
    }

    public LiveData<List<Article>> getAllArticles() {
        return mAllArticles;
    }

    public void insert(Article article){
        mRepository.insert(article);
    }
    public void delete(Article article){
        mRepository.delete(article);
    }
    public void deleteAll(){
        mRepository.deleteAll();
    }
}
