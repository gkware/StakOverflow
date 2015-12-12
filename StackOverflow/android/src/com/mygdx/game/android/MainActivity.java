package com.mygdx.game.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.mygdx.game.android.R;

public class MainActivity extends Activity {

    //Credit to John's Android Studio Tutorials videos on YouTube
    //For the how-to on linking activities and buttons

    private Button PlayButton, InstructionsButton, AboutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button InstructionsButton = (Button) findViewById(R.id.InstructionsButton);
        InstructionsButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, InstructionsPage.class);
                startActivity(intent);
            }

        });

        Button AboutButton = (Button) findViewById(R.id.AboutButton);
        AboutButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(MainActivity.this, com.mygdx.game.android.AboutPage.class);
                startActivity(intent1);
            }

        });

        Button PlayButton = (Button) findViewById(R.id.PlayButton);
        PlayButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(MainActivity.this, PlayGame.class);
                startActivity(intent2);
            }

        });

    }
}