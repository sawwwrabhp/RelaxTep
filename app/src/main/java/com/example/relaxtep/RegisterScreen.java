package com.example.relaxtep;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegisterScreen extends AppCompatActivity {

    private static final String TAG = "TAG";
    FirebaseAuth mAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    EditText name;
    EditText pno;
    EditText eid;
    EditText user;
    EditText pass;
    Button btnRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_screen);
        mAuth = FirebaseAuth.getInstance();
        btnRegister = findViewById(R.id.btn_register);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerr();
            }
        });
    }
    void registerr(){
        name = findViewById(R.id.name);
        pno = findViewById(R.id.phoneno);
        eid = findViewById(R.id.email);
        user = findViewById(R.id.username);
        pass = findViewById(R.id.password);
        String n = name.getText().toString();
        String pn = pno.getText().toString();
        String e = eid.getText().toString();
        String u = user.getText().toString();
        String p = pass.getText().toString();
        if(TextUtils.isEmpty(n)){
            Toast.makeText(this, "Name cannot be empty!", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(pn)){
            Toast.makeText(this, "Phone number cannot be empty!", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(e)){
            Toast.makeText(this, "Email cannot be empty!", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(u)){
            Toast.makeText(this, "Username cannot be empty!", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(p)){
            Toast.makeText(this, "Password cannot be empty!", Toast.LENGTH_SHORT).show();
        }else{
            mAuth.createUserWithEmailAndPassword(e, p)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                String uuid = user.getUid().toString();
                                //save to database
                                user newUser = new user(n,p,pn,e,uuid,u,"user");
                                db.collection("users").add(newUser).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
                                        Log.d(TAG,"User added!");
                                        Intent intent = new Intent(RegisterScreen.this,UserPage.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d(TAG,"Cannot add the user!");
                                    }
                                });
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(RegisterScreen.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
}