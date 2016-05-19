package AlgoritmSecventa;

import java.util.LinkedList;
import java.util.List;

public class Element implements Comparable<Element>{
    
    private int nod;
    private int distanta;
    private int precedent;
    
    public Element(int nod){
        this.nod = nod;
        this.distanta = 0;
        this.precedent = 0;
    }
    
    public Element(int nod, int distanta, int precedent){
        this.nod = nod;
        this.distanta = distanta;
        this.precedent = precedent;
    }

    @Override
    public int compareTo(Element o) {
        
        if(this.getDistanta() < o.getDistanta()){
            return -1;
        }else
        if(this.getDistanta() == o.getDistanta()){
            return 0;
        }else{
            return 1;
        }
        
    }
    
    public int getDistanta(){
        return this.distanta;
    }
    
    public int getPrecedent(){
        return this.precedent;
    }
    
    public int getNod(){
        return this.nod;
    }
    
}
