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
    , TourInfo varchar(255)
    , TourID varchar(30) primary key        -- skammstofun TourName OperatorName TourLocation TourDate TourStartTime
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

CREATE TABLE BOOKING
    ( CustomerUserID varchar(20) references CUSTOMER(CustomerUserID)
    , TourID varchar(30) references TOUR(TourID)
    , Travellers int not null
--    , CONSTRAINT check_TourMaxTravellers  -- Gaetum viljad gera constraint herna til ad passa ad thad verdi ekki yfirbokad
--        CHECK ()                          -- liklega betra ad utfaera thad tho i java og i vidmoti, byggt a TourMaxTravellers og select count
    );