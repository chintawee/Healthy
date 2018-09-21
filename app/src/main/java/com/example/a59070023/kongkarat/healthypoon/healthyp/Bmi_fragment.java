package com.example.a59070023.kongkarat.healthypoon.healthyp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Bmi_fragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_bmi, container,false);

    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        initCalculateBMI();
        prevButton();
    }
    void initCalculateBMI(){
        Button _calBtn = getView().findViewById(R.id.bmi_calculate_btn);
        _calBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                EditText _weight = getView().findViewById(R.id.bmi_weight);
                EditText _height = getView().findViewById(R.id.bmi_height);
                String _weightStr = _weight.getText().toString();
                String _heightStr = _height.getText().toString();
                TextView _result = getView().findViewById(R.id.bmi_Result);
                Log.d("BMI", "ButtonBMI");
                if (_weightStr.isEmpty() || _heightStr.isEmpty()){
                    Log.d("BMI","Something is empty");
                    Toast.makeText(getActivity(),"please enter your weight and height.",Toast.LENGTH_SHORT).show();

                }
                else {
                    Log.d("BMI", "Calculated!!");
                    Toast.makeText(getActivity(), "Calculated.", Toast.LENGTH_SHORT).show();
                    double _heightf = Double.parseDouble(_heightStr)/100;
                    double _weightf = Double.parseDouble(_weightStr);
                    Double _resultf = _weightf/(Math.pow(_heightf, 2));
                    _result.setText(String.format("%.2f", _resultf));
                }
            }
        });
    }


    void prevButton(){
        Button preBtn = getView().findViewById(R.id.previous);
        preBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("BMI", "BackButtonBMI");
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_view, new Menu_fragment()).commit();
            }
        });
    }
}
