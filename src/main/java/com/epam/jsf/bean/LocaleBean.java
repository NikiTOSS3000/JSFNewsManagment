package com.epam.jsf.bean;

import com.epam.jsf.util.MessageManager;
import java.util.Locale;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public final class LocaleBean{
    private String locale;

    public LocaleBean() {
        locale = MessageManager.getStr("locale.eng");
    }
    
    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }
    
    public String changeLocale() {
        FacesContext.getCurrentInstance().getViewRoot().setLocale(new Locale(locale));
        return MessageManager.getStr("path.list");
    }
}
