package com.example.eapal.findmyparking;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Date;
import java.util.Objects;

public class Principal extends AppCompatActivity implements View.OnClickListener {

    private Button btnGoogle, btnFb, btnCorreo;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authListener;
    private GoogleApiClient googleApiClient;
    private ProgressBar progressBar;

    private static final int RC_SIGN_IN = 777;


    public Principal() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        btnGoogle = findViewById(R.id.btnGoogle);
        btnGoogle.setOnClickListener(this);
        btnFb = findViewById(R.id.btnFb);
        btnGoogle.setOnClickListener(this);
        btnFb.setOnClickListener(this);
        btnCorreo = findViewById(R.id.btnCorreo);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this, null).addApi(Auth.GOOGLE_SIGN_IN_API, gso).build();
        mAuth = FirebaseAuth.getInstance();
        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    goListActivity();
                }
            }
        };
        progressBar = (ProgressBar)findViewById(R.id.progress_bar);
       // UserParqueadero u = new UserParqueadero(Datos.getId(),"La 84","3106659495","CRA 44 #84",3000,new MapMarkers(10.7,4.5),"8AM A 2PM","Jorge","");u.guardar();

    }

    private void goListActivity() {

        Intent i = new Intent(this, ListadoParqueadero1.class);
        startActivity(i);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult googleSignInResult = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(googleSignInResult);
        }
    }

    private void handleSignInResult(GoogleSignInResult googleSignInResult) {
        if (googleSignInResult.isSuccess()) {
            firebaseAuthWithGoogle(Objects.requireNonNull(googleSignInResult.getSignInAccount()));
        } else {
            Snackbar.make(findViewById(R.id.main_layout), getString(R.string.errorLogin), Snackbar.LENGTH_LONG).show();
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        progressBar.setVisibility(View.VISIBLE);
        btnFb.setVisibility(View.GONE);
        btnGoogle.setVisibility(View.GONE);
        btnCorreo.setVisibility(View.GONE);
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                btnFb.setVisibility(View.VISIBLE);
                btnGoogle.setVisibility(View.VISIBLE);
                btnCorreo.setVisibility(View.VISIBLE);
                if (!task.isComplete()) {
                    Toast.makeText(getApplicationContext(), getString(R.string.errorLogin), Toast.LENGTH_LONG).show();
                }else{
                    goListActivity();
                }
            }
        });
    }

    private void signInGoogle() {
        Intent i = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(i, RC_SIGN_IN);
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(authListener);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnGoogle:
                signInGoogle();
                break;
            case R.id.btnFb:

                break;
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (authListener != null) {
            mAuth.removeAuthStateListener(authListener);
        }
    }
}
