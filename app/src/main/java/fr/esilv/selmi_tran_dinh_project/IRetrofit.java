package fr.esilv.selmi_tran_dinh_project;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IRetrofit {
    @GET("?offset=0&limit=1000")
    Call<All_Pokemons> pokemon_list();

    @GET("{name}")
    Call<PokemonDetails> pokemon_details(@Path("name") String name);
}
