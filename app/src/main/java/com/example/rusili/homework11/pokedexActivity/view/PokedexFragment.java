package com.example.rusili.homework11.pokedexActivity.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rusili.homework11.R;
import com.example.rusili.homework11.controller.PokedexAdapter;
import com.example.rusili.homework11.detailscreen.model.Pokemon;
import com.example.rusili.homework11.detailscreen.model.objects.Sprites;
import com.example.rusili.homework11.network.RetrofitFactory;
import com.example.rusili.homework11.pokedexActivity.model.Pokedex;
import com.example.rusili.homework11.pokedexActivity.model.objects.PokemonEntries;
import com.example.rusili.homework11.recyclerviewscreen.api.PokemonSetApi;
import com.example.rusili.homework11.recyclerviewscreen.model.PokemonData;
import com.example.rusili.homework11.recyclerviewscreen.model.PokemonSet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by rusi.li on 11/22/17.
 */

public class PokedexFragment extends Fragment {
	private Retrofit retrofit;
	private RecyclerView recyclerView;
	private PokedexAdapter pokedexAdapter;
	private int offset;
	private boolean canLoad;

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_pokedex, container, false);
		recyclerView = (RecyclerView) view.findViewById(R.id.pokedex_frag_recyclerview);
		pokedexAdapter = new PokedexAdapter(getContext());
		final GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
		recyclerView.setAdapter(pokedexAdapter);
		recyclerView.setHasFixedSize(true);
		recyclerView.setLayoutManager(gridLayoutManager);
		recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
			@Override
			public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
				super.onScrolled(recyclerView, dx, dy);
				if (dy > 0) {
					int visibleItemCount = gridLayoutManager.getChildCount();
					int totalItemCount = gridLayoutManager.getItemCount();
					int pastVisibleItems = gridLayoutManager.findFirstVisibleItemPosition();

					if (canLoad) {
						if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
							Log.d("pokedexfrag", "Recyclerview loading");

							canLoad = false;
							offset += 20;
							getPokemonData(offset);
						}
					}
				}
			}
		});

		unlimitedPower();
		return view;
	}

	private void unlimitedPower() {
		retrofit = new Retrofit.Builder()
				.baseUrl("http://pokeapi.co/api/v2/")
				.addConverterFactory(GsonConverterFactory.create())
				.build();

		canLoad = false;
		offset = 0;

		getPokemonData(offset);
	}

	private void getPokemonData(int offset) {
		PokemonSetApi pokemonSetApi = retrofit.create(PokemonSetApi.class);
		Call<PokemonData> pokemonDataCall = pokemonSetApi.obtainPokemonList(20, offset);
		pokemonDataCall.enqueue(new Callback<PokemonData>() {
			@Override
			public void onResponse(@NonNull Call<PokemonData> call, @NonNull Response<PokemonData> response) {
				canLoad = true;
				if (response.isSuccessful()) {
					PokemonData pokemonData = response.body();
					ArrayList<PokemonSet> pokemonSets = pokemonData.getResults();
					pokedexAdapter.listPokemonSet(pokemonSets);
				} else {
					Log.e("PokedexFrag", " onResponse: " + response.errorBody());
				}
			}

			@Override
			public void onFailure(@NonNull Call<PokemonData> call, @NonNull Throwable t) {
				canLoad = true;
				Log.e("onFailure: ", t.getMessage());
			}
		});
	}
}
