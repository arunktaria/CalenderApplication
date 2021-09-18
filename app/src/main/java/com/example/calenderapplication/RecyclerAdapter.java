package com.example.calenderapplication;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolderClss> {
List<CalenderData> list;
CalenderData calenderData;
Context context;
DaoInterface database;
    public RecyclerAdapter(List<CalenderData> list, Context context) {
        this.context=context;
        this.list = list;
        database=RoomDataBase.instance.getDao();
    }

    @NonNull
    @Override
    public RecyclerAdapter.ViewHolderClss onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
     View view= LayoutInflater.from(context).inflate(R.layout.itemlist,parent,false);
     return new ViewHolderClss(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.ViewHolderClss holder, int position) {
        calenderData=list.get(position);
        holder.title.setText(calenderData.getEventName());
        holder.des.setText(calenderData.getDes());
        holder.date.setText(calenderData.getDate());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public class ViewHolderClss extends RecyclerView.ViewHolder {
     TextView title;
     TextView des;
     TextView date;
        public ViewHolderClss(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.itemtitle);
            des=itemView.findViewById(R.id.itemdes);
            date=itemView.findViewById(R.id.itemdate);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    AlertDialog.Builder alertDialog=new AlertDialog.Builder(context);
                    alertDialog.setTitle("Delete!");
                   alertDialog.setMessage("Are you sure?");
                    alertDialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                       new Thread(new Runnable() {
                           @Override
                           public void run() {
                               CalenderData calenderData=list.get(getAdapterPosition());
                               database.deleteItem(calenderData);
                           }
                       })    .start();
                        }
                    });
                    alertDialog.setNegativeButton("cancle", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(context, "canceled!", Toast.LENGTH_SHORT).show();
                        }
                    });
                    alertDialog.create();
                    alertDialog.show();

                    return false;
                }
            });





        }
    }


}
