package Cod;
public class Persoana {
	private String nume;
	private String cnp;
	private int varsta;

	public Persoana(String nume, String cnp, int varsta) {
		this.nume = nume;
		this.cnp = cnp;
		this.varsta = varsta;
	}
	public String getNume() {
		return this.nume;
	}
	public void setNume(String nume) {
		this.nume = nume;
	}
	public String getCnp() {
		return this.cnp;
	}
	public void setCnp(String cnp) {
		this.cnp = cnp;
	}
	public int getVarsta() {
		return this.varsta;
	}
	public void setVarsta(int varsta) {
		this.varsta = varsta;
	}

}
