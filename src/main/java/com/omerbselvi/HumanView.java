package com.omerbselvi;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.util.ArrayList;

@ManagedBean(name = "humanView")
@SessionScoped
public class HumanView {

    private ArrayList<Human> humanList;

    public void onLoad(){
        if(getHumanList() == null) {
            setHumanList(new ArrayList<Human>());
            getHumanList().add(new Human("Ömer Buğra", "Selvi", 21));
            getHumanList().add(new Human("TEST", "TEST2", 12));
            getHumanList().add(new Human("TEST3", "TEST4", 13));
        }
    }

    public ArrayList<Human> getHumanList() {
        return humanList;
    }

    public void setHumanList(ArrayList<Human> humanList) {
        this.humanList = humanList;
    }
}