/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Admin;
import java.util.*;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;

/**
 *
 * @author Stefan
 */
public class Admin {
    private final int MaxTime;
    private List<FctState> FctList;
    private Map <User, Map<Integer, List<FctState>>> Interfaces;
    private GUI gui;
    public List<User> users;
    private AdminView admvw;
    public Admin(int MaxTime){
        this.MaxTime=MaxTime;
        this.FctList= new ArrayList<FctState>();
        this.Interfaces=new HashMap<>();
        this.gui = new GUI();
        this.admvw = new AdminView(gui);
    }

    /**
     *
     * @return
     */
    public boolean ValidateRuntime(){
        if(FctList.isEmpty()){
            return false;
        }
        for(FctState iterator:FctList){
            long startTime = 0,endTime = 0;
            if(iterator.getState()==true){
                startTime=System.currentTimeMillis();
            }
            if(iterator.getState()==false){
                endTime=System.currentTimeMillis();
            }
            return (endTime-startTime) <= MaxTime;
        }
        return false;
    }

    public void addInMap(User user, Map<Integer, List<FctState>> noduri){
    	Graph gr = new SingleGraph(user.getUserID());
    	gui.graphs.put(user.getUserID(),gr);
    	gui.addTab(user, user.getTasks(), noduri);
    	gui.frame.pack();
    }
    

    public void removeFromMap(User user){
    	Interfaces.remove(user);
    	gui.removeTab(user.getUserID());
    	gui.frame.pack();
    }

    public boolean addFct(FctState PC){
        return FctList.add(PC);
    }

    public void run(){
    	admvw.executeGUI();
    }


    public boolean removeFct(FctState PC){
        return FctList.remove(PC);
    }
    
    public void addEdge(String ID, int nivel, int nr){
    	gui.addEdge(ID, nivel, nr);
    }

}
