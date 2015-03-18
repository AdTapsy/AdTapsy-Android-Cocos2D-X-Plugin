package org.cocos2dx.cpp;

import org.cocos2dx.lib.Cocos2dxActivity;

import com.adtapsy.sdk.AdTapsy;

import android.os.Bundle;

public class AdTapsyCocosActivity extends Cocos2dxActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AdTapsy.onCreate(this);
	}

	@Override
	protected void onStart() {
		super.onStart();
		AdTapsy.onStart(this);
	}

	@Override
	public void onResume() {
		super.onResume();
		AdTapsy.onResume(this);
	}

	@Override
	public void onPause() {
		super.onPause();
		AdTapsy.onPause(this);
	}

	@Override
	public void onStop() {
		super.onStop();
		AdTapsy.onStop(this);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		AdTapsy.onDestroy(this);
	}

	@Override
	public void onBackPressed() {
		// If ad is on the screen - close it
		boolean addWasClosed = AdTapsy.closeAd();
		if(!addWasClosed){
			super.onBackPressed();			
		}
	}
}
