CREATE TABLE TOUR
    ( TourName varchar(30) not null
    , OperatorName varchar(30) references OPERATOR(OperatorName)
    , TourLocation varchar(20) not null
    , TourStartTime char(4) not null -- hhmm
    , TourEndTime char(4) not null -- hhmm
    , TourDuration int
    , TourDate char(8) not null -- ddmmyyyy
    , TourMaxCustomers int
    , TourMinCustomers int -- spurning med ad bjoda upp a thad
    , TourCurrentCustomers int
    , TourInfo varchar(255)
    , primary key(TourName, TourDate, TourStartTime, TourEndTime)
    );

CREATE TABLE OPERATOR
    ( OperatorName varchar(30) primary key
    , OperatorLocation varchar(20)
    );