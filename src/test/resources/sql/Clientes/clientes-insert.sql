insert into USUARIOS (id, username, password, role) values (100, 'ana@email.com', '123456', 'ROLE_ADMIN');
insert into USUARIOS (id, username, password, role) values (101, 'bia@email.com', '123456', 'ROLE_CLIENTE');
insert into USUARIOS (id, username, password, role) values (102, 'bob@email.com', '123456', 'ROLE_CLIENTE');

insert into clientes (id,nome ,cpf,usuario_id) values (10, 'Bianca Silva','58828754044', 101);
insert into clientes (id,nome ,cpf,usuario_id) values (28, 'Roberto Gomes','42495151094', 102);
