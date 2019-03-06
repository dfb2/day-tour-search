-- Taflan Tour inniheldur eftirfarandi upplýsingar um dagsferðir: 
-- Nafn (TourName), 
-- Fyrirtækið sem sér um ferðina (OperatorName), 
-- Upphafsstað dagsferðarinnar (TourLocation)
-- Upphafstíma (TourStartTime), lokatíma (TourEndTime) og dagsetningu (TourDate) ferðarinnar
-- Núverandi fjölda gesta skráða í ferðina, hámarksfjölda leyfðra gesta (TourMaxTravellers), 
-- verð (TourPrice), lýsingu (TourInfo) og leitarorð sem tengjast dagsferðinni (TourKeywords). 
CREATE TABLE TOUR
    ( TourName varchar(30) not null
    , OperatorName varchar(30) not null references OPERATOR(OperatorName)
    , TourLocation varchar(20) not null      
    , TourStartTime char(4) not null        -- hhmm
    , TourEndTime char(4) not null          -- hhmm
    , TourDate date not null                -- yyyy-mm-dd
    , TourTravellers int default 0              
    , TourMaxTravellers int
    , TourPrice int not null
    , TourInfo varchar(255)
    , TourKeywords varchar(255)             -- Gæti verið góð leið til að útfæra leit
    , constraint TourID primary key(TourName, 
      OperatorName, TourLocation, TourDate, TourStartTime) -- Gerum þá kröfu að þessi samsetning eiginda sé UNIQUE og ekki NULL
    -- , TourImg varchar(255) not null -- slóð á mynd í tölvu
    );

insert into OPERATOR values ('Kattegat Travel', 'Kattegat', 'Our CEO is a god.', 'Ivar the Boneless', 'theboss@kattegat.no');
insert into TOUR values ('Horse-riding adventure', 'Kattegat Travel', 'Akureyri', '1400', '1700', '2019-02-28', 10, 100, 'Ride by the coast.', 'Horse Horses Coast Wind');

-- Taflan Operator inniheldur eftirfarandi upplýsingar um þá sem sjá um að bjóða upp á dagsferðir.
-- Nafn (OperatorName) og staðsetningu (OperatorLocation) fyrirtækisins og lýsingu á því (OperatorInfo).
-- Nafn forstjóra fyrirtækisins (OperatorCEO) og netfangið hans (OperatorEmail)
CREATE TABLE OPERATOR
    ( OperatorName varchar(30) primary key
    , OperatorLocation varchar(20) not null
    , OperatorInfo varchar(255) not null
    , OperatorCEO varchar(50) not null
    , OperatorEmail varchar(40) not null
    , CustomerPassword varchar(40) not null
    , constraint chk_email check (OperatorEmail like '%_@__%.__%')     -- inniheldur lagmark 1 staf fyrir @, 2 fyrir ., 2 eftir .
    );

-- Taflan Customer inniheldur eftirfarandi upplýsingar um mögulega kaupendur dagsferða: 
-- Fornafn (CustomerFname) og eftirnafn (CustomerLname)
-- Netfang (CustomerEmail) og lykilorð (CustomerPassword)
CREATE TABLE CUSTOMER
    ( CustomerName varchar(50) not null
    , CustomerEmail varchar(40) primary key
    , CustomerPassword varchar(40) not null
    , constraint chk_email check (CustomerEmail like '%_@__%.__%')     -- inniheldur lagmark 1 staf fyrir @, 2 fyrir ., 2 eftir .
    );

-- Taflan Booking inniheldur eftirfarandi upplýsingar um bókanir í dagsferðir:
-- Netfang þess sem bókar (CustomerUserID)
-- Auðkenni ferðarinnar (TourID)
-- Fjölda farþega (Travellers)
CREATE TABLE BOOKING                        
    ( CustomerUserID varchar(20) not null references CUSTOMER(CustomerEmail)
    , TourID varchar(30) not null references TOUR(TourID)
    , Travellers int not null
--    , CONSTRAINT check_TourMaxTravellers  -- Gaetum viljad gera constraint herna til ad passa ad thad verdi ekki yfirbokad
--        CHECK ()                          -- liklega betra ad utfaera thad tho i java og i vidmoti, byggt a TourMaxTravellers og select count
    );

CREATE TABLE REVIEW 
    ( TourName varchar(30) references TOUR(TourName)
    , TourLocation varchar(20) references TOUR(TourLocation)
    , TourOperator varchar(20) references TOUR(TourOperator)
    , ReviewDate date not null
    , CustomerReview varchar(255)
    );

