package Cod;
public class Vot {
	private String cnpVotant;
	private String cnpCandidat;
	private boolean valid;
	private Circumscriptie circumscriptie;
	private String idAlegeri;

	public Vot(String cnpVotant, String cnpCandidat, boolean valid, Circumscriptie circumscriptie) {
		this.cnpVotant = cnpVotant;
		this.cnpCandidat = cnpCandidat;
		this.valid = valid;
		this.circumscriptie = circumscriptie;
	}
	public String getCnpVotant() {
		return this.cnpVotant;
	}
	public void setCnpVotant(String cnpVotant) {
		this.cnpVotant = cnpVotant;
	}
	public String getCnpCandidat() {
		return this.cnpCandidat;
	}
	public void setCnpCandidat(String cnpCandidat) {
		this.cnpCandidat = cnpCandidat;
	}
	public boolean isValid() {
		return this.valid;
	}
	public void setValid(boolean valid) {
		this.valid = valid;
	}
	public Circumscriptie getCircumscriptie() {
		return this.circumscriptie;
	}
	public void setCircumscriptie(Circumscriptie circumscriptie) {
		this.circumscriptie = circumscriptie;
	}
	public String getIdAlegeri() {
		return this.idAlegeri;
	}
	public void setIdAlegeri(String idAlegeri) {
		this.idAlegeri = idAlegeri;
	}

}
