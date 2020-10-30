package com.timkontrakan.kantinkom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {

    private final Context context;
    private final ArrayList<MyCart> myCart;

    public CartAdapter(Context c, ArrayList<MyCart> p) {
        context = c;
        myCart = p;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater
                .from(context)
                .inflate(R.layout.item_cart, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        //Binding Data
        myViewHolder.nama_item.setText(myCart.get(i).getNama_item());
        myViewHolder.harga.setText(myCart.get(i).getHarga());
        myViewHolder.penjual.setText(myCart.get(i).getPenjual());

    }

    @Override
    public int getItemCount() {
        return myCart.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        final TextView nama_item;
        final TextView penjual;
        final TextView harga;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);

            nama_item = itemView.findViewById(R.id.nama_item);
            penjual = itemView.findViewById(R.id.penjual);
            harga = itemView.findViewById(R.id.harga);
        }
    }
}
