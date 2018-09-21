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
import android.widget.ListView;

import com.example.a59070023.kongkarat.healthypoon.healthyp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;

public class Weight_fragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_weight,container,false);
    }
    ArrayList<Weight> weights = new ArrayList<>();
    FirebaseAuth fbAuth;
    FirebaseFirestore fbStore;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        initDateCall();
        initAddWeight();

    }
    void initDateCall(){
        fbAuth = FirebaseAuth.getInstance();
        fbStore = FirebaseFirestore.getInstance();

        final ListView _weightList = getView().findViewById(R.id.weight_list);
        final WeightAdapter _weightAdapter = new WeightAdapter(getActivity(), R.layout.fragment_weight_item, weights);
        _weightList.setAdapter(_weightAdapter);

        fbStore.collection("myfitness").document(fbAuth.getUid()).collection("weight").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {
                _weightAdapter.clear();
                for (QueryDocumentSnapshot doc: queryDocumentSnapshots){
                    weights.add(doc.toObject(Weight.class));
                }
                Collections.reverse(weights);
                initAssignStatus();
                _weightAdapter.notifyDataSetChanged();
            }
        });
    }
    void initAssignStatus(){
        for (int i = 0; i <weights.size() -1;i++){
            if (weights.get(i).getWeight()> weights.get(i+1).getWeight())
                weights.get(i).setStatus("UP");
            else if (weights.get(i).getWeight() < weights.get(i+1).getWeight())
                weights.get(i).setStatus("Down");
        }
    }

    void initAddWeight(){
        Button _addWeightBtn = getActivity().findViewById(R.id.WeightAdd);
        _addWeightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_view, new WeightFormFragment()).commit();
                Log.d("WEIGHT", "Hello Add Weight");
            }
        });
    }

}