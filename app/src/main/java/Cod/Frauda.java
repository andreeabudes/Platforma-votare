package Cod;
import java.util.ArrayList;

public class Frauda {
	private String cnpVotant;
	private String numeCircumscriptie;
	private String numeVotant;

	public Frauda(String cnpVotant, String numeCircumscriptie, String numeVotant) {
		this.cnpVotant = cnpVotant;
		this.numeCircumscriptie = numeCircumscriptie;
		this.numeVotant = numeVotant;
	}
	public String getCnpVotant() {
		return this.cnpVotant;
	}
	public void setCnpVotant(String cnpVotant) {
		this.cnpVotant = cnpVotant;
	}
	public String getNumeCircumscriptie() {
		return this.numeCircumscriptie;
	}
	public void setNumeCircumscriptie(String numeCircumscriptie) {
		this.numeCircumscriptie = numeCircumscriptie;
	}
	public String getNumeVotant() {
		return this.numeVotant;
	}
	public void setNumeVotant(String numeVotant) {
		this.numeVotant = numeVotant;
	}

	public String raportFraude(String id, ArrayList<Alegeri> listaAlegeri, ArrayList<Frauda> listaFraude,
							   ArrayList<Circumscriptie> listaCircumscriptii, ArrayList<Votant> listaVotanti) {
		Alegeri alegeriGasite = null;
		for (Alegeri alegeri : listaAlegeri) {
			if (alegeri.getId().equals(id)) {
				alegeriGasite = alegeri;
				break;
			}
		}

		if (alegeriGasite == null) {
			return "EROARE: Nu exista alegeri cu acest id";
		}

		if (!alegeriGasite.getStagiu().equals("FINALIZAT")) {
			return "EROARE: Inca nu s-a terminat votarea";
		}
		if (listaFraude.isEmpty()) {
			return "GOL: Romanii sunt cinstiti";
		}

		String S = "Fraude comise:\n";
		String numeVotantFrauda = null;
		for (Frauda frauda : listaFraude) {
			// caut in circumscriptie
			for (Circumscriptie circumscriptie : listaCircumscriptii ) {
				if (circumscriptie.getNumeCircumscriptie().equals(frauda.getNumeCircumscriptie())) {
					for (Persoana votant : listaVotanti ) {
						if (votant.getCnp().equals(frauda.getCnpVotant())) {
							numeVotantFrauda = votant.getNume();
						}
					}
					S += "in " +frauda.getNumeCircumscriptie() + ": " + frauda.getCnpVotant() + " " + numeVotantFrauda + "\n";
					//S+= "Numele votantului este:" + frauda.getNumeVotant() + "\n";
					break;
				}
			}
		}
		return S;
	}
}
