package Admin;


public class AdminView {
    
    public GUI gui;
    
    public AdminView(GUI gui){
        this.gui = gui;
    }
    
    public void executeGUI(){
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                gui.createAndShowGUI();
            }
        });
    }
}
