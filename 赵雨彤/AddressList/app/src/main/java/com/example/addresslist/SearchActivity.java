package com.example.addresslist;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class SearchActivity extends AppCompatActivity{

    public static final String EMPTY = "";
    private EditText nameEditText;
    private TextView numberTextView,telTextView;
    private String tel = null;
    private String number = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_layout);

        nameEditText = (EditText) findViewById(R.id.Edit_name);
        numberTextView = (TextView) findViewById(R.id.Edit_number);
        telTextView = (TextView) findViewById(R.id.Edit_tel);
        Button searchButton = (Button) findViewById(R.id.searchButton);
        Button returnButton = (Button) findViewById(R.id.returnButton);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String gotName = nameEditText.getText().toString();

                DatabaseHelper dbHelper = new DatabaseHelper(SearchActivity.this,"student");
                SQLiteDatabase db = dbHelper.getReadableDatabase();

                Cursor cursor = db.rawQuery("select * from student where name like ?",new String[]{gotName});
                int record = 0;
                //游标查找
                while(cursor.moveToNext()){
                    tel = cursor.getString(cursor.getColumnIndex("tel"));
                    number = cursor.getString(cursor.getColumnIndex("number"));
                    record++;
                }
                cursor.close();

                //判断是否搜索到结果
                if(record == 0){
                    number = EMPTY;
                    tel = EMPTY;
                    Toast.makeText(SearchActivity.this,"查无此人！",Toast.LENGTH_SHORT).show();
                }

                //显示数据
                numberTextView.setText(number);
                telTextView.setText(tel);
                db.close();

            }
        });
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}