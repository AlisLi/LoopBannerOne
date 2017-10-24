package com.example.loopbannerone;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.example.loopbannerone.adapter.ImagePagerAdapter;
import com.example.loopbannerone.widget.CircleFlowIndicator;
import com.example.loopbannerone.widget.ViewFlow;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    //横向循环滚动的图片控件
    private ViewFlow mViewFlow;
    //指示滚动的小圆点
    private CircleFlowIndicator mFlowIndicator;
    //获取图片的url列表
    private ArrayList<String> imageUrlList = new ArrayList<String>();
    //获取点击图片后跳转的页面的url列表
    private ArrayList<String> linkUrlArray = new ArrayList<String>();
    //上下滚动的通知列表
    private ArrayList<String> titleList = new ArrayList<String>();

    private LinearLayout notice_parent_11;
    private ViewFlipper notice_vf;
    private int mCurrPos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化控件
        initView();
        //获取图片资源和通知资源
        getResource();
        //实现横向循环滚动图片
        initBanner(imageUrlList);
        //实现竖向滚动通知
        initRollNotice();


    }

    private void initView(){
        mViewFlow = (ViewFlow) findViewById(R.id.view_flow);
        mFlowIndicator = (CircleFlowIndicator) findViewById(R.id.view_flow_index);
    }

    private void getResource(){
        imageUrlList
                .add("https://image.baidu.com/search/redirect?tn=redirect&word=j&juid=386CA8&sign=ckawwkaoco&url=http%3A%2F%2Fimg.mshishang.com%2Fpics%2F2017%2F0115%2F20170115070622404.jpg&bakfurl1=http%3A%2F%2Fimg.mshishang.com%2Fpics%2F2017%2F0115%2F20170115070622404.jpg&objurl=https%3A%2F%2Ftimgsa.baidu.com%2Ftimg%3Fimage%26quality%3D80%26size%3Db9999_10000%26sec%3D1508860998201%26di%3Daa50785ee1286281f9b44f86b5bab81c%26imgtype%3D0%26src%3Dhttp%253A%252F%252Fg.hiphotos.baidu.com%252Fimage%252Fpic%252Fitem%252Fa71ea8d3fd1f4134919093532e1f95cad1c85e73.jpg");
        imageUrlList
                .add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1508861166432&di=77f933766f2bcc98453ed2aab7901b49&imgtype=0&src=http%3A%2F%2Fimg.grtn.cn%2Fmaterial%2Fnews%2Fimg%2F2017%2F05%2Fc3f53d347d3f8c268a708daa38651511.jpg");
        imageUrlList
                .add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1508861196677&di=c37497a4ecb76137ab230eeb6f69eef8&imgtype=0&src=http%3A%2F%2Ff.hiphotos.baidu.com%2Fzhidao%2Fpic%2Fitem%2F562c11dfa9ec8a13e689fcf0f003918fa0ecc00b.jpg");
        imageUrlList
                    .add("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2577874080,4285978287&fm=27&gp=0.jpg");
        linkUrlArray
                .add("http://blog.csdn.net/finddreams/article/details/44301359");
        linkUrlArray
                .add("http://blog.csdn.net/finddreams/article/details/43486527");
        linkUrlArray
                .add("http://blog.csdn.net/finddreams/article/details/44648121");
        linkUrlArray
                .add("http://blog.csdn.net/finddreams/article/details/44619589");
        titleList.add("常见Android进阶笔试题");
        titleList.add("GridView之仿支付宝钱包首页");
        titleList.add("仿手机QQ网络状态条的显示与消失 ");
        titleList.add("Android循环滚动广告条的完美实现 ");
    }

    private void initBanner(ArrayList<String> imageUrlList){
        //适配资源（横向滚动图片，点击图片后的链接，竖向的滚动通知）；初始设置为循环
        mViewFlow.setAdapter(new ImagePagerAdapter(this,imageUrlList,linkUrlArray,titleList).setInfiniteLoop(true));

        mViewFlow.setmSideBuffer(imageUrlList.size());
        //设置指示图片位置的圆点
        mViewFlow.setFlowIndicator(mFlowIndicator);
        //设置循环时间
        mViewFlow.setTimeSpan(4500);
        //设置初始位置
        mViewFlow.setSelection(imageUrlList.size()*1000);
        //启动自动播放
        mViewFlow.startAutoFlowTimer();
    }

    private void initRollNotice() {
        FrameLayout main_notice = (FrameLayout) findViewById(R.id.main_notice);
        notice_parent_11 = (LinearLayout) getLayoutInflater().inflate(
                R.layout.layout_notice, null);
        notice_vf = ((ViewFlipper) this.notice_parent_11
                .findViewById(R.id.homepage_notice_vf));
        main_notice.addView(notice_parent_11);
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        moveNext();
                        Log.d("Task", "下一个");
                    }
                });

            }
        };
        Timer timer = new Timer();
        timer.schedule(task, 0, 4000);
    }

    private void moveNext() {
        setView(this.mCurrPos, this.mCurrPos + 1);
        this.notice_vf.setInAnimation(this, R.anim.in_bottomtop);
        this.notice_vf.setOutAnimation(this, R.anim.out_bottomtop);
        this.notice_vf.showNext();
    }

    private void setView(int curr, int next) {

        View noticeView = getLayoutInflater().inflate(R.layout.notice_item,
                null);
        TextView notice_tv = (TextView) noticeView.findViewById(R.id.notice_tv);
        if ((curr < next) && (next > (titleList.size() - 1))) {
            next = 0;
        } else if ((curr > next) && (next < 0)) {
            next = titleList.size() - 1;
        }
        notice_tv.setText(titleList.get(next));
        notice_tv.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Bundle bundle = new Bundle();
                bundle.putString("url", linkUrlArray.get(mCurrPos));
                bundle.putString("title", titleList.get(mCurrPos));
                Intent intent = new Intent(MainActivity.this,
                        BaseWebActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        if (notice_vf.getChildCount() > 1) {
            notice_vf.removeViewAt(0);
        }
        notice_vf.addView(noticeView, notice_vf.getChildCount());
        mCurrPos = next;

    }
}
