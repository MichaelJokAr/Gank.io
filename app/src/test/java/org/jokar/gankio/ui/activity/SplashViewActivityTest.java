package org.jokar.gankio.ui.activity;

import com.trello.rxlifecycle.android.ActivityEvent;

import junit.framework.TestCase;

import org.jokar.gankio.BuildConfig;
import org.jokar.gankio.view.activity.SplashViewActivity;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowLog;
import org.robolectric.util.ActivityController;

/**
 * Created by JokAr on 16/9/11.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21, manifest = "--default")
public class SplashViewActivityTest extends TestCase {

    @Before
    public void before() {
        //输出日志
        ShadowLog.stream = System.out;
    }

    @Test
    public void test() {

        SplashViewActivity splashViewActivity = Robolectric.setupActivity(SplashViewActivity.class);
        assertNotNull(splashViewActivity);

//        splashViewActivity.mSplashViewPresenter.getImage(splashViewActivity.getApplicationContext(),
//                splashViewActivity.bindUntilEvent(ActivityEvent.STOP));

    }
}
