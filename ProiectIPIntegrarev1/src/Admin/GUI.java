package Admin;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import org.apache.commons.lang3.StringUtils;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.ui.view.View;
import org.graphstream.ui.view.Viewer;

@SuppressWarnings("serial")
public class GUI extends JPanel {
    int inset;
    int height;
    int width;
	public JFrame frame = new JFrame("Map");
    int index = 1;
    String result;
    public Map<String, Graph> graphs = new HashMap<>();
    JTabbedPane tabbedPane = new JTabbedPane();
    public List<User> users = new ArrayList<>();
    /*public Graph graph = new MultiGraph("Tutorial 1");
	private Viewer viewer = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
	private View view = viewer.addDefaultView(false);*/

    public GUI() {
        super(new GridLayout(1,1));

        inset = 200;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        height = screenSize.height - inset*2;
        width = screenSize.width  - inset*2;

        JComponent form = makeTextPanel("");

        tabbedPane.addTab("Database", form);
        frame.add(tabbedPane);
    }

    protected JComponent makeTextPanel(String text) {
        JPanel mainPanel = new JPanel(false);
        JLabel filler = new JLabel(text);
        mainPanel.setLayout(new GridLayout(1, 1));
        mainPanel.add(filler);


        JTextField IP = new JTextField("");
        JTextField Port = new JTextField("");
        JTextField Input = new JTextField("");
        JTextField Output = new JTextField("");
        JTextField functionType = new JTextField("");

        mainPanel.setPreferredSize(new Dimension(700,250));
        mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.PAGE_AXIS));
        JPanel emptyPcImagePanel = new JPanel(new GridLayout(0, 1));
        JPanel getInformationAboutPcPanel = new JPanel(new GridLayout(0, 1));
        JPanel contentPanel = new JPanel();
        JPanel buttonsPanel = new JPanel();
        JButton insert = new JButton("Insert");
        JButton delete = new JButton("Delete");
        JPanel labelsAndButtonsPanel = new JPanel();

        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.X_AXIS));
        labelsAndButtonsPanel.setLayout(new BoxLayout(labelsAndButtonsPanel, BoxLayout.Y_AXIS));

        try {
            BufferedImage myPicture = ImageIO.read(new File("EmptyPC.png"));
            JLabel picLabel = new JLabel(new ImageIcon(myPicture));
            emptyPcImagePanel.add(picLabel, BorderLayout.NORTH);
        } catch (IOException e) {
            e.printStackTrace();
        }


        getInformationAboutPcPanel.add(new JLabel("IP:"));
        getInformationAboutPcPanel.add(IP);
        getInformationAboutPcPanel.add(new JLabel("Port:"));
        getInformationAboutPcPanel.add(Port);
        getInformationAboutPcPanel.add(new JLabel("Input: "));
        getInformationAboutPcPanel.add(Input);
        getInformationAboutPcPanel.add(new JLabel("Output: "));
        getInformationAboutPcPanel.add(Output);
        getInformationAboutPcPanel.add(new JLabel("Function type: "));
        getInformationAboutPcPanel.add(functionType);
        contentPanel.add(getInformationAboutPcPanel,BorderLayout.NORTH);

        insert.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(IP.getText()+ " " + Port.getText()+" "+Input.getText()+" "+Output.getText());
                if(!StringUtils.isNumeric(Port.getText())||(Port.getText().length()<4)){
                    JOptionPane.showMessageDialog(null, "Portul trebuie sa fie un numar de 4 cifre", "Alert", JOptionPane.INFORMATION_MESSAGE);
                }
                if(!StringUtils.isNumeric(Input.getText())){
                    JOptionPane.showMessageDialog(null, "Parametrul de tip in este incorect!!", "Alert", JOptionPane.INFORMATION_MESSAGE);
                }
                if(!StringUtils.isNumeric(Output.getText())){
                    JOptionPane.showMessageDialog(null, "Parametrul de tip out este incorect!!", "Alert", JOptionPane.INFORMATION_MESSAGE);
                }
                if((!StringUtils.isNumeric(IP.getText())&&IP.getText().matches(".{3,4}")) || StringUtils.isNumeric(IP.getText())){
                    JOptionPane.showMessageDialog(null, "IP-ul nu respecta formatul xxx.x.x.x", "Alert", JOptionPane.INFORMATION_MESSAGE);
                }
                if(!StringUtils.isNumeric(functionType.getText())){
                    JOptionPane.showMessageDialog(null, "Tipul functiei incorect!!", "Alert", JOptionPane.INFORMATION_MESSAGE);
                }
                try {
                    Database db = new Database();
                    String sql="INSERT INTO Computers DATA VALUES ("+"'"+IP.getText()+"'"+","+Port.getText()+","
                                                +Input.getText()+","+Output.getText()+","+functionType.getText()+",0)";
                    db.insert(sql);
                } catch(SQLException se){
                    se.printStackTrace();
                }
            }
        });

        delete.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Database db = new Database();
                    String sql="delete from Computers where port="+Port.getText();
                    System.out.println(sql);
                    if(!StringUtils.isNumeric(Port.getText())||(Port.getText().length()<4)){
                        JOptionPane.showMessageDialog(null, "Portul trebuie sa fie un numar de 4 cifre", "Alert", JOptionPane.INFORMATION_MESSAGE);
                    }
                    db.delete(sql);
                } catch(SQLException se){
                    se.printStackTrace();
                }
            }
        });


        buttonsPanel.add(insert);
        buttonsPanel.add(delete);
        emptyPcImagePanel.add(buttonsPanel, BorderLayout.CENTER);
        contentPanel.add(emptyPcImagePanel, BorderLayout.NORTH);
        mainPanel.add(contentPanel, BorderLayout.NORTH);

        return mainPanel;
    }

    public void setActive(Node nodulet){
        nodulet.setAttribute("ui.style", "" +

                "   fill-color: #0ef412;");
    }
    public void setAvailable(Node nodulet){
        nodulet.setAttribute("ui.style", "" +

                "   fill-color: #b3d1b4;");
    }

    public void setBusy(Node nodulet){
        nodulet.setAttribute("ui.style", "" +

                "   fill-color: #cc0000;");
    }
    
    public void addEdge(String ID, int nivel, int nr){
    	User user = new User();
    	for (int index = 0; index < users.size(); index++) {
    		if (users.get(index).getUserID().equals(ID)) {
    			user = users.get(index);
    		}
    	}
    	String port = user.getNod(nivel, nr);
    }

    public void addTab(User ID, int Tasks, Map<Integer, List<FctState>> noduri){
    	users.add(ID);
    	System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
    	Graph graph = graphs.get(ID);
    	Viewer viewer = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
    	View view = viewer.addDefaultView(false);
    	viewer.disableAutoLayout();

	    graph.addNode("App");
	    Node x = graph.getNode("App");
	    x.setAttribute("x", width / 10);
        x.setAttribute("y", height / 2);
        x.setAttribute("ui.label", "App");
	    x.setAttribute("ui.style", "shape: box;");

    	for (int nivel = 0; nivel < noduri.size(); nivel++) {
    	    for (int listIt = 0; listIt < noduri.get(nivel).size(); listIt++) {
            	    graph.addNode(noduri.get(nivel).get(listIt).getPort());
            	    Node nod = graph.getNode(noduri.get(nivel).get(listIt).getPort());
                    nod.setAttribute("x", width/10 + ((7 * width/10) * (nivel+0.5)) / noduri.size());
                    nod.setAttribute("y", (height - 100) / noduri.get(nivel).size()*(listIt+1));
                    nod.setAttribute("ui.label", noduri.get(nivel).get(listIt).getPort());

                    if(noduri.get(nivel).get(listIt).getState() == true) {
                        setAvailable(graph.getNode(noduri.get(nivel).get(listIt).getPort()));
                    } else {
                        setBusy(graph.getNode(noduri.get(nivel).get(listIt).getPort()));
                    }
    	    }
    	}

    	graph.addNode("Output");
        Node y = graph.getNode("Output");
        y.setAttribute("x", 8 * width / 10);
        y.setAttribute("y", height / 2);
        y.setAttribute("ui.label", "Output");
        y.setAttribute("ui.style", "shape: box;");

        Node attachement = graph.getNode("App");
        for (int nivel = 0; nivel < noduri.size(); nivel++) {
            for (int listIt = 0; listIt < noduri.get(nivel).size(); listIt++) {
                if(noduri.get(nivel).get(listIt).getState() == true){
                    graph.addEdge((String) attachement.getAttribute("ui.label") + "->" + noduri.get(nivel).get(listIt).getPort(),
                            (String) attachement.getAttribute("ui.label"), noduri.get(nivel).get(listIt).getPort(), true);
                    setActive(graph.getNode(noduri.get(nivel).get(listIt).getPort()));
                    noduri.get(nivel).get(listIt).setState(false);
                    Edge ed = graph.getEdge((String) attachement.getAttribute("ui.label") + "->" + noduri.get(nivel).get(listIt).getPort());
                    ed.setAttribute("ui.label", noduri.get(nivel).get(listIt).getInput());
                    attachement = graph.getNode(noduri.get(nivel).get(listIt).getPort());
                    if(nivel == (noduri.size() - 1)) {
                        graph.addEdge( (String) attachement.getAttribute("ui.label") + "->" + (String) y.getAttribute("ui.label"),
                                (String) attachement.getAttribute("ui.label"), (String) y.getAttribute("ui.label"), true);
                        Edge end = graph.getEdge((String) attachement.getAttribute("ui.label") + "->" + (String) y.getAttribute("ui.label"));
                        end.setAttribute("ui.label", noduri.get(nivel).get(listIt).getOutput());
                    }
                    break;
                }
            }
    	}





        graph.setAttribute("ui.stylesheet", "" +
                            "node {" +
                            "   size: 33px;" +
                            "   fill-color: #b5ac8b;" +
                            "   text-style: bold;" +
                            "   text-background-mode: none;" +
                            "   text-padding: 3;" +
                           // "   size-mode: fit;" +
                            "}");

        graph.setAttribute("ui.stylesheet", "" +
                "edge {" +
                "   text-alignment: above;" +
                "}");

    	tabbedPane.addTab("User " + Integer.toString(index), (Component) view);
    	index++;
    	tabbedPane.revalidate();
    	validate();
    	frame.pack();
    }

    public void removeTab(String ID){
    	int pos = users.indexOf(ID);
    	if(pos >= 0){
    		tabbedPane.remove(pos+1);
    		index--;
    	}
    	tabbedPane.revalidate();
    	validate();
    	//frame.pack();
    }
    
   

    void createAndShowGUI() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //frame.setSize(1000, 300);
        frame.setPreferredSize( new Dimension(1000, 300));
        frame.setLocation(inset, inset);

        frame.pack();
        frame.setVisible(true);
    }
}
