package com.guosun.lovego.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import com.guosun.lovego.LovegoApplication;
import com.guosun.lovego.R;
import com.guosun.lovego.fragment.ContactsFragment;
import com.guosun.lovego.fragment.HomeFragment;
import com.guosun.lovego.fragment.MoiveFragment;
import com.guosun.lovego.util.ULog;
import com.guosun.lovego.widget.FragmentSwitchTool;


public class MainActivity extends BaseActivity implements View.OnClickListener{
    private RadioButton rb_home, rb_hot, rb_movie, rb_user;
    private FragmentSwitchTool tool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        application = (LovegoApplication) this.getApplication();
        initView();
        tool = new FragmentSwitchTool(getFragmentManager(), R.id.fl_main_content);
        tool.setClickableViews(rb_home, rb_hot, rb_movie, rb_user);
        tool.addSelectedViews(new View[]{rb_home}).addSelectedViews(new View[]{rb_hot})
                .addSelectedViews(new View[]{rb_movie}).addSelectedViews(new View[]{rb_user});
        tool.setFragments(HomeFragment.class, ContactsFragment.class, MoiveFragment.class, ContactsFragment.class);

        tool.changeTag(rb_home);
        initListener();
    }

    @Override
    protected boolean showHeader() {
        return false;
    }

    @Override
    protected String getHeaderTitle() {
        return "首页";
    }

    @Override
    protected void onResume() {
        super.onResume();
        ULog.e("lovego", this.getClass().getName() + " onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        ULog.e("lovego", this.getClass().getName() + " onPause");

    }

    private void initView(){


        rb_home = (RadioButton) findViewById(R.id.rb_home);
        rb_hot = (RadioButton) findViewById(R.id.rb_hot);
        rb_movie = (RadioButton) findViewById(R.id.rb_movie);
        rb_user = (RadioButton) findViewById(R.id.rb_user);
    }
    private void initListener(){
    }

    @Override
    public void onClick(View v) {

    }
}
