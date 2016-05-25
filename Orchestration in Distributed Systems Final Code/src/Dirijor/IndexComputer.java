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
import Server.Server;

public class IndexComputer {

	JSONObject inputJs;
	String input;
	private ArrayList<Integer> cList;
	private String finalOutput;
	private Admin admin;
	private BazaDate bd;
	boolean status = true;
	private String idUser;
	private User user;
	private int flagPcIsFree;
	private int flagFunction;

	private ArrayList<Calculator> ComputerInfo;
	int noComputers;

	public IndexComputer() {
	};

	public IndexComputer(BazaDate bd, Admin admin, String idUser) {

		this.bd = bd;
		this.admin = admin;
		this.idUser = idUser;

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

	public String operateComputer() {
		Map<Integer, List<FctState>> lista = new HashMap<>();
		int statusPC;

		for (int cursor = 0; cursor < cList.size(); cursor++) {

			try {
				ComputerInfo = this.bd.getStatus(this.cList.get(cursor));
				List<FctState> nivel = new ArrayList<>();
				for (int i = 0; i < ComputerInfo.size(); ++i) {
					nivel.add(new FctState(this.cList.get(cursor), ComputerInfo.get(i).getIp(),
							ComputerInfo.get(i).getPort().toString(), ComputerInfo.get(i).getNumeInput(),
							ComputerInfo.get(i).getNumeOutput(), ComputerInfo.get(i).getStatus()));
				}

				lista.put(cursor, nivel);

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // IN TESTE
		}
		
		System.out.println("Id user = "+ idUser);
		this.user = new User(lista.size(), idUser);

		admin.addInMap(this.user, lista);

		for (int cursor = 0; cursor < cList.size(); cursor++) {
			DirijorCalculatoare nextComputer;
			try {
				ComputerInfo = this.bd.getStatus(this.cList.get(cursor));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // IN TESTE
			statusPC = 0;
			for (int cursor2 = 0; cursor2 < ComputerInfo.size(); cursor2++) {
				try {
					statusPC = bd.getFunctionStatus(ComputerInfo.get(cursor2).getPort(),
							ComputerInfo.get(cursor2).getIp().toString());
				} catch (SQLException e2) {
					System.out.println("[Index Computer]Statusul nu a putut fi detectat.");
				}
				this.flagFunction = cursor2;
				if (statusPC == 0)
					break;
			}
			if (statusPC == 0) {
				System.out.println("Functia apelata este:" + ComputerInfo.get(this.flagFunction).getIp() + " "
						+ ComputerInfo.get(this.flagFunction).getPort().toString());
				nextComputer = new DirijorCalculatoare(admin, bd, inputJs,
						ComputerInfo.get(this.flagFunction).getIp() + ":"
								+ ComputerInfo.get(this.flagFunction).getPort().toString(),
						cursor, this.flagFunction, user.getUserID());
				try {
					inputJs = nextComputer.getOutput();
				} catch (IOException ex) {
					Logger.getLogger(IndexComputer.class.getName()).log(Level.SEVERE, null, ex);
				}
			} else {
				System.out.println("Functia apelata este:" + ComputerInfo.get(0).getIp() + " "
						+ ComputerInfo.get(0).getPort().toString());
				while (true) {
					try {
						statusPC = bd.getFunctionStatus(ComputerInfo.get(0).getPort(),
								ComputerInfo.get(0).getIp().toString());
						if (statusPC == 1) {
							Thread.sleep(1000);
							this.flagPcIsFree++;
						} else {
							break;
						}
						if (this.flagPcIsFree == 7) {
							input = "{\"code\" :\"4101\", \"message\":\"[IndexComputer]Serverul este ocupat momentan.\",\"data\":{\"eroare\":\"nu exista\"}}";
							return input;
						}
					} catch (SQLException e1) {
						System.out.println("[IndexComputer]Statusul nu a fost detectat!");
						input = "{\"code\" :\"4101\", \"message\":\"Eroare modificare status pc. Reincercati.\",\"data\":{\"eroare\":\"nu exista\"}}";
						return input;
					} catch (InterruptedException e) {
						System.out.println("[IndexComputer]Eroare la sleep thread!");
					}

				}
				nextComputer = new DirijorCalculatoare(admin, bd, inputJs,
						ComputerInfo.get(0).getIp() + ":" + ComputerInfo.get(0).getPort().toString(), cursor, 0,
						user.getUserID());
				try {
					inputJs = nextComputer.getOutput();
				} catch (IOException ex) {
					Logger.getLogger(IndexComputer.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
			noComputers--;
			if (nextComputer.getStatus() == false)
				cursor = cList.size();
		}

		input = this.inputJs.toString();
		return input;
	}

	public JSONObject getFinalOutput() throws IOException, JSONException {

		if (status == true) { // tratare caz de eroare input primar de la
								// server.

			this.finalOutput = operateComputer();
			inputJs = new JSONObject(finalOutput);
		}
		System.out.println("User id: " + user.getUserID());
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		admin.removeFromMap(this.user);
		return this.inputJs;
	}

}
