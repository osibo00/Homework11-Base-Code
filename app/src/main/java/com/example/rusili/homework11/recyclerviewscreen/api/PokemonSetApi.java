package com.example.rusili.homework11.recyclerviewscreen.api;

import com.example.rusili.homework11.recyclerviewscreen.model.PokemonData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by oleg on 12/1/17.
 */

public interface PokemonSetApi {

    @GET("pokemon")
    Call<PokemonData> obtainPokemonList(@Query("limit") int limit, @Query("offset") int offset);
}
