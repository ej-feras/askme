insert IGNORE into `askmedb`.user (id,create_date,email,enabled,first_name, last_name, modify_date, password,role,username) values(1,'2021-03-17 17:22:29.637000','admin@gmail.com', 1 ,'adminFirstName','adminLastName','2021-03-17 17:22:29.637000','$2a$10$0IRR38Ff/texOTJeGCWmqO6mXFoM2XoEGq0RJEyI62pu4nsnc8SVS','ROLE_ADMIN','admin');
insert IGNORE into `askmedb`.userseq value (2);


insert IGNORE into `askmedb`.test value(1,'2021-03-17 17:39:40.809000','2021-03-17 19:50:15.582000','Math 1',1,15,1);
insert IGNORE into `askmedb`.testseq value (2);


insert IGNORE into `askmedb`.question values (1,'2021-03-17 19:01:50.256000','2021-03-17 19:01:51.254000',3,'Which two of the following numbers have a product that is between –1 and 0?',1),
(2,'2021-03-17 19:34:20.274000','2021-03-17 19:34:20.625000',3.5,' A clock strikes once at 1 o’clock, twice at 2 o’clock, thrice at 3 o’clock and so on. How many times will it strike in 24 hours?',1),
(3,'2021-03-17 19:06:42.142000','2021-03-17 19:06:42.349000',3,'Which of the following could be the units digit of 57 to the power n where n is a positive integer?',1),
(4,'2021-03-17 19:31:28.173000','2021-03-17 19:31:28.374000',2.5,'The locus of a point in a plane equidistant from a fixed point is known as:',1),
(5,'2021-03-17 19:02:34.246000','2021-03-17 19:02:34.418000',3,'Which of the following integers are multiples of both 2 and 3?',1);
insert IGNORE into `askmedb`.questionseq  value(6);

insert IGNORE  into `askmedb`.answer values
(1,1,'2021-03-17 19:01:50.256000','2021-03-17 19:01:51.254000',0,'-10',1),
(2,0,'2021-03-17 19:01:50.256000','2021-03-17 19:01:51.254000',0,'-20',1),
(3,1,'2021-03-17 19:01:50.256000','2021-03-17 19:01:51.254000',0,'2^(-4)',1),
(4,0,'2021-03-17 19:01:50.256000','2021-03-17 19:01:51.254000',0,'-2',1),

(5,0,'2021-03-17 19:01:50.256005','2021-03-17 19:01:51.254005',0,'78',2),
(6,0,'2021-03-17 19:01:50.256006','2021-03-17 19:01:51.254006',0,'136',2),
(7,1,'2021-03-17 19:01:50.256007','2021-03-17 19:01:51.254007',0,'156',2),
(8,0,'2021-03-17 19:01:50.256008','2021-03-17 19:01:51.254008',0,'196',2),

(9,1,'2021-03-17 19:01:50.256009','2021-03-17 19:01:51.254009',0,'1',3),
(10,1,'2021-03-17 19:01:50.256010','2021-03-17 19:01:51.254010',0,'3',3),
(11,0,'2021-03-17 19:01:50.256011','2021-03-17 19:01:51.254011',0,'5',3),
(12,1,'2021-03-17 19:01:50.256012','2021-03-17 19:01:51.254012',0,'7',3),


(13,1,'2021-03-17 19:01:50.256013','2021-03-17 19:01:51.2540013',0,'circle',4),
(14,0,'2021-03-17 19:01:50.256014','2021-03-17 19:01:51.254014',0,'straight line',4),
(15,0,'2021-03-17 19:01:50.256015','2021-03-17 19:01:51.254015',0,'parabola',4),
(16,0,'2021-03-17 19:01:50.256016','2021-03-17 19:01:51.254016',0,'hyperbola',4),

(17,0,'2021-03-17 19:01:50.256005','2021-03-17 19:01:51.254005',0,'9',5),
(18,1,'2021-03-17 19:01:50.256006','2021-03-17 19:01:51.254006',0,'12',5),
(19,1,'2021-03-17 19:01:50.256007','2021-03-17 19:01:51.254007',0,'18',5),
(20,1,'2021-03-17 19:01:50.256008','2021-03-17 19:01:51.254008',0,'36',5);

insert IGNORE into `askmedb`.answerseq value(21);

Insert IGNORE into `askmedb`.resultseq value (1);
Insert IGNORE into `askmedb`.tokenseq value (1);