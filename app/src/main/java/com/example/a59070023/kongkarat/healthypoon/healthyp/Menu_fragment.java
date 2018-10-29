package com.example.a59070023.kongkarat.healthypoon.healthyp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.a59070023.kongkarat.healthypoon.healthyp.Weight.Weight_fragment;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class Menu_fragment extends Fragment{
    ArrayList<String> _menu = new ArrayList<>();
    FirebaseAuth fnAuth;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        _menu.add("BMI");
        _menu.add("Weight");
        _menu.add("Sign out");
    }

    @Override
    public void onActivityCreated (@Nullable Bundle savedInStanceState){
        super.onActivityCreated(savedInStanceState);
        fnAuth = FirebaseAuth.getInstance();

        ListView menuList = getView().findViewById(R.id.menu_list);
        final ArrayAdapter<String> menuAdapter = new ArrayAdapter<String>(
                getActivity(),android.R.layout.simple_list_item_1,_menu);

        menuList.setAdapter(menuAdapter);
        menuList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(_menu.get(position).equals("BMI")){
                    Log.d("Menu","go to bmi");
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_view,new Bmi_fragment()).addToBackStack(null).commit();
                }
                else if (_menu.get(position).equals("Weight")){
                    Log.d("MENU", "go to weight");
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_view,new Weight_fragment()).addToBackStack(null).commit();
                }
                else if (_menu.get(position).equals("Sign out")){
                    fnAuth.signOut();
                    Log.d("menu","SIGN OUT");
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_view,new login_fragment()).commit();
                }
            }
        });


    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_menu,container,false);
    }
}
