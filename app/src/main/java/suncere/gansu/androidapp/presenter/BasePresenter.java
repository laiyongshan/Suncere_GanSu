package suncere.gansu.androidapp.presenter;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import suncere.gansu.androidapp.api.ApiNetManager;
import suncere.gansu.androidapp.api.RetrofitSrevice;

/**
 * Created by Hjo on 2017/5/11.
 */

public class BasePresenter<V>  {

    public V mIView;
    protected RetrofitSrevice mRetrofitService;
    private   CompositeSubscription mCompositeSubscription;

    /**
     * 使用的IView接口
     * @param IView
     */
    public void attrchIView(V IView){
        this.mIView=IView;
        this.mRetrofitService= ApiNetManager.getInstence().getRetrofitSrevice();
    }


    public void detachView() {
        this.mIView = null;
        onUnsubscribe();
    }


    //RXjava取消注册，以避免内存泄露
    public void onUnsubscribe() {
        if (mCompositeSubscription != null && mCompositeSubscription.hasSubscriptions()) {
            mCompositeSubscription.unsubscribe();
        }
    }


    public void addSubscription(Observable observable, Subscriber subscriber) {
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber));
    }



}
