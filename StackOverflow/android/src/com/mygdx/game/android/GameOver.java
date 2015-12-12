package com.mygdx.game.android;

/**
 * Created by Jenna Zhu on 12/11/2015.
 */
        import android.os.Bundle;
        import android.content.Intent;
        import android.view.View;
        import android.widget.Button;

        import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class GameOver extends MainActivity {

    private Button MainMenuButton;
    private Button PlayAgainButton;
    int score;
    public int highscore;
    private String yourScoreName;
    private String yourHighScoreName;
    BitmapFont yourBitmapFontName;
    private int state = 0;
    static int MENU_STATE = 0;
    static int GAME_STATE = 1;
    static int END_STATE = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        //for play again button
        Button PlayAgainButton = (Button) findViewById(R.id.PlayAgainButton);
        PlayAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GameOver.this, PlayGame.class);
                startActivity(intent);
            }

        });

        //for return to menu button
        Button MainMenuButton = (Button) findViewById(R.id.MainMenuButton);
        MainMenuButton.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(GameOver.this, MainActivity.class);
                startActivity(intent);
            }

        });
    }

}
