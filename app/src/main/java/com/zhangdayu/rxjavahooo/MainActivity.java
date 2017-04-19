package com.zhangdayu.rxjavahooo;


import android.graphics.Bitmap;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    Observable observable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //创建一个观察者
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onCompleted() {
                Log.e(TAG, "onCompleted: ");
            }

            //onComplete 和onError 只会走一个方法
            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: ");
            }

            @Override
            public void onNext(String s) {
                Log.e(TAG, "onNext: ");
            }
        };

        //除了Observer接口之外，RxJava还内置一个实现了Observer 的抽象类：Subscriber
        //Subscriber对Ovserver接口做了一些扩展，
        //但是他们的使用方式是完全一样的。
        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {
                Log.e(TAG, " subscriber -- onCompleted: ");
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, " subscriber -- onError: ");
            }

            @Override
            public void onNext(String s) {
                Log.e(TAG, " subscriber -- onNext: ");
            }
        };
        //选择Observer和Subscriber是完全一样的。
        //他们的区别对于使用者来说主要有两点：


        //使用observable.create()创建被观察者
        Observable obsevable1 = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("Hello");
                subscriber.onNext("World");
                subscriber.onNext("zhy");
                subscriber.onCompleted();
            }

        });

        /**
         * 可以看到，这里传入了一个OnSubscribe对象作为参数。
         * onSubscribe会被存储在返回的observable对象中，
         * 他的作用相当于一个计划表，当observalbe被订阅的时候，
         * oNservable的call()方法就会自动被调用，
         * 事件序列就会依照设定依次触发（对于上面的代码），
         * 就是观察者Subscribe将会被调用三次onNext()和一次onComplete().
         *
*/
        /**
         *  create()方法是RxJava最基本的创造时间序列的方法。
         *  基于这个方法，RxJava还提供了一些方法用来快捷创建事件队列。例如
         *
         *  just(T ...)将传入的参数一次发送出来
         */
        Observable observable = Observable.just("hello","world","aaa");
        //将会依次调用
        //onNext("hello");
        //onNext("world");
        //onNext("aaa");
        //onCompleted();

        /**
         * from(T[])/from(Iterable<? extends T>):将传入的数组或Iterable拆分成具体对象后，依次发出来
         *
         */
        String[] words = {"hello","world","ccc"};
        Observable obsevable = Observable.from(words);
        //将会依次调用
        //onNext("hello");
        //onNext("world");
        //onNext("ccc");
        //onCompleted();

        /**
         * 上面的just(T...)的例子和from(T[])的例子，都和之前的create(OnSubscribe)的例子是等价的。
         */

        /**
         * 订阅
         * 创建了Observerable和Obserer之后，再用subscribe()方法将它们连接起来，整条链子就可以工作了。
         */
        observable.subscribe(observer);
        //or
        observable.subscribe(subscriber);
    }

    public void p(){
        /**
         * 除了subscribe(Observer)和subscribe(Subscriber),subscribe()还支持不完整定义的回调。
         * RxJava 会自动根据定义创建出 Subscriber 。形式如下：
         */
        Action1<String> onNextAciton = new Action1<String>() {
            @Override
            public void call(String s) {

            }
        };
        Action1<Throwable> onErrorAction = new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

            }
        };
        Action0 onCompleteAction = new Action0(){

            @Override
            public void call() {

            }
        };
        observable.subscribe(onNextAciton);
        observable.subscribe(onNextAciton,onErrorAction);
        //自动创建Subscriber，并使用onNextAction、onErrorAction来定义onNext()和onError();
        observable.subscribe(onNextAciton,onErrorAction,onCompleteAction);

    }






    private Bitmap getBitmapFromFile(File file) {
        return null;
    }


}
