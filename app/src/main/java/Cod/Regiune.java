package Cod;
import java.util.ArrayList;

public class Regiune {
	private String nume;
	private ArrayList<Vot> voturiRegiune;

	public Regiune(String nume) {
		this.nume = nume;
		this.voturiRegiune = new ArrayList<>();
	}
	public String getNume() {
		return this.nume;
	}
	public ArrayList<Vot> getVoturiRegiune() {
		return this.voturiRegiune;
	}
	public void setNume(String nume) {
		this.nume = nume;
	}
	public void setListaVoturiRegiune(ArrayList<Vot> listaRegiune) {
		this.voturiRegiune = listaRegiune;
	}

}
