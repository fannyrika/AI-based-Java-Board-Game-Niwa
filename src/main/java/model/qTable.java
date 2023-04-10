package main.java.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class qTable implements Serializable{
    protected Map<State, Map<Action, Double>> map = new HashMap<>();

    public qTable() {
        this.map = new HashMap<>();
    }

    //print the qTable as a matrix of states and actions
    public void print(){
        for (Map.Entry<State, Map<Action, Double>> entry : map.entrySet()) {
            System.out.println(entry.getKey());
            for (Map.Entry<Action, Double> entry2 : entry.getValue().entrySet()) {
                System.out.println("    " + entry2.getKey() + " : " + entry2.getValue());
            }
        }
    }
}
