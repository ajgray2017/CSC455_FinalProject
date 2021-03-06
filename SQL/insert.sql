SET FOREIGN_KEY_CHECKS = 0;

INSERT INTO employee VALUES(001, 1, "Ricky", "123 Lulu St.", 7.25, "123-123-4561", "Server");
INSERT INTO employee VALUES(002, 1, "Gertude", "55 Stank Rd.", 7.25, "444-864-4561", "Server");
INSERT INTO employee VALUES(003, 1, "Jeb", "4 Jiminy St.", 12.50, "133-443-4500", "Cook");
INSERT INTO employee VALUES(004, 1, "Lucy", "123 Lulu St.", 13.00, "123-321-0001", "Cook");
INSERT INTO employee VALUES(005, 1, "Carl", "666 Camel Ct.", 14.75, "345-786-2345", "Cook");

INSERT INTO custOrder VALUES(5001, 001, 005, 45);
INSERT INTO custOrder VALUES(5002, 001, 003, 54);
INSERT INTO custOrder VALUES(5003, 001, 003, 41);
INSERT INTO custOrder VALUES(5004, 002, 004, 42);
INSERT INTO custOrder VALUES(5005, 002, 005, 51);
INSERT INTO custOrder VALUES(5006, 002, null, 52);
INSERT INTO custOrder VALUES(5007, 002, 005, 46);

INSERT INTO orderContains VALUES(501, 5001, 1);
INSERT INTO orderContains VALUES(501, 5002, 2);
INSERT INTO orderContains VALUES(501, 5003, 1);
INSERT INTO orderContains VALUES(501, 5004, 4);
INSERT INTO orderContains VALUES(502, 5001, 3);
INSERT INTO orderContains VALUES(502, 5006, 1);
INSERT INTO orderContains VALUES(502, 5005, 2);
INSERT INTO orderContains VALUES(502, 5007, 2);
INSERT INTO orderContains VALUES(503, 5001, 1);
INSERT INTO orderContains VALUES(503, 5002, 2);
INSERT INTO orderContains VALUES(503, 5003, 1);
INSERT INTO orderContains VALUES(503, 5004, 4);
INSERT INTO orderContains VALUES(503, 5005, 2);
INSERT INTO orderContains VALUES(503, 5006, 2);
INSERT INTO orderContains VALUES(503, 5007, 4);

INSERT INTO menu VALUES(501, "Burger", 9.99);
INSERT INTO menu VALUES(502, "ChickenSandwich", 7.99);
INSERT INTO menu VALUES(503, "Salad", 4.99);

INSERT INTO takesFrom VALUES(501, 101);
INSERT INTO takesFrom VALUES(501, 102);
INSERT INTO takesFrom VALUES(501, 104);
INSERT INTO takesFrom VALUES(501, 105);
INSERT INTO takesFrom VALUES(502, 102);
INSERT INTO takesFrom VALUES(502, 103);
INSERT INTO takesFrom VALUES(502, 104);
INSERT INTO takesFrom VALUES(502, 105);
INSERT INTO takesFrom VALUES(503, 102);
INSERT INTO takesFrom VALUES(503, 105);

INSERT INTO stock VALUES(101, "Patty", 1000);
INSERT INTO stock VALUES(102, "Lettuce", 1000);
INSERT INTO stock VALUES(103, "Chicken", 1000);
INSERT INTO stock VALUES(104, "Bun", 1000);
INSERT INTO stock VALUES(105, "Tomato", 1000);

SET FOREIGN_KEY_CHECKS = 1;