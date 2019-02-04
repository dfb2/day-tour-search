/**
 * Virkar ekki í Windows PowerShell en þó í Command Prompt:
 javac MySQLpractice.java
 java -cp .;mysql-connector-java-8.0.14.jar MySQLpractice
 Bætti í system environmental variable paths
 C:\Program Files (x86)\MySQL\Connector J 8.0\mysql-connector-java-8.0.14
 sem dugði ekki fyrir VSC en að hafa þetta jar file í project library í netbeans dugði


 MySQL Shell
 Tengjast við gagnagrunn: 
    \connect hbv401v19cl3@den1.mysql1.gear.host:3306/ 
 password will be prompted

Byrja að gera sql skipanir
    \sql
Velja gagnagrunn
    use hbv401v19cl3

Þá er hægt að keyra skipanir á skipanalínunni.
Gat því miður ekki keyrt 
                source dts.sql
    beint af þeirri skipanalínu. Prófaðu full path. Fæ villur. En get kóperað inn create Table skipanirnar
 */

CREATE TABLE TOUR
    ( TourName varchar(30) not null
    , OperatorName varchar(30) references OPERATOR(OperatorName)
    , TourLocation varchar(20) not null     -- 
    , TourStartTime char(4) not null        -- hhmm
    , TourEndTime char(4) not null          -- hhmm
--    , TourDuration int                    -- Otharfi ad geyma i gagnagrunni. Reiknad vid birtingu i toflu
    , TourDate char(8) not null             -- ddmmyyyy
    , TourMaxTravellers int
--    , TourCurrentTravellers int           -- Otharfi ad geyma. Haegt ad reikna med:      select sum(Travellers) from BOOKING where TourID = "blablaAk"
    , TourPrice int
    , TourInfo varchar(255)
    , TourID varchar(30) primary key        -- skammstofun TourName OperatorName TourLocation TourDate TourStartTime
    , TourKeywords varchar(255)             -- Gæti verið góð leið til að útfæra leit
--    , TourImg 
/*
if your pictures or document are typically over 1 MB in size, 
storing them in the filesystem is more efficient 
(and with SQL Server 2008's FILESTREAM attribute, 
they're still under transactional control and part of the database)
*/
    );

CREATE TABLE OPERATOR
    ( OperatorName varchar(30) primary key
    , OperatorLocation varchar(20)
    , OperatorInfo varchar(255)
    , OperatorCEO varchar(50)
    , OperatorEmail char(8)
    , constraint chk_email check (OperatorEmail like '%_@__%.__%')     -- inniheldur lagmark 1 staf fyrir @, 2 fyrir ., 2 eftir .
    );

CREATE TABLE CUSTOMER
    ( CustomerFname varchar(20) not null
    , CustomerLname varchar(30) not null
    , CustomerEmail varchar(40) primary key
    , CustomerPassword varchar(40)
    , constraint chk_email check (CustomerEmail like '%_@__%.__%')     -- inniheldur lagmark 1 staf fyrir @, 2 fyrir ., 2 eftir .
    );

CREATE TABLE BOOKING                        -- Spurning hvort thurfi primary key
    ( CustomerUserID varchar(20) references CUSTOMER(CustomerUserID)
    , TourID varchar(30) references TOUR(TourID)
    , Travellers int not null
--    , CONSTRAINT check_TourMaxTravellers  -- Gaetum viljad gera constraint herna til ad passa ad thad verdi ekki yfirbokad
--        CHECK ()                          -- liklega betra ad utfaera thad tho i java og i vidmoti, byggt a TourMaxTravellers og select count
    );

/* Líklegast óþarfa tafla
CREATE TABLE REVIEW 
    ( TourName varchar(30) references TOUR(TourName)
    , TourLocation varchar(20) references TOUR(TourLocation)
    , TourOperator varchar(20) references TOUR(TourOperator)
    , CustomerReview varchar(255)
    );

/

