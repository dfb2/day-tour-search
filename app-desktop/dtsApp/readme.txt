�  netbeans getur veri� a� �a� �urfi a� b�ta vi� jdbc driver (mysql-connector-java-8.0.14 � r�t m�ppunnar).
�a� er gert me� �v� a� h�grismella � dtsApp -> Properties -> Libraries -> Add JAR

�a� helsta sem �i� g�tu� �urft a� nota sem er komi� er:

en.hi.dtsapp.controller TourCatalog.java sem notar eftirfarandi klasa 
til a� sm��a Tours �r gagnagrunni og geymir �� � private Final (Immutable) List,
a�fer�ir � honum gerir svo ObserveableList afrit af �essum lista og �a� er h�gt a� s�a hann.

A�rir klasar:
en.hi.dtsapp.model Tour.java sem sm��ar Tours,
en.hi.dtsapp.model Tour.java sem sm��ar TourDAO.

Eins og er �� erum vi� bara b�in a� �tf�ra mj�g basic �tlit og �a� er h�gt a� sko�a Tours, leita eftir lykilor�i og dagsetningu.
Me� �essum kl�sum h�rna geti� �i� �tf�rt �a� sama auk annara s�anna.