SET MODE PostgreSQL;

insert into User (name, last_name, email, hashed_password, admin) values ('Admin', 'CRM', 'admin@crm.com', '$2a$10$PDWuLGUHp5FSitTIER7jv.yJPdJHVOEtZ/gn7TqZ02YPN5gal1Giy', 1);