package fr.esilv.selmi_tran_dinh_project;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class PokemonDetailsFragment extends Fragment {
    View rootView;

    TextView pokemon_id, pokemon_name, pokemon_types, pokemon_hp, pokemon_atk,
            pokemon_atk_sp, pokemon_def, pokemon_def_sp, pokemon_speed, pokemon_techniques;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.details_fragment,container,false);
        final Context context = getActivity().getBaseContext();

        final ArrayList<PokemonDetails> pokemon_details = new ArrayList<>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/pokemon/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IRetrofit Iretrofit = retrofit.create(IRetrofit.class);

        Call<PokemonDetails> call = Iretrofit.pokemon_details("blaziken");

        call.enqueue(new Callback<PokemonDetails>() {
            @Override
            public void onResponse(Call<PokemonDetails> call, Response<PokemonDetails> response) {
                PokemonDetails res = response.body();

                pokemon_id = (TextView) rootView.findViewById(R.id.pokemon_id);
                pokemon_name = (TextView) rootView.findViewById(R.id.pokemon_name);
                pokemon_types = (TextView) rootView.findViewById(R.id.pokemon_types);
                pokemon_hp = (TextView) rootView.findViewById(R.id.pokemon_hp);
                pokemon_atk = (TextView) rootView.findViewById(R.id.pokemon_atk);
                pokemon_atk_sp = (TextView) rootView.findViewById(R.id.pokemon_atk_sp);
                pokemon_def = (TextView) rootView.findViewById(R.id.pokemon_def);
                pokemon_def_sp = (TextView) rootView.findViewById(R.id.pokemon_def_sp);
                pokemon_speed = (TextView) rootView.findViewById(R.id.pokemon_speed);
                pokemon_techniques = (TextView) rootView.findViewById(R.id.pokemon_techniques);

                if(response.body().toString() != null)
                {
                    Log.i("Testing", "onResponse: " + res.getId());
                    pokemon_id.setText(Integer.toString(res.getId()));

                    pokemon_name.setText(res.getName());

                    List<PokemonDetails.Types> list1 = res.getTypes();
                    StringBuilder temp = new StringBuilder();
                    for(int index = 0; index < list1.size(); index++)
                    {
                        temp.append(list1.get(index).getType().getName());
                        if(index < list1.size() - 1)
                        {
                            temp.append(" / ");
                        }
                    }
                    pokemon_types.setText(temp);

                    List<PokemonDetails.Stats> list2 = res.getStats();
                    pokemon_hp.setText(Integer.toString(list2.get(5).getBase_stat()));
                    pokemon_atk.setText(Integer.toString(list2.get(4).getBase_stat()));
                    pokemon_atk_sp.setText(Integer.toString(list2.get(2).getBase_stat()));
                    pokemon_def.setText(Integer.toString(list2.get(3).getBase_stat()));
                    pokemon_def_sp.setText(Integer.toString(list2.get(1).getBase_stat()));
                    pokemon_speed.setText(Integer.toString(list2.get(0).getBase_stat()));

                    List<PokemonDetails.Abilities> list3 = res.getAbilities();
                    StringBuilder temp2 = new StringBuilder();
                    for(int index = 0; index < list3.size(); index++)
                    {
                        temp2.append(list3.get(index).getAbility().getName());
                        if(index < list3.size() - 1)
                        {
                            temp2.append(" / ");
                        }
                    }
                    pokemon_techniques.setText(temp2);
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
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Details");
    }
}
