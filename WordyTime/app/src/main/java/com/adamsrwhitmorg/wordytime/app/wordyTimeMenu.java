// Rachel Adams & Grace Whitmore
// 4/9/14
// Mobile App: Assignment 1
// A game where you make anagram substrings out of a given word.
// Now with new and resume current game options!


package com.adamsrwhitmorg.wordytime.app;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import android.view.View;
import android.widget.Button;

public class wordyTimeMenu extends Activity implements View.OnClickListener {

    public static final String MyPREFERENCES = "MyPrefs";
    public static final String CurrentWord = "wordKey";
    public static final String CurrentScore = "scoreKey";

    SharedPreferences saveData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Instantiate and listen for resume and new game buttons
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wordy_time_menu);

        Button button = (Button)this.findViewById(R.id.newGameButton);
        button.setOnClickListener(this);

        Button resumeButton = (Button)this.findViewById(R.id.resumeGameButton);
        resumeButton.setOnClickListener(this);
    }

    public void onClick(View view) {
        // Reset score and word if new game button is clicked,
        // load previous if resume game is clicked.
        if (view.getId() == R.id.newGameButton) {
            saveData = getSharedPreferences(MyPREFERENCES, 0);
            SharedPreferences.Editor editor = saveData.edit();
            editor.putString("CurrentWord", "");
            editor.putInt("CurrentScore", 0);
            editor.commit();
            startActivity(new Intent(getApplicationContext(), WordyTime.class));
        }
        if (view.getId() == R.id.resumeGameButton){
            startActivity(new Intent(getApplicationContext(), WordyTime.class));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.wordy_time_menu, menu);
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
