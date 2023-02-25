package com.example.demo;

public class AList {
    int[] a = new int[100_0000];
    int size = 0;
    public void add(int i){
        synchronized (this){
            a[size] = i;
            size++;
        }
    }
}
