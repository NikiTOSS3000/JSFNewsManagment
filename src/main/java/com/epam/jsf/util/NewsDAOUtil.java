package com.epam.jsf.util;

import com.epam.jsf.database.INewsDAO;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;

public final  class NewsDAOUtil {
    
    public static INewsDAO getNewsDao() {
        WebApplicationContext listener = ContextLoaderListener.getCurrentWebApplicationContext();
        INewsDAO newsDAO = (INewsDAO) listener.getBean(MessageManager.getStr("other.newsDAO"));
        return newsDAO;
    }
    
    private NewsDAOUtil(){}
}
