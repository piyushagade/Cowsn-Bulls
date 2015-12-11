package com.jp.cowsnbulls;

import java.util.Random;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class GameActivity extends Activity {

	String d_1,d_2,d_3,d_4;
	TextView tv_d_1,tv_d_2,tv_d_3,tv_d_4,tv_bulls;
	LinearLayout rl_feed;
	RelativeLayout rl_body;	
	ImageButton i_reload,i_scores;
	SQLiteDatabase db_scoreinfo=null;	
	Random r;
	String[] rno=new String[4];
	String[] uip=new String[4];
	int bg_index;
	int blank;
	int feed_count=0;
	int bulls=0;
	int cows=0;
	int least_score_yet;
	String[] bg = {"#ff3434","#f834ff","#3442ff","#34b0ff","#34ff34","#8fff34","#ffe034","#ff6701","#fa1313"};
	boolean distinct=true,cont;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,        
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);

		tv_d_1 = (TextView) findViewById(R.id.d_1);
		tv_d_2 = (TextView) findViewById(R.id.d_2);
		tv_d_3 = (TextView) findViewById(R.id.d_3);
		tv_d_4 = (TextView) findViewById(R.id.d_4);
		tv_bulls = (TextView)findViewById(R.id.tv_bulls);

		// Users DB create
		db_scoreinfo=openOrCreateDatabase("table_score", MODE_PRIVATE, null);
		db_scoreinfo.execSQL("create table if not exists users(id INTEGER PRIMARY KEY, leastmoves number)");

		Cursor c1=db_scoreinfo.rawQuery("select * from users",null);	//Only one user saved
		c1.moveToFirst();
		if(c1.getCount()>0)
		{
			// Get values
			least_score_yet=c1.getInt(1);

		}
		else //If no rows in the table
		{
			db_scoreinfo.execSQL("insert into  users values('1','9999')");  // first-time run row creator
		}

		rl_feed = (LinearLayout) findViewById(R.id.ll_feed);
		rl_body = (RelativeLayout) findViewById(R.id.rl_body);

		i_reload = (ImageButton) findViewById(R.id.i_reload);
		i_scores = (ImageButton) findViewById(R.id.i_scores);

		Button done = (Button) findViewById(R.id.b_done);

		// Lock Digits
		tv_d_2.setOnLongClickListener(new View.OnLongClickListener(){
			public boolean onLongClick(View arg0) {
				if(tv_d_2.isEnabled()==true)
				{
					tv_d_2.setBackgroundResource(R.drawable.bg_lock);
					tv_d_2.setEnabled(false);

				}
				else
				{
					tv_d_2.setBackgroundResource(R.drawable.bg);
					tv_d_2.setEnabled(true);
				}

				return false;
			}
		});

		tv_d_3.setOnLongClickListener(new View.OnLongClickListener(){
			public boolean onLongClick(View arg0) {
				if(tv_d_3.isEnabled())
				{
					tv_d_3.setBackgroundResource(R.drawable.bg_lock);
					tv_d_3.setEnabled(false);
				}
				else
				{
					tv_d_3.setBackgroundResource(R.drawable.bg);
					tv_d_3.setEnabled(true);
				}

				return true;
			}
		});

		tv_d_4.setOnLongClickListener(new View.OnLongClickListener(){
			public boolean onLongClick(View arg0) {
				if(tv_d_4.isEnabled())
				{
					tv_d_4.setBackgroundResource(R.drawable.bg_lock);
					tv_d_4.setEnabled(false);
				}
				else
				{
					tv_d_4.setBackgroundResource(R.drawable.bg);
					tv_d_4.setEnabled(true);
				}

				return true;
			}
		});

		tv_d_1.setOnLongClickListener(new View.OnLongClickListener(){
			public boolean onLongClick(View arg0) {
				if(tv_d_1.isEnabled())
				{
					tv_d_1.setBackgroundResource(R.drawable.bg_lock);
					tv_d_1.setEnabled(false);
				}
				else
				{
					tv_d_1.setBackgroundResource(R.drawable.bg);
					tv_d_1.setEnabled(true);
				}

				return false;
			}
		});

		// Change Focus
		tv_d_1.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				if(tv_d_1.getText().length()!=0)
				{
					tv_d_2.requestFocus();
				}

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub

			}
		});

		tv_d_2.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				if(tv_d_2.getText().length()!=0)
				tv_d_3.requestFocus();


			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub

			}
		});

		tv_d_3.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				if(tv_d_3.getText().length()!=0)
				tv_d_4.requestFocus();


			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub

			}
		});

		tv_d_4.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				if(tv_d_4.getText().length()!=0)
				tv_d_1.requestFocus();


			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub

			}
		});



		initializeGame();

		//		tv_bulls.setText(rno[0]+" "+rno[1]+" "+rno[2]+" "+rno[3]);



		i_reload.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				rl_feed.removeAllViews();
				initializeGame();
			}

		});
		
		i_scores.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				tv_bulls.setText(String.valueOf(least_score_yet));
			}

		});



		done.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				cont=true;

				uip[0] = tv_d_1.getText().toString();
				uip[1] = tv_d_2.getText().toString();
				uip[2] = tv_d_3.getText().toString();
				uip[3] = tv_d_4.getText().toString();



				if(uip[0].equals("")||uip[2].equals("")||uip[3].equals("")||uip[1].equals(""))
				{
					Toast.makeText(getApplicationContext(), "Please do not leave any field empty.", Toast.LENGTH_SHORT).show();
					tv_d_1.setBackgroundResource(R.drawable.bg_red);
					tv_d_2.setBackgroundResource(R.drawable.bg_red);
					tv_d_3.setBackgroundResource(R.drawable.bg_red);
					tv_d_4.setBackgroundResource(R.drawable.bg_red);
					cont=false;
				}
				else
				{	
					tv_d_1.setBackgroundResource(R.drawable.bg);
					tv_d_2.setBackgroundResource(R.drawable.bg);
					tv_d_3.setBackgroundResource(R.drawable.bg);
					tv_d_4.setBackgroundResource(R.drawable.bg);

					for(int i=0;i<3;i++)
						for(int k=i+1;k<4;k++)
							if (uip[i].equals(uip[k]))
							{
								if(i==0)
									tv_d_1.setBackgroundResource(R.drawable.bg_red);
								else if (i==1)
									tv_d_2.setBackgroundResource(R.drawable.bg_red);
								else if (i==2)
									tv_d_3.setBackgroundResource(R.drawable.bg_red);
								else if (i==3)
									tv_d_4.setBackgroundResource(R.drawable.bg_red);

								if(k==0)
									tv_d_1.setBackgroundResource(R.drawable.bg_red);
								else if (k==1)
									tv_d_2.setBackgroundResource(R.drawable.bg_red);
								else if (k==2)
									tv_d_3.setBackgroundResource(R.drawable.bg_red);
								else if (k==3)
									tv_d_4.setBackgroundResource(R.drawable.bg_red);

								cont=false;
								break;
							}

					if (cont)
					{
						tv_d_1.setHint(tv_d_1.getText().toString());
						tv_d_2.setHint(tv_d_2.getText().toString());
						tv_d_3.setHint(tv_d_3.getText().toString());
						tv_d_4.setHint(tv_d_4.getText().toString());

						tv_d_1.setText("");
						tv_d_2.setText("");
						tv_d_3.setText("");
						tv_d_4.setText("");

						bulls=0;
						cows=0;
						for(int i=0;i<4;i++)
							if(uip[i].equals(rno[i]))
								bulls+=1;						




						for(int i=0;i<4;i++)
							for(int k=0;k<4;k++)
								if (uip[i].equals(rno[k]))
								{
									cows+=1;
								}

						cows=cows-bulls;

						tv_bulls.setText("Bulls: "+String.valueOf(bulls)+" Cows: "+String.valueOf(cows));

						final TextView row = new TextView(getBaseContext());
						row.setTextAppearance(getBaseContext(),android.R.style.TextAppearance_Medium);
						row.setText(uip[0]+" "+uip[1]+" "+uip[2]+" "+uip[3]+"       "+"Bulls: "+String.valueOf(bulls)+"   Cows: "+String.valueOf(cows));
						row.setTextColor(Color.parseColor("#000000"));
						feed_count+=1;


						rl_feed.addView(row);

						if(bulls==4&&cows==0)
						{
							tv_bulls.setText("Completed in: "+String.valueOf(feed_count)+" steps.");
							if(least_score_yet>feed_count)
							{
								db_scoreinfo.execSQL("update table_score set leastmoves='"+String.valueOf(feed_count)+"' ");
								tv_bulls.setText("This is the least numbers of moves you solved the puzzle yet.");
							}
							else
								tv_bulls.setText("This is the least numbers of moves you solved the puzzle yet.");
						}
					}
				}
			}

		});
		


	}

	@SuppressLint("ShowToast")
	void sendToast(Object charSequence){
		Toast.makeText(getBaseContext(), (String)charSequence, Toast.LENGTH_SHORT);

	}

	public void initializeGame(){
		tv_bulls.setText("");
		
		tv_d_1.setHint("");
		tv_d_2.setHint("");
		tv_d_3.setHint("");
		tv_d_4.setHint("");
		
		// Random Digits Generator
		r = new Random();

		// Random Background Color
		bg_index=r.nextInt(8);
		rl_body.setBackgroundColor(Color.parseColor(bg[bg_index]));

		// Distinct digits generator
		do{
			for(int i = 0;i<4;i++)
				rno[i]=String.valueOf(r.nextInt(10));

			distinct=true;

			for(int i=0;i<3;i++)
				for(int k=i+1;k<4;k++)
					if (rno[i].equals(rno[k]))
					{
						distinct=false;
						break;
					}


		}while(distinct==false);
	}
}
