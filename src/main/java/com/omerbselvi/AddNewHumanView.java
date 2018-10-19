package com.omerbselvi;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.sql.SQLException;

@ManagedBean(name = "addNewHuman")
@SessionScoped
public class AddNewHumanView {
    private String name;
    private String surname;
    private int age;
    private String phoneNumber;
    private String address;

    @ManagedProperty(value = "#{humanView}")
    private HumanView humanView;

    public void sendBundledMessage(FacesMessage.Severity messageType, String subject, String description){
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(messageType, subject, description));
    }

    //TODO IMPROVE THIS METHOD
    public String addHumanAction(){
        boolean anyError = false;
        if(name == null || name.length() < 1){
             sendBundledMessage(FacesMessage.SEVERITY_WARN, "Warning", "Fill in the name field");
             anyError = true;
        }
        if(surname == null || surname.length() < 1){
            sendBundledMessage(FacesMessage.SEVERITY_WARN, "Warning", "Fill in the surname field");
            anyError = true;
        }
        if(age < 0){
            sendBundledMessage(FacesMessage.SEVERITY_WARN, "Warning", "Fill in the age field");
            anyError = true;
        }
        Human newHumanToAdd = new Human(name, surname, age, phoneNumber, address);

        HumanBean humanBean = new HumanBean();
        int count = humanBean.isDuplicate(newHumanToAdd);
        if (count > 0){
            sendBundledMessage(FacesMessage.SEVERITY_ERROR, "Warning", "There are already someone named: " + name + " " + surname);
            anyError = true;
        }
        if(anyError){
            return "addNewHuman.xhtml";
        }
        try {
            humanBean.insertHumanAction(newHumanToAdd);
            sendBundledMessage(FacesMessage.SEVERITY_INFO, "Info", "New address is successfully created.");
        } catch (SQLException e) {
            sendBundledMessage(FacesMessage.SEVERITY_ERROR, "Error", "SQL Exception");
            e.printStackTrace();
        }
        return "addNewHuman.xhtml";
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public HumanView getHumanView() {
        return humanView;
    }

    public void setHumanView(HumanView humanView) {
        this.humanView = humanView;
    }
}
