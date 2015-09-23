package org.cocos2dx.plugin;

import java.util.Hashtable;

import android.app.Activity;
import android.content.Context;
import org.cocos2dx.plugin.InterfaceAds;

import com.adtapsy.sdk.AdTapsyDelegate;
import com.adtapsy.sdk.AdTapsyRewardedDelegate;

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
			public void onAdSkipped(int zoneId) {
				AdsWrapper.onAdsResult(mAdapter,
						AdsWrapper.RESULT_CODE_AdsDismissed,
						"" + zoneId);
			}

			@Override
			public void onAdShown(int zoneId) {
				AdsWrapper.onAdsResult(mAdapter,
						AdsWrapper.RESULT_CODE_AdsShown, "" + zoneId);
			}

			@Override
			public void onAdFailToShow(int zoneId) {
				AdsWrapper.onAdsResult(mAdapter, AdsWrapper.RESULT_CODE_UnknownError, "" + zoneId);
			}

			@Override
			public void onAdClicked(int zoneId) {
			    AdsWrapper.onAdsResult(mAdapter,
						AdsWrapper.RESULT_CODE_AdsDismissed,
						"" + zoneId);
			}
			@Override
			public void onAdCached(int zoneId) {

                    AdsWrapper.onAdsResult(mAdapter, AdsWrapper.RESULT_CODE_AdsReceived,
						"" + zoneId);
			}

		});
		String appId = devInfo.get("appId");
		if(appId==null){
			throw new RuntimeException("Please provide appId parameter to developer info when calling this method");
		}
		com.adtapsy.sdk.AdTapsy.startSession((Activity) mContext,
				appId);
		com.adtapsy.sdk.AdTapsy.setRewardedDelegate(new AdTapsyRewardedDelegate(){
			@Override
			public void onRewardEarned(int amount){
				AdsWrapper.onPlayerGetPoints(mAdapter, amount);
			}
			
		});

	}
	
	@Override
	public void showAds(Hashtable<String,String> h, int pos){
	}

	protected void showInterstitial() {
			com.adtapsy.sdk.AdTapsy.showInterstitial((Activity) mContext);			
	}
	protected void showRewardedVideo() {
			com.adtapsy.sdk.AdTapsy.showRewardedVideo((Activity) mContext);
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
	protected boolean isInterstitialReadyToShow() {
		return com.adtapsy.sdk.AdTapsy.isInterstitialReadyToShow();
	}
	protected boolean isRewardedVideoReadyToShow(){
		return com.adtapsy.sdk.AdTapsy.isRewardedVideoReadyToShow();
	}
	protected void setUserIdentifier(String userId){
		com.adtapsy.sdk.AdTapsy.setUserIdentifier(userId);
	}
	protected void setRewardedVideoAmount(int newReward){
		com.adtapsy.sdk.AdTapsy.setRewardedVideoAmount(newReward);
	}
	protected void setRewardedVideoPrePopupEnabled(boolean toShow){
		com.adtapsy.sdk.AdTapsy.setRewardedVideoPrePopupEnabled(toShow);
	}
	protected void setRewardedVideoPostPopupEnabled(boolean toShow){
		com.adtapsy.sdk.AdTapsy.setRewardedVideoPostPopupEnabled(toShow);
	}

}
