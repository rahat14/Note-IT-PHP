package com.syntexerror.crudonmysql.Api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.syntexerror.crudonmysql.constents;

import retrofit2.Retrofit;
import retrofit2.Retrofit.Builder;
import retrofit2.converter.gson.GsonConverterFactory;

public class apiCilent {

     ;
    public  static Retrofit retrofit ;

    public  static  Retrofit getApiClient(){

        if (retrofit == null )
        {



            retrofit = new Retrofit.Builder()
                    .baseUrl(constents.BASE_URL )

                    .addConverterFactory(GsonConverterFactory
                            .create())
                    .build();
        }

        return  retrofit ;

    }



}
