package com.syntexerror.crudonmysql.activity;

import com.syntexerror.crudonmysql.Api.apiBaseInterface;
import com.syntexerror.crudonmysql.Api.apiCilent;
import com.syntexerror.crudonmysql.model.Note;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainPresenter {

            private  MainView mainView ;
            public  MainPresenter(MainView view ){

                this.mainView = view ;

            }


            void  getData()
            {
                mainView.showLoading();
                // Request to sever
                apiBaseInterface  apiInterface = apiCilent.getApiClient().create(apiBaseInterface.class) ;
                Call<List<Note>> call = apiInterface.getNotes() ;

                call.enqueue(new Callback<List<Note>>() {
                    @Override
                    public void onResponse(Call<List<Note>> call, Response<List<Note>> response) {

                        mainView.hideloading();
                        if (response.isSuccessful()&& response.body() != null)
                        {

                            mainView.onGetResult(response.body());
                        }

                    }

                    @Override
                    public void onFailure(Call<List<Note>> call, Throwable t) {

                        mainView.hideloading();
                        mainView.onErrorLoading(t.getLocalizedMessage());


                    }
                });




            }


}
