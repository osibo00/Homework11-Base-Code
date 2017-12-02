package com.example.rusili.homework11.controller;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.rusili.homework11.R;

import com.example.rusili.homework11.detailscreen.model.Pokemon;
import com.example.rusili.homework11.detailscreen.view.PokemonDetailActivity;
import com.example.rusili.homework11.network.RetrofitFactory;
import com.example.rusili.homework11.pokedexActivity.model.Pokedex;

import com.example.rusili.homework11.pokedexActivity.model.objects.PokemonEntries;
import com.example.rusili.homework11.recyclerviewscreen.model.PokemonSet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by oleg on 11/29/17.
 */

public class PokedexAdapter extends RecyclerView.Adapter<PokedexAdapter.PokedexViewHolder> {


    private PokemonSet pokemonSet;

    private ArrayList<PokemonSet> sets;
    private Context context;

    public PokedexAdapter(Context context) {
        this.context = context;
        sets = new ArrayList<>();
    }


    @Override
    public PokedexViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pokedex_itemview, parent, false);
        return new PokedexViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final PokedexViewHolder holder, int position) {
        pokemonSet = sets.get(position);
        holder.pokedexText.setText(pokemonSet.getName());

        //Onclick listener
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(view.getContext(), holder.email.getText(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(view.getContext(), PokemonDetailActivity.class);
                intent.putExtra("pokemon", holder.pokedexText.getText());
                intent.putExtra("url", "http://pokeapi.co/media/sprites/pokemon/" + pokemonSet.getNumber() + ".png");
                view.getContext().startActivity(intent);

            }
        });

        Glide.with(context)
                .load("http://pokeapi.co/media/sprites/pokemon/" + pokemonSet.getNumber() + ".png")
                .into(holder.pokedexView);


    }

    @Override
    public int getItemCount() {
        return sets.size();
    }

    public void listPokemonSet(ArrayList<PokemonSet> pokemonSets) {
        sets.addAll(pokemonSets);
        notifyDataSetChanged();
    }

    public class PokedexViewHolder extends RecyclerView.ViewHolder {

        ImageView pokedexView;
        TextView pokedexText;
        CardView layout;

        public PokedexViewHolder(View itemView) {
            super(itemView);

            pokedexView = (ImageView) itemView.findViewById(R.id.pokedex_imageview);
            pokedexText = (TextView) itemView.findViewById(R.id.pokedex_textview);
            layout = (CardView) itemView.findViewById(R.id.idLayout);
        }
    }
}
