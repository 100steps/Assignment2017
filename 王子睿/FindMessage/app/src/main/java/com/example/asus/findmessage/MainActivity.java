package com.example.asus.findmessage;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    protected EditText editTextName, editTextNumber;
    protected Button buttonFind, buttonStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextName=(EditText)findViewById(R.id.editTextName);
        editTextNumber=(EditText)findViewById(R.id.editTextNumber);
        buttonFind=(Button)findViewById(R.id.buttonFind);
        buttonStore=(Button)findViewById(R.id.buttonStore);

        final Message message=new Message();

        buttonFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                message.NameGot=editTextName.getText().toString();
                editTextNumber.setText(message.getNumber());

            }
        });

        buttonStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                message.NameGot=editTextName.getText().toString();
                message.NumGot=editTextNumber.getText().toString();
                message.record++;
                message.storeMessage();
            }
        });


    }

}
