package Server;

import org.json.JSONObject;

public class Decodificare {

    private JSONObject jsonObj;//string citit
    private JSONObject jsonData;

    public Decodificare(String input) {
        try {
            this.jsonObj = new JSONObject(input);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public int return_codinput() {
        return jsonObj.getInt("codeinput");
    }

    public int return_codoutput() {
        return jsonObj.getInt("codeoutput");
    }

    public String get_value(String cheie) {//returneaza valoarea cheii
        return jsonObj.get(cheie).toString();
    }

    public String get_value_data(String cheie) {//returneaza valoarea cheii
        return jsonData.get(cheie).toString();
    }

    public boolean validare_cheie_obj(String sir) {
        return jsonObj.has(sir);
    }

    public boolean validare_cheie_data(String sir) {
        return jsonData.has(sir);
    }
}
