package org.jokar.gankio.presenter.event;


import com.trello.rxlifecycle2.LifecycleTransformer;

/**
 * Created by JokAr on 2016/9/23.
 */

public interface AddGankPresenter {

    /**
     *
     * @param lifecycleTransformer
     * @param url     想要提交的网页地址
     * @param desc    对干货内容的描述
     * @param who     提交者 ID
     * @param type    干货类型 -可选参数: Android | iOS | 休息视频 | 福利 | 拓展资源 | 前端 | 瞎推荐 | App
     * @param debug   当前提交为测试数据	 -如果想要测试数据是否合法, 请设置 debug 为 true! 可选参数: true | false
     */
    void submit(LifecycleTransformer lifecycleTransformer,
                String url,
                String desc,
                String who,
                String type,
                boolean debug);
}
