package com.epam.jsf.util;

import java.util.ResourceBundle;

public final class MessageManager {

    private static ResourceBundle resource = ResourceBundle.getBundle("com.epam.jsf.resources.messages");

    public static String getStr(String key) {
        return resource.getString(key);
    }

    private MessageManager() {
    }
}
