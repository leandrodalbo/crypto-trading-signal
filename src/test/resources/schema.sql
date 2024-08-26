DROP TABLE IF EXISTS oneday;
CREATE TABLE oneday (
    symbol              varchar(30) PRIMARY KEY NOT NULL,
    smacrossover        varchar(10) NOT NULL,
    version             integer NOT NULL
);

DROP TABLE IF EXISTS fourhour;
CREATE TABLE fourhour (
    symbol              varchar(30) PRIMARY KEY NOT NULL,
    smacrossover        varchar(10) NOT NULL,
    version             integer NOT NULL
);

DROP TABLE IF EXISTS onehour;
CREATE TABLE onehour (
    symbol              varchar(30) PRIMARY KEY NOT NULL,
    smacrossover        varchar(10) NOT NULL,
    version             integer NOT NULL
);