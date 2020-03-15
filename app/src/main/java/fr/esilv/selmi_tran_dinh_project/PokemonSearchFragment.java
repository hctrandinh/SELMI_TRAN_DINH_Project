package fr.esilv.selmi_tran_dinh_project;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PokemonSearchFragment extends Fragment{

    View rootView;
    TextView pokemon_results;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.search_fragment,container,false);
        final Context context = getActivity().getBaseContext();

        final ArrayList<PokemonDetails> pokemon_details = new ArrayList<>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/pokemon/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IRetrofit Iretrofit = retrofit.create(IRetrofit.class);

        Call<PokemonDetails> call = Iretrofit.pokemon_details("ditto");

        call.enqueue(new Callback<PokemonDetails>() {
            @Override
            public void onResponse(Call<PokemonDetails> call, Response<PokemonDetails> response) {
                PokemonDetails res = response.body();
                pokemon_results = (TextView) rootView.findViewById(R.id.pokemon_results);

                if(response.body().toString() != null)
                {
                    pokemon_results.setText(res.getName());
                }
            }

            @Override
            public void onFailure(Call<PokemonDetails> call, Throwable t) {

            }
        });
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Search");
    }
}
