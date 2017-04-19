package com.zhangdayu.rxjavahooo;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/4/19.
 */

public class OtherAcitvity extends Activity {
    private static final String TAG = "OtherAcitvity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        test1();
//
//        test2();
//        test3();

//        test4();
        test5();

        test6();
        test7();
    }

    /**
     * 由于可以在嵌套的Observable中添加异步代码，
     * flatMap()也常用于嵌套的异步操作，例如嵌套的网络请求。
     * 例如（Retrofit+RxJava）
     */
    private void test7() {


    }

    /**
     * 一个Student转化成多个Course，这时候要用flatMap()
     */
    private void test6() {
        Student[] students = {new Student("name1"), new Student("name2")};
        Subscriber<Course> subscriber = new Subscriber<Course>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Course course) {
                Log.e(TAG, "test6 onNext: course.name == " + course.getName());
            }
        };
        Observable.from(students)
                .flatMap(new Func1<Student, Observable<Course>>() {
                    @Override
                    public Observable<Course> call(Student student) {
                        return Observable.from(student.getCourse());
                    }
                })
                .subscribe(subscriber);
    }

    /**
     * 要打印出每个学生所需要修的所有课程的名字
     * 每个学生有多个课程
     */
    private void test5() {
        Student[] students = {new Student("name1"), new Student("name2")};
        Subscriber<Student> subscriber = new Subscriber<Student>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Student student) {
                List<Course> courses = student.getCourse();
                for (int i = 0; i < courses.size(); i++) {
                    Course course = courses.get(i);
                    Log.e(TAG, "test5 onNext: course == " + course.getName());
                }
            }

        };
        Observable.from(students)
                .subscribe(subscriber);
    }

    /**
     * 假设一个数据结构【学生】，现在需要打印一组学生的名字
     */
    private void test4() {
        Student[] students = {new Student("name1"), new Student("name2")};
        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                Log.e(TAG, "onNext:  name = " + s);
            }
        };
        Observable.from(students)
                .map(new Func1<Student, String>() {
                    @Override
                    public String call(Student student) {
                        return student.getName();

                    }
                })
                .subscribe(subscriber);
    }

    private void test2() {
        final int drawableRes = R.mipmap.ic_launcher;
        final ImageView imageView = (ImageView) findViewById(R.id.iv_show);
        Observable.create(new Observable.OnSubscribe<Drawable>() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void call(Subscriber<? super Drawable> subscriber) {
                Drawable drawable = getTheme().getDrawable(drawableRes);
                subscriber.onNext(drawable);
                subscriber.onCompleted();
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Drawable>() {
                    @Override
                    public void onCompleted() {
                        Log.e(TAG, "onCompleted: ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: --------");
                        Toast.makeText(OtherAcitvity.this, "Error", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(Drawable drawable) {
                        imageView.setImageDrawable(drawable);
                    }
                });

    }

    private void test1() {
        String[] name = {"hello", "aa", "bb"};
        Observable.from(name)
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.e(TAG, "call: " + s);
                    }
                });
    }

    /**
     * 3.线程控制-Scheduler(一)
     */
    private void test3() {
        /**
         * 如果要切换线程，就需要用到Scheduler(调度器)
         * 在RxJava中，Scheduler调度器，相当于线程控制器，
         * RxJava通过它来指定每一段代码应该运行在什么样的线程。
         * RxJava已经内置了几个Scheduler，他们已经使用大多数的使用场景：
         * Schedulers.immediate():直接在当前线程运行，相当于不指定线程。这是默认的Scheduler.
         * Schedulers.newThread():总是启动新线程，并在新线程执行操作。
         * Schedulers.io() :io操作（读写文件。读写数据库，网络信息交互等）所使用的Scheduler。
         * 行为模式和newThread（）差不多。
         *
         * subscribeOn：指定subscribe()所发生的线程，即Onservable.OnSubscribe被激活是所处的线程。
         * 或者叫做时间产生的线程。
         */

        Observable.just(1, 2, 3, 4)
                .subscribeOn(Schedulers.io()) // 指定subscribe（）发生在io线程
                .observeOn(AndroidSchedulers.mainThread()) //指定subscriber的对调发生在主线程
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        Log.e(TAG, "number = " + integer);
                    }
                });
    }
}
