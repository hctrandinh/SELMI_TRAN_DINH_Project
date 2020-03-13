package fr.esilv.selmi_tran_dinh_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements MyRecyclerViewAdapter.ItemClickListener{

    MyRecyclerViewAdapter adapter;
    TextView count_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pokemonlistfragment);
        final Context context = getBaseContext();
        final ArrayList<Pokemon> pokemon_list = new ArrayList<>();
        count_main = findViewById(R.id.count_main);

        RecyclerView recyclerView = findViewById(R.id.pokemons);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        adapter = new MyRecyclerViewAdapter(context, pokemon_list);
        recyclerView.setAdapter(adapter);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/pokemon/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IRetrofit Iretrofit = retrofit.create(IRetrofit.class);

        Call<All_Pokemons> call = Iretrofit.pokemon_list();

        call.enqueue(new Callback<All_Pokemons>() {
            @Override
            public void onResponse(Call<All_Pokemons> call, Response<All_Pokemons> response) {
                MyRecyclerViewAdapter.ItemClickListener mClickListener;
                All_Pokemons res = response.body();

                if(response.body().toString() != null)
                {
                    count_main.setText("Pokemons found: " + Integer.toString(res.getCount()));
                    List<All_Pokemons.Results> list = res.getResults();
                    for(int index = 0; index < list.size(); index++)
                    {
                        Pokemon temp = new Pokemon(list.get(index).getName(), list.get(index).getUrl());
                        Log.i("test", "onResponse: " + temp.getUrl());
                        pokemon_list.add(temp);
                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<All_Pokemons> call, Throwable t) {

            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "You clicked on row number " + position, Toast.LENGTH_SHORT).show();
    }

}
