package com.epam.jsf.bean;

import com.epam.jsf.database.INewsDAO;
import com.epam.jsf.model.News;
import com.epam.jsf.resources.Constants;
import com.epam.jsf.util.MessageManager;
import com.epam.jsf.util.NewsDAOUtil;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public final class NewsAddBean {

    private INewsDAO newsDAO = NewsDAOUtil.getNewsDao();
    private int id;
    private News newsMessage;
    private boolean fromVeiw = false;
    private String date;

    public void loadNewsMessage() {
        fromVeiw = false;
        Date data = new Date();
        date = Constants.FORMATTER.format(data);
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        if (params.get("id") != null) {
            id = Integer.parseInt(params.get("id"));
            newsMessage = newsDAO.fetchById(id);
            date = Constants.FORMATTER.format(newsMessage.getDate());
        } else {
            newsMessage = new News();
        }
    }

    public String saveNewsMessage() throws ParseException {
        Date data = Constants.FORMATTER.parse(date);
        newsMessage.setDate(data);
        if (newsMessage.getId() == 0) {
            newsMessage.setId(newsDAO.save(newsMessage));
            id = newsMessage.getId();
            return String.format(MessageManager.getStr("path.view.redirect.format"), id);
        } else {
            newsDAO.update(newsMessage);
            return MessageManager.getStr("path.view");
        }
    }
    
    public String cancel() {
        if (fromVeiw) {
            return String.format(MessageManager.getStr("path.view.redirect.format"), id);
        } else
        return MessageManager.getStr("path.list");
    }

    public String add() {
        date = Constants.FORMATTER.format(newsMessage.getDate());
        return MessageManager.getStr("path.add");
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public boolean isFromVeiw() {
        return fromVeiw;
    }

    public void setFromVeiw(boolean fromVeiw) {
        this.fromVeiw = fromVeiw;
    }
}
