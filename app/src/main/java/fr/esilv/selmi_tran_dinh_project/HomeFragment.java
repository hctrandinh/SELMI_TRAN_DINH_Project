package fr.esilv.selmi_tran_dinh_project;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {

    View rootView;
    ImageView homeimage;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.home,container,false);
        //String content = getActivity().getIntent().getStringExtra("InfoActivityHomeImg");

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getBaseContext());
        String content = preferences.getString("key_home", null);

        Context context = getActivity().getBaseContext();
        homeimage = (ImageView) rootView.findViewById(R.id.homeimage);

        if(content == null)
        {
            homeimage.setImageDrawable(context.getResources().getDrawable(R.drawable.home1));
        }
        else
        {
            Drawable myDrawable;

            if(content.compareTo("1") == 0)
            {
                myDrawable = context.getResources().getDrawable(R.drawable.home1);
            }
            else if(content.compareTo("2") == 0)
            {
                myDrawable = context.getResources().getDrawable(R.drawable.home2);
            }
            else if(content.compareTo("3") == 0)
            {
                myDrawable = context.getResources().getDrawable(R.drawable.home3);
            }
            else if(content.compareTo("4") == 0)
            {
                myDrawable = context.getResources().getDrawable(R.drawable.home4);
            }
            else if(content.compareTo("5") == 0)
            {
                myDrawable = context.getResources().getDrawable(R.drawable.home5);
            }
            else
            {
                myDrawable = context.getResources().getDrawable(R.drawable.home6);
            }

            homeimage.setImageDrawable(myDrawable);
        }

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Home");
    }
}