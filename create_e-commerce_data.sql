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

INSERT INTO `product` (`productId`,`productImage`,`productName`,`productPrice`,`productStock`,`storageId`) VALUES (1,'https://androidpctv.com/wp-content/uploads/2021/04/ipason-maxbook-p1-n01.jpg','IPASON MaxBook P1 FullHD Laptop',379.99,10,'1');
INSERT INTO `product` (`productId`,`productImage`,`productName`,`productPrice`,`productStock`,`storageId`) VALUES (2,'https://live.staticflickr.com/65535/48089718446_faa785c270_b.jpg','Tide Detergent',7.99,25,'1');
INSERT INTO `product` (`productId`,`productImage`,`productName`,`productPrice`,`productStock`,`storageId`) VALUES (3,'https://live.staticflickr.com/7629/16606983830_94178fc149_b.jpg','Red Mug',5.99,35,'1');
INSERT INTO `product` (`productId`,`productImage`,`productName`,`productPrice`,`productStock`,`storageId`) VALUES (4,'https://lh6.googleusercontent.com/-s16-yAJndVA/TYoqFJKb5aI/AAAAAAAAAOs/fXtsm-CNBZI/s1600/Oats.jpg','Quaker Oats',8.99,100,'1');

/* set foreign_key_checks=1;
