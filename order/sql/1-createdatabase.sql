create table purchase (id bigint not null, address varchar(255), date_created datetime(6) not null,
 date_updated datetime(6), locality varchar(255), zip_code varchar(255), primary key (id)) engine=InnoDB;

create table user_seq (next_val bigint) engine=InnoDB;
insert into user_seq values ( 1 );

create table purchase_product (id bigint not null, date_created datetime(6) not null, date_updated datetime(6),
 price float(53) not null, quantity integer not null, title varchar(255), purchase_id bigint not null,
  primary key (id)) engine=InnoDB;

create table user_seq (next_val bigint) engine=InnoDB;
insert into user_seq values ( 1 );

alter table purchase_product add constraint FK1te3j5efipmc5c19wve8c90qd
    foreign key (purchase_id) references purchase (id)