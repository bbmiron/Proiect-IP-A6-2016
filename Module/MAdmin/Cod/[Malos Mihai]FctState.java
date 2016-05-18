/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Mihai
 */
public class FctState {
    Boolean state;
    String port;
    String IP;
    int fctID;
    String input;
    String output;

    public FctState() {
        this.setState(false);
    }

    public FctState(int ID, String IP, String port, String input, String output, boolean b){
        this.setState(b);
        this.setIP(IP);
        this.setPort(port);
        this.setID(ID);
        this.setInput(input);
        this.setOutput(output);
    }

    public boolean getState(){
        return this.state;
    }

    public int getID(){
        return this.fctID;
    }

    public void setState(boolean state){
        this.state = state;
    }

    private void setID(int ID){
        this.fctID = ID;
    }

    private void setInput(String input) {
        this.input = input;
    }

    private void setOutput(String output) {
        this.output = output;
    }

    public String getInput() {
        return this.input;
    }

    public String getOutput() {
        return this.output;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    public String getPort() {
        return this.port;
    }

    public String getIP() {
        return this.IP;
    }
}
