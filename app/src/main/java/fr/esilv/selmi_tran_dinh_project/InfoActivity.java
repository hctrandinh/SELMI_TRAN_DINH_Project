package fr.esilv.selmi_tran_dinh_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class InfoActivity extends AppCompatActivity implements MyRecyclerViewAdapterImage.ItemClickListener{

    MyRecyclerViewAdapterImage adapter;
    ArrayList<String> home_url = new ArrayList<String>();

    private TextView txt_info;
    private EditText input_info;
    private Button btn_info;

    private static final String SHARED_PREF_NAME = "username";
    private static final String KEY_NAME = "key_username";
    private static final String SHARED_PREF_HOME = "home";
    private static final String KEY_HOME = "key_home";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        final Context context = getBaseContext();

        home_url.add("home1");
        home_url.add("home2");
        home_url.add("home3");
        home_url.add("home4");
        home_url.add("home5");
        home_url.add("home6");

        RecyclerView recyclerView = findViewById(R.id.home_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        adapter = new MyRecyclerViewAdapterImage(context, home_url);
        recyclerView.setAdapter(adapter);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

        txt_info = (TextView) findViewById(R.id.txt_info);
        input_info = (EditText) findViewById(R.id.input_info);
        btn_info = (Button) findViewById(R.id.btn_info);

        get_saved_data();

        btn_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save_data();
            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(getBaseContext(), "You have chosen home background " + (position + 1), Toast.LENGTH_SHORT).show();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(KEY_HOME, Integer.toString((position + 1)));
        editor.commit();

        Intent mainActivity = new Intent(InfoActivity.this, MainActivity.class);
        //String content = Integer.toString((position + 1));
        //mainActivity.putExtra("InfoActivityHomeImg", content);
        startActivity(mainActivity);
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent mainActivity = new Intent(InfoActivity.this, MainActivity.class);
        startActivity(mainActivity);
        finish();
    }

    private void get_saved_data()
    {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        String username = sharedPreferences.getString(KEY_NAME, null);
        txt_info.setText("Information Page of " + username);
    }

    private void save_data(){
        String username = input_info.getText().toString();
        if(username.length() == 0)
        {
            username = "No_name";
        }
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(KEY_NAME, username);
        editor.apply();
        input_info.setText("");
        get_saved_data();
    }
}
