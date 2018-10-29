package com.example.a59070023.kongkarat.healthypoon.healthyp;

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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Register_fragment extends Fragment {
    private FirebaseAuth fbAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        fbAuth = FirebaseAuth.getInstance();
        Log.d("Login","On Create");
        initNewAccount();
        initBack();

    }
    void initNewAccount(){
        Button _regBtn = getView().findViewById(R.id.register_btn);
        _regBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                EditText _regEmail =  getView().findViewById(R.id.reg_email);
                EditText _regPassword =  getView().findViewById(R.id.reg_password);
                EditText _regRepassword =  getView().findViewById(R.id.reg_repassword);
                String _regEmailStr = _regEmail.getText().toString();
                String _regPasswordStr = _regPassword.getText().toString();
                String _regRepasswordStr = _regRepassword.getText().toString();

                if (_regEmailStr.isEmpty() || _regPasswordStr.isEmpty()){
                    Toast.makeText(getActivity(),"please enter your Email, Password, Repassword",Toast.LENGTH_SHORT).show();

                }
                else if (_regPasswordStr.length() < 6 || _regRepasswordStr.length() < 6){
                    Toast.makeText(getActivity(),"Password must has more than 6 characters",Toast.LENGTH_SHORT).show();
                    Log.d("Register", "Password doesn't match");
                }
                else if (!_regPasswordStr.equals(_regRepasswordStr)){
                    Toast.makeText(getActivity(), "Password doesn't match with Repassword",Toast.LENGTH_SHORT).show();
                    Log.d("REGISTER","Password doesn't match");
                }
                else{
                    fbAuth.createUserWithEmailAndPassword(_regEmailStr,_regPasswordStr).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            sandVerifiedEmail(authResult.getUser());
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_view,new login_fragment()).commit();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("REGISTER", "Error  : " + e.getMessage());
                        }
                    });
                }
            }
        });
    }

    void initBack(){
        Button _backbutton =getView().findViewById(R.id.reg_backBtn);
        _backbutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_view,new login_fragment()).commit();
                Log.d("Register","Back to Login");
            }
        });
    }
    private void sandVerifiedEmail (FirebaseUser _user){
        _user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("REGISTER","send verify email success");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("REGISTER","send verify email failed : " + e.getMessage());
            }
        });
    }
}

