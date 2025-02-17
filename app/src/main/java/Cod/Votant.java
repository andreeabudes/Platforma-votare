package Cod;
import java.util.ArrayList;
import java.util.Comparator;

public class Votant extends Persoana {
	private String neindemanatic;

	public Votant(String nume, String cnp, int varsta, String neindemanatic) {
		super(nume, cnp, varsta);
		this.neindemanatic = neindemanatic;
	}
	public String isNeindemanatic() {
		return this.neindemanatic;
	}

	public String adaugareVotant(String id, String numeCircumscriptie, String cnp, int varsta, String neindemanatic,
								 String nume, ArrayList<Alegeri> listaAlegeri, ArrayList<Votant> listaVotanti,
								 ArrayList<Circumscriptie> listaCircumscriptie,ArrayList<Vot> listaVoturi) {
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

		// caut circumscriptia in lista
		Circumscriptie circumscriptie = null;
		boolean circumscriptieGasita = false;
		for (Circumscriptie c: listaCircumscriptie) {
			if (c.getNumeCircumscriptie().equals(numeCircumscriptie)) {
				circumscriptieGasita = true;
				circumscriptie = c;
				break;
			}
		}
		if (!circumscriptieGasita) {
			return "EROARE: Nu exista o circumscriptie cu numele " + numeCircumscriptie;
		}

		// verific datele votantului
		if (cnp.length() != 13) {
			return "EROARE: CNP invalid";
		}
		if (varsta < 18) {
			return "EROARE: Varsta invalida";
		}

		// acum verific daca votantul este deja acelasi CNP
		for (Votant v: listaVotanti) {
			if (v.getCnp().equals(cnp)) {
				return "EROARE: Votantul " + v.getNume()+ " are deja acelasi CNP";
			}
		}

		// verific daca votantul e neindemanatic
		boolean valid = neindemanatic.equals("nu");

		// adaug votantul
		Votant votantNou = new Votant(nume, cnp, varsta, " ");
		listaVotanti.add(votantNou);
		circumscriptie.getVotanti().add(votantNou);

		return "S-a adaugat votantul " + votantNou.getNume();
	}

	public String listareVotanti(String id, String numeCircumscriptie, ArrayList<Votant> listaVotanti,
								 ArrayList<Alegeri> listaAlegeri, ArrayList<Circumscriptie> listaCircumscriptii) {
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
		// caut circumscriptia
		Circumscriptie circumscriptieGasita = null;
		for (Circumscriptie c : listaCircumscriptii) {
			if (c.getNumeCircumscriptie().equals(numeCircumscriptie)) {
				circumscriptieGasita = c;
				break;
			}
		}
		// verific daca exista
		if (circumscriptieGasita == null) {
			return "EROARE: Nu exista o circumscriptie cu numele " + numeCircumscriptie;
		}

		// verific daca lista de votanti e goala
		if (circumscriptieGasita.getVotanti().isEmpty()) {
			return "GOL: Nu sunt votanti in " + numeCircumscriptie;
		}

		// sortarea votantilor dupa cnp
		circumscriptieGasita.getVotanti().sort(Comparator.comparing(Votant::getCnp));

		StringBuilder sb = new StringBuilder();
		sb.append("Votantii din " + numeCircumscriptie + ":\n");
		for (Votant v : circumscriptieGasita.getVotanti()) {
			sb.append(v.getNume()).append(" ").append(v.getCnp()).append(" ").append(v.getVarsta()).append("\n");
		}

		return sb.toString();
	}


}
