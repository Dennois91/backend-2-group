create table customer (id bigint not null, date_created datetime(6), date_updated datetime(6), email varchar(255),
first_name varchar(255), last_name varchar(255), phone varchar(255), ssn varchar(255), primary key (id)) engine=InnoDB;
create table customer_seq (next_val bigint) engine=InnoDB;
insert into customer_seq values ( 1 );