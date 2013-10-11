package com.example.lifememory.activity;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.example.lifememory.R;
import com.example.lifememory.activity.model.BabyJiShiBen;
import com.example.lifememory.activity.model.PregnancyJiShiBen;
import com.example.lifememory.db.service.BabyDiaryJiShiBenService;
import com.example.lifememory.db.service.PregnancyDiaryJiShiBenService;
import com.example.lifememory.dialog.DialogAlert;
import com.example.lifememory.dialog.DialogAlertListener;
import com.example.lifememory.dialog.DialogInputListener;
import com.example.lifememory.dialog.DialogPregnancyJiShiBenReNameDialog;
import com.example.lifememory.utils.MyAnimations;
import com.iflytek.speech.SpeechError;
import com.iflytek.speech.SynthesizerPlayer;
import com.iflytek.speech.SynthesizerPlayerListener;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.style.BackgroundColorSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class BabyJiShiBenReadActivity extends Activity implements SynthesizerPlayerListener{
	private TextView contentEt = null;
	private int[] textSize = {20, 25, 30}; //锟斤拷锟斤拷锟斤拷锟�
	private  int[] colors = new int[6];
	private BabyJiShiBen jishibenItem; //锟斤拷锟节硷拷锟铰憋拷
	private int itemId = 0;
	private TextView titleTv = null;
	private BabyDiaryJiShiBenService dbService = null;
	//锟斤拷Path
	private boolean areButtonsShowing;
	private RelativeLayout composerButtonsWrapper;
	private ImageView composerButtonsShowHideButtonIcon;
	private RelativeLayout composerButtonsShowHideButton;
	
	//缓存对象.
	private SharedPreferences mSharedPreferences;
	//缓冲进度
	private int mPercentForBuffering = 0;
	
	//播放进度
	private int mPercentForPlaying = 0;
	//合成对象.
	private SynthesizerPlayer mSynthesizerPlayer;
	//弹出提示
	private Toast mToast;
	
	
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			//锟斤拷取
			case 0:
				initViews();
				break;
			//删锟斤拷
			case 1:
				BabyJiShiBenReadActivity.this.finish();
				overridePendingTransition(R.anim.activity_steady, R.anim.activity_down);
				Toast.makeText(BabyJiShiBenReadActivity.this, "删锟斤拷锟秸记成癸拷!", 0).show();
				break;
			//锟斤拷锟斤拷锟斤拷
			case 2:
				Toast.makeText(BabyJiShiBenReadActivity.this, "锟秸硷拷锟斤拷锟斤拷锟斤拷晒锟�", 0).show();
				break;
		
			}
		};
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.baby_pregnancydiray_jishiben_read);
		
		dbService = new BabyDiaryJiShiBenService(this);
		
		MyAnimations.initOffset(BabyJiShiBenReadActivity.this);
		
		initColors();   //锟斤拷始锟斤拷锟斤拷色锟斤拷锟斤拷
		findViews();    //实锟斤拷锟斤拷图
		
		mSharedPreferences = getSharedPreferences(getPackageName(),
				MODE_PRIVATE);

		mToast = Toast.makeText(this,
				String.format(getString(R.string.tts_toast_format), 0, 0),
				Toast.LENGTH_LONG);
		new InitDatasThread().start(); //锟斤拷锟竭筹拷锟叫筹拷始锟斤拷锟斤拷荩锟斤拷锟斤拷锟斤拷叱锟斤拷锟斤拷锟饺撅拷锟酵�
//		initDatas();    //锟斤拷始锟斤拷锟斤拷锟�
//		initViews();	//锟斤拷锟斤拷锟酵硷拷锟斤拷锟�
		
		
		composerButtonsShowHideButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!areButtonsShowing) {
					MyAnimations.startAnimationsIn(composerButtonsWrapper, 300);
					composerButtonsShowHideButtonIcon
							.startAnimation(MyAnimations.getRotateAnimation(0,
									-270, 300));
				} else {
					MyAnimations
							.startAnimationsOut(composerButtonsWrapper, 300);
					composerButtonsShowHideButtonIcon
							.startAnimation(MyAnimations.getRotateAnimation(
									-270, 0, 300));
				}
				areButtonsShowing = !areButtonsShowing;
			}
		});
		for (int i = 0; i < composerButtonsWrapper.getChildCount(); i++) {
			composerButtonsWrapper.getChildAt(i).setOnClickListener(
					new View.OnClickListener() {
						@Override
						public void onClick(View view) {
							switch (view.getId()) {
							//锟睫革拷
							case R.id.composer_button_edit:
								Intent intent = new Intent(BabyJiShiBenReadActivity.this, BabyJiShiBenEditActivity.class);
								intent.putExtra("itemId", itemId);
								BabyJiShiBenReadActivity.this.startActivity(intent);
								overridePendingTransition(R.anim.activity_up, R.anim.activity_steady);
								break;
							//锟斤拷锟斤拷
							case R.id.composer_button_read:
								MyAnimations.startAnimationsOut(
										composerButtonsWrapper, 300);
								composerButtonsShowHideButtonIcon
										.startAnimation(MyAnimations
												.getRotateAnimation(-270, 0,
														300));
								areButtonsShowing = false;
								synthetizeInSilence();
								break;
							//锟斤拷锟斤拷锟斤拷
							case R.id.composer_button_rename:
								new DialogPregnancyJiShiBenReNameDialog(BabyJiShiBenReadActivity.this, listener2, titleTv.getText().toString(), R.layout.dialog_rename).show();
								break;
							//删锟斤拷
							case R.id.composer_button_del:
								new DialogAlert(BabyJiShiBenReadActivity.this, listener, "锟斤拷确锟斤拷删锟斤拷锟斤拷锟斤拷占锟斤拷锟�").show();
								break;
								
							}
						}
					});
		}
		
		composerButtonsShowHideButton.startAnimation(MyAnimations
				.getRotateAnimation(0, 360, 200));
	}
	
	/**
	 * 使用SynthesizerPlayer合成语音，不弹出合成Dialog.
	 * @param
	 */
	private void synthetizeInSilence() {
		if (null == mSynthesizerPlayer) {
			//创建合成对象.
			mSynthesizerPlayer = SynthesizerPlayer.createSynthesizerPlayer(
					this, "appid=" + getString(R.string.app_id));
		}

		//设置合成发音�?
		String role = mSharedPreferences.getString(
				getString(R.string.preference_key_tts_role),
				getString(R.string.preference_default_tts_role));
		mSynthesizerPlayer.setVoiceName(role);

		//设置发音人语�?
		int speed = mSharedPreferences.getInt(
				getString(R.string.preference_key_tts_speed),
				50);
		mSynthesizerPlayer.setSpeed(speed);

		//设置音量.
		int volume = mSharedPreferences.getInt(
				getString(R.string.preference_key_tts_volume),
				50);
		mSynthesizerPlayer.setVolume(volume);

		//设置背景�?
		String music = mSharedPreferences.getString(
				getString(R.string.preference_key_tts_music),
				getString(R.string.preference_default_tts_music));
		mSynthesizerPlayer.setBackgroundSound(music);

		//获取合成文本.
		String source = contentEt.getText().toString();

		//进行语音合成.
		mSynthesizerPlayer.playText(source, null,this);
		mToast.setText(String
				.format(getString(R.string.tts_toast_format), 0, 0));
		mToast.show();
	}
	
	private class RenameThread extends Thread {
		String title;
		public RenameThread(String title) {
			this.title = title;
		}
		@Override
		public void run() {
			dbService.updateTitleById(itemId, title, getDate(), getYMD());
			handler.sendEmptyMessage(2);
		}
	}
	
	private DialogInputListener listener2 = new DialogInputListener() {
		
		@Override
		public void onDialogOk(Dialog dlg, Object param) {
			
		}
		
		@Override
		public void onDialogCreate(Dialog dlg, Object param) {
			
		}
		
		@Override
		public void onDialogCancel(Dialog dlg, Object param) {
			
		}
		
		@Override
		public void onDialogInput(Dialog dlg, String text) {
			titleTv.setText(text);
			new RenameThread(text).start();
		}

		@Override
		public void onDialogSave(Dialog dlg, Object param) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onDialogUnSave(Dialog dlg, Object param) {
			// TODO Auto-generated method stub
			
		}
	};
	
	private class DelThread extends Thread {
		@Override
		public void run() {
			dbService.deleteItemById(jishibenItem.getIdx());
			handler.sendEmptyMessage(1);
		}
	}
	
    private DialogAlertListener listener = new DialogAlertListener() {
		
		@Override
		public void onDialogOk(Dialog dlg, Object param) {
			new DelThread().start();
		}
		
		@Override
		public void onDialogCreate(Dialog dlg, Object param) {
			
		}
		
		@Override
		public void onDialogCancel(Dialog dlg, Object param) {
		}

		@Override
		public void onDialogSave(Dialog dlg, Object param) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onDialogUnSave(Dialog dlg, Object param) {
			// TODO Auto-generated method stub
			
		}
	};
	
	private void initDatas() {
		itemId = this.getIntent().getIntExtra("itemId", 0);
		Log.i("a", "itemId = " + itemId);
		if(itemId > 0) {
			jishibenItem = dbService.findItemById(itemId);
		}
	}
	
	private class InitDatasThread extends Thread {
		@Override
		public void run() {
			initDatas();
			handler.sendEmptyMessage(0);
		}
	}
	
	private void initColors() {
		colors[0] = Color.BLACK;
		colors[1] = getResources().getColor(R.color.colorBlue);
		colors[2] = Color.GREEN;
		colors[3] = getResources().getColor(R.color.colorPink);
		colors[4] = Color.RED;
		colors[5] = getResources().getColor(R.color.colorPurple);
	}
	
	private void findViews() {
		contentEt = (TextView) this.findViewById(R.id.content);
		titleTv = (TextView) this.findViewById(R.id.title);
		composerButtonsWrapper = (RelativeLayout) findViewById(R.id.composer_buttons_wrapper);
		composerButtonsShowHideButton = (RelativeLayout) findViewById(R.id.composer_buttons_show_hide_button);
		composerButtonsShowHideButtonIcon = (ImageView) findViewById(R.id.composer_buttons_show_hide_button_icon);
	}
	
	public void btnClick(View view) {
		switch (view.getId()) {
		case R.id.back:
			back();
			break;
		}
	}
	
	private void back() {
		BabyJiShiBenReadActivity.this.finish();
		overridePendingTransition(R.anim.activity_steady, R.anim.activity_down);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK) {
			back();
		}
		return true;
	}
	
	//锟斤拷锟斤拷锟酵�
	private void initViews() {
		contentEt.setTextColor(colors[jishibenItem.getTextColorIndex()]);
		contentEt.setTextSize(textSize[jishibenItem.getTextSizeIndex()]);
		contentEt.setText(jishibenItem.getContent());
		titleTv.setText(jishibenItem.getTitle());
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		if(itemId > 0) {
			new InitDatasThread().start();
		}
	}
	
	//锟斤拷锟绞憋拷锟�
	private String getDate() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy锟斤拷MM锟斤拷dd锟斤拷 HH时mm锟斤拷ss锟斤拷");
		String dateStr = sdf.format(date); 
		return dateStr;
	}
	//锟斤拷取锟斤拷锟斤拷锟斤拷
	private String getYMD() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy锟斤拷MM锟斤拷dd锟斤拷");
		String dateStr = sdf.format(date); 
		return dateStr;
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		dbService.closeDB();
	}

	@Override
	public void onBufferPercent(int percent, int arg1, int arg2) {
		mPercentForBuffering = percent;
		mToast.setText(String.format(getString(R.string.tts_toast_format),
				mPercentForBuffering, mPercentForPlaying));
		mToast.show();
	}

	@Override
	public void onEnd(SpeechError arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPlayBegin() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPlayPaused() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPlayPercent(int percent, int arg1, int arg2) {
		mPercentForPlaying = percent;
		mToast.setText(String.format(getString(R.string.tts_toast_format),
				mPercentForBuffering, mPercentForPlaying));
		mToast.show();		
	}

	@Override
	public void onPlayResumed() {
		// TODO Auto-generated method stub
		
	}
}





