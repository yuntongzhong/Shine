package com.zyt.shine.activity;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.zyt.shine.R;
import com.zyt.shine.utils.ImmersedStatusbarUtils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * Created by zyt on 2015/11/20.
 */
@EActivity(R.layout.login_activity)
public class LoginActivity extends AppCompatActivity{
    @ViewById(R.id.toolbar)
    Toolbar toolbar;

    @AfterViews
   protected void afterViews(){
        ImmersedStatusbarUtils.initAfterSetContentView(this, toolbar);

        setSupportActionBar(toolbar);
        //设置是否有返回箭头
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
