package com.syntexerror.crudonmysql.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.syntexerror.crudonmysql.R;
import com.syntexerror.crudonmysql.model.Note;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter <MainAdapter.RecyclerViewAdapter>{


   private List<Note>notes ;
    private  Context context ;
    private  ItemClickListenter itemClickListenter ;


    public MainAdapter(List<Note> notes, Context context, ItemClickListenter itemClickListenter) {
        this.notes = notes;
        this.context = context;
        this.itemClickListenter = itemClickListenter;
    }

    public RecyclerViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_on_note_list, parent, false);
        return new  RecyclerViewAdapter(view  , itemClickListenter);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter holder, int position) {

//TO DO  need to implemenmt wht to do


        Note note = notes.get(position) ;
        holder.titletv.setText(note.getTitle());
        holder.descTv.setText(note.getNote());
        holder.dateTv.setText(note.getDate());



    }

    @Override
    public int getItemCount() {
        return  notes.size();

    }



    public class RecyclerViewAdapter extends  RecyclerView.ViewHolder implements View.OnClickListener  {

        TextView  titletv , descTv  , dateTv ;
        CardView cardView ;

        ItemClickListenter  itemClickListenter ;


        public RecyclerViewAdapter(@NonNull View itemView , ItemClickListenter itemClickListenter) {
            super(itemView);

            this.itemClickListenter = itemClickListenter ;

            titletv =  itemView.findViewById(R.id.title);
            descTv = itemView.findViewById(R.id.note);
            cardView = itemView.findViewById(R.id.card_item) ;
            dateTv = itemView.findViewById(R.id.date);

            this.itemClickListenter = itemClickListenter ;
            cardView.setOnClickListener(this);



        }

        @Override
        public void onClick(View view) {

            itemClickListenter.onItemClick(view, getAdapterPosition());




        }
    }

    public  interface  ItemClickListenter {
        void onItemClick(View view , int pos) ;


    }
}
