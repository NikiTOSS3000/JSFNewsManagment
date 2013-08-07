package com.epam.jsf.bean;

import com.epam.jsf.database.INewsDAO;
import com.epam.jsf.model.News;
import com.epam.jsf.util.MessageManager;
import com.epam.jsf.util.NewsDAOUtil;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public final class NewsListBean {

    private INewsDAO newsDAO = NewsDAOUtil.getNewsDao();
    private List<News> newsList;
    private Map<Integer, Boolean> checkboxes;

    public NewsListBean() {
        checkboxes = new HashMap<Integer, Boolean>();
    }

    public List<News> getNewsList() {
        return newsList;
    }

    public void setNewsList(List<News> list) {
        this.newsList = list;
    }

    public Map<Integer, Boolean> getCheckboxes() {
        return checkboxes;
    }

    public void setCheckboxes(Map<Integer, Boolean> checkboxes) {
        this.checkboxes = checkboxes;
    }

    public void loadNews() throws ParseException {
        newsList = newsDAO.getList();
    }

    public String deleteNews() {
        Iterator<Integer> iterator = checkboxes.keySet().iterator();
        Integer[] ids = new Integer[checkboxes.size()];
        int i = 0;
        while (iterator.hasNext()) {
            Integer id = iterator.next();
            if (checkboxes.get(id)) {
                ids[i++] = id;
            }
        }
        newsDAO.remove(ids);
        return MessageManager.getStr("path.list");
    }
}
