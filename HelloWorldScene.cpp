#define COCOS2D_DEBUG 1
#include "HelloWorldScene.h"
#include "PluginManager.h"
#include "ProtocolAds.h"
#include <map>
#include <string>
USING_NS_CC;

class MyAdsListener: public cocos2d::plugin::AdsListener
{
	void onAdsResult(cocos2d::plugin::AdsResultCode code, const char* msg)
	{
			if(code == cocos2d::plugin::AdsResultCode::kAdsShown)
			{
				CCLOG("*** AdTapsy Ad Shown ***");
			} else if(code == cocos2d::plugin::AdsResultCode::kAdsDismissed)
			{
				CCLOG("*** AdTapsy Ad Dismissed ***");
			} else if(code == cocos2d::plugin::AdsResultCode::kUnknownError){
				CCLOG("*** AdTapsy Ad Failed To Show");
			} else if(code == cocos2d::plugin::AdsResultCode::kAdsReceived){
				CCLOG("*** AdTapsy Ad Loaded***");
			}

	}
	void onPlayerGetPoints(cocos2d::plugin::ProtocolAds* pAdsPlugin, int points)
	{
		CCLOG("*** AdTapsy Reward Received***");
	}
};
Scene* HelloWorld::createScene()
{
    // 'scene' is an autorelease object
    auto scene = Scene::create();

    // 'layer' is an autorelease object
    auto layer = HelloWorld::create();

    // add layer as a child to scene
    scene->addChild(layer);

    // return the scene
    return scene;
}

cocos2d::plugin::AdsListener* listener = new MyAdsListener();
// on "init" you need to initialize your instance
cocos2d::plugin::ProtocolAds* adtapsy;

bool HelloWorld::init()
{
	adtapsy = dynamic_cast<cocos2d::plugin::ProtocolAds*>(cocos2d::plugin::PluginManager::getInstance()->loadPlugin("AdTapsy"));
	std::map<std::string,std::string> developer_info;
	developer_info["appId"] = "54982cf7e4b052cd2a20a7b8";

	adtapsy->setAdsListener(listener);
	adtapsy-> configDeveloperInfo(developer_info);
	cocos2d::plugin::PluginParam rewardAmount(10);
	adtapsy-> callFuncWithParam("setRewardedVideoAmount", &rewardAmount, NULL);
	cocos2d::plugin::PluginParam userId("userId");
	adtapsy-> callFuncWithParam("setUserIdentifier", &userId, NULL);
	cocos2d::plugin::PluginParam showPrePopup(false);
	adtapsy-> callFuncWithParam("setRewardedVideoPrePopupEnabled", &showPrePopup, NULL);
	cocos2d::plugin::PluginParam showPostPopup(false);
	adtapsy-> callFuncWithParam("setRewardedVideoPostPopupEnabled", &showPostPopup, NULL);

    //////////////////////////////
    // 1. super init first
    if ( !Layer::init() )
    {
        return false;
    }

    Size visibleSize = Director::getInstance()->getVisibleSize();
    Vec2 origin = Director::getInstance()->getVisibleOrigin();

    /////////////////////////////
    // 2. add a menu item with "X" image, which is clicked to quit the program
    //    you may modify it.

    // add a "close" icon to exit the progress. it's an autorelease object
    auto closeItem = MenuItemImage::create(
                                           "CloseNormal.png",
                                           "CloseSelected.png",
                                           CC_CALLBACK_1(HelloWorld::menuCloseCallback, this));

	closeItem->setPosition(Vec2(origin.x + visibleSize.width - closeItem->getContentSize().width/2 ,
                                origin.y + closeItem->getContentSize().height/2));


	auto showAdLabel = MenuItemLabel::create(LabelTTF::create("Show Interstitial", "Courier New.ttf", 51), CC_CALLBACK_1(HelloWorld::showAd, this));
	showAdLabel->setPosition(Vec2(origin.x + visibleSize.width/3.5 ,
            origin.y + visibleSize.height/2 ));

	auto showRewardedLabel = MenuItemLabel::create(LabelTTF::create("Show Rewarded", "Courier New.ttf", 51), CC_CALLBACK_1(HelloWorld::showRewarded, this));
	showRewardedLabel->setPosition(Vec2(origin.x + visibleSize.width/1.3 ,
		                                origin.y + visibleSize.height/2));

	// create menu, it's an autorelease object
	auto menu = Menu::create(closeItem, showAdLabel, showRewardedLabel, NULL);
    menu->setPosition(Vec2::ZERO);
    this->addChild(menu, 1);

    /////////////////////////////
    // 3. add your codes below...

    // add a label shows "Hello World"
    // create and initialize a label

    auto label = Label::createWithTTF("Hello World", "fonts/Marker Felt.ttf", 24);

    // position the label on the center of the screen
    label->setPosition(Vec2(origin.x + visibleSize.width/2,
                            origin.y + visibleSize.height - label->getContentSize().height));

    // add the label as a child to this layer
    this->addChild(label, 1);

    // add "HelloWorld" splash screen"
    auto sprite = Sprite::create("HelloWorld.png");

    // position the sprite on the center of the screen
    sprite->setPosition(Vec2(visibleSize.width/2 + origin.x, visibleSize.height/2 + origin.y));

    // add the sprite as a child to this layer
  //  this->addChild(sprite, 0);

    return true;
}


void HelloWorld::menuCloseCallback(Ref* pSender)
{
#if (CC_TARGET_PLATFORM == CC_PLATFORM_WP8) || (CC_TARGET_PLATFORM == CC_PLATFORM_WINRT)
	MessageBox("You pressed the close button. Windows Store Apps do not implement a close button.","Alert");
    return;
#endif

    Director::getInstance()->end();

#if (CC_TARGET_PLATFORM == CC_PLATFORM_IOS)
    exit(0);
#endif
}
void HelloWorld::showAd(Ref* pSeonder)
{
	 bool readyToShow = adtapsy-> callBoolFuncWithParam("isInterstitialReadyToShow", NULL);
	 if(readyToShow){
		 adtapsy-> callFuncWithParam("showInterstitial", NULL);
	 } else {
		 CCLOG("*** No ad ready to show***");
	 }
}
void HelloWorld::showRewarded(Ref* pSeonder)
{
	bool readyToShow = adtapsy-> callBoolFuncWithParam("isRewardedVideoReadyToShow", NULL);
	if(readyToShow){
		adtapsy-> callFuncWithParam("showRewardedVideo", NULL);
	} else {
		CCLOG("*** No ad ready to show***");
	}
}
