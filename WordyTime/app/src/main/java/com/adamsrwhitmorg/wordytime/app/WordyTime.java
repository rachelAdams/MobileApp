package com.adamsrwhitmorg.wordytime.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.KeyEvent;
import android.view.View.OnKeyListener;

import java.io.BufferedReader;
import java.io.Console;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import android.widget.Toast;
import android.view.View;
import android.widget.Button;

import static android.view.View.OnClickListener;

public class WordyTime extends Activity implements View.OnClickListener  {

    BufferedReader dictFile;
    File file = new File("sdcard/Download/words10thou.txt");

    TextView currentWord;
    TextView currentScore;

    public static final String MyPREFERENCES = "MyPrefs";
    public static final String CurrentWord = "wordKey";
    public static final String CurrentScore = "scoreKey";

    String word;
    String userWord;

    int score = 0;

    List<String> usedWords = new ArrayList<String>();

    private EditText edittext;

    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wordy_time);

        addKeyListener();

        Button button = (Button)this.findViewById(R.id.newWordButton);
        button.setOnClickListener(this);
        Button menuButton = (Button)this.findViewById(R.id.menuButton);
        menuButton.setOnClickListener(this);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, 0);
        /*SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("CurrentWord", "");
        editor.putInt("CurrentScore", 0);
        editor.commit();*/
        if (sharedpreferences.getString("CurrentWord", "") == ""){
            getNewWord();
            score = 0;
        }
        else{
            word = sharedpreferences.getString("CurrentWord", "");
            score = sharedpreferences.getInt("CurrentScore", 0);
            TextView textToDisplay = (TextView)findViewById(R.id.displayWord);
            textToDisplay.setText(word);
            TextView scoreToDisplay = (TextView)findViewById(R.id.scoreNumber);
            scoreToDisplay.setText(Integer.toString(score));
        }
    }

    public void onClick(View view) {
        if (view.getId() == R.id.newWordButton) {
            getNewWord();
        }
        if (view.getId() == R.id.menuButton){
            startActivity(new Intent(getApplicationContext(), wordyTimeMenu.class));
        }
    }

    public void getNewWord(){

        try{
            TextView textToDisplay = (TextView)findViewById(R.id.displayWord);
            dictFile = new BufferedReader(new FileReader(file));
            pickRandomWord();

            textToDisplay.setText(word);
        }
        catch(IOException e) {e.printStackTrace();}
    }

    public void addKeyListener(){
        edittext = (EditText) findViewById(R.id.editText);
        edittext.setOnKeyListener(new OnKeyListener(){

        public boolean onKey(View v, int keyCode, KeyEvent event) {

            if (keyCode == KeyEvent.KEYCODE_ENTER){
                if (event.getAction()!=KeyEvent.ACTION_DOWN)
                    return true;
                else{
                userWord = edittext.getText().toString().trim();

                try {

                    Log.i("wat", "calling is a word");
                    isAWord(true);

                } catch (IOException e) {
                    e.printStackTrace();
                }
                return true;
            }

            }
            return false;}
        });
    }


    public void pickRandomWord() throws IOException{
        Random random = new Random();
        int randInt = random.nextInt(10000);
        int count = 0;

        String line;

        line = dictFile.readLine();
        while (line != null && count != randInt){
            line = dictFile.readLine();
            word = line;
            count++;
        }
        word = word.trim();

    }

    public void isAWord(boolean newWord) throws IOException{
        StringBuilder mutableWord = new StringBuilder(word);
        StringBuilder mutableWord2 = new StringBuilder(userWord);
        if (!(usedWords.contains(userWord) && newWord)){

        for (int x=0; x<userWord.length(); x++)
        {
            int letterIndex = mutableWord.indexOf(Character.toString(mutableWord2.charAt(x)));
            if (letterIndex != -1) {
                mutableWord.deleteCharAt(letterIndex);

            }
            else{
                Toast.makeText(WordyTime.this, "One of those letters isn't in the word!", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        dictFile = new BufferedReader(new FileReader(file));
        String line =  dictFile.readLine();
        while ((line != null) ){

            if (line.equals(userWord)){
                score += userWord.length();
                TextView scoreToDisplay = (TextView)findViewById(R.id.scoreNumber);
                scoreToDisplay.setText(Integer.toString(score));
                sharedpreferences = getSharedPreferences(MyPREFERENCES, 0);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("CurrentWord", word);
                editor.putInt("CurrentScore", score);
                editor.commit();

                Toast.makeText(WordyTime.this, "Nice!", Toast.LENGTH_SHORT).show();
                usedWords.add(userWord);
                return;

            }
            line = dictFile.readLine();
        }
        Toast.makeText(WordyTime.this, "Oof. Not in our (shabby) dictionary.", Toast.LENGTH_SHORT).show();
        usedWords.add(userWord);
            return;
        }
    else{
        Toast.makeText(WordyTime.this, "You already tried that word, dummy.", Toast.LENGTH_SHORT).show();
        }
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
