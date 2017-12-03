-- auto-generated definition
create table carmanager.persons
(
	ID bigint not null auto_increment
		primary key,
	CREATED datetime not null,
	EMAIL varchar(100) not null,
	FIRST_NAME varchar(100) not null,
	LAST_NAME varchar(100) not null,
	LOGIN varchar(100) null,
	NOTES longtext null,
	PASSWORD varchar(255) null,
	PICTURE varchar(255) null,
	UPDATED datetime  not null,
	BIRTH_DATE date null,
	GENDER varchar(15) null,
	PHONE_NUMBER varchar(20) null,
	ADDRESS bigint null,
	NAME varchar(150) null,
	CARD bigint null,
	constraint EMAIL
	unique (EMAIL),
	constraint persons_LOGIN_uindex
	unique (LOGIN),
	constraint persons_NAME_uindex
	unique (NAME),
	constraint persons_address_ID_fk
	foreign key (ADDRESS) references address (ID)
		on update cascade on delete cascade,
	constraint persons_credit_card_ID_fk
	foreign key (CARD) references credit_card (ID)
		on update cascade on delete cascade
)
;

create index persons_address_ID_fk
	on persons (ADDRESS)
;

create index persons_credit_card_ID_fk
	on persons (CARD)
;

