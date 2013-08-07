package com.epam.jsf.bean;

import com.epam.jsf.database.INewsDAO;
import com.epam.jsf.model.News;
import com.epam.jsf.util.MessageManager;
import com.epam.jsf.util.NewsDAOUtil;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public final class NewsViewBean {

    private INewsDAO newsDAO = NewsDAOUtil.getNewsDao();
    private int id;
    private News newsMessage;

    public void loadNewsMessage() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        if (params.get("id") == null) {
            newsMessage = newsDAO.fetchById(id);
        } else {
            id = Integer.parseInt(params.get("id"));
            newsMessage = newsDAO.fetchById(id);
        }
    }

    public String deleteNewsMessage() {
        newsDAO.remove(newsMessage);
        return MessageManager.getStr("path.list");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public News getNewsMessage() {
        return newsMessage;
    }

    public void setNewsMessage(News newsMessage) {
        this.newsMessage = newsMessage;
    }
}
