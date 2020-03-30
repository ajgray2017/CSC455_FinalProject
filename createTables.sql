use ajg8669;

SET foreign_key_checks = 0;

drop table if exists employee;
drop table if exists order;
drop table if exists contains;
drop table if exists menu;
drop table if exists takesFrom;
drop table if exists stock;

create table employee
(
    EID int not null,
    locationID int not null,
    employeeName varchar(50) not null,
    employeeAddress varchar(50) not null,
    wage float not null,
    primaryPhone varchar(12) not null,
    position varchar(10) not null,
    primary key (EID)
)
ENGINE=INNODB;

create table order
(
    orderID int not null,
    orderTakenEID int not null,
    orderPreparedEID int not null,
    tableNumber int not null,
    primary key (orderID)
    foreign key(orderTakenEID) references employee(EID) on update CASCADE on delete RESTRICT,
    foreign key(orderPreparedEID) references employee(EID) on update CASCADE on delete RESTRICT
)
ENGINE=INNODB;

create table contains
(
    menuID int not null,
    orderID int not null,
    qty int not null,
    primary key (orderID),
    foreign key(orderID) references order(orderID) on update CASCADE on delete RESTRICT
) 
ENGINE=INNODB;

create table menu
(
    menuID int not null,
    primary key (menuID),
    itemName varchar(30) not null,
    price float not null,
    foreign key(menuID) references contains(menuID) on update CASCADE on delete RESTRICT
) 
ENGINE=INNODB;

create table takesFrom
(
    menuID int not null,
    itemID int not null,
    primary key (menuID),
    foreign key(menuID) references contains(menuID) on update CASCADE on delete RESTRICT
)
ENGINE=INNODB;

create table stock 
(
    itemID int not null,
    ingredientName carchar(30) not null,
    primary key (itemID),
    foreign key(itemID) references takesFrom(itemID) on update CASCADE on delete RESTRICT
)
ENGINE=INNODB;

SET foreign_key_checks = 1;