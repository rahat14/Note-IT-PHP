package com.syntexerror.crudonmysql.Api;

import com.syntexerror.crudonmysql.model.Note;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface apiBaseInterface {


    @FormUrlEncoded
    @POST("save.php")
    Call<Note> saveNote(
            @Field("title") String title,
            @Field("note") String note,
            @Field("color") int color

    ) ;


    @GET("getnotes.php")
    Call<List<Note>>getNotes() ;

    @FormUrlEncoded
    @POST("update.php")
    Call<Note> updateNote(
            @Field("id") int id,
            @Field("title") String title,
            @Field("note") String note,
            @Field("color") int color

    ) ;



    @FormUrlEncoded
    @POST("deleteNotes.php")
    Call<Note> deleteNotes(@Field("id") int id) ;





}
