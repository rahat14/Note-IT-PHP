package com.syntexerror.crudonmysql.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.syntexerror.crudonmysql.R;
import com.syntexerror.crudonmysql.adapter.MainAdapter;
import com.syntexerror.crudonmysql.model.Note;

import java.util.List;

public class noteList extends AppCompatActivity implements MainView  {

    private static final int INTENT_EDIT = 200;
    private static final int INTENT_ADD =100 ;
    SwipeRefreshLayout swipeRefreshLayout ;
    RecyclerView recyclerView ;
CardView cardView  ;

MainPresenter presenter  ;
MainAdapter adapter ;
MainAdapter.ItemClickListenter itemClickListenter ;
List<Note> note ;




    FloatingActionButton fabButton ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);


        fabButton = findViewById(R.id.fabButton);
        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        swipeRefreshLayout  = findViewById(R.id.swipeRefresh);







        fabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                Intent  intent = new Intent(getApplicationContext() , EditorActivity.class);
                startActivityForResult(intent , INTENT_ADD) ;  

            }
        });


        presenter = new MainPresenter(this ) ;

        presenter.getData();

        swipeRefreshLayout.setOnRefreshListener(

                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        presenter.getData();
                    }
                }


        );


        itemClickListenter  = new MainAdapter.ItemClickListenter() {
            @Override
            public void onItemClick(View view, final  int pos) {

                // onclick function
                String title = note.get(pos).getTitle();
                 String desc = note.get(pos).getNote() ;
                 int id = note.get(pos).getId() ;
                 int color = note.get(pos).getId() ;


                 Intent intent = new Intent(getApplicationContext() , EditorActivity.class);
                 intent.putExtra("ID", id) ;
                 intent.putExtra("TITLE" , title) ;
                 intent.putExtra("NOTE", desc );
                 intent.putExtra("COLOR", color);
                 startActivityForResult(intent , INTENT_EDIT);







            }
        } ;

    }

    @Override
    public void showLoading() {

        swipeRefreshLayout.setRefreshing(true);

    }

    @Override
    public void hideloading() {

        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onGetResult(List<Note> notes) {

        adapter = new MainAdapter(notes, this, itemClickListenter);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter) ;
        note = notes;



    }

    @Override
    public void onErrorLoading(String message) {

        Toast.makeText(this , message , Toast.LENGTH_SHORT)
                .show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == INTENT_ADD && resultCode == RESULT_OK)
        {
            presenter.getData(); // reload the data
        }
        else if (requestCode == INTENT_EDIT && resultCode == RESULT_OK)
        {
            presenter.getData(); // reload the data

        }
        else {


        }



    }
}
