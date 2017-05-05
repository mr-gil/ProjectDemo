package com.jgg.rxretrofitlibrary.retrofit_rx.subscribers;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.jgg.rxretrofitlibrary.R;
import com.jgg.rxretrofitlibrary.retrofit_rx.Api.BaseApi;
import com.jgg.rxretrofitlibrary.retrofit_rx.RxRetrofitApp;
import com.jgg.rxretrofitlibrary.retrofit_rx.exception.ApiException;
import com.jgg.rxretrofitlibrary.retrofit_rx.exception.CodeException;
import com.jgg.rxretrofitlibrary.retrofit_rx.exception.HttpTimeException;
import com.jgg.rxretrofitlibrary.retrofit_rx.http.cookie.CookieResulte;
import com.jgg.rxretrofitlibrary.retrofit_rx.listener.HttpOnNextListener;
import com.jgg.rxretrofitlibrary.retrofit_rx.utils.BaseUtil;
import com.jgg.rxretrofitlibrary.retrofit_rx.utils.CookieDbUtil;

import org.json.JSONException;

import java.lang.ref.SoftReference;

import rx.Observable;
import rx.Subscriber;

/**
 * 用于在Http请求开始时，自动显示一个ProgressDialog
 * 在Http请求结束是，关闭ProgressDialog
 * 调用者自己对请求数据进行处理
 */
public class ProgressSubscriber<T> extends Subscriber<T> {
    /*是否弹框*/
    private boolean showPorgress = true;
    //    回调接口
    private SoftReference<HttpOnNextListener> mSubscriberOnNextListener;

    /*请求数据*/
    private BaseApi api;

    /**
     * 构造
     *
     * @param api
     */
    public ProgressSubscriber(BaseApi api) {
        this.api = api;
        this.mSubscriberOnNextListener = api.getOnNextListener();

        setShowPorgress(api.isShowProgress());

    }


    /**
     * 订阅开始时调用
     * 显示ProgressDialog
     */
    @Override
    public void onStart() {
        if (!BaseUtil.isNetworkAvailable(RxRetrofitApp.getApplication())) {
            return;
        }
        /*缓存并且有网*/
        if (api.isCache()) {
             /*获取缓存数据*/
            CookieResulte cookieResulte = CookieDbUtil.getInstance().queryCookieBy(api.getUrl());
            if (cookieResulte != null) {
                long time = (System.currentTimeMillis() - cookieResulte.getTime()) / 1000;
                if (time < api.getCookieNetWorkTime()) {
                    if (mSubscriberOnNextListener.get() != null) {
                        mSubscriberOnNextListener.get().onNext(cookieResulte.getResulte(), api.getMethod());
                    }
                    onCompleted();
                    unsubscribe();
                }
            }
        }
    }

    /**
     * 完成，隐藏ProgressDialog
     */
    @Override
    public void onCompleted() {

    }

    /**
     * 对错误进行统一处理
     * 隐藏ProgressDialog
     *
     * @param e
     */
    @Override
    public void onError(Throwable e) {
        /*需要緩存并且本地有缓存才返回*/
        if (api.isCache()) {
            getCache();
        } else {
            errorDo(e);
        }
    }

    /**
     * 获取cache数据
     */
    private void getCache() {
        Observable.just(api.getUrl()).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                errorDo(e);
            }

            @Override
            public void onNext(String s) {
                           /*获取缓存数据*/
                CookieResulte cookieResulte = CookieDbUtil.getInstance().queryCookieBy(s);
                if (cookieResulte == null) {
                    throw new HttpTimeException(HttpTimeException.NO_CHACHE_ERROR);
                }
                long time = (System.currentTimeMillis() - cookieResulte.getTime()) / 1000;
                if (time < api.getCookieNoNetWorkTime()) {
                    if (mSubscriberOnNextListener.get() != null) {
                        mSubscriberOnNextListener.get().onNext(cookieResulte.getResulte(), api.getMethod());
                    }
                } else {
                    CookieDbUtil.getInstance().deleteCookie(cookieResulte);
                    throw new HttpTimeException(HttpTimeException.CHACHE_TIMEOUT_ERROR);
                }
            }
        });
    }


    /**
     * 错误统一处理
     *
     * @param e
     */
    private void errorDo(Throwable e) {
        HttpOnNextListener httpOnNextListener = mSubscriberOnNextListener.get();
        if (httpOnNextListener == null) return;
        if (e instanceof ApiException) {
            httpOnNextListener.onError((ApiException) e);
        } else if (e instanceof HttpTimeException) {
            HttpTimeException exception = (HttpTimeException) e;
            httpOnNextListener.onError(new ApiException(exception, CodeException.RUNTIME_ERROR, exception.getMessage()));
        } else if (e instanceof JSONException) {
            httpOnNextListener.onError(new ApiException(e, CodeException.JSON_ERROR, e.getMessage()));
        } else {
            httpOnNextListener.onError(new ApiException(e, CodeException.UNKNOWN_ERROR, e.getMessage()));
        }

    }


    /**
     * 将onNext方法中的返回结果交给Activity或Fragment自己处理
     *
     * @param t 创建Subscriber时的泛型类型
     */
    @Override
    public void onNext(T t) {
         /*缓存处理*/
        if (api.isCache()) {
            CookieResulte resulte = CookieDbUtil.getInstance().queryCookieBy(api.getUrl());
            long time = System.currentTimeMillis();
            /*保存和更新本地数据*/
            if (resulte == null) {
                resulte = new CookieResulte(api.getUrl(), t.toString(), time);
                CookieDbUtil.getInstance().saveCookie(resulte);
            } else {
                resulte.setResulte(t.toString());
                resulte.setTime(time);
                CookieDbUtil.getInstance().updateCookie(resulte);
            }
        }

        if (mSubscriberOnNextListener.get() != null) {
            mSubscriberOnNextListener.get().onNext((String) t, api.getMethod());
        }
    }


    /**
     * 取消ProgressDialog的时候，取消对observable的订阅，同时也取消了http请求
     */
    public void onCancelProgress() {
        if (!this.isUnsubscribed()) {
            this.unsubscribe();
        }
    }


    public boolean isShowPorgress() {
        return showPorgress;
    }

    /**
     * 是否需要弹框设置
     *
     * @param showPorgress
     */
    public void setShowPorgress(boolean showPorgress) {
        this.showPorgress = showPorgress;
    }
}