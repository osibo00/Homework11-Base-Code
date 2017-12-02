package com.example.rusili.homework11.detailscreen.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.rusili.homework11.R;
import com.example.rusili.homework11.detailscreen.model.Pokemon;
import com.example.rusili.homework11.detailscreen.model.objects.Stat;
import com.example.rusili.homework11.detailscreen.model.objects.Stats;
import com.example.rusili.homework11.detailscreen.model.objects.Type;
import com.example.rusili.homework11.detailscreen.model.objects.Types;
import com.example.rusili.homework11.network.RetrofitFactory;

import java.util.ArrayList;

public class PokemonDetailActivity extends AppCompatActivity {
    private RetrofitFactory.PokemonNetworkListener pokemonNetworkListener;

    private String id;
    TextView namePokemon;
    TextView statsTextView;
    TextView typeTextView;
    TextView averageTextView;

    Intent intent;

    String url;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_layout);
        //intent = getIntent();
        intent= getIntent();

        id = intent.getStringExtra("pokemon");


        url= intent.getStringExtra("url");



        namePokemon = (TextView) findViewById(R.id.nameTextView);
        namePokemon.setText(id);
        statsTextView = (TextView) findViewById(R.id.statsTextView);
        typeTextView = (TextView) findViewById(R.id.typeTextView);
        averageTextView = (TextView) findViewById(R.id.averageTextView);

        initialize();
    }

    private void initialize() {
        getPokemonDetails();
    }

    private void getPokemonDetails() {
        pokemonNetworkListener = new RetrofitFactory.PokemonNetworkListener() {
            @Override
            public void pokemonCallback(Pokemon pokemon) {

//                Glide.with(getApplicationContext())
//                        .load(url)
//                        .apply(new RequestOptions().override(600, 200))
//                        .into((ImageView) findViewById(R.id.pokemonimage_ImageView));






                Glide.with(getApplicationContext())
                        .load(pokemon.getSprites().getFront_default())
                        .into((ImageView) findViewById(R.id.pokemonimage_ImageView));

                Stats[] stats = pokemon.getStats();
                Types[] types = pokemon.getTypes();
                String st = "";
                String typ = "";
                int average = 0;

                for (int i = 0; i < stats.length; i++) {

                    st += stats[i].getStat().getName() + " : " + stats[i].getBase_stat() + "\n";

                    average += stats[i].getBase_stat();

                    System.out.println("Number= " + i + "  " + stats[i].getStat().getName() + " : " + stats[i].getBase_stat());
                    System.out.println("Number= " + i + "  " + stats[i].getStat().getUrl());
                }

                for (int i = 0; i < types.length; i++) {

                    typ += types[i].getType().getName() + "\n";
                    System.out.println("Number= " + i + "  " + types[i].getType().getName());

                }

                int total = average / 6;
                //statsTextView.setText(stats[0].getStat().getName()+" : "+stats[0].getBase_stat());
                statsTextView.setText(st);
                typeTextView.setText(typ);

                averageTextView.setText(total + "%");

                //System.out.println("==========="+);
                //stats[0].getStat();

                ProgressBar simpleProgressBar = (ProgressBar) findViewById(R.id.simpleProgressBar); // initiate the progress bar
                simpleProgressBar.setMax(100); // 100 maximum value for the progress value
                simpleProgressBar.setProgress(average / 6); // 50 default progress value for the progress bar

            }
        };
        RetrofitFactory.getInstance().setPokemonNetworkListener(pokemonNetworkListener);
        RetrofitFactory.getInstance().getPokemon(id);
    }
}


