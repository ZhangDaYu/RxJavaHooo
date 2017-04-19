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
        //����һ���۲���
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onCompleted() {
                Log.e(TAG, "onCompleted: ");
            }

            //onComplete ��onError ֻ����һ������
            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: ");
            }

            @Override
            public void onNext(String s) {
                Log.e(TAG, "onNext: ");
            }
        };

        //����Observer�ӿ�֮�⣬RxJava������һ��ʵ����Observer �ĳ����ࣺSubscriber
        //Subscriber��Ovserver�ӿ�����һЩ��չ��
        //�������ǵ�ʹ�÷�ʽ����ȫһ���ġ�
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
        //ѡ��Observer��Subscriber����ȫһ���ġ�
        //���ǵ��������ʹ������˵��Ҫ�����㣺


        //ʹ��observable.create()�������۲���
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
         * ���Կ��������ﴫ����һ��OnSubscribe������Ϊ������
         * onSubscribe�ᱻ�洢�ڷ��ص�observable�����У�
         * ���������൱��һ���ƻ�����observalbe�����ĵ�ʱ��
         * oNservable��call()�����ͻ��Զ������ã�
         * �¼����оͻ������趨���δ�������������Ĵ��룩��
         * ���ǹ۲���Subscribe���ᱻ��������onNext()��һ��onComplete().
         *
*/
        /**
         *  create()������RxJava������Ĵ���ʱ�����еķ�����
         *  �������������RxJava���ṩ��һЩ����������ݴ����¼����С�����
         *
         *  just(T ...)������Ĳ���һ�η��ͳ���
         */
        Observable observable = Observable.just("hello","world","aaa");
        //�������ε���
        //onNext("hello");
        //onNext("world");
        //onNext("aaa");
        //onCompleted();

        /**
         * from(T[])/from(Iterable<? extends T>):������������Iterable��ֳɾ����������η�����
         *
         */
        String[] words = {"hello","world","ccc"};
        Observable obsevable = Observable.from(words);
        //�������ε���
        //onNext("hello");
        //onNext("world");
        //onNext("ccc");
        //onCompleted();

        /**
         * �����just(T...)�����Ӻ�from(T[])�����ӣ�����֮ǰ��create(OnSubscribe)�������ǵȼ۵ġ�
         */

        /**
         * ����
         * ������Observerable��Obserer֮������subscribe()���������������������������ӾͿ��Թ����ˡ�
         */
        observable.subscribe(observer);
        //or
        observable.subscribe(subscriber);
    }

    public void p(){
        /**
         * ����subscribe(Observer)��subscribe(Subscriber),subscribe()��֧�ֲ���������Ļص���
         * RxJava ���Զ����ݶ��崴���� Subscriber ����ʽ���£�
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
        //�Զ�����Subscriber����ʹ��onNextAction��onErrorAction������onNext()��onError();
        observable.subscribe(onNextAciton,onErrorAction,onCompleteAction);

    }






    private Bitmap getBitmapFromFile(File file) {
        return null;
    }


}
