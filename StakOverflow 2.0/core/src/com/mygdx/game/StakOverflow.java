package com.mygdx.game;

import java.util.Iterator;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;


public class StakOverflow extends ApplicationAdapter {

	private Texture codeimage;
	private Texture dougimage;
	private Texture errorimage;
	private Texture playimage;
	private Texture retryimage;
    private Texture titleimage;
	private Texture gameoverimage;
	private Texture backgroundimage;
	private Texture iconimage;
	private OrthographicCamera camera; //Camera
	private SpriteBatch batch; //Sprites
	private Rectangle doug;
	private Rectangle playbutton;
	private Rectangle retrybutton;
	private Array<Rectangle> codearray;
	private Array<Rectangle> errorarray;
	private long lastCodeTime;
	private long lastErrorTime;
	public double getcodex;
	public double getcodey;
	public double geterrorx;
	int score;
	public int highscore;
	public int permhighscore;
	private String yourScoreName;
	private String yourHighScoreName;
	BitmapFont yourBitmapFontName;
	private boolean dougheld;
	private int state = 0;
	static int MENU_STATE = 0;
	static int GAME_STATE = 1;
	static int END_STATE = 2;

	private void fallingcode()
	{
		Rectangle code = new Rectangle();
		code.width = codeimage.getWidth();
		code.height = codeimage.getHeight();
		code.x = MathUtils.random(0, Gdx.graphics.getWidth() - code.width) % Gdx.graphics.getWidth(); //Random between 0 and right hand side
		code.y = Gdx.graphics.getHeight();
		getcodex = code.x; //getter for spawnBombs method
		getcodey = code.y; //getter for spawnBombs method
		codearray.add(code); //Add candy to candy array
		lastCodeTime = TimeUtils.nanoTime();

	}

	private void fallingerror()
	{
		Rectangle error = new Rectangle();
		error.width = errorimage.getWidth();
		error.height = errorimage.getHeight();
		error.x = MathUtils.random(0, Gdx.graphics.getWidth() - error.width)% (Gdx.graphics.getWidth()); //Random between 0 and right hand side
		error.y = Gdx.graphics.getHeight();
		geterrorx = error.x;
		lastErrorTime = TimeUtils.nanoTime();
		if(Math.abs(getcodex - error.x) > Gdx.graphics.getWidth()/4 && TimeUtils.nanoTime() - lastCodeTime > 100050000) //Only spawn bomb if not near candy
			errorarray.add(error); //Add bomb to bomb array


	}

	@Override
	public void create () //Load sprites
	{

		//Sprites here
		codeimage  = new Texture(Gdx.files.internal("code.gif"));
		dougimage = new Texture(Gdx.files.internal("douggie3.gif"));
		errorimage = new Texture(Gdx.files.internal("error.gif"));
		playimage = new Texture(Gdx.files.internal("play.gif"));
		retryimage = new Texture(Gdx.files.internal("playagain.gif"));
		gameoverimage = new Texture(Gdx.files.internal("gameover.gif"));
		backgroundimage = new Texture(Gdx.files.internal("playgame.jpg"));
		iconimage = new Texture(Gdx.files.internal("gameover3.gif"));
        titleimage = new Texture(Gdx.files.internal("stakoverflow.gif"));

		//Camera stuff here
		camera = new OrthographicCamera(); //Create new camera
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); //Set camera. False means y points up
		batch = new SpriteBatch(); //Helper class to draw images

		//Instantiate hippo 'rectangle'
		doug = new Rectangle();
		doug.width = (int) (Gdx.graphics.getWidth() *dougimage.getWidth() /480);
		doug.height = Gdx.graphics.getHeight() * dougimage.getHeight() /800;
		doug.x = Gdx.graphics.getWidth()/2 - doug.width / 2;
		doug.y = 0;

		//Instantiate candy
		codearray = new Array<Rectangle>();

		//Instantiate bombs
		errorarray = new Array<Rectangle>();
		fallingerror();

		//Buttons
		playbutton = new Rectangle();
		retrybutton = new Rectangle();

		//Load high score from phone
		Preferences prefs = Gdx.app.getPreferences("highscore");
		permhighscore = prefs.getInteger("highscore");

		//Score
		score = 0;
		highscore = 0;
		yourScoreName = "Score: 0";
		if (highscore == 0)
		{
			yourHighScoreName = "High Score: 0";
		}
		else
		{
			yourHighScoreName = "High Score: " + highscore;
		}
		yourBitmapFontName = new BitmapFont();
		yourBitmapFontName.getData().scale(2);


	}
	public void updatebutton()
	{
		camera.update(); //Update camera once per frame
		if(Gdx.input.isTouched())
		{
			Vector3 touchPos2 = new Vector3();
			touchPos2.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos2);
			if(playbutton.contains(touchPos2.x, touchPos2.y)) //If rectangle for play contains finger touch, change state
				state = GAME_STATE;

		}
	}

	public void updateretry()
	{
		camera.update(); //Update camera once per frame
		if(Gdx.input.isTouched())
		{
			Vector3 touchPos3 = new Vector3();
			touchPos3.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos3);
			if(retrybutton.contains(touchPos3.x, touchPos3.y)) //If rectangle for play contains finger touch, change state
				state = GAME_STATE;

		}
	}

	public void update()
	{
		camera.update(); //Update camera once per frame

		//Check if touched
		if(Gdx.input.isTouched())
		{
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);
			if(!dougheld && doug.contains(touchPos.x, touchPos.y)) //If rectangle for doug contains finger touch, the hippo is held
				dougheld = true;

			if(dougheld)
				doug.x = touchPos.x - doug.width /2; //Adding on change in mouse position
		}
		else
			dougheld = false;

		if(doug.x < 0) //Doesn't let hippo go left past bound
			doug.x = 0;

		if(doug.x + doug.getWidth() > Gdx.graphics.getWidth()) //Doesn't let doug go right past bound
			doug.x = Gdx.graphics.getWidth() - doug.getWidth();

		//Creates new bomb if certain criteria are met
		if(Math.abs(getcodex - geterrorx) > Gdx.graphics.getWidth()/4 && TimeUtils.nanoTime() - lastCodeTime > 100050000 && (TimeUtils.nanoTime() - lastErrorTime)/2 > 1000000000)
			fallingerror();

		//Creates new candy after a certain amount of time
		if(TimeUtils.nanoTime() - lastCodeTime > (500000000 - (score * 3000000)))
			fallingcode();


		//Iterate through bomb array
		Iterator<Rectangle> iterbomb = errorarray.iterator();
		while(iterbomb.hasNext())
		{
			Rectangle bomb = iterbomb.next();

			if(bomb.y +64 < 0) //If bomb does NOT hit hippo add point, delete
			{
				iterbomb.remove();
				score++;
				yourScoreName = "Score: " + score;
				if (score > highscore)
				{
					highscore = score;
					yourHighScoreName = "High Score: " + highscore;
				}
			}
			else if (bomb.overlaps(doug))
			{
				iterbomb.remove();
				state = END_STATE;
			}

			bomb.y = (float) (bomb.y - ((750 + 2.8*(score +1) ) * Gdx.graphics.getDeltaTime())); //Move n pixels/unit (changes based on score to make it harder:) )

		}

		//Iterate through candy array
		Iterator<Rectangle> iter = codearray.iterator();
		while(iter.hasNext())
		{
			Rectangle candy = iter.next();

			if(candy.y +64 < 0) //When it leaves screen remove candy
			{
				iter.remove();
				state = END_STATE;
			}

			candy.y = (float) (candy.y - ((750 + 2.8*(score +1) ) * Gdx.graphics.getDeltaTime())); //Move n pixels/unit (changes based on score to make it harder:) )

			//When candy hits hippo get rid of it and add score
			if(candy.overlaps(doug))
			{
				iter.remove();
				score++;
				yourScoreName = "Score: " + score;
				if (score > highscore)
				{
					highscore = score;
					yourHighScoreName = "High Score: " + highscore;
				}
			}
		}

	}

	@Override
	public void render()
	{
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


		if (state == MENU_STATE)
		{
			updatebutton();
			camera.update();
			batch.setProjectionMatrix(camera.combined);

			//Draw background
			batch.setProjectionMatrix(camera.combined);
			batch.begin();
			batch.draw(backgroundimage, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			batch.end();

            //Draw title icon
            batch.setProjectionMatrix(camera.combined);
            batch.begin();
            batch.draw(titleimage, Gdx.graphics.getWidth()/4 - playbutton.width/2, Gdx.graphics.getHeight()/4 - playbutton.height / 2, Gdx.graphics.getWidth() * titleimage.getWidth() / 480, Gdx.graphics.getHeight() * titleimage.getHeight() / 800);
            batch.end();

			//Draw in Doug icon
			batch.setProjectionMatrix(camera.combined);
			batch.begin();
			batch.draw(iconimage, Gdx.graphics.getWidth() / 2 - playbutton.width / 2, Gdx.graphics.getHeight()/2 - playbutton.height / 2, Gdx.graphics.getWidth() * iconimage.getWidth() /480, Gdx.graphics.getHeight() * iconimage.getHeight() /800);
			batch.end();

			//Render in Play Box
			playbutton.width = (int) (Gdx.graphics.getWidth() * playimage.getWidth() /480);
			playbutton.height = Gdx.graphics.getHeight() * playimage.getHeight() /800;
			playbutton.x = Gdx.graphics.getWidth()/2 - playbutton.width / 2;
			playbutton.y = Gdx.graphics.getHeight()/3 - playbutton.height / 2;

			//Draw in play button
			batch.setProjectionMatrix(camera.combined);
			batch.begin();
			batch.draw(playimage, playbutton.x, playbutton.y, Gdx.graphics.getWidth() * playimage.getWidth() /480, Gdx.graphics.getHeight() * playimage.getHeight() /800);
			batch.end();


		}

		else if(state == GAME_STATE)
		{
			update();

			//Draw background
			batch.setProjectionMatrix(camera.combined);
			batch.begin();
			batch.draw(backgroundimage, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			batch.end();

			//Text Score
			batch.begin();
			yourBitmapFontName.setColor(1.0f, 0, 0, 1.0f);
			yourBitmapFontName.draw(batch, yourScoreName, (float)(Gdx.graphics.getWidth() * .05), (float)(Gdx.graphics.getHeight() * .98));
			batch.end();

			//Render Hippo
			batch.setProjectionMatrix(camera.combined);
			batch.begin();
			batch.draw(dougimage, doug.x, doug.y, Gdx.graphics.getWidth() * dougimage.getWidth() /480, Gdx.graphics.getHeight() * dougimage.getHeight() /800);
			batch.end();

			//Render candy
			batch.begin();
			for(Rectangle candy: codearray) {
				batch.draw(codeimage, candy.x, candy.y, Gdx.graphics.getWidth() * codeimage.getWidth() /480, Gdx.graphics.getHeight() * codeimage.getHeight() /800);
			}
			batch.end();

			//Render bombs
			batch.begin();
			for(Rectangle bomb: errorarray) {
				batch.draw(errorimage, bomb.x, bomb.y, Gdx.graphics.getWidth() * errorimage.getWidth() /480, Gdx.graphics.getHeight() * errorimage.getHeight() /800);
			}
			batch.end();

		}

		else if(state == END_STATE)
		{
			updateretry();
			camera.update();
			batch.setProjectionMatrix(camera.combined);

			//Draw background
			batch.setProjectionMatrix(camera.combined);
			batch.begin();
			batch.draw(backgroundimage, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			batch.end();

			//Render in retry Box
			retrybutton.width = (int) (Gdx.graphics.getWidth() * retryimage.getWidth() /480);
			retrybutton.height = Gdx.graphics.getHeight() * retryimage.getHeight() /800;
			retrybutton.x = Gdx.graphics.getWidth()/2 - retrybutton.width / 2;
			retrybutton.y = (float) (Gdx.graphics.getHeight()/1.3 - retrybutton.height / 2);

			//Draw in retry button
			batch.setProjectionMatrix(camera.combined);
			batch.begin();
			batch.draw(retryimage, retrybutton.x, retrybutton.y, Gdx.graphics.getWidth() * retryimage.getWidth() /480, Gdx.graphics.getHeight() * retryimage.getHeight() /800);
			batch.end();

			//Draw game over image
			batch.setProjectionMatrix(camera.combined);
			batch.begin();
			batch.draw(gameoverimage, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getWidth());
			batch.end();

			//Draw in high score
			batch.begin();
			yourBitmapFontName.setColor(1.0f, 0, 0, 1.0f);
			if(permhighscore > highscore)//Draw correct high score
			{
				yourHighScoreName = "High Score: " + permhighscore;
				yourBitmapFontName.draw(batch, yourHighScoreName, (float)(Gdx.graphics.getWidth() * .05), (float)(Gdx.graphics.getHeight() * .98));
			}
			else
				yourBitmapFontName.draw(batch, yourHighScoreName, (float)(Gdx.graphics.getWidth() * .05), (float)(Gdx.graphics.getHeight() * .98));

			batch.end();

			if(highscore > permhighscore) //Only write if new highscore is higher then old
			{
				Preferences prefs = Gdx.app.getPreferences("highscore");
				prefs.putInteger("highscore", highscore);
				prefs.flush();
			}
			//Clear the board
			codearray.clear();
			errorarray.clear();
			doug.x = Gdx.graphics.getWidth()/2 - doug.width / 2;
			score = 0;
			yourScoreName = "Score: " + score;
		}

	}

	@Override
	public void dispose() {
		// dispose of all the native resources
		dougimage.dispose();
		codeimage.dispose();
		errorimage.dispose();
		yourBitmapFontName.dispose();
		batch.dispose();
		if(highscore > permhighscore) //Only write if new highscore is higher then old
		{
			Preferences prefs = Gdx.app.getPreferences("highscore");
			prefs.putInteger("highscore", highscore);
			prefs.flush();
		}

	}

}
