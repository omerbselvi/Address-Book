package com.omerbselvi;

import javax.faces.application.Application;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

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

    //TODO IMPROVE THIS METHOD
    public String addHumanAction(){
        Human newHumanToAdd = new Human(name, surname, age, phoneNumber, address);
        HumanBean humanBean = new HumanBean();
        humanBean.insertHumanAction(newHumanToAdd);
        return "index.xhtml";
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
