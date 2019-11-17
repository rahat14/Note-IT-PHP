package com.syntexerror.crudonmysql.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.syntexerror.crudonmysql.Api.apiBaseInterface;
import com.syntexerror.crudonmysql.Api.apiCilent;
import com.syntexerror.crudonmysql.R;
import com.syntexerror.crudonmysql.model.Note;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditorActivity extends AppCompatActivity {

    EditText titleEdit , descEdit ;
    Button submitBtn , DelteBTn ;
    String title , desc ;
    ProgressDialog  progressDialog ;
    apiBaseInterface apiInterface ;
    String   intitle , indesc ;


        int id  , color  ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // init view
        titleEdit = findViewById(R.id.titleEditText);
        descEdit = findViewById(R.id.descEditText);
        submitBtn = findViewById(R.id.submitEditText);
        DelteBTn = findViewById(R.id.deleteBtn);


        Intent intent = getIntent();

        id= intent.getIntExtra("ID", 0);

        color = intent.getIntExtra("COLOR", 0);
        title = intent.getStringExtra("TITLE") ;
        desc = intent.getStringExtra("NOTE");


            setDataFromIntent() ;


            Toast.makeText(getApplicationContext() , "id "+ id  , Toast.LENGTH_SHORT)
                    .show();

        //init the progress dialouge

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading.......");


        // listening to the button

        DelteBTn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



              apiInterface = apiCilent.getApiClient().create(apiBaseInterface.class);
                Call<Note>call = apiInterface.deleteNotes(id) ;
                call.enqueue(new Callback<Note>() {
                    @Override
                    public void onResponse(Call<Note> call, Response<Note> response) {

                        if (response.isSuccessful() && response.body() != null) {
                            Boolean success = response.body().isSuccess();

                            if (success) {
                                Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT)
                                        .show();

                                setResult(RESULT_OK);
                                finish();

                            } else {
                                Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT)
                                        .show();

                            }
                        }



                    }

                    @Override
                    public void onFailure(Call<Note> call, Throwable t) {

                        Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT)
                                .show();
                    }
                });



            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                 intitle = titleEdit.getText().toString();
                 indesc = descEdit.getText().toString() ;

                //TO DO  upload the  data to  mysql server

                if (id!=0){



                    updateData(intitle , indesc ,id , color);



                }
                else {
                    saveDataToOnlineDb(intitle , indesc);
                }



            }
        });




    }

    private void updateData(String intitle, String indesc, int id, int color) {

        progressDialog.show();

        apiInterface = apiCilent.getApiClient().create(apiBaseInterface.class) ;

        Call<Note>call = apiInterface.updateNote(id, intitle , indesc, color) ;
        call.enqueue(new Callback<Note>() {
            @Override
            public void onResponse( @NonNull  Call<Note> call, @NonNull Response<Note> response) {
                progressDialog.dismiss();

                if (response.isSuccessful() && response.body() != null) {
                    Boolean success = response.body().isSuccess();

                    if (success) {
                        Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT)
                                .show();

                       setResult(RESULT_OK);
                        finish();

                    } else {
                        Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT)
                                .show();

                    }


                }
            }

            @Override
            public void onFailure( @NonNull Call<Note> call, @NonNull Throwable t) {

                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), t.getLocalizedMessage() , Toast.LENGTH_SHORT)
                        .show();

            }
        });



    }

    private void setDataFromIntent() {

            if(id!= 0){

                descEdit.setText(desc);
                titleEdit.setText(title);
                getSupportActionBar().setTitle("Update Note");

                editMode();


            }
            else {

                    submitBtn.setText("Update Now");

                editMode();

            }


    }

    private void readMode() {
        titleEdit.setFocusableInTouchMode(false);
        descEdit.setFocusableInTouchMode(false);
        titleEdit.setFocusable(false);
        descEdit.setFocusable(false);



    }

    private void editMode() {
        titleEdit.setFocusableInTouchMode(true);
        descEdit.setFocusableInTouchMode(true);

    }

    private void saveDataToOnlineDb(final  String title,final String desc) {

        progressDialog.show();

        apiInterface = apiCilent.getApiClient().create(apiBaseInterface.class) ;


        int a = 122 ;

        Call<Note> call = apiInterface.saveNote(title , desc , a) ;

        call.enqueue(new Callback<Note>() {
            @Override

            public void onResponse(Call<Note>  call, Response<Note> response) {
                progressDialog.dismiss();

                if(response.isSuccessful() && response.body() != null)
                {
                    Boolean success = response.body().isSuccess() ;

                    if (success)
                    {
                        Toast.makeText(getApplicationContext() , response.body().getMessage(), Toast.LENGTH_SHORT)
                                .show();

                        setResult(RESULT_OK);
                        finish();


                    }
                    else {
                        Toast.makeText(getApplicationContext() , response.body().getMessage(), Toast.LENGTH_SHORT)
                                .show();

                    }


                }

            }

            @Override
            public void onFailure(@NonNull Call<Note> call,@NonNull Throwable t) {
                progressDialog.dismiss();

                Toast.makeText(getApplicationContext() , t.getLocalizedMessage() ,  Toast.LENGTH_SHORT)
                        .show();


            }
        });


    }
}
