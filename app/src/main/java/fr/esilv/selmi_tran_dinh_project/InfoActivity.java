package fr.esilv.selmi_tran_dinh_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class InfoActivity extends AppCompatActivity {

    private TextView txt_info;
    private EditText input_info;
    private Button btn_info;

    private static final String SHARED_PREF_NAME = "username";
    private static final String KEY_NAME = "key_username";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

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
