package org.jokar.gankio.view.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;


import com.jakewharton.rxbinding2.widget.RxTextView;
import com.trello.rxlifecycle2.android.ActivityEvent;

import org.jokar.gankio.BuildConfig;
import org.jokar.gankio.R;
import org.jokar.gankio.di.component.preseneter.DaggerAddGanPresneterCom;
import org.jokar.gankio.di.module.models.AddGankModelModule;
import org.jokar.gankio.di.module.view.AddGankViewModel;
import org.jokar.gankio.presenter.impl.AddGankPresenterImpl;
import org.jokar.gankio.view.ui.AddGankView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JokAr on 16/9/21.
 */

public class AddGankActivity extends BaseActivity implements AddGankView {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.select_type)
    TextView selectType;
    @BindView(R.id.ed_desc)
    TextInputEditText edDesc;
    @BindView(R.id.layout_desc)
    TextInputLayout layoutDesc;
    @BindView(R.id.ed_url)
    TextInputEditText edUrl;
    @BindView(R.id.layout_url)
    TextInputLayout layoutUrl;
    @BindView(R.id.ed_who)
    TextInputEditText edWho;
    @BindView(R.id.layout_who)
    TextInputLayout layoutWho;
    @BindView(R.id.coordinator)
    CoordinatorLayout coordinator;

    @Inject
    AddGankPresenterImpl mAddGankPresenter;

    private String[] types = new String[]{"Android", "iOS", "休息视频", "福利", "拓展资源", "前端", "瞎推荐", "App"};

    private int typeIndex = -1;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addgank);
        ButterKnife.bind(this);

        init();
    }

    private void init() {
        initToolbar(toolbar, "提交干货");
        //初始化presenter
        DaggerAddGanPresneterCom.builder()
                .addGankViewModel(new AddGankViewModel(this))
                .addGankModelModule(new AddGankModelModule())
                .build()
                .inject(this);
        //绑定输入view事件
        RxTextView.textChanges(edDesc).compose(bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(charSequence -> {
            layoutDesc.setErrorEnabled(false);
            layoutDesc.setHintEnabled(true);
        });

        RxTextView.textChanges(edUrl).compose(bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(charSequence -> {
            layoutUrl.setErrorEnabled(false);
            layoutUrl.setHintEnabled(true);
        });

        RxTextView.textChanges(edWho).compose(bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(charSequence -> {
            layoutWho.setErrorEnabled(false);
            layoutWho.setHintEnabled(true);
        });

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("正在提交,请稍等...");
        mProgressDialog.setCancelable(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.addgank_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.submit: {
                submit();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 选择类型
     *
     * @param view
     */
    @OnClick(R.id.select_type)
    public void onClick(View view) {
        new AlertDialog.Builder(this)
                .setTitle("选择类型")
                .setSingleChoiceItems(types, typeIndex, (dialog, which) -> {
                    typeIndex = which;
                    selectType.setText(types[typeIndex]);

                })
                .setCancelable(false)
                .setPositiveButton("确定", null)
                .setNegativeButton("取消", null)
                .show();
    }

    /**
     * 提交
     */
    private void submit() {
        hideInput();
        String type = "";
        if (typeIndex > -1) {
            type = types[typeIndex];
        }
        mAddGankPresenter.submit(bindUntilEvent(ActivityEvent.STOP),
                edUrl.getText().toString(), edDesc.getText().toString(),
                edWho.getText().toString(), type, BuildConfig.DEBUG);
    }

    @Override
    public void showDescEmtyError(String error) {
        layoutDesc.setHintEnabled(false);
        layoutDesc.setError(error);
    }

    @Override
    public void showUrlEmtyError() {
        layoutUrl.setHintEnabled(false);
        layoutUrl.setError("网页地址不能为空");
    }

    @Override
    public void showWhoEmtyError() {
        layoutWho.setHintEnabled(false);
        layoutWho.setError("提交者ID不能为空");
    }

    @Override
    public void showTypeEmtyError() {
        Snackbar.make(coordinator, "干货类型不能为空", Snackbar.LENGTH_LONG)
                .setAction("去选择", v -> onClick(null)).show();
    }

    @Override
    public void showSubmitProgress() {
        if (mProgressDialog != null) {
            mProgressDialog.show();
        }
    }

    @Override
    public void compeleteSubmitProgress() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void showSubmitSuccess(String msg) {
        typeIndex = -1;
        selectType.setText("");
        edDesc.setText("");
        edUrl.setText("");
        edWho.setText("");
        Snackbar.make(coordinator, msg, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showSubmiError(Throwable e) {
        Snackbar.make(coordinator, e.getMessage(), Snackbar.LENGTH_LONG).show();
    }

    /**
     * 隐藏输入法
     */
    private void hideInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        // 隐藏软键盘
        imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
    }
}
