package com.zhangdayu.rxjavahooo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/19.
 */
public class Student {
    String name;
    List<Course> course;

    public Student(String name) {
        this.name = name;
        course = new ArrayList<>();
        course.add(new Course("course1"));
        course.add(new Course("course2"));
        course.add(new Course("course3"));
    }

    public String getName() {
        return name;
    }

    public List<Course> getCourse() {
        return course;
    }

}


class Course {
    String name;

    public Course(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}