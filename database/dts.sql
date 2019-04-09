-- Taflan Tour inniheldur eftirfarandi upplýsingar um dagsferðir: 
-- Nafn (TourName), 
-- Fyrirtækið sem sér um ferðina (OperatorName), 
-- Upphafsstað dagsferðarinnar (TourLocation)
-- Upphafstíma (TourStartTime), lokatíma (TourEndTime) og dagsetningu (TourDate) ferðarinnar
-- Núverandi fjölda gesta skráða í ferðina, hámarksfjölda leyfðra gesta (TourMaxTravellers), 
-- verð (TourPrice), lýsingu (TourInfo) og leitarorð sem tengjast dagsferðinni (TourKeywords). 
CREATE TABLE TOUR
    ( TourName varchar(50) not null
    , TourOperator varchar(50) references OPERATOR(OperatorName)
    , TourLocation varchar(50) not null     -- 
    , TourStartTime char(4) not null        -- hhmm
    , TourEndTime char(4) not null          -- hhmm
    , TourDate char(8) not null             -- ddmmyyyy
    , TourTravellers int default 0
    , TourMaxTravellers int default 100
    , TourPrice int not null
    , TourInfo varchar(255)
    , TourKeywords varchar(255)             -- Gæti verið góð leið til að útfæra leit
    , TourImg varchar(255) -- slóð á mynd í tölvu
    , constraint TourID primary key(TourName, 
      TourOperator, TourLocation, TourStartTime, TourDate) -- Gerum þá kröfu að þessi samsetning eiginda sé UNIQUE og ekki NULL
    ); -- Bæta við vísi


-- Taflan Operator inniheldur eftirfarandi upplýsingar um þá sem sjá um að bjóða upp á dagsferðir.
-- Nafn (OperatorName) og staðsetningu (OperatorLocation) fyrirtækisins og lýsingu á því (OperatorInfo).
-- Nafn forstjóra fyrirtækisins (OperatorCEO) og netfangið hans (OperatorEmail)
CREATE TABLE OPERATOR
    ( OperatorName varchar(50) primary key
    , OperatorLocation varchar(50) not null
    , OperatorInfo varchar(255) not null
    , OperatorCEO varchar(50) not null
    , OperatorEmail varchar(50) not null
    , OperatorPassword varchar(50) not null
    , constraint chk_email check (OperatorEmail like '%_@__%.__%')     -- inniheldur lagmark 1 staf fyrir @, 2 fyrir ., 2 eftir .
    );

-- Taflan Customer inniheldur eftirfarandi upplýsingar um mögulega kaupendur dagsferða: 
-- Fornafn (CustomerFname) og eftirnafn (CustomerLname)
-- Netfang (CustomerEmail) og lykilorð (CustomerPassword)
CREATE TABLE CUSTOMER
    ( CustomerName varchar(50) not null
    , CustomerEmail varchar(50) primary key
    , CustomerPassword varchar(50) not null
    , constraint chk_email check (CustomerEmail like '%_@__%.__%')     -- inniheldur lagmark 1 staf fyrir @, 2 fyrir ., 2 eftir .
    );

-- Taflan Booking inniheldur eftirfarandi upplýsingar um bókanir í dagsferðir:
-- Netfang þess sem bókar (CustomerUserID)
-- Fjölda farþega (Travellers)
CREATE TABLE BOOKING
    ( CustomerEmail varchar(50) references CUSTOMER(CustomerUserID)
    , TourName varchar(50) references Tour(TourName)
    , TourOperator varchar(50) references Tour(TourOperator)
    , TourLocation varchar(50) references Tour(TourLocation)
    , TourStartTime char(4) references Tour(TourStartTime)
    , TourDate char(8) references Tour(TourDate)
    , Travellers int not null
    , constraint BookingID primary key(CustomerEmail, TourName, 
      TourOperator, TourLocation, TourStartTime, TourDate)
    );

CREATE TABLE REVIEW 
    ( TourName varchar(50) references TOUR(TourName)
    , TourOperator varchar(50) references TOUR(TourOperator)
    , TourLocation varchar(50) references TOUR(TourLocation)
    , CustomerName varchar(50) references CUSTOMER(CustomerName)
    , CustomerEmail varchar(50) references CUSTOMER(CustomerEmail)
    , ReviewDate char(8) not null
    , CustomerReview varchar(255)
    );


-- Dæmi um gildi
insert into OPERATOR values ('Kattegat Travel', 'Kattegat', 'Our CEO is a god.', 'Ivar the Boneless', 'theboss@kattegat.no', 'ourbosspassword');
insert into TOUR values ('Horse-riding adventure', 'Kattegat Travel', 'Akureyri', '1400', '1700', '28022019', 0, 10, 100, 'Ride by the coast on a skeleton horse.', 'Horse Horses Coast Wind Skeletons Blood', null);
insert into CUSTOMER values ('Erling Oskar', 'eok4@hi.is', 'ErlingWasHere');
insert into REVIEW values ('Horse-riding adventure', 'Kattegat Travel', 'Akureyri', 'Erling Oskar', 'eok4@hi.is', '21032019', 'Búinn með einn bjór og bara gaman að gera gagnagrunn');
