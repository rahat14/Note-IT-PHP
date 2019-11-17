package com.syntexerror.crudonmysql.activity;

import com.syntexerror.crudonmysql.model.Note;

import java.util.List;

public interface MainView {
    void showLoading() ;
    void hideloading() ;
    void onGetResult(List<Note> notes) ;
    void onErrorLoading(String message) ;




}
