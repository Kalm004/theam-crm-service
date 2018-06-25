SET MODE PostgreSQL;

insert into User (name, last_name, email, hashed_password, admin) values ('User1', 'LastName1', 'user1@domain.com', '$2a$10$PDWuLGUHp5FSitTIER7jv.yJPdJHVOEtZ/gn7TqZ02YPN5gal1Giy', 0);
insert into User (name, last_name, email, hashed_password, admin) values ('User2', 'LastName2', 'user2@domain.com', '$2a$10$PDWuLGUHp5FSitTIER7jv.yJPdJHVOEtZ/gn7TqZ02YPN5gal1Giy', 1);

insert into Customer (name, surname, created_by_user_id, modified_by_user_id) values ('Customer1', 'Surname1', 1, null);