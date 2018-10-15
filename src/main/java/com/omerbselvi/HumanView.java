package com.omerbselvi;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.sql.SQLException;
import java.util.ArrayList;

@ManagedBean(name = "humanView")
@SessionScoped
public class HumanView {

    private ArrayList<Human> humanList;

    public void onLoad(){
        if(getHumanList() == null) {
            setHumanList(new ArrayList<Human>());
            HumanBean humanBean = new HumanBean();

            try {
                humanList.addAll(humanBean.getHumans());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void deleteHumanObject(Human humanToDelete){
        humanList.remove(humanToDelete);
    }

    public ArrayList<Human> getHumanList() {
        return humanList;
    }

    public void setHumanList(ArrayList<Human> humanList) {
        this.humanList = humanList;
    }
}
