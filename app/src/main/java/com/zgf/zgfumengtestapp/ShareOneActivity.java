package com.zgf.zgfumengtestapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.ShareBoardConfig;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;

/**
 * 试用友盟分享问题：
 * 1、各平台的 key 填写正确
 * 2、配置正确
 * 3、分享平台的确定
 * 4、点击分享时弹出的面板自己设置
 *      不需要添加友盟自己的分享面板布局 jar 包和图片信息
 *      将自己的面板需求加入，点击单个按钮进行单个操作即可
 * 5、确定各平台分享的内容的差异
 *
 * 友盟分享成功后的分享信息中，左下角的图标不是自己 APP 的图标问题？
 * 答：这个图标是根据你的 APP 在各个平台上上传的图标识一致的，
 * 因此只要保证在各个平台上上传的图标正确即可。
 *
 */
public class ShareOneActivity extends AppCompatActivity {
    private String shareString = "this is a share demo!";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_one);
        initView();
    }

    private void initView() {
        findViewById(R.id.bt_qq_share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qqShare();
            }
        });
        findViewById(R.id.bt_weixin_share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weixinShare();
            }
        });
        findViewById(R.id.bt_sina_share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sinaShare();
            }
        });
        findViewById(R.id.bt_all_share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allShare();
            }
        });
    }

    private void qqShare() {
//        shareText(SHARE_MEDIA.QQ);

//        shareImage(SHARE_MEDIA.QQ);

//        shareUrl(SHARE_MEDIA.QQ);

        //==============空间================
//        shareText(SHARE_MEDIA.QZONE);

//        shareImage(SHARE_MEDIA.QZONE);

        shareUrl(SHARE_MEDIA.QZONE);
    }

    private void weixinShare() {
//        shareText(SHARE_MEDIA.WEIXIN);

//        shareImage(SHARE_MEDIA.WEIXIN);

//        shareUrl(SHARE_MEDIA.WEIXIN);

        //=============朋友圈==================
//        shareText(SHARE_MEDIA.WEIXIN);

//        shareImage(SHARE_MEDIA.WEIXIN);

        shareUrl(SHARE_MEDIA.WEIXIN_CIRCLE);
    }

    private void sinaShare() {
//        shareText(SHARE_MEDIA.SINA);

//        shareImage(SHARE_MEDIA.SINA);

        shareUrl(SHARE_MEDIA.SINA);
    }

    private void allShare() {
//        shareAll();

        //分享面板
        customeShareView();
    }

    // 打开分享面板
    private void shareAll() {
        Log.e("===all====", "=======");

        /*new ShareAction(this)
                .withText("hello")
                .setDisplayList(SHARE_MEDIA.SINA,SHARE_MEDIA.QQ,SHARE_MEDIA.WEIXIN)
                .setCallback(umShareListener)
                .open();*/

        //================设置分享的面板================================

        UMImage image = new UMImage(this, "https://www.baidu.com/img/bd_logo1.png");//网络图片

        UMWeb web = new UMWeb("https://www.baidu.com/");
        web.setTitle("This is music title");//标题
        web.setThumb(image);  //缩略图
        web.setDescription("my description");//描述

        ShareAction shareAction = new ShareAction(this);
        shareAction.withText("hello")
                .setDisplayList(SHARE_MEDIA.SINA,SHARE_MEDIA.QQ,SHARE_MEDIA.WEIXIN)
                .setCallback(umShareListener)
                .withMedia(web);

        ShareBoardConfig config = new ShareBoardConfig();
        config.setShareboardPostion(ShareBoardConfig.SHAREBOARD_POSITION_CENTER);
        config.setMenuItemBackgroundShape(ShareBoardConfig.BG_SHAPE_ROUNDED_SQUARE);

//        config.setTitleText("title");
//        config.setTitleVisibility(false);
//        config.setMenuItemTextColor(color);
//        config.setMenuItemIconPressedColor(color);
//        config.setMenuItemBackgroundColor(color);
//        config.setMenuItemBackgroundShape(shape);// 北京形状
//        config.setIndicatorColor(color);// 指示器颜色
//        config.setIndicatorVisibility(true);
//        config.setCancelButtonText(text);
//        ……

        config.setCancelButtonVisibility(true);
        shareAction.open(config);
    }

    private void customeShareView() {
        UMImage image = new UMImage(this, "https://www.baidu.com/img/bd_logo1.png");//网络图片

        UMWeb web = new UMWeb("https://www.baidu.com/");
        web.setTitle("This is music title");//标题
        web.setThumb(image);  //缩略图
        web.setDescription("my description");//描述

        ShareBoardConfig config = new ShareBoardConfig();
        config.setShareboardPostion(ShareBoardConfig.SHAREBOARD_POSITION_BOTTOM);
        config.setCancelButtonVisibility(false);
        config.setIndicatorVisibility(false);

        ShareAction shareAction = new ShareAction(this);
        shareAction.setDisplayList(SHARE_MEDIA.SINA, SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
                .addButton("umeng_sharebutton_custom", "umeng_sharebutton_custom", "ic_launcher", "info_icon_1")
                .setShareboardclickCallback(shareBoardlistener)
                .setCallback(umShareListener)
                .withMedia(web)
                .open(config);
    }

    // 分享文本
    private void shareText(SHARE_MEDIA shareMedia) {
        Log.e("===Text===", "=======");

        new ShareAction(this)
                .setPlatform(shareMedia)
                .withText(shareString)
                .setCallback(umShareListener)
                .share();
    }

    // 分享文本和图片
    private void shareImage(SHARE_MEDIA shareMedia) {
        UMImage image = new UMImage(this, "https://www.baidu.com/img/bd_logo1.png");//网络图片
//        UMImage image = new UMImage(this, file);//本地文件
//        UMImage image = new UMImage(this, R.drawable.xxx);//资源文件
//        UMImage image = new UMImage(this, bitmap);//bitmap文件
//        UMImage image = new UMImage(this, byte[]);//字节流

        new ShareAction(this)
                .setPlatform(shareMedia)
                .withText(shareString)
                .withMedia(image)
                .setCallback(umShareListener)
                .share();

    }

    private void shareUrl(SHARE_MEDIA shareMedia) {
        UMImage image = new UMImage(this, "https://www.baidu.com/img/bd_logo1.png");//网络图片

        UMWeb web = new UMWeb("https://www.baidu.com/");
        web.setTitle("This is music title");//标题
        web.setThumb(image);  //缩略图
        web.setDescription("my description");//描述

        new ShareAction(this)
                .setPlatform(shareMedia)
                .withMedia(web)
                .setCallback(umShareListener)
                .share();
    }

    /**
     * 分享的回调
     */
    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {

        }

        @Override
        public void onResult(SHARE_MEDIA share_media) {
            Toast.makeText(ShareOneActivity.this, share_media + " 分享成功啦", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(SHARE_MEDIA share_media, Throwable throwable) {
            Toast.makeText(ShareOneActivity.this,share_media + " 分享失败啦", Toast.LENGTH_SHORT).show();
            if(throwable!=null){
                Log.d("throw","throw:"+throwable.getMessage());
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {
            Toast.makeText(ShareOneActivity.this,share_media + " 分享取消了", Toast.LENGTH_SHORT).show();
        }
    };

    /**
     * 自定义分享按钮的分享面板的监听
     */
    private ShareBoardlistener shareBoardlistener = new ShareBoardlistener() {

        @Override
        public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
            if (share_media == null) {
                if (snsPlatform.mKeyword.equals("umeng_sharebutton_custom")) {
                    Toast.makeText(ShareOneActivity.this, "add button success", Toast.LENGTH_LONG).show();
                }

            } else {
                new ShareAction(ShareOneActivity.this).setPlatform(share_media).setCallback(umShareListener)
                        .withText("多平台分享")
                        .share();
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }
}
