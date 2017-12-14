package com.example.asus.findmessage;

import android.app.AlertDialog;
import android.util.Log;

/**
 * Created by Asus on 2017/12/7.
 */

public class Message {

        protected String[] Name= new String[20];
        protected String[] Number= new String[20];

        public int record=0;

        {
            Number[0]="Not Found";
            for (int i=0;i<20;i++){
                Name[i]="不存在";
            }

        }

    protected String NameGot;
    protected String NumGot;


    public String getNumber() {
        int step=0;
        Log.i("1", Name[1]);
        Log.i("2", NameGot);


        for (int i=1;i< 20;i++){
            if (Name[i].equals(NameGot)){
                step=i;

            }

        }
        Log.i("3", String.valueOf(step));
        return Number[step];
    }

    public void storeMessage(){
        if (record<19){
            Number[record]=NumGot;
            Name[record]=NameGot;

        }
    }
}
