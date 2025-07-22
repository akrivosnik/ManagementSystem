package com.example.storage;

import com.example.model.Student;
import com.example.model.Course;
import com.example.model.AttendanceRecord;

import java.util.HashMap;
import java.util.Map;

public class DataStore {


    public static Map<Integer, Student> students = new HashMap<>();


    public static Map<Integer, Course> courses = new HashMap<>();


    public static Map<Integer, AttendanceRecord> attendanceRecords = new HashMap<>();


    public static int studentIdCounter = 1;
    public static int courseIdCounter = 1;
    public static int attendanceIdCounter = 1;


}
