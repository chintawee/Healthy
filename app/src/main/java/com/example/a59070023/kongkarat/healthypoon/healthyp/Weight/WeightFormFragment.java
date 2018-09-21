package com.example.a59070023.kongkarat.healthypoon.healthyp.Weight;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a59070023.kongkarat.healthypoon.healthyp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class WeightFormFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_weight_form, container, false);
    }

    FirebaseAuth fbAuth;
    FirebaseFirestore fbStore;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        fbAuth = FirebaseAuth.getInstance();
        fbStore = FirebaseFirestore.getInstance();
        initSaveBtn();
    }

    void initSaveBtn() {
        final String uid = fbAuth.getUid();
        Button _addBtn = getView().findViewById(R.id.weight_form_add);
        _addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("WEIGHT", "Goto AddWeight");
                EditText _weight = getView().findViewById(R.id.weight_form_weight);
                EditText _date = getView().findViewById(R.id.weight_form_date);
                int weightInt = Integer.parseInt(_weight.getText().toString());
                String _dateStr = _date.getText().toString();
                _dateStr = _dateStr.replace("/", "-");
                Weight weightObj = new Weight(_dateStr, weightInt);
                fbStore.collection("myfitness").document(uid).collection("weight").document(_dateStr).set(weightObj).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_view,new Weight_fragment()).commit();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
