use ajg8669;

SET foreign_key_checks = 0;

drop table if exists employee;
drop table if exists custOrder;
drop table if exists orderContains;
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

create table custOrder
(
    orderID int not null,
    orderTakenEID int not null,
    orderPreparedEID int,
    tableNumber int not null,
    primary key (orderID),
    foreign key(orderTakenEID) references employee(EID) on update CASCADE on delete RESTRICT
)
ENGINE=INNODB;

create table orderContains
(
    menuID int not null,
    orderID int not null,
    qty int not null,
    primary key (menuID, orderID),
    foreign key(orderID) references custOrder(orderID) on update CASCADE on delete RESTRICT,
    foreign key(menuID) references menu(menuID) on update CASCADE on delete RESTRICT
) 
ENGINE=INNODB;

create table menu
(
    menuID int not null,
    itemName varchar(30) not null,
    price float not null,
    primary key (menuID)
) 
ENGINE=INNODB;

create table takesFrom
(
    menuID int not null,
    itemID int not null,
    primary key (menuID, itemID),
    foreign key(menuID) references menu(menuID) on update CASCADE on delete RESTRICT,
    foreign key(itemID) references stock(itemID) on update CASCADE on delete RESTRICT
)
ENGINE=INNODB;

create table stock 
(
    itemID int not null,
    ingredientName varchar(30) not null,
    amount int not null,
    check(amount >= 0),
    primary key (itemID)
)
ENGINE=INNODB;

SET foreign_key_checks = 1;