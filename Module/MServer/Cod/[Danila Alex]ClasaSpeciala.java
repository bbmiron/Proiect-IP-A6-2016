/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ip.database.v2;

/**
 *
 * @author Alex
 */
public class ClasaSpeciala {
    private int input;
    private int output;
    private int tipFunctie;
    
    public ClasaSpeciala(int input, int output, int tipFunctie){
        this.input = input;
        this.output = output;
        this.tipFunctie = tipFunctie;
    }

    public int getInput() {
        return input;
    }

    public void setInput(int input) {
        this.input = input;
    }

    public int getOutput() {
        return output;
    }

    public void setOutput(int output) {
        this.output = output;
    }

    public int getTipFunctie() {
        return tipFunctie;
    }

    public void setTipFunctie(int tipFunctie) {
        this.tipFunctie = tipFunctie;
    }
}
