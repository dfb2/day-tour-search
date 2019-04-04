Í  netbeans getur verið að það þurfi að bæta við jdbc driver (mysql-connector-java-8.0.14 í rót möppunnar).
Það er gert með því að hægrismella á dtsApp -> Properties -> Libraries -> Add JAR

Það helsta sem þið gætuð þurft að nota sem er komið er:

en.hi.dtsapp.controller TourCatalog.java sem notar eftirfarandi klasa 
til að smíða Tours úr gagnagrunni og geymir þá í private Final (Immutable) List,
aðferðir í honum gerir svo ObserveableList afrit af þessum lista og það er hægt að sía hann.

Aðrir klasar:
en.hi.dtsapp.model Tour.java sem smíðar Tours,
en.hi.dtsapp.model Tour.java sem smíðar TourDAO.

Eins og er þá erum við bara búin að útfæra mjög basic Útlit og það er hægt að skoða Tours, leita eftir lykilorði og dagsetningu.
Með þessum klösum hérna getið þið útfært það sama auk annara síanna.