package com.example.android.myapplication.repository;

import com.example.android.myapplication.data.XkcdService;

/*
Repository pattern that is a Singleton class.
It abstracts the call to the XkcdService object
 */
public class XkcdRepository {

    // Make the variable variable, so that memory barrier is inserted
    // All writes initiated prior to that of INSTANCE will be completed before
    // the INSTANCE is modified
    // source: https://stackoverflow.com/a/11640026/8132331
    private static volatile XkcdRepository INSTANCE;


    // Private constructor
    public XkcdRepository() {
        if (INSTANCE != null) {
            throw new RuntimeException("Use getInstance() method to get the single instance of " +
                    "this class.");
        }
    }

    public static XkcdRepository getInstance() {
        if (INSTANCE == null) {
            // If there is no instance available, create a new one
            synchronized (XkcdRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new XkcdRepository();
                }
            }
        }

        return INSTANCE;
    }

}
