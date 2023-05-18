create table product (id bigint not null, balance integer not null, date_created datetime(6) not null, date_updated datetime(6), description varchar(255), price float(53) not null, title varchar(255), primary key (id)) engine=InnoDB;
create table product_seq (next_val bigint) engine=InnoDB;
insert into product_seq values ( 1 );