package com.example.rusili.homework11;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.rusili.homework11.pokedexActivity.view.PokedexFragment;

public class MainActivity extends AppCompatActivity {

    TextView errorTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        pokedexFrag();
    }

    private void pokedexFrag() {
        PokedexFragment pokedexFragment = new PokedexFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_container, pokedexFragment);
        fragmentTransaction.commit();
        Log.d("main", "frag ran");
    }
}


