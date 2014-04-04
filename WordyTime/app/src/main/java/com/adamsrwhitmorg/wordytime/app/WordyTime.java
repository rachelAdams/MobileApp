package com.adamsrwhitmorg.wordytime.app;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.view.KeyEvent;
import android.view.View.OnKeyListener;

import java.io.BufferedReader;
import java.io.Console;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import android.widget.Toast;

public class WordyTime extends Activity {

    BufferedReader dictFile;
    File file = new File("./words10thou.txt");
    {
        try {
            dictFile = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    String word;
    String userWord;
    //final EditText ET = (EditText) findViewById(R.id.editText);
    private EditText edittext;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wordy_time);
        addKeyListener();


    }

    public void addKeyListener(){
        edittext = (EditText) findViewById(R.id.editText);
        edittext.setOnKeyListener(new OnKeyListener(){

        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_ENTER){
                //Toast.makeText(WordyTime.this, edittext.getText(), Toast.LENGTH_LONG).show();
                userWord = edittext.getText().toString().trim();
                try {
                    isAWord();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return true;
            }
            return false;
        }
        });
    }


    public void pickRandomWord() throws IOException{
        Random random = new Random();
        int randInt = random.nextInt(10000);
        int count = 0;
        String startWord = "";

        String line;
        line = dictFile.readLine();
        while (line != null && count != randInt){
            startWord = line;
            count++;
        }
        startWord.trim();

    }

    public void isAWord() throws IOException{
        String line = null;
        String s;
        if ((s = dictFile.readLine()) != null){
            line = s;
        }
        else{
            Toast.makeText(WordyTime.this, userWord, Toast.LENGTH_LONG).show();}

        //line = dictFile.readLine().toString();


//        while (line != null && line != userWord)
//        {
//
//           line = dictFile.readLine().trim();


//        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.wordy_time, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
