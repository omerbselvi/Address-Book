package com.omerbselvi;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@ManagedBean(name = "loginAction")
@SessionScoped
public class LoginAction extends ConnectionBase{

    @ManagedProperty(value = "#{loginSession}")
    private LoginSession loginSession;

    public void logOut(){
        ExternalContext ec = FacesContext
                .getCurrentInstance()
                .getExternalContext();
        HttpServletRequest request = (HttpServletRequest) ec.getRequest();
        request.getSession( false ).invalidate();
    }

    public void loginCheck() throws IOException {
        if(loginSession == null) loginSession = new LoginSession();
        ExternalContext ec = FacesContext
                .getCurrentInstance()
                .getExternalContext();
        HttpServletRequest httpServletRequest = (HttpServletRequest) ec.getRequest();
        String url = httpServletRequest.getRequestURL().toString();
        if(url != null && url.trim().length() > 0){
            if(!getLoginSession().isLoggedIn() && !url.contains("Login")){
                ec.redirect(ec.getRequestContextPath() + "/Login.xhtml");
            }
            else if(getLoginSession().isLoggedIn() && url.contains("Login")){
                ec.redirect(ec.getRequestContextPath() + "/index.xhtml");
            }
        }
    }
    public String activateUser(){
        String username = "";
        String password = "";
        boolean anyFieldEmpty = false;
        if(loginSession.getPassword() == null || loginSession.getPassword().trim().length() == 0){
            sendBundledMessage(FacesMessage.SEVERITY_ERROR, "Username", "Username field empty.");
            anyFieldEmpty = true;
        }
        if(loginSession.getPassword() == null || loginSession.getPassword().trim().length() == 0){
            sendBundledMessage(FacesMessage.SEVERITY_ERROR, "Password", "Password field empty.");
            anyFieldEmpty = true;
        }
        if(anyFieldEmpty){
            return "Login";
        }
        username = loginSession.getUsername();
        password = loginSession.getPassword();

        boolean isAuthenticated;
        isAuthenticated = authenticateUser(username, password);
        if(isAuthenticated){
            loginSession.setLoggedIn(true);
            return "index";
        }
        else{
            loginSession.setLoggedIn(false);
            sendBundledMessage(FacesMessage.SEVERITY_ERROR, "Error", "Username or password wrong.");
            return "Login";
        }
    }


    public final static String AUTH_USER = "SELECT COUNT(id) AS cnt FROM User WHERE username = ? AND password = ?";
    public boolean authenticateUser(String username, String password){
        PreparedStatement ps = null;
        ResultSet rs = null;
        int count = 0;
        try {
            ps = connect.prepareStatement(AUTH_USER);
            ps.setString(1, username);
            ps.setString(2, password);
            rs = ps.executeQuery();
            rs.next();
            count = rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(count > 0){return true;}
        else {return false;}
    }

    public void sendBundledMessage(FacesMessage.Severity messageType, String subject, String description){
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(messageType, subject, description));
    }

    public LoginSession getLoginSession() {
        return loginSession;
    }

    public void setLoginSession(LoginSession loginSession) {
        this.loginSession = loginSession;
    }
}
