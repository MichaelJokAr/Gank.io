package org.jokar.gankio;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowLog;

/**
 * Created by JokAr on 2016/9/25.
 */
@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class BipartiteTest {

    @Before
    public void before() {
        //输出日志
        ShadowLog.stream = System.out;
    }

    @Test
    public void test() {
        int[] num = new int[]{0, 6, 8, 10, 14};
        System.out.print(binarySearch(num, 1, 0, num.length - 1));
    }

    /**
     * 二分查找特定整数在整型数组中的位置(递归)
     *
     * @paramdataset
     * @paramdata
     * @parambeginIndex
     * @paramendIndex
     * @returnindex
     */
    public static int binarySearch(int[] dataset, int data, int beginIndex, int endIndex) {
        int midIndex = (beginIndex + endIndex) / 2;
//        if (data < dataset[beginIndex] || data > dataset[endIndex] || beginIndex > endIndex) {
//            return -1;
//        }

        if (data < dataset[midIndex]) {
            if (data > dataset[midIndex - 1])
                return midIndex;
            return binarySearch(dataset, data, beginIndex, midIndex - 1);
        } else if (data > dataset[midIndex]) {
            return binarySearch(dataset, data, midIndex + 1, endIndex);
        } else {
            return midIndex;
        }
    }
}
