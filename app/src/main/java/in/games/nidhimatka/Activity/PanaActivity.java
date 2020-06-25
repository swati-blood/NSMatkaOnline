package in.games.nidhimatka.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import in.games.nidhimatka.Adapter.PagerAdapter;
import in.games.nidhimatka.Common.Common;
import in.games.nidhimatka.R;

import static in.games.nidhimatka.Objects.sp_input_data.singlePaana;

public class PanaActivity extends AppCompatActivity implements View.OnClickListener {
   ImageView iv_back;
   TextView tv_title,tv_wallet;
   TabLayout tablayout;
   Common common;
   Activity ctx=PanaActivity.this;
   ViewPager viewpager;
   PagerAdapter pagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pana);
        initViews();
    }
    private void initViews() {
        tablayout=findViewById(R.id.tablayout);
        iv_back=findViewById(R.id.iv_back);
        tv_title=findViewById(R.id.tv_title);
        tv_wallet=findViewById(R.id.tv_wallet);
        viewpager=findViewById(R.id.viewpager);
        iv_back.setOnClickListener(this);
        common=new Common(ctx);
        setTabLayout();

        viewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tablayout));
        tablayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewpager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    void setTabLayout() {
        tablayout.setVisibility(View.VISIBLE);
        int len = singlePaana.length;
        for (int i = 0; i < 10; i++) {
            int ind = i + 1;
            tablayout.addTab(tablayout.newTab().setText(String.valueOf(ind)), i);

        }
        tablayout.setTabGravity(TabLayout.GRAVITY_FILL);
        pagerAdapter=new PagerAdapter(getSupportFragmentManager(),3);
        viewpager.setAdapter(pagerAdapter);

    }
    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.iv_back)
        {
            finish();
        }
    }
}
