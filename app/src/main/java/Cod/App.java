package Cod;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.text.*;

public class App {
	private Scanner scanner;
	private ArrayList<Alegeri> listaAlegeri = new ArrayList<Alegeri>();
	private ArrayList<Circumscriptie> listaCircumscriptii =  new ArrayList<Circumscriptie>();
	private ArrayList<Vot> listaVoturi = new ArrayList<Vot>();
	private ArrayList<Votant> listaVotanti = new ArrayList<Votant>();
	private ArrayList<Frauda> listaFraude = new ArrayList<Frauda>();

	public App(InputStream input) {
		this.scanner = new Scanner(input);
	}

	public void run() {

		boolean aplicatie = true;
		while (aplicatie) {
			int comanda;
			try {
				comanda = scanner.nextInt(); // Citire număr comandă
				scanner.nextLine();

			} catch (NumberFormatException e) {
				System.out.println("EROARE: Introduceti un numar valid.");
				continue;
			}

			switch (comanda) {
				case 0:
					String input = scanner.nextLine();
					int indexSpace = input.indexOf(" ");

					if (indexSpace == -1) {
						System.out.println("EROARE: Parametri insuficienti.");
					} else {
						String idAlegere = input.substring(0, indexSpace).trim();
						String numeAlegere = input.substring(indexSpace + 1).trim();

						Alegeri a = new Alegeri(" ", idAlegere);
						String mesaj = a.creareAlegeri(numeAlegere, idAlegere, listaAlegeri);
						System.out.println(mesaj);
					}
					break;

				case 1:
					String id = scanner.nextLine();
					Alegeri a = new Alegeri(" ", id);
					String mesaj = a.pornireAlegeri(id, listaAlegeri);
					System.out.println(mesaj);
					break;

				case 2:
					String inputCircumscriptie = scanner.nextLine();
					int firstSpaceIndex = inputCircumscriptie.indexOf(" "); // gasesc primul spațiu
					int secondSpaceIndex = inputCircumscriptie.indexOf(" ", firstSpaceIndex + 1); //al doilea spatiu

					if (firstSpaceIndex == -1 || secondSpaceIndex == -1) {
						System.out.println("EROARE: Parametri insuficienti.");
					} else {
						String idAlegere = inputCircumscriptie.substring(0, firstSpaceIndex).trim();
						String numeCircumscriptie = inputCircumscriptie.substring(firstSpaceIndex + 1, secondSpaceIndex).trim();
						String regiune = inputCircumscriptie.substring(secondSpaceIndex + 1).trim();

						Circumscriptie c = new Circumscriptie(" ", regiune);
						String m = c.adaugareCircumscriptie(idAlegere, numeCircumscriptie, regiune, listaAlegeri, listaCircumscriptii);
						System.out.println(m);
					}
					break;

				case 3:
					String inputCircumscriptie2 = scanner.nextLine();
					int spaceIndex = inputCircumscriptie2.indexOf(" "); // gasesc primul spațiu

					if (spaceIndex == -1) {
						System.out.println("EROARE: Parametri insuficienti.");
					} else {
						String idAlegere = inputCircumscriptie2.substring(0, spaceIndex).trim();
						String numeCircumscriptie = inputCircumscriptie2.substring(spaceIndex + 1).trim();

						Circumscriptie c = new Circumscriptie(" ", " ");
						String m = c.eliminareCircumscriptie(idAlegere, numeCircumscriptie, listaAlegeri, listaCircumscriptii, listaVoturi);
						System.out.println(m);
					}
					break;

				case 4:
					String inputCandidat = scanner.nextLine();
					int spaceIndex1 = inputCandidat.indexOf(" "); // Găsesc primul spațiu
					int spaceIndex2 = inputCandidat.indexOf(" ", spaceIndex1 + 1); // Găsesc al doilea spațiu
					int spaceIndex3 = inputCandidat.indexOf(" ", spaceIndex2 + 1); // Găsesc al treilea spațiu

					if (spaceIndex1 == -1 || spaceIndex2 == -1 || spaceIndex3 == -1) {
						System.out.println("EROARE: Parametri insuficienti.");
					} else {
						String idAlegere = inputCandidat.substring(0, spaceIndex1).trim();
						String cnp = inputCandidat.substring(spaceIndex1 + 1, spaceIndex2).trim();
						int varsta = Integer.parseInt(inputCandidat.substring(spaceIndex2 + 1, spaceIndex3).trim());
						String nume = inputCandidat.substring(spaceIndex3 + 1).trim();

						Candidat c = new Candidat(" ", cnp, varsta);
						System.out.println(c.adaugareCandidat(idAlegere, cnp, varsta, nume, listaAlegeri));
					}

					break;
				case 5:
					String inputEliminare = scanner.nextLine();
					int space = inputEliminare.indexOf(" ");

					if (space == -1) {
						System.out.println("EROARE: Parametri insuficienti.");
					} else {
						String idAlegere = inputEliminare.substring(0, space).trim();
						String cnp = inputEliminare.substring(space + 1).trim();

						Candidat c = new Candidat(" ", cnp, 0);
						System.out.println(c.eliminareCandidat(idAlegere, cnp, listaAlegeri, listaVoturi));
					}
					break;

				case 6:
					String inputVotant = scanner.nextLine();
					int firstSpace = inputVotant.indexOf(" ");
					int secondSpace = inputVotant.indexOf(" ", firstSpace + 1);
					int thirdSpace = inputVotant.indexOf(" ", secondSpace + 1);
					int fourthSpace = inputVotant.indexOf(" ", thirdSpace + 1);
					int fifthSpace = inputVotant.indexOf(" ", fourthSpace + 1);

					if (firstSpace == -1 || secondSpace == -1 || thirdSpace == -1 || fourthSpace == -1 || fifthSpace == -1) {
						System.out.println("EROARE: Parametri insuficienti.");
					} else {
						String idAlegere = inputVotant.substring(0, firstSpace).trim();
						String numeCircumscriptie = inputVotant.substring(firstSpace + 1, secondSpace).trim();
						String cnp = inputVotant.substring(secondSpace + 1, thirdSpace).trim();
						int varsta = Integer.parseInt(inputVotant.substring(thirdSpace + 1, fourthSpace).trim());
						String neindemanatic = inputVotant.substring(fourthSpace + 1, fifthSpace).trim();
						String nume = inputVotant.substring(fifthSpace + 1).trim();

						Votant v = new Votant(nume, cnp, varsta, "");
						System.out.println(v.adaugareVotant(idAlegere, numeCircumscriptie, cnp, varsta, neindemanatic, nume, listaAlegeri, listaVotanti, listaCircumscriptii, listaVoturi));
					}
					break;

				case 7:
					String idAlegereListare = scanner.nextLine();
					Candidat c = new Candidat(" ", idAlegereListare, 0);

					String mesajListare = c.listareCandidati(idAlegereListare, listaAlegeri);
					System.out.println(mesajListare);
					break;

				case 8:
					String inputListare = scanner.nextLine();
					int spatiu = inputListare.indexOf(" ");

					if (spatiu == -1) {
						System.out.println("EROARE: Parametri insuficienti.");
					} else {
						String idAlegeri = inputListare.substring(0, spatiu).trim();
						String numeCircumscriptie = inputListare.substring(spatiu + 1).trim();

						Votant v = new Votant(" ", " ", 0, " ");
						System.out.println(v.listareVotanti(idAlegeri, numeCircumscriptie, listaVotanti, listaAlegeri, listaCircumscriptii));
					}
					break;

				case 9:
					String inputVotare = scanner.nextLine();
					int first = inputVotare.indexOf(" ");
					int second = inputVotare.indexOf(" ", first + 1);
					int third = inputVotare.indexOf(" ", second + 1);

					if (first == -1 || second == -1 || third == -1) {
						System.out.println("EROARE: Parametri insuficienti.");
					} else {
						String idAlegere = inputVotare.substring(0, first).trim();
						String numeCircumscriptie = inputVotare.substring(first + 1, second).trim();
						String cnpVotant = inputVotare.substring(second + 1, third).trim();
						String cnpCandidat = inputVotare.substring(third + 1).trim();

						Circumscriptie circumscriptie = null;
						for (Circumscriptie cm: listaCircumscriptii) {
							if (cm.getNumeCircumscriptie().equals(numeCircumscriptie)) {
								circumscriptie = cm;
							}
						}
						if (circumscriptie == null) {
							System.out.println("EROARE: Nu exista o circumscriptie cu numele " + numeCircumscriptie);
						} else {

							if (idAlegere.isEmpty() || numeCircumscriptie.isEmpty() || cnpVotant.isEmpty() || cnpCandidat.isEmpty()) {
								System.out.println("EROARE: Parametri insuficienti sau invalizi.");
							} else {
								Alegeri alegeri = new Alegeri(" ", idAlegere);
								//Circumscriptie circum =  new Circumscriptie(numeCircumscriptie, " ");
								System.out.println(alegeri.votare(idAlegere, circumscriptie, cnpVotant, cnpCandidat, listaAlegeri,
										listaCircumscriptii, listaFraude, listaVoturi, listaVotanti));
							}
						}
					}
					break;

				case 10:
					String idOprire = scanner.nextLine();
					Alegeri alegere = new Alegeri(" ", idOprire);
					System.out.println(alegere.oprireAlegeri(idOprire, listaAlegeri));
					break;

				case 11:
					String inputRaport = scanner.nextLine();
					int s = inputRaport.indexOf(" ");

					if (s == -1) {
						System.out.println("EROARE: Parametri insuficienti.");
					} else {
						String idAlegeri = inputRaport.substring(0, s).trim();
						String numeCircumscriptie = inputRaport.substring(s + 1).trim();

						Circumscriptie circumscriptie = new Circumscriptie(numeCircumscriptie, " ");
						System.out.println(circumscriptie.raportVoturiCircumscriptie(idAlegeri, numeCircumscriptie, listaAlegeri, listaCircumscriptii,
								listaVoturi));
					}
					break;
				case 12:
					String idRaportNational = scanner.nextLine();
					Alegeri alegeri = new Alegeri(" ", idRaportNational);
					System.out.println(alegeri.raportVoturiNational(idRaportNational, listaAlegeri, listaVoturi));

					break;
				case 13:
					String inputAnaliza = scanner.nextLine();
					int spatiuAnaliza = inputAnaliza.indexOf(" ");

					if (spatiuAnaliza == -1) {
						System.out.println("EROARE: Parametri insuficienti.");
					} else {
						String idAlegeri = inputAnaliza.substring(0, spatiuAnaliza);
						String numeCircumscriptie = inputAnaliza.substring(spatiuAnaliza + 1).trim();

						Circumscriptie circum = new Circumscriptie(numeCircumscriptie, " ");
						System.out.println(circum.analizaCircumscriptie(idAlegeri, numeCircumscriptie, listaAlegeri, listaCircumscriptii, listaVoturi));
					}
					break;

				case 14:
					String idAleg = scanner.nextLine();
					Alegeri al = new Alegeri(" ", idAleg);
					System.out.println(al.analizaNationala(idAleg, listaAlegeri, listaVoturi, listaCircumscriptii));
					break;

				case 15:
					String idFraude = scanner.nextLine();
					Frauda frauda = new Frauda(" ", " ", " ");
					System.out.println(frauda.raportFraude(idFraude, listaAlegeri, listaFraude, listaCircumscriptii, listaVotanti));
					break;

				case 16:
					String idStergere = scanner.nextLine();
					Alegeri aleg = new Alegeri(" ", idStergere);
					System.out.println(aleg.stergereAlegeri(idStergere, listaAlegeri, listaCircumscriptii, listaVoturi));
					break;

				case 17:
					Alegeri alegeriListate = new Alegeri( " ", " ");
					System.out.println(alegeriListate.listareAlegeri(listaAlegeri));
					break;
				case 18:
					aplicatie = false;
					System.out.println("Iesire din aplicatie.");
					break;
				default:
					System.out.println("EROARE: Comanda necunoscuta.");
			}
		}
	}

	public static void main(String[] args) {
		App app = new App(System.in);
		app.run();
	}

}
