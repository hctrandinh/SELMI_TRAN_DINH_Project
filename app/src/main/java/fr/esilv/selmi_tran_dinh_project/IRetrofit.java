package fr.esilv.selmi_tran_dinh_project;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IRetrofit {
    @GET("?offset=0&limit=1000")
    Call<All_Pokemons> pokemon_list();

    @GET("search?part=snippet&maxResults=25&key=AIzaSyBDlyYW8DRb-jWRLOg8uI3eBOTcrw4EsrM")
    Call<All_Pokemons> videoslist2(@Query("q") String query);
}
