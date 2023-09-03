package com.example.firebaseproject;

public class Count {
    private int value;

    // Required empty constructor for Firestore
    public Count() {
    }

    public Count(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
