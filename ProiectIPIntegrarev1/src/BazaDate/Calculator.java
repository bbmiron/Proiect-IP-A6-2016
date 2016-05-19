package BazaDate;

public class Calculator {
	private String ip;
	private int port;
	private String numeInput;
	private String numeOutput;
	private boolean status;
	
	public Calculator(String ip, int port, String numeInput, String numeOutput, boolean status){
		this.ip = ip;
		this.port = port;
		this.numeInput = numeInput;
		this.numeOutput = numeOutput;
		this.status = status;
	}
	
	public String getIp() {
		return ip;
	}

	public Integer getPort() {
		return port;
	}

	public String getNumeInput() {
		return numeInput;
	}

	public String getNumeOutput() {
		return numeOutput;
	}
	public boolean getStatus(){
		return this.status;
	}
}
