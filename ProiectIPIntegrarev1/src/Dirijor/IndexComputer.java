/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dirijor;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.*;

import Admin.Admin;
import Admin.FctState;
import Admin.User;
import AlgoritmSecventa.ClasaSpeciala;
import BazaDate.BazaDate;
import BazaDate.Calculator;

public class IndexComputer {

	JSONObject inputJs;
	String input;
	private ArrayList<Integer> cList;
	private String finalOutput;
	private Admin admin;
	private BazaDate bd;
	boolean status = true;

	private ArrayList<Calculator> ComputerInfo;
	int noComputers;

	public IndexComputer() {
	};

	public IndexComputer(BazaDate bd, Admin admin) {

		this.bd = bd;
		this.admin = admin;

	}

	public void setInfoFromDServer(ArrayList<Integer> cList, String input) {

		this.input = input;
		this.cList = cList;
		noComputers = cList.size();

		try {
			this.inputJs = new JSONObject(input);
		} catch (JSONException ex) {

			// String eronat primit de la Server
			// return JSON --> cod eroare
			this.status = false;

		}
	}

	public void operateComputer() {
		
		Map<Integer, List<FctState>> lista = new HashMap<>();
		
		for (int cursor = 0; cursor < cList.size(); cursor++) {

			try {
				ComputerInfo = this.bd.getStatus(this.cList.get(cursor));
				List<FctState> nivel = new ArrayList<>();
				for(int i=0; i<ComputerInfo.size(); ++i){
					nivel.add(new FctState(this.cList.get(cursor), ComputerInfo.get(i).getIp(), ComputerInfo.get(i).getPort().toString(), ComputerInfo.get(i).getNumeInput(), ComputerInfo.get(i).getNumeOutput(), ComputerInfo.get(i).getStatus()));
				}
				
				lista.put(cursor, nivel);
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // IN TESTE
		}
		
		User user = new User(lista.size(), "2345");
		
		admin.addInMap(user, lista);

		for (int cursor = 0; cursor < cList.size(); cursor++) {

			try {
				ComputerInfo = this.bd.getStatus(this.cList.get(cursor));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // IN TESTE
			
			System.out.println("@@@@@@@ " + cList.get(cursor));

			for (int cursor2 = 0; cursor2 < ComputerInfo.size(); cursor2++) {

				DirijorCalculatoare nextComputer;
				nextComputer = new DirijorCalculatoare(admin, inputJs, ComputerInfo.get(cursor2).getIp() + ":" + ComputerInfo.get(cursor2).getPort().toString(), cursor, cursor2, user.getUserID());

				try {

					inputJs = nextComputer.getOutput();
				} catch (IOException ex) {
					Logger.getLogger(IndexComputer.class.getName()).log(Level.SEVERE, null, ex);
				}

				if (nextComputer.getStatus() == false) {

					cursor2 = ComputerInfo.size();
					cursor = cList.size();
				}

			}

			noComputers--;
		}

		input = this.inputJs.toString();
		this.finalOutput = input;
	}

	public JSONObject getFinalOutput() throws IOException, JSONException {

		if (status == true) { // tratare caz de eroare input primar de la
								// server.

			operateComputer();
			inputJs = new JSONObject(finalOutput);
		}

		return this.inputJs;
	}

}
