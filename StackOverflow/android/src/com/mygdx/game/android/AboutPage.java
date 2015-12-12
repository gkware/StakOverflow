package com.mygdx.game.android;

import android.os.Bundle;
import android.content.Intent;
import android.app.Activity;
import android.view.View;
import android.widget.Button;

public class AboutPage extends MainActivity {

    private Button ReturnToMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_page);


        Button ReturnToMain = (Button) findViewById(R.id.ReturnToMain);
        ReturnToMain.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AboutPage.this, MainActivity.class);
                startActivity(intent);
            }

        });
    }
}