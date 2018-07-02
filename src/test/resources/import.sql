SET MODE PostgreSQL;

insert into User (name, last_name, email, hashed_password, admin, deleted) values ('User1', 'LastName1', 'user1@domain.com', '$2a$10$PDWuLGUHp5FSitTIER7jv.yJPdJHVOEtZ/gn7TqZ02YPN5gal1Giy', 0, false);
insert into User (name, last_name, email, hashed_password, admin, deleted) values ('User2', 'LastName2', 'user2@domain.com', '$2a$10$PDWuLGUHp5FSitTIER7jv.yJPdJHVOEtZ/gn7TqZ02YPN5gal1Giy', 1, false);
insert into User (name, last_name, email, hashed_password, admin, deleted) values ('DeletedUser', 'LastDeleted', 'deleted@domain.com', '$2a$10$PDWuLGUHp5FSitTIER7jv.yJPdJHVOEtZ/gn7TqZ02YPN5gal1Giy', 1, true);
insert into User (name, last_name, email, hashed_password, admin, deleted) values ('User4', 'LastName4', 'user4@domain.com', '$2a$10$PDWuLGUHp5FSitTIER7jv.yJPdJHVOEtZ/gn7TqZ02YPN5gal1Giy', 1, false);

insert into Customer (name, surname, created_by_user_id, modified_by_user_id, photo_filename) values ('Customer1', 'Surname1', 1, null, 'customer1.jpg');
insert into Customer (name, surname, created_by_user_id, modified_by_user_id) values ('Customer2', 'Surname2', 3, null);