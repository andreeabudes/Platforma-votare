package Cod;
import java.util.Map;
import java.util.*;

public class Alegeri {
	private String nume;
	private String id;
	private String stagiu;
	private ArrayList<Candidat> candidati;
	private ArrayList<Regiune> listaRegiuni;

	public Alegeri(String nume, String id) {
		this.nume = nume;
		this.id = id;
		this.stagiu = "NEINCEPUT";
		this.candidati = new ArrayList<>();
		this.listaRegiuni = new ArrayList<>();
	}

	// getteri si setteri
	public String getNume() {
		return this.nume;
	}
	public void setNume(String nume) {
		this.nume = nume;
	}
	public String getId() {
		return this.id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getStagiu() {
		return this.stagiu;
	}
	public void setStagiu(String stagiu) {
		this.stagiu = stagiu;
	}
	public ArrayList<Candidat> getCandidati() {
		return this.candidati;
	}
	public void setCandidati(ArrayList<Candidat> candidati) {
		this.candidati = candidati;
	}
	public ArrayList<Regiune> getListaRegiuni() {
		return this.listaRegiuni;
	}
	public void setListaRegiuni(ArrayList<Regiune> listaRegiuni) {
		this.listaRegiuni = listaRegiuni;
	}

	public String creareAlegeri(String nume, String id, ArrayList<Alegeri> listaAlegeri) {
		// verific alegerile
		for(Alegeri alegeri : listaAlegeri) {
			if(alegeri.getId().equals(id)) {
				return "EROARE: Deja exista alegeri cu id " + id;
			}
		}
		Alegeri alegeriNoi = new Alegeri(nume, id);
		listaAlegeri.add(alegeriNoi);

		return "S-au creat alegerile " + alegeriNoi.getNume();
	}

	public String pornireAlegeri(String id, ArrayList<Alegeri> listaAlegeri ) {
		for (Alegeri alegeri : listaAlegeri) {
			if(alegeri.getId().equals(id)) {
				if(alegeri.getStagiu().equals("NEINCEPUT")) {
					alegeri.setStagiu("IN_CURS");
					return "Succes! Au pornit alegerile " + alegeri.getNume();
				} else {
					return "EROARE: Alegerile deja au inceput";
				}
			}
		}
		return "EROARE: Nu exista alegeri cu acest id";
	}

	public String votare(String id, Circumscriptie circumscriptie, String cnpVotant, String cnpCandidat, ArrayList<Alegeri> listaAlegeri,
						 ArrayList<Circumscriptie> listaCircumscriptii, ArrayList<Frauda> listaFraude, ArrayList<Vot> listaVoturi,
						 ArrayList<Votant> listaVotanti) {
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

		// verificare circumscriptie
		Circumscriptie circGasita = null;
		for (Circumscriptie c : listaCircumscriptii) {
			if (c.getNumeCircumscriptie().equals(circumscriptie.getNumeCircumscriptie())) {
				circGasita = c;
				break;
			}
		}
		if (circGasita == null) {
			return "EROARE: Nu exista o circumscriptie cu numele " + circumscriptie.getNumeCircumscriptie();
		}
		// verificare votant
		Votant votant = null;
		for (Votant v: listaVotanti) {
			if (v.getCnp().equals(cnpVotant)) {
				votant = v;
				break;
			}
		}
		if (votant == null) {
			return "EROARE: Nu exista un votant cu CNP-ul " + cnpVotant;
		}

		// votantul nu este inregistrat in circumcriptie
		boolean votantGasit = false;
		for (Votant v : circGasita.getVotanti()) {
			if (v.getCnp().equals(cnpVotant)) {
				votantGasit = true;
				break;
			}
		}

		if (!votantGasit) {
			// caut votantul in alta circumscriptie
			boolean altaCircumscriptie = false;
			for (Circumscriptie c : listaCircumscriptii) {
				for (Votant v : c.getVotanti()) {
					if (v.getCnp().equals(cnpVotant)) {
						altaCircumscriptie = true;
						Frauda frauda = new Frauda(cnpVotant, circumscriptie.getNumeCircumscriptie(),"");
						boolean ok = false;
						for (Frauda f : listaFraude){
							if(f.getCnpVotant().equals(cnpVotant)){
								ok = true;
							}
						}
						if(ok == false){
							listaFraude.add(frauda);
						}
						return "FRAUDA: Votantul cu CNP-ul " + cnpVotant + " a incercat sa comita o frauda. Votul a fost anulat";
					}
				}
			}
			if (!altaCircumscriptie) {
				return "EROARE: Nu exista un votant cu CNP-ul " + cnpVotant;
			}

		}

		// vot multiplu
		for (Vot vot: listaVoturi) {
			if (vot.getCnpVotant().equals(cnpVotant)) {
				Frauda frauda = new Frauda(cnpVotant, circumscriptie.getNumeCircumscriptie(), "");
				boolean ok = false;
				for (Frauda f : listaFraude){
					if(f.getCnpVotant().equals(cnpVotant)){
						ok = true;
					}
				}
				if(ok == false){
					listaFraude.add(frauda);
				}
				return "FRAUDA: Votantul cu CNP-ul " + cnpVotant + " a incercat sa comita o frauda. Votul a fost anulat";
			}
		}

		// verificare candidat
		Candidat c = null;
		for (Candidat candidat : a.getCandidati()) {
			if (candidat.getCnp().equals(cnpCandidat)) {
				c = candidat;
				break;
			}
		}

		if (c == null) {
			return "EROARE: Nu exista un candidat cu CNP-ul " + cnpCandidat;
		}

		// votant neindemanatic
		boolean esteValid = votant.isNeindemanatic().equals("nu");
		if (esteValid) {
			return votant.getNume() + " a votat pentru " + c.getNume();
		} else {
			// realizare vot
			Vot vot = new Vot(cnpVotant, cnpCandidat, true, circumscriptie);
			listaVoturi.add(vot);
			circGasita.getVoturiCircumscriptie().add(vot);

			return votant.getNume() + " a votat pentru " + c.getNume();
		}
	}

	public String raportVoturiNational(String id, ArrayList<Alegeri> listaAlegeri, ArrayList<Vot> listaVoturi) {
		StringBuilder raport = new StringBuilder("Raport voturi Romania:" + ":\n");
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
		if (listaVoturi.isEmpty()) {
			return "GOL: Lumea nu isi exercita dreptul de vot in Romania";
		}
		for (Candidat c : a.getCandidati()) {
			int nrVoturi = 0;
			for (Vot vot : listaVoturi) {
				if (vot.getCnpCandidat().equals(c.getCnp())) {
					nrVoturi++;
				}
			}
			// sortez tot cu bubble sort, dar invers
			for (int i = 0; i < a.getCandidati().size() - 1; i++) {
				for (int j = 0; j < a.getCandidati().size() - i - 1; j++) {
					Candidat c1 = a.getCandidati().get(j);
					Candidat c2 = a.getCandidati().get(j + 1);

					if (!c1.getNrVoturi().equals(c2.getNrVoturi()) || (c1.getNrVoturi() == c2.getNrVoturi() && c1.getCnp().compareTo(c2.getCnp()) < 0)) {
						a.getCandidati().set(j + 1, c1);
						a.getCandidati().set(j, c2);
					}
				}
			}

			if (nrVoturi >= 0 ) {
				raport.append(c.getNume()).append(" ").append(c.getCnp()).append(" - ").append(nrVoturi).append("\n");
			}
		}

		return raport.toString();
	}

	public List<ArrayList<?>> calculeazaVoturiRegiune (ArrayList<Vot> listaVoturi, ArrayList<Circumscriptie> listaCircumscriptii) {
		Set<String> numeRegiuniAux = new LinkedHashSet<>();

		for (Circumscriptie circ : listaCircumscriptii) {
			numeRegiuniAux.add(circ.getRegiune());
		}
		// transform in lista ca sa pot accesa indexul
		ArrayList<String> numeRegiuni = new ArrayList<>(numeRegiuniAux);
		// sortez alfabetic numele
		Collections.sort(numeRegiuni);
		Collections.reverse(numeRegiuni);
		ArrayList<Integer> nrvoturiPePozitieRegiune = new ArrayList<Integer>();
		ArrayList<ArrayList<Vot>> listaVoturiPePozitieRegiune = new ArrayList<>();
		for (int i = 0; i < numeRegiuni.size(); i++) {
			nrvoturiPePozitieRegiune.add(0);
			listaVoturiPePozitieRegiune.add(new ArrayList<>());
		}

		for (Vot vot : listaVoturi) {
			// Aflam pt ce regiune este votul
			String numeRegiune = vot.getCircumscriptie().getRegiune();
			// Aflam ce pozitie are Regiunea in cele doua arraylist-uri
			int pozitiaRegiunii = numeRegiuni.indexOf(numeRegiune);
			// Adaugam +1 vot in lista cu nrvoturiPePozitieRegiune pe pozitia pozitiaRegiunii
			nrvoturiPePozitieRegiune.set(pozitiaRegiunii,nrvoturiPePozitieRegiune.get(pozitiaRegiunii) + 1);
			// Adaugam obiectul vot in lista cu listaVoturiPePozitieRegiune pe pozitia pozitiaRegiunii
			ArrayList<Vot> listAux = listaVoturiPePozitieRegiune.get(pozitiaRegiunii);
			listAux.add(vot);
			listaVoturiPePozitieRegiune.set(pozitiaRegiunii, listAux);
		}
		List<ArrayList<?>> rezultat = new ArrayList<>();
		rezultat.add(numeRegiuni);
		rezultat.add(nrvoturiPePozitieRegiune);
		rezultat.add(listaVoturiPePozitieRegiune);

		return rezultat;
	}

	public String analizaNationala (String id, ArrayList<Alegeri> listaAlegeri, ArrayList<Vot> listaVoturi, ArrayList<Circumscriptie> listaCircumscriptii) {
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
		if (listaVoturi.isEmpty()) {
			return "GOL: Lumea nu isi exercita dreptul de vot in Romania";
		}

		List<ArrayList<?>> rezultat = calculeazaVoturiRegiune(listaVoturi, listaCircumscriptii);
		ArrayList<String> numeRegiuni = (ArrayList<String>)new ArrayList<>(rezultat.get(0));
		ArrayList<Integer> nrvoturiRegiuni = (ArrayList<Integer>)new ArrayList<>(rezultat.get(1));
		ArrayList<ArrayList<Vot>> voturiRegiuni = (ArrayList<ArrayList<Vot>>)new ArrayList<>(rezultat.get(2));

		String S = "in Romania au fost " + listaVoturi.size() + " voturi.\n";


		for (int i = 0; i < numeRegiuni.size(); i++) {
			int procentRegiune = (int)(((float)nrvoturiRegiuni.get(i)/ (float)listaVoturi.size()) * 100);

			int maxVoturi = 0;
			String cnpCastigator = " ";
			String numeCastigator = " ";
			int nrVoturiCandidat = 0;
			int procentCastigator = 0;
			for(Candidat candidat : a.getCandidati()) {
				nrVoturiCandidat = 0;
				// numar voturile pt fiecare candidat din regiune
				for (Vot vot : voturiRegiuni.get(i)) {
					if (vot.isValid()) {
						if (vot.getCnpCandidat().equals(candidat.getCnp())) {
							nrVoturiCandidat++;
						}
					}
				}
				// actualizez castigatorul
				if (nrVoturiCandidat > maxVoturi) {
					maxVoturi = nrVoturiCandidat;
					cnpCastigator = candidat.getCnp();
					numeCastigator = candidat.getNume();
					procentCastigator = (int)((float)nrVoturiCandidat / (float)nrvoturiRegiuni.get(i) * 100);

				} else if (nrVoturiCandidat == maxVoturi) {
					if (cnpCastigator.compareTo(candidat.getCnp()) < 0) {
						maxVoturi = nrVoturiCandidat;
						cnpCastigator = candidat.getCnp();
						numeCastigator = candidat.getNume();
						procentCastigator = (int)((float)nrVoturiCandidat / (float)nrvoturiRegiuni.get(i) * 100);
					}
				}
			}

			S += "in " + numeRegiuni.get(i) + " au fost " + nrvoturiRegiuni.get(i) + " voturi din " + listaVoturi.size() + ". Adica " + procentRegiune + "%. Cele mai multe voturi au fost stranse de ";
			S += cnpCastigator + " " + numeCastigator + ". Acestea constituie " + procentCastigator + "% din voturile regiunii.\n";
		}
		return S;
	}

	public String oprireAlegeri (String id, ArrayList<Alegeri> listaAlegeri) {
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

		a.setStagiu("FINALIZAT");
		return "S-au terminat alegerile " + a.getNume();
	}

	public String stergereAlegeri (String id, ArrayList<Alegeri> listaAlegeri, ArrayList<Circumscriptie> listaCircumscriptii,
								   ArrayList<Vot> listaVoturi) {
		Alegeri alegeriDeSters = null;
		for (Alegeri alegeri : listaAlegeri) {
			if (alegeri.getId().equals(id)) {
				alegeriDeSters = alegeri;
				break;
			}
		}
		if (alegeriDeSters == null) {
			return "EROARE: Nu exista alegeri cu acest id";
		}

		// sterg toate circumscriptiile
		for (Circumscriptie circ : listaCircumscriptii) {
			if (circ.getNumeCircumscriptie().equals(id)) {
				listaCircumscriptii.remove(circ);
				break;
			}
		}

		// sterg toate voturile
		for (Vot vot : listaVoturi) {
			if (vot.getIdAlegeri() != null && vot.getIdAlegeri().equals(id)) {
				listaVoturi.remove(vot);
				break;
			}
		}
		listaAlegeri.remove(alegeriDeSters);

		return "S-au sters alegerile " + alegeriDeSters.getNume();
	}

	public String listareAlegeri (ArrayList<Alegeri> listaAlegeri) {
		if (listaAlegeri.isEmpty()) {
			return "GOL: Nu sunt alegeri";
		}
		StringBuilder sb = new StringBuilder();
		sb.append("Alegeri:\n");
		for (Alegeri alegeri : listaAlegeri) {
			sb.append(alegeri.getId() + " " + alegeri.getNume() + "\n");
		}
		return sb.toString();
	}
}

