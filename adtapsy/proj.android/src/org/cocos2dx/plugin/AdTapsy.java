package org.cocos2dx.plugin;

import java.util.Hashtable;

import android.app.Activity;
import android.content.Context;
import org.cocos2dx.plugin.InterfaceAds;

import com.adtapsy.sdk.AdTapsyDelegate;

public class AdTapsy implements InterfaceAds {
	private Context mContext;
	private AdTapsy mAdapter;

	public AdTapsy(Context context) {
		mContext = context;
		mAdapter = this;
	}

	@Override
	public void configDeveloperInfo(Hashtable<String, String> devInfo) {
		com.adtapsy.sdk.AdTapsy.setDelegate(new AdTapsyDelegate() {

			@Override
			public void onAdSkipped() {
				AdsWrapper.onAdsResult(mAdapter,
						AdsWrapper.RESULT_CODE_AdsDismissed,
						"Ad skipped by user");
			}

			@Override
			public void onAdShown() {
				AdsWrapper.onAdsResult(mAdapter,
						AdsWrapper.RESULT_CODE_AdsShown, "Ad shown");
			}

			@Override
			public void onAdFailToShow() {
				AdsWrapper.onAdsResult(mAdapter, AdsWrapper.RESULT_CODE_UnknownError, "Ad Failed to show");
			}

			@Override
			public void onAdClicked() {
				AdsWrapper.onAdsResult(mAdapter,
						AdsWrapper.RESULT_CODE_AdsDismissed,
						"Ad clicked by user");
			}
		});
		String appId = devInfo.get("appId");
		if(appId==null){
			throw new RuntimeException("Please provide appId parameter to developer info when calling this method");
		}
		com.adtapsy.sdk.AdTapsy.startSession((Activity) mContext,
				appId);

	}

	@Override
	public void showAds(Hashtable<String, String> adsInfo, int pos) {
		com.adtapsy.sdk.AdTapsy.showInterstitial((Activity) mContext);
	}

	@Override
	public void hideAds(Hashtable<String, String> adsInfo) {
		// TODO Auto-generated method stub

	}

	@Override
	public void queryPoints() {
		// TODO Auto-generated method stub

	}

	@Override
	public void spendPoints(int points) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setDebugMode(boolean debug) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getSDKVersion() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPluginVersion() {
		// TODO Auto-generated method stub
		return null;
	}

}
