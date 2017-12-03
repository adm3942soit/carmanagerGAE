-- auto-generated definition
-- auto-generated definition
create table carmanager.renta_history
(
  ID bigint not null auto_increment
    primary key,
  CREATED timestamp not null,
  UPDATED timestamp not null,
  PERSON varchar(255) null,
  VEHICLE varchar(255) null,
  PRICE double default '0' null,
  PRICE_WEEK  DOUBLE DEFAULT '0'                      NULL,
  PRICE_MONTH DOUBLE DEFAULT '0'                      NULL,
  PRICE_DAY   DOUBLE DEFAULT '0'                      NULL,
  SUMMA double default '0' null,
  PAID bit default b'0' null,
  FROM_DATE timestamp  not null,
  TO_DATE timestamp  not null,
  constraint renta_history_ID_uindex
  unique (ID)
)
;



