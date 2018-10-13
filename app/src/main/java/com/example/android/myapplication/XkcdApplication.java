package com.example.android.myapplication;

import android.app.Application;

import com.example.android.myapplication.data.XkcdApiManager;
import com.example.android.myapplication.data.XkcdService;

// Singleton class that extends the Application.
// The Application is the first context to be created and the last to be destroyed. Thus,
// it surrounds the life cycle of the app.
public class XkcdApplication extends Application {

    private static volatile XkcdApplication INSTANCE;
    private XkcdService xkcdService;

    // Private constructor
    private XkcdApplication() {
        if (INSTANCE != null) {
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
    }

    public static XkcdApplication getInstance() {
        if (INSTANCE == null) {
            synchronized (XkcdApplication.class) {
                if (INSTANCE == null) {
                    INSTANCE = new XkcdApplication();
                }
            }
        }

        return INSTANCE;
    }

    /**
     * Getter method that creates a Retrofit instance from the {@link XkcdApiManager}
     *
     * @return the service for the XKCD Comics API
     */
    public XkcdService getXkcdApi() {
        if (xkcdService == null) {
            xkcdService = XkcdApiManager.create();
        }

        return xkcdService;
    }
}
