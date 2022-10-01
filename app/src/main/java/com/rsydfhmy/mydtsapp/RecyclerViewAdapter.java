package com.rsydfhmy.mydtsapp;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.Holder> {


private Context context;
    private List<Konfigurasi> daftarMhs;

    // create object for Onitemclick interface
    private OnItemClickListener mListerner;



    public RecyclerViewAdapter(Context context, List<Konfigurasi> daftarMhs) {
        this.context = context;
        this.daftarMhs = daftarMhs;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_data,parent,false);
        return new Holder(view, mListerner);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {

        holder.nama.setText(daftarMhs.get(position).getNama());
//        holder.nrp.setText(daftarMhs.get(position).getNrp());
        holder.jenisKelamin.setText( daftarMhs.get(position).getJenisKelamin());
        holder.alamat.setText( daftarMhs.get(position).getAlamat() );
        holder.noHp.setText( daftarMhs.get(position).getNoHp());
        holder.lokasi.setText( daftarMhs.get(position).getLokasi());

        // now process image get from database & set it in imageview throw model class
        byte[] fotoArray = daftarMhs.get(position).getFoto();
        Bitmap bitmap = BitmapFactory.decodeByteArray(fotoArray,0,fotoArray.length);
        holder.foto.setImageBitmap(bitmap);

    }

    @Override
    public int getItemCount() {

        if (daftarMhs == null) {
            return 0;
        }
        return daftarMhs.size();

    }

    class Holder extends RecyclerView.ViewHolder{

        TextView nama, jenisKelamin ,alamat ,noHp ,lokasi;
        ImageView foto;

        Holder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            nama = itemView.findViewById(R.id.xnama);
            jenisKelamin = itemView.findViewById(R.id.xjk);
            alamat = itemView.findViewById( R.id.xalamat );
            noHp =itemView.findViewById( R.id.xnohp );
            lokasi =itemView.findViewById( R.id.xloc );
            foto = itemView.findViewById(R.id.xfoto);

            // add click on itemview
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (listener!= null)
                    {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION)
                        {
                            listener.onItemClick(position);
                        }
                    }
                }
            });

        }
    }

    //interface
    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    // this will call in activity
    public void setOnItemClickListener(OnItemClickListener listener)
    {
        mListerner = listener;
    }

}