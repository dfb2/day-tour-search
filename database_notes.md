# DataBase Notes

Er að vinna í að gera Einindavenslalíkan og -rit. Glósur eru hér neðst.

Er búinn að vera að skoða Heroku. Þetta er stórmerkilegt umhverfi til að forrita í eða með. Þeir vilja að maður geri _Deploy App_ í gegnum síðuna sína og tengi það við github. Þannig getur maður svo tengt það við gagnagrunninn sinn. Ég gat ekki gert það því Davíð er eigandi github repo'sins okkar, en mun skoða með að setja bara upp mitt eigið afrit til að prófa þetta. Svo á maður að geta tengst gagnagrunninum gegnum CLI sem er náttúrulega það sem ég vill gera.

### Links
[PostgreSQL Installer](https://www.enterprisedb.com/downloads/postgres-postgresql-downloads) Heroku styðst við PostgreSQL 10.6

[PostgreSQL 10 Documentation](https://www.postgresql.org/docs/10/index.html)

[Heroku Postgres Information](https://devcenter.heroku.com/articles/heroku-postgresql): Inniheldur m.a. upplýsingar um hvernig á að tengjast með Java/JDBC sem og Command Line Interface (CLI)

[Heroku CLI](https://devcenter.heroku.com/articles/heroku-cli) Upplýsingar um uppsetningu ef einhver vill/þarf að nota það. Erling henti því allavega upp.

[Heroku Get Started](https://devcenter.heroku.com/articles/getting-started-with-java#use-a-database)

### Hvaða upplýsingar þurfa að vera í gagnagrunninum (verða að töflu(m))

Day Tours: Name, StartTime (hhmm), EndTime (hhmm), Duration (calculated), Date, Weekday (derived), Operator, Location, MaxCustomers, CurrentCustomers, Info

Hef hugsað mér að hafa _primary key(TourName, TourDate, TourStartTime, TourEndTime)_ því það væri skrítið að vera með margar ferðir með sama nafni á sama tíma, þó það gæti gerst, sérstaklega ef það eru mismunandi TourOperators á þeim.

Operator: Name, Location

Fyrstu drög að gagnagrunns schema komið í dts.sql; Dettur í raun ekkert meira í hug til að setja í þetta.