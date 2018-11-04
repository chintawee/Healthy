package com.example.a59070023.kongkarat.healthypoon.healthyp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import javax.annotation.Nullable;

public class Sleep_fragent extends Fragment{
    SQLiteDatabase myDB;

    @Override
    public void onCreate(@Nullable Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        myDB = getActivity().openOrCreateDatabase("my.db", Context.MODE_PRIVATE, null);
        myDB.execSQL("CREATE TABLE IF NOT EXISTS sleep (id INTEGER PRIMARY KEY AUTOINCREMENT, date VARCHAR(10), toBedTime VARCHAR(5), awakeTime VARCHAR(5))");
    }

    @android.support.annotation.Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @android.support.annotation.Nullable ViewGroup container, @android.support.annotation.Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sleep, container, false);
    }

    public void onActivityCreated(@Nullable Bundle saveInstanceState){
        super.onActivityCreated(saveInstanceState);

        initBackButton();
        initAddButton();
        getAndShowData();
    }

    public void initBackButton(){
        Button backButton = getView().findViewById(R.id.sleep_back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();

            }
        });
    }
    public void initAddButton()
    {
        Button addSleepButton = getView().findViewById(R.id.sleep_add_sleep);
        addSleepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new AddSleepFragment())
                        .addToBackStack(null)
                        .commit();
                //
            }
        });
    }

    public void getAndShowData()
    {
        Cursor cursor = myDB.rawQuery("select id, date, toBed, awakeTime from sleep",null);
        final ArrayList<Sleep> sleepList = new ArrayList<>();
        while (cursor.moveToNext())
        {
            int id = cursor.getInt(0);
            String date = cursor.getString(1);
            String toBedTime = cursor.getString(2);
            String awakeTime = cursor.getString(3);
            Sleep sleep = new Sleep();
            sleep.setId(id);
            sleep.setDate(date);
            sleep.setToBedTime(toBedTime);
            sleep.setAwakeTime(awakeTime);
            sleepList.add(sleep);
        }
        cursor.close();

        ListView sleepListViwe = getView().findViewWithTag(R.id.sleep_list_view);
        SleepAdapter sleepAdapter = new SleepAdapter(getActivity(), R.layout.fragment_sleep_list_item, sleepList);
        sleepListViwe.setAdapter(sleepAdapter);
        sleepListViwe.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("sleep object", sleepList.get(position));
                Fragment addsleepFragment = new AddSleepFragment();
                addsleepFragment.setArguments(bundle);
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.replace(R.id.main_view, addsleepFragment).addToBackStack(null).commit();
            }
        });

    }
}
