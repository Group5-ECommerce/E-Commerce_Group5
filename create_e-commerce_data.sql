INSERT INTO role
(`roleType`, `roleId`) VALUES
('ROLE_CUSTOMER', 1);
INSERT INTO role
(`roleType`, `roleId`) VALUES
('ROLE_ADMIN', 2);
INSERT INTO role
(`roleType`, `roleId`) VALUES
('ROLE_DB_OPERATOR', 3);
INSERT INTO role
(`roleType`, `roleId`) VALUES
('ROLE_GUEST', 4);

INSERT INTO `user` (`userId`,`email`,`password`,`username`) VALUES (1,'admin@e-commerce.com','$2a$10$DKoxF/Nv8HXFPPc0bYdKYejxIhBftx7YZEAS6MDksa4ANYgVz8bYO','admin');
INSERT INTO `user` (`userId`,`email`,`password`,`username`) VALUES (2,'customer@e-commerce.com','$2a$10$Qqau.TBQf186FgZU4eA2zeqe1m97nX026nQSezskUa9hZdUCDTNCe','customer');
INSERT INTO `user` (`userId`,`email`,`password`,`username`) VALUES (3,'christian@gmail.com','$2a$10$JiXDvGr7aqPVEfT4/fsIju.xo.2QYZkZVmiFyOl.bCiQOEQNQLJge','christian');
INSERT INTO `user` (`userId`,`email`,`password`,`username`) VALUES (4,'dbadmin@e-commerce.com','$2a$10$l88h38Otm4uDQiiMHGmuBevCbTLB18fcFURnSp.Xy/jj.GryqMTR2','dbadmin');
INSERT INTO `user` (`userId`,`email`,`password`,`username`) VALUES (5,'guest@gmail.com','$2a$10$0tL/SxolmHlv2qFpgzZaHeobvpgI/URfEOc1w3QqzgWf7U50wMviq','guest');

INSERT INTO user_roles
(`userId`, `roleId`) VALUES (1, 2);
INSERT INTO user_roles
(`userId`, `roleId`) VALUES (2, 1);
INSERT INTO user_roles
(`userId`, `roleId`) VALUES (3, 1);
INSERT INTO user_roles
(`userId`, `roleId`) VALUES (4, 3);
INSERT INTO user_roles
(`userId`, `roleId`) VALUES (5, 4);

Insert into product (productId,productImage,productName,productPrice,productStock,storageId)
Values
(1,"https://res.cloudinary.com/gaurav-cloudinary/image/upload/v1662499764/yhxjpckahasrtdzoli9q.jpg","Laptop",379.99,10,1),
(2,"https://res.cloudinary.com/gaurav-cloudinary/image/upload/v1662499786/h3p3llvz1j8tafnuine1.jpg","Cell Phone",199.99,27,1),
(3,"https://res.cloudinary.com/gaurav-cloudinary/image/upload/v1662499741/pvdymik2azc9a4oj8fhp.jpg","Cell Phone Charger",14.99,27,1),
(4,"https://res.cloudinary.com/gaurav-cloudinary/image/upload/v1662499618/qh15m2xu5rstosomgxsm.png","Wireless Earbuds",19.99,100,1),
(9,"https://res.cloudinary.com/gaurav-cloudinary/image/upload/v1662145126/swvwgqlna0yjgam2i7lh.jpg","Computer Mouse",9.99,19,1),
(10,"https://res.cloudinary.com/gaurav-cloudinary/image/upload/v1662498087/ke3jf0qt3ozphejkaab6.jpg","Calculator",14.99,20,1),
(11,"https://res.cloudinary.com/gaurav-cloudinary/image/upload/v1662498222/lnc80wlrv0zecyawwuvj.jpg","Camera",499.99,20,1),
(12,"https://res.cloudinary.com/gaurav-cloudinary/image/upload/v1662498319/ru5e8lsuxr1igfqhzddv.jpg","Television",299.99,12,1),
(13,"https://res.cloudinary.com/gaurav-cloudinary/image/upload/v1662498658/zu6eafvx6oxf11hydrag.jpg","Computer Monitor",199.99,15,1),
(14,"https://res.cloudinary.com/gaurav-cloudinary/image/upload/v1662498738/h0jenmitanxw9caed1th.jpg","Headphones",49.99,30,1),
(15,"https://res.cloudinary.com/gaurav-cloudinary/image/upload/v1662498807/ottoffmteb3r6mrs67jw.jpg","Keyboard",109.99,20,1),
(16,"https://res.cloudinary.com/gaurav-cloudinary/image/upload/v1662498893/blbdwl5uuuyie1yo7gxj.jpg","Hard Drive",99.99,20,1),
(17,"https://res.cloudinary.com/gaurav-cloudinary/image/upload/v1662499021/jprzgnne8l2fxsr8rjm7.jpg","Tablet",99.99,8,1),
(18,"https://res.cloudinary.com/gaurav-cloudinary/image/upload/v1662500006/iewbh8flnbupno6yy4mn.jpg","VR Headset",399.99,10,1);

/* set foreign_key_checks=1;
