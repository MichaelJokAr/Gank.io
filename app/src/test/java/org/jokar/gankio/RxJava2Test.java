package org.jokar.gankio;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowLog;

import java.util.ArrayList;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * Created by JokAr on 2017/2/26.
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest= Config.NONE)
public class RxJava2Test {

    @Before
    public void before() {
        //输出日志
        ShadowLog.stream = System.out;
    }
    
    @Test
    public void test(){
        ArrayList<String> arrays = new ArrayList<>();

        
    }
}
