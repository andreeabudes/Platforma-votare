package Cod;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class Circumscriptie {
	private String numeCircumscriptie;
	private String regiune;
	private ArrayList<Votant> votanti;
	private ArrayList<Vot> voturiCircumscriptie;

	public Circumscriptie(String numeCircumscriptie, String regiune) {
		this.numeCircumscriptie = numeCircumscriptie;
		this.regiune = regiune;
		this.votanti = new ArrayList<>();
		this.voturiCircumscriptie = new ArrayList<>();
	}
	// getteri si setteri
	public String getNumeCircumscriptie() {
		return this.numeCircumscriptie;
	}
	public void setNumeCircumscriptie(String numeCircumscriptie) {
		this.numeCircumscriptie = numeCircumscriptie;
	}
	public String getRegiune() {
		return this.regiune;
	}
	public void setRegiune(String regiune) {
		this.regiune = regiune;
	}
	public ArrayList<Votant> getVotanti() {
		return this.votanti;
	}
	public void setVotanti(ArrayList<Votant> votanti) {
		this.votanti = votanti;
	}
	public ArrayList<Vot> getVoturiCircumscriptie() {
		return this.voturiCircumscriptie;
	}
	public void setVoturiCircumscriptie(ArrayList<Vot> voturiCircumscriptie) {
		this.voturiCircumscriptie = voturiCircumscriptie;
	}

	public String adaugareCircumscriptie(String id, String numeCircumscriptie, String regiune,
										 ArrayList<Alegeri>listaAlegeri, ArrayList<Circumscriptie> listaCircumscriptii) {
		boolean alegeriGasite = false;
		for (Alegeri alegeri: listaAlegeri) {
			if (alegeri.getId().equals(id)) {
				alegeriGasite = true;
				if (!alegeri.getStagiu().equals("IN_CURS")) {
					return "EROARE: Nu este perioada de votare";
				}
				break;
			}
		}
		if (!alegeriGasite) {
			return "EROARE: Nu exista alegeri cu acest id";
		}
		for (Circumscriptie c : listaCircumscriptii) {
			if (c.getNumeCircumscriptie().equals(numeCircumscriptie)) {
				return "EROARE: Deja exista o circumscriptie cu numele " + numeCircumscriptie;
			}
		}
		Circumscriptie circumscriptieNoua = new Circumscriptie(numeCircumscriptie, regiune);
		listaCircumscriptii.add(circumscriptieNoua);

		return "S-a adaugat circumscriptia " + numeCircumscriptie + " " + regiune;
	}

	public String eliminareCircumscriptie(String id, String numeCircumscriptie, ArrayList<Alegeri>listaAlegeri,
										  ArrayList<Circumscriptie> listaCircumscriptii, ArrayList<Vot> listaVoturi) {
		boolean alegeriGasite = false;
		for (Alegeri alegeri: listaAlegeri) {
			if (alegeri.getId().equals(id)) {
				alegeriGasite = true;
				if (!alegeri.getStagiu().equals("IN_CURS")) {
					return "EROARE: Nu este perioada de votare";
				}
				break;
			}
		}
		if (!alegeriGasite) {
			return "EROARE: Nu exista alegeri cu acest id";
		}
		Circumscriptie circDeSters = null;
		for (Circumscriptie c : listaCircumscriptii) {
			if (c.getNumeCircumscriptie().equals(numeCircumscriptie)) {
				circDeSters = c;
				break;
			}
		}
		if (circDeSters == null) {
			return "EROARE: Nu exista o circumscriptie cu numele " + numeCircumscriptie;
		}

		// sterg voturile si votantii din circumscriptie
		for (int i = 0; i < listaVoturi.size(); i++) {
			Vot vot = listaVoturi.get(i);
			if (vot.getCircumscriptie().equals(circDeSters.numeCircumscriptie)) {
				listaVoturi.remove(i);
				i--;
			}
		}
		circDeSters.getVotanti().clear();

		listaCircumscriptii.remove(circDeSters);
		return "S-a sters circumscriptia " + numeCircumscriptie;
	}

	public String raportVoturiCircumscriptie (String id, String numeCircumscriptie, ArrayList<Alegeri>listaAlegeri, ArrayList<Circumscriptie> listaCircumscriptii,
											  ArrayList<Vot> listaVoturi) {
		Alegeri a = null;
		boolean alegeriGasite = false;
		for (Alegeri alegeri: listaAlegeri) {
			if (alegeri.getId().equals(id)) {
				alegeriGasite = true;
				a = alegeri;
				if (!alegeri.getStagiu().equals("FINALIZAT")) {
					return "EROARE: Inca nu s-a terminat votarea";
				}
				break;
			}
		}
		if (!alegeriGasite) {
			return "EROARE: Nu exista alegeri cu acest id";
		}
		Circumscriptie circ = null;
		for (Circumscriptie c : listaCircumscriptii) {
			if (c.getNumeCircumscriptie().equals(numeCircumscriptie)) {
				circ = c;
				break;
			}
		}
		if (circ == null) {
			return "EROARE: Nu exista o circumscriptie cu numele " + numeCircumscriptie;
		}
		if (circ.getVoturiCircumscriptie().isEmpty()) {
			return "GOL: Lumea nu isi exercita dreptul de vot in " + numeCircumscriptie;
		}

		StringBuilder raport = new StringBuilder("Raport voturi " + numeCircumscriptie + ":\n");

		// parcurg candidatii
		for (Candidat candidat : a.getCandidati()) {
			int nrVoturi = 0;

			// numar voturile din circumscriptie pentru fiecare candidat
			for (Vot vot : circ.getVoturiCircumscriptie()) {
				if (vot.getCnpCandidat().equals(candidat.getCnp())) {
					nrVoturi++;
				}
			}

			// sortez voturile -> bubble sort
			for (int i = 0; i < a.getCandidati().size() - 1; i++) {
				for (int j = 0; j < a.getCandidati().size() - i - 1; j++) {
					Candidat c1 = a.getCandidati().get(j);
					Candidat c2 = a.getCandidati().get(j + 1);

					// compar dupa numarul de voturi si daca sunt egale, atunci compar cnp-ul
					if (!c1.getNrVoturi().equals(c2.getNrVoturi()) ||
							(c1.getNrVoturi() == c2.getNrVoturi() && c1.getCnp().compareTo(c2.getCnp()) < 0)) {
						// schimb locurile
						a.getCandidati().set(j, c2);
						a.getCandidati().set(j + 1, c1);
					}
				}
			}
			// adaug voturile candidatului in raport
			if (nrVoturi >= 0) {
				raport.append(candidat.getNume()).append(" ").append(candidat.getCnp()).append(" - ").append(nrVoturi).append("\n");
			}
		}
		return raport.toString();
	}

	public String analizaCircumscriptie (String id, String numeCircumscriptie, ArrayList<Alegeri> listaAlegeri, ArrayList<Circumscriptie> listaCircumscriptii,
										 ArrayList<Vot> listaVoturi) {
		Alegeri a = null;
		boolean alegeriGasite = false;
		for (Alegeri alegeri : listaAlegeri) {
			if (alegeri.getId().equals(id)) {
				alegeriGasite = true;
				a = alegeri;
				if (!alegeri.getStagiu().equals("FINALIZAT")) {
					return "EROARE: Inca nu s-a terminat votarea";
				}
				break;
			}
		}
		if (!alegeriGasite) {
			return "EROARE: Nu exista alegeri cu acest id";
		}
		Circumscriptie circ = null;
		for (Circumscriptie c : listaCircumscriptii) {
			if (c.getNumeCircumscriptie().equals(numeCircumscriptie)) {
				circ = c;
				break;
			}
		}
		if (circ == null) {
			return "EROARE: Nu exista o circumscriptie cu numele " + numeCircumscriptie;
		}
		if (circ.getVoturiCircumscriptie().isEmpty()) {
			return "GOL: Lumea nu isi exercita dreptul de vot in " + numeCircumscriptie;
		}

		StringBuilder analiza = new StringBuilder();

		int nrVoturiNational = listaVoturi.size();
		int nrVoturiCircum = circ.getVoturiCircumscriptie().size();


		int maxVoturi = 0;                                               // nr maxim voturi candidat
		String cnpCandidatMax = " ";
		String numeCandidatMax = " ";

		for (Candidat candidat : a.getCandidati()) {
			int nrVoturiCandidat = candidat.getNrVoturi().size();

			for (Vot vot : circ.getVoturiCircumscriptie()) {
				if (vot.getCnpCandidat().equals(candidat.getCnp())) {
					if (vot.isValid()) {
						nrVoturiCandidat++;
					}
				}
			}
			System.out.println(nrVoturiCandidat + candidat.getNume());

			if (nrVoturiCandidat > maxVoturi) {
				maxVoturi = nrVoturiCandidat;
				cnpCandidatMax = candidat.getCnp();
				numeCandidatMax = candidat.getNume();
			} else if (nrVoturiCandidat == maxVoturi) {
				if (cnpCandidatMax.compareTo(candidat.getCnp()) < 0) {
					maxVoturi = nrVoturiCandidat;
					cnpCandidatMax = candidat.getCnp();
					numeCandidatMax = candidat.getNume();
				}
			}
		}

		int procentCircum = (nrVoturiCircum * 100) / nrVoturiNational;
		int procentCandidat = (maxVoturi * 100) / nrVoturiCircum;

		analiza.append("in " + numeCircumscriptie + " " + "au fost " + nrVoturiCircum + " voturi din " + nrVoturiNational + ". Adica " + procentCircum +
				"%. Cele mai multe voturi au fost stranse de " + cnpCandidatMax + " " + numeCandidatMax + ". Acestea constituie " + procentCandidat +
				"% din voturile circumscriptiei.");

		return analiza.toString();
	}
}
