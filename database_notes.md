# DataBase Notes

Er að vinna í að gera Einindavenslalíkan og -rit. Glósur eru hér neðst.

### MySQL upplýsingar

[MySQL Cheatsheet](https://gist.github.com/hofmannsven/9164408)

[MySQL Downloads](https://dev.mysql.com/downloads/file/?id=483327) Erling hlóð niður MySQL Installer og í honum gat ég installað JDBC Connector fyrir Java (líklega óþarfi, held það sé innbyggt í Java, þið checkið bara annars ekkert mál að ná í það), MySQL Workbench (sem ég hata en þar er hægt að skoða gagnagrunninn, framkvæma skipanir o.fl.) og MySQL Shell (CLI sem ég elska).

* Opna MySQL Shell
* Tengjast með: `\connect hbv401v19cl3@den1.mysql1.gear.host:3306/`
* Verður beðinn um lykilorð: `Xi6VcurQ~x-6` 
* Byrja að gera SQL skipanir: `\sql`
* Velja gagnagrunn: `use hbv401v19cl3`
* Þá er hægt að gera `Create Table, Insert, Update Table` o.fl. skipanir og vistast þær jafnóðum í gagnagrunninn.
* Það á að vera hægt að gera _import dump_ með skipun í áttina við `source dts.sql`, m.v. að dts.sql sé þá í _current working directory_. Eða hafa _full path_ á undan. Þó fékk ég það ekki til að virka, en það er hægt að kópera skrána þangað inn. Gæti verið að kommentin valdi veseni.


<details>
  <summary>Innskráningarupplýsingar á vefsíðu Gearhost</summary>
Sem hýsir MySQL Server'inn okkar
  
https://my.gearhost.com/Account/Login 
eok4@hi.is
Bestpasswordeu_1
</details>

### Hvaða upplýsingar þurfa að vera í gagnagrunninum (verða að töflu(m))

**Day Tours**: Name, StartTime (hhmm), EndTime (hhmm), Duration (calculated), Date, Weekday (derived), Operator, Location, MaxTravellers, CurrentTravellers, Price, Info

Hef hugsað mér að hafa _primary key(TourName, TourDate, TourStartTime, TourEndTime)_ því það væri skrítið að vera með margar ferðir með sama nafni á sama tíma, þó það gæti gerst, sérstaklega ef það eru mismunandi TourOperators á þeim.

**Operator**: Name, Location, Info, CEO

**Customer**: Fname, Lname, UserID, Email, Password

**Bookings**: UserID, TourID, Travellers

Við gætum bætt in Tour Guides eða einhverju. Sé ekki tilganginn. Í raun er Operator taflan meira að segja gagnslaus (það verða nú örugglega bara 5-10 línur þar).

Fyrstu drög að gagnagrunns schema komið í dts.sql; Dettur í raun ekkert meira í hug til að setja í þetta.





<details>
  <summary>Heroku upplýsingar - Notum ekki</summary>
  
  Er búinn að vera að skoða Heroku. Þetta er stórmerkilegt umhverfi til að forrita í eða með. Þeir vilja að maður geri _Deploy App_ í gegnum síðuna sína og tengi það við github. Þannig getur maður svo tengt það við gagnagrunninn sinn. Ég gat ekki gert það því Davíð er eigandi github repo'sins okkar, en mun skoða með að setja bara upp mitt eigið afrit til að prófa þetta. Svo á maður að geta tengst gagnagrunninum gegnum CLI sem er náttúrulega það sem ég vill gera.

### Links
[PostgreSQL Installer](https://www.enterprisedb.com/downloads/postgres-postgresql-downloads) Heroku styðst við PostgreSQL 10.6

[PostgreSQL 10 Documentation](https://www.postgresql.org/docs/10/index.html)

[Heroku Postgres Information](https://devcenter.heroku.com/articles/heroku-postgresql): Inniheldur m.a. upplýsingar um hvernig á að tengjast með Java/JDBC sem og Command Line Interface (CLI)

[Heroku CLI](https://devcenter.heroku.com/articles/heroku-cli) Upplýsingar um uppsetningu ef einhver vill/þarf að nota það. Erling henti því allavega upp.

[Heroku Get Started](https://devcenter.heroku.com/articles/getting-started-with-java#use-a-database)

</details>
