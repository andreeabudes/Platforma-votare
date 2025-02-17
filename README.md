  VoteSmart este o platformă de vot online care permite gestionarea procesului electoral într-un mod eficient și securizat. Proiectul simulează un sistem real de alegeri, incluzând adăugarea și eliminarea 
candidaților, votanților, gestionarea circumscripțiilor și raportarea fraudelor.
  Scurtă descriere a claselor:
1️⃣ App.java
Punctul de intrare al aplicației
Procesează comenzile utilizatorului pentru gestionarea alegerilor, votanților și rapoartelor.

2️⃣ Alegeri.java
Gestionează crearea, pornirea, oprirea și ștergerea alegerilor.
Permite administrarea candidaților și voturilor.

3️⃣ Circumscriptie.java
Reprezintă o zonă electorală cu votanți și voturi.
Permite adăugarea, eliminarea și raportarea voturilor.

4️⃣ Persoana.java (părinte pentru Votant și Candidat)
Conține CNP, nume și vârstă.

5️⃣ Votant.java
Extinde Persoana, adăugând atributul „neîndemânatic” pentru validitatea votului.

6️⃣ Candidat.java
Extinde Persoana, conținând lista voturilor primite.

7️⃣ Vot.java
Înregistrează un vot, verifică validitatea și posibile fraude.

8️⃣ Frauda.java
Identifică și raportează voturile suspecte (ex. vot multiplu).

9️⃣ Regiune.java
Grupează și analizează voturile pe zone geografice.
