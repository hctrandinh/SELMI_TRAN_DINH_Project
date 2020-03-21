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

public class PokemonSearchFragment extends Fragment implements MyRecyclerViewAdapter.ItemClickListener{

    MyRecyclerViewAdapter adapter;
    ArrayList<Pokemon> pokemon_list = new ArrayList<>();
    ArrayList<Pokemon> pokemon_list_save = new ArrayList<>(); //To keep copy of full list and avoid requesting again to api.
    EditText input_search;

    FragmentListActionListener fragmentListActionListener;

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.search_fragment,container,false);
        final Context context = getActivity().getBaseContext();

        if(context != null) Log.i("context", "not null");
        else Log.i("context", "null");

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getBaseContext());
        Set<String> set = new HashSet<>();
        set = preferences.getStringSet("KEY_FAVORITES", null);
        if(set != null)
        {
            Iterator<String> iterator = set.iterator();
            while(iterator.hasNext()) {
                String setElement = iterator.next();
                Pokemon temp = new Pokemon(setElement, "");
                pokemon_list.add(temp);
                pokemon_list_save.add(temp);
            }
        }

        input_search = (EditText) rootView.findViewById(R.id.input_search);

        input_search.addTextChangedListener(new TextWatcher() {
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

        RecyclerView recyclerView = rootView.findViewById(R.id.pokemons_favorites);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        adapter = new MyRecyclerViewAdapter(context, pokemon_list);
        recyclerView.setAdapter(adapter);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        return rootView;
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(getActivity().getBaseContext(), "You have clicked on row number " + position, Toast.LENGTH_SHORT).show();
        if(fragmentListActionListener != null)
        {
            fragmentListActionListener.onPokemonSelected(pokemon_list_save.get(position).getName());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Favorites");
    }
}
