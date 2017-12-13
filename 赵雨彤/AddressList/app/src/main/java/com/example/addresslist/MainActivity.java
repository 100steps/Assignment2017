package com.example.addresslist;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private Button saveButton,queryButton;
    private EditText nameEditText,telEditText,numberEditText;

    public static final String EMPTY = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameEditText = (EditText) findViewById(R.id.Edit_name);
        telEditText = (EditText) findViewById(R.id.Edit_tel);
        numberEditText = (EditText) findViewById(R.id.Edit_number);

        saveButton = (Button) findViewById(R.id.save);
        queryButton = (Button) findViewById(R.id.query);

        saveButton.setOnClickListener(new saveOnClickListener());
        queryButton.setOnClickListener(new queryOnClickListener());


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    //保存数据
    private class saveOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {

            DatabaseHelper dbHelper = new DatabaseHelper(MainActivity.this,"student");
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            //取出EditText的字符串
            String name = nameEditText.getText().toString();
            String tel = telEditText.getText().toString();
            String number = numberEditText.getText().toString();

            //判断三者是否有空
            if(name.equals(EMPTY) || tel.equals(EMPTY) || number.equals(EMPTY)){
                Toast.makeText(MainActivity.this,"信息不能为空！请重新输入",Toast.LENGTH_SHORT).show();
            }
            else {
                db.execSQL("insert into student(name,tel,number) values(?,?,?)",new Object[]{name,tel,number});
            }
            db.close();
        }
    }
    //查询数据
    private class queryOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, SearchActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
