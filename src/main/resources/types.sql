-- auto-generated definition
-- auto-generated definition
create table carmanager.vehicle_types
(
  ID bigint not null auto_increment
    primary key,
  CREATED datetime  not null,
  UPDATED datetime not null,
  TYPE varchar(50) not null,
  PICTURE varchar(200) null,
  constraint vehicle_types_ID_uindex
  unique (ID),
  constraint vehicle_types_TYPE_uindex
  unique (TYPE)
)
;


;

