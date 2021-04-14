create table PRODUCT
(
	ID INTEGER identity,
	NAME CHARACTER(64),
	PRICE INTEGER
);

create unique index PRODUCT_ID_UINDEX
	on PRODUCT (ID);

create unique index SYS_IDX_SYS_PK_10092_10093
	on PRODUCT (ID);


