package com.example.camer.client.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.camer.client.activities.MainActivity;
import com.example.camer.client.R;
import com.example.camer.client.background.aSyncLogin;
import com.example.camer.client.background.aSyncRegister;
import com.example.camer.client.background.syncEvents;
import com.example.camer.client.background.syncPeople;

import model.Data;
import model.User;
import model.auth_token;
import model.loginRequest;
import model.loginResponse;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LoginFragment} interface
 * to handle interaction events.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_TITLE = "param1";

    // TODO: Rename and change types of parameters

    private String title = null;
    private Button signIn;
    private Button register;
    private EditText serverIP;
    private EditText serverPort;
    private EditText userName;
    private EditText password;
    private EditText firstName;
    private EditText lastName;
    private EditText email;
    private RadioButton male;
    private RadioButton female;
    TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }
        @Override
        public void afterTextChanged(Editable s) {
            if(validLoginInfo())
                signIn.setEnabled(true);
            else
                signIn.setEnabled(false);
            if(validRegisterInfo())
                register.setEnabled(true);
            else
                register.setEnabled(false);
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

    };


    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param name name
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String name) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, name);
        //args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            title = getArguments().getString(ARG_TITLE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        signIn = (Button) getView().findViewById(R.id.signIn);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSignIn();
            }
        });
        register = (Button) getView().findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRegister();
            }
        });
        serverIP = getView().findViewById(R.id.serverIP);
        serverIP.addTextChangedListener(watcher);
        serverPort = getView().findViewById(R.id.serverPort);
        serverPort.addTextChangedListener(watcher);
        userName = getView().findViewById(R.id.userName);
        userName.addTextChangedListener(watcher);
        password = getView().findViewById(R.id.password);
        password.addTextChangedListener(watcher);
        firstName = getView().findViewById(R.id.firstName);
        firstName.addTextChangedListener(watcher);
        lastName = getView().findViewById(R.id.lastName);
        lastName.addTextChangedListener(watcher);
        email = getView().findViewById(R.id.email);
        email.addTextChangedListener(watcher);
        male = getView().findViewById(R.id.male);
        female = getView().findViewById(R.id.female);
        signIn.setEnabled(false);
        register.setEnabled(false);
    }

    private void onSignIn(){
        if(validLoginInfo()) {
            loginRequest login = new loginRequest(userName.getText().toString(), password.getText().toString());
            aSyncLogin task = new aSyncLogin(serverIP.getText().toString(), serverPort.getText().toString(), this);
            task.execute(login);
        }
        else
            Toast.makeText(this.getActivity(),"Insufficient Information",Toast.LENGTH_SHORT).show();
    }

    private boolean validLoginInfo(){
        if(serverIP.getText().toString().length() != 0 &&
                serverPort.getText().toString().length() != 0 &&
                userName.getText().toString().length() != 0 &&
                password.getText().toString().length() != 0
                )
            return true;
        return false;
    }

    public void onCallBackLogin(Object returned, aSyncLogin login){
        loginResponse response = (loginResponse) returned;
        if(response.getMessage() != null){
            Toast.makeText(this.getActivity(),"Login Failed", Toast.LENGTH_SHORT).show();
        }
        else if(response.getAuth_token() == null){
            Toast.makeText(this.getActivity(),"Login Failed", Toast.LENGTH_SHORT).show();
        }
        else{
            auth_token token = new auth_token(response.getAuth_token(),response.getPersonID());
            Data.getInstance().setAuth_token(token);
            syncEvents syncEvents = new syncEvents(serverIP.getText().toString(),serverPort.getText().toString(),(MainActivity)this.getActivity());
            syncEvents.execute(token);
            syncPeople syncPeople = new syncPeople(serverIP.getText().toString(),serverPort.getText().toString(),(MainActivity)this.getActivity());
            syncPeople.execute(token);
        }
    }

    private void onRegister(){
        if(validRegisterInfo()){
            User user = new User();
            user.setUsername(userName.getText().toString());
            user.setPassword(password.getText().toString());
            user.setFirstName(firstName.getText().toString());
            user.setLastName(lastName.getText().toString());
            user.setEmail(email.getText().toString());
            if(male.isChecked()){
                user.setGender("m");
            }
            else{
                user.setGender("f");
            }
            aSyncRegister task = new aSyncRegister(serverIP.getText().toString(),serverPort.getText().toString(),this);
            task.execute(user);
        }
        else{
            Toast.makeText(this.getActivity(),"Insufficient Information",Toast.LENGTH_SHORT).show();
        }
    }

    public boolean validRegisterInfo(){
        if(!validLoginInfo())
            return false;
        if(firstName.getText().toString().length() != 0 &&
                lastName.getText().toString().length() != 0  &&
                email.getText().toString().length() != 0  &&
                (male.isChecked() || female.isChecked()))
            return true;
        return false;
    }

    public void onCallBackRegister(Object returned, aSyncRegister login){
        loginResponse response = (loginResponse) returned;
        if(response.getMessage() != null){
            Toast.makeText(this.getActivity(),"Register Failed", Toast.LENGTH_SHORT).show();
        }
        else if(response.getAuth_token() == null){
            Toast.makeText(this.getActivity(),"Register Failed", Toast.LENGTH_SHORT).show();
        }
        else{
            auth_token token = new auth_token(response.getAuth_token(),response.getPersonID());
            Data.getInstance().setAuth_token(token);
            syncEvents syncEvents = new syncEvents(serverIP.getText().toString(),serverPort.getText().toString(),(MainActivity)this.getActivity());
            syncEvents.execute(token);
            syncPeople syncPeople = new syncPeople(serverIP.getText().toString(),serverPort.getText().toString(),(MainActivity)this.getActivity());
            syncPeople.execute(token);
        }
    }


}
