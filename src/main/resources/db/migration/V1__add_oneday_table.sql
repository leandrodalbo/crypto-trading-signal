CREATE TABLE oneday (
    symbol              varchar(30) PRIMARY KEY NOT NULL,
    signaltime          BIGINT NOT NULL,
    buystrength         varchar(10) NOT NULL,
    sellstrength        varchar(10) NOT NULL,
    bollingerbands      varchar(10) NOT NULL,
    ema                 varchar(10) NOT NULL,
    sma                 varchar(10) NOT NULL,
    macd                varchar(10) NOT NULL,
    obv                 varchar(10) NOT NULL,
    rsi                 varchar(10) NOT NULL,
    rsidivergence       varchar(10) NOT NULL,
    stochastic          varchar(10) NOT NULL,
    engulfingcandle     varchar(10) NOT NULL,
    version             integer NOT NULL
);