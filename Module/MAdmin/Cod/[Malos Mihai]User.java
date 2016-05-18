/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Mihai
 */
public class User {

    Integer noOfTasks;
    String UserID;
    Map<Integer, List<FctState>> functii;

    public User() {
        this.setTasks(0);
        this.setUserID("");
        functii = new HashMap<>();
    }
    public User(Integer number, String id){
        this.setTasks(number);
        this.setUserID(id);
        functii = new HashMap<>();
    }
    public int getTasks(){
        return this.noOfTasks;
    }
    public String getUserID(){
        return this.UserID;
    }
    public void setTasks(Integer nr_tasks){
        this.noOfTasks=nr_tasks;
    }
    public void setUserID(String user_id){
        this.UserID=user_id;
    }
    public void setFunctii(Map<Integer, List<FctState>> funct) {
        this.functii = funct;
    }
    public Map<Integer, List<FctState>> getFunctii() {
        return this.functii;
    }
}
