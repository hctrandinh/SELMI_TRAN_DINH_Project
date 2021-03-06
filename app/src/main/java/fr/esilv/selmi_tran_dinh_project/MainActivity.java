package fr.esilv.selmi_tran_dinh_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements FragmentListActionListener{

    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;
    private BottomNavigationView bv;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    public void addListFragment()
    {
        fragmentTransaction=fragmentManager.beginTransaction();

        PokemonListFragment pokemonListFragment = new PokemonListFragment();
        pokemonListFragment.setFragmentListActionListener(this);

        fragmentTransaction.replace(R.id.fragmentContainer, pokemonListFragment);
        fragmentTransaction.commit();
    }

    public void addSearchFragment()
    {
        fragmentTransaction=fragmentManager.beginTransaction();

        PokemonSearchFragment pokemonSearchFragment = new PokemonSearchFragment();
        pokemonSearchFragment.setFragmentListActionListener(this);

        fragmentTransaction.replace(R.id.fragmentContainer, pokemonSearchFragment);
        fragmentTransaction.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //String content = getIntent().getStringExtra("InfoActivityHomeImg");

        fragmentManager = getSupportFragmentManager();

        dl = (DrawerLayout)findViewById(R.id.activity_main);
        t = new ActionBarDrawerToggle(this, dl, R.string.Open, R.string.Close);

        dl.addDrawerListener(t);
        t.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fragmentTransaction=fragmentManager.beginTransaction();

        HomeFragment homeFragment = new HomeFragment();

        fragmentTransaction.replace(R.id.fragmentContainer, homeFragment);
        fragmentTransaction.commit();

        nv = (NavigationView)findViewById(R.id.nv);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch(id)
                {
                    case R.id.account:
                        Toast.makeText(MainActivity.this, "My Account",Toast.LENGTH_SHORT).show();
                        Intent nextActivity = new Intent(MainActivity.this, InfoActivity.class);
                        startActivity(nextActivity);
                        finish();
                        break;
                    case R.id.settings:
                        Toast.makeText(MainActivity.this, "Settings",Toast.LENGTH_SHORT).show();break;
                    case R.id.mycart:
                        Toast.makeText(MainActivity.this, "My Cart",Toast.LENGTH_SHORT).show();break;
                    default:
                        return true;
                }
                return true;
            }
        });

        bv = (BottomNavigationView) findViewById(R.id.bottom_nav);
        bv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch(menuItem.getItemId())
                {
                    case R.id.home_icon:
                        Toast.makeText(MainActivity.this, "Home",Toast.LENGTH_SHORT).show();
                        fragmentTransaction=fragmentManager.beginTransaction();

                        HomeFragment homeFragment = new HomeFragment();

                        fragmentTransaction.replace(R.id.fragmentContainer, homeFragment);
                        fragmentTransaction.commit();
                        break;

                    case R.id.list_icon:
                        Toast.makeText(MainActivity.this, "List",Toast.LENGTH_SHORT).show();
                        addListFragment();
                        break;

                    case R.id.search_icon:
                        Toast.makeText(MainActivity.this, "Search",Toast.LENGTH_SHORT).show();
                        addSearchFragment();
                        break;

                    case R.id.contact_icon:
                        Toast.makeText(MainActivity.this, "Contact",Toast.LENGTH_SHORT).show();
                        fragmentTransaction=fragmentManager.beginTransaction();

                        PokemonContactFragment pokemonContactFragment = new PokemonContactFragment();

                        fragmentTransaction.replace(R.id.fragmentContainer, pokemonContactFragment);
                        fragmentTransaction.commit();
                        break;

                    default:
                        return true;
                }
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(t.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPokemonSelected(String name)
    {
        fragmentTransaction=fragmentManager.beginTransaction();

        PokemonDetailsFragment pokemonDetailsFragment = new PokemonDetailsFragment();

        Bundle bundle=new Bundle();
        bundle.putString(FragmentListActionListener.KEY_POKEMON_SELECTED, name);
        pokemonDetailsFragment.setArguments(bundle);

        fragmentTransaction.add(R.id.fragmentContainer, pokemonDetailsFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
