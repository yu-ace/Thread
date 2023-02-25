package com.example.demo;

public class T4 {
    public static void main(String[] args) {
        System.out.println(type(10));
    }

    public static int type(int n){
        if(n <= 1){
            return 1;
        }else if(n == 2){
            return 2;
        }
        return type(n-1)+type(n-2);
    }
}
