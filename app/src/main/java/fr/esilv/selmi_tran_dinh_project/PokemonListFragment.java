package fr.esilv.selmi_tran_dinh_project;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PokemonListFragment extends Fragment implements MyRecyclerViewAdapter.ItemClickListener{

    MyRecyclerViewAdapter adapter;
    ArrayList<Pokemon> pokemon_list = new ArrayList<>();
    ArrayList<Pokemon> pokemon_list_save = new ArrayList<>(); //To keep copy of full list and avoid requesting again to api.
    TextView count_main;
    EditText input_filter;

    FragmentListActionListener fragmentListActionListener;

    int click = 0;

    View rootView;

    public void setFragmentListActionListener(FragmentListActionListener fragmentListActionListener)
    {
        this.fragmentListActionListener = fragmentListActionListener;
    }

    void filter(String name){
        if(name.length() == 0)
        {
            Log.i("Filter", "Empty filter.");
            pokemon_list.clear();
            pokemon_list.addAll(pokemon_list_save);
            adapter.notifyDataSetChanged();
        }
        else
        {
            pokemon_list.clear();
            pokemon_list.addAll(pokemon_list_save);
            adapter.notifyDataSetChanged();
            ArrayList<Pokemon> temp = new ArrayList();
            for(Pokemon element: pokemon_list){
                if(element.getName().contains(name)){
                    temp.add(element);
                }
            }
            pokemon_list.clear();
            pokemon_list.addAll(temp);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        //Toast.makeText(getActivity().getBaseContext(), "You have clicked on row number " + position, Toast.LENGTH_SHORT).show();
        click++;
        final int pos = position;
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(click == 1)
                {
                    Toast.makeText(getActivity().getBaseContext(), "Details",Toast.LENGTH_SHORT).show();
                    click = 0;
                    if(fragmentListActionListener != null)
                    {
                        fragmentListActionListener.onPokemonSelected(pokemon_list_save.get(pos).getName());
                    }
                }
                if(click == 2)
                {
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getBaseContext());
                    Set<String> set = new HashSet<>(preferences.getStringSet("KEY_FAVORITES", new HashSet<String>()));
                    if(set != null)
                    {
                        if(set.contains(pokemon_list_save.get(pos).getName()) == false)
                        {
                            set.add(pokemon_list_save.get(pos).getName());
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putStringSet("KEY_FAVORITES", set);
                            editor.apply();
                        }
                    }
                    else
                    {
                        set.add(pokemon_list_save.get(pos).getName());
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putStringSet("KEY_FAVORITES", set);
                        editor.apply();
                    }
                    Toast.makeText(getActivity().getBaseContext(), "Added to Favorites",Toast.LENGTH_SHORT).show();
                    click = 0;
                }
                else
                {
                    click = 0;
                }
            }
        }, 500);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.pokemonlistfragment,container,false);
        final Context context = getActivity().getBaseContext();

        count_main = (TextView) rootView.findViewById(R.id.count_main);
        input_filter = (EditText) rootView.findViewById(R.id.input_filter);

        input_filter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });

        RecyclerView recyclerView = rootView.findViewById(R.id.pokemons);
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
                    List<All_Pokemons.Results> list = res.getResults();

                    count_main.setText("Pokemons found: " + Integer.toString(list.size()));

                    for(int index = 0; index < list.size(); index++)
                    {
                        Pokemon temp = new Pokemon(list.get(index).getName(), list.get(index).getUrl());
                        Log.i("test", "onResponse: " + temp.getUrl());
                        pokemon_list.add(temp);
                        pokemon_list_save.add(temp);
                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<All_Pokemons> call, Throwable t) {

            }
        });
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("List");
    }

}