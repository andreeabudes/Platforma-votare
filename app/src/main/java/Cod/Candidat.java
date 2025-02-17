package Cod;
import java.util.ArrayList;
import java.util.Comparator;

public class Candidat extends Persoana {
	public Candidat(String nume, String cnp, int varsta) {
		super(nume, cnp, varsta);
	}

	ArrayList<Vot> nrVoturi = new ArrayList<>();      // nr voturi per candidat

	public ArrayList<Vot> getNrVoturi() {
		return this.nrVoturi;
	}
	public void setNrVoturi(ArrayList<Vot> nrVoturi) {
		this.nrVoturi = nrVoturi;
	}

	public String adaugareCandidat(String id, String cnp, int varsta, String nume, ArrayList<Alegeri> listaAlegeri) {
		Alegeri a = null;
		boolean alegeriGasite = false;
		for (Alegeri alegeri: listaAlegeri) {
			if (alegeri.getId().equals(id)) {
				alegeriGasite = true;
				a = alegeri;
				if (!alegeri.getStagiu().equals("IN_CURS")) {
					return "EROARE: Nu este perioada de votare";
				}
				break;
			}
		}
		if (!alegeriGasite) {
			return "EROARE: Nu exista alegeri cu acest id";
		}
		if (cnp.length() != 13) {
			return "EROARE: CNP invalid";
		}
		if (varsta < 35) {
			return "EROARE: Varsta invalida";
		}

		for (Candidat candidat : a.getCandidati()) {
			if (candidat.getCnp().equals(cnp)) {
				return "EROARE: Candidatul " + candidat.getNume() + " are deja acelasi CNP";
			}
		}
		Candidat candidatNou = new Candidat(nume, cnp, varsta);
		a.getCandidati().add(candidatNou);

		return "S-a adaugat candidatul " + candidatNou.getNume();
	}

	public String eliminareCandidat(String id, String cnp, ArrayList<Alegeri> listaAlegeri,
									ArrayList<Vot> listaVoturi) {
		Alegeri a = null;
		boolean alegeriGasite = false;
		for (Alegeri alegeri: listaAlegeri) {
			if (alegeri.getId().equals(id)) {
				alegeriGasite = true;
				a = alegeri;
				if (!alegeri.getStagiu().equals("IN_CURS")) {
					return "EROARE: Nu este perioada de votare";
				}
				break;
			}
		}
		if (!alegeriGasite) {
			return "EROARE: Nu exista alegeri cu acest id";
		}

		Candidat candidatDeSters = null;
		for (Candidat candidat : a.getCandidati()) {
			if (candidat.getCnp().equals(cnp)) {
				candidatDeSters = candidat;
				break;
			}
		}
		if (candidatDeSters == null) {
			return "EROARE: Nu exista un candidat cu CNP-ul " + cnp;
		}

		// acum sterg voturile candidatului
		for (int i = listaVoturi.size() - 1; i >= 0; i--) {
			if (listaVoturi.get(i).getCnpCandidat().equals(cnp)) {
				listaVoturi.remove(i);											// sterg din lista globala
			}
		}
		candidatDeSters.getNrVoturi().clear();
		a.getCandidati().remove(candidatDeSters);								// sterg din lista locala
		return "S-a sters candidatul " + candidatDeSters.getNume();
	}

	public String listareCandidati (String id, ArrayList<Alegeri> listaAlegeri) {
		Alegeri a = null;
		boolean alegeriGasite = false;
		for (Alegeri alegeri: listaAlegeri) {
			if (alegeri.getId().equals(id)) {
				alegeriGasite = true;
				a = alegeri;
				if (!alegeri.getStagiu().equals("IN_CURS")) {
					return "EROARE: Nu este perioada de votare";
				}
				break;
			}
		}
		if (!alegeriGasite) {
			return "EROARE: Nu exista alegeri cu acest id";
		}

		if (a.getCandidati().isEmpty()) {
			return "GOL: Nu sunt candidati ";
		}

		// acum sortez candidatii dupa cnp
		a.getCandidati().sort(Comparator.comparing(Candidat::getCnp));

		StringBuilder sb = new StringBuilder();
		sb.append("Candidatii:\n");
		for (Candidat c : a.getCandidati()) {
			sb.append(c.getNume()).append(" ").append(c.getCnp()).append(" ").append(c.getVarsta()).append("\n");
		}
		return sb.toString();
	}

}

