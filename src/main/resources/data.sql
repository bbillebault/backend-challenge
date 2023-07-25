-- Create roles
insert into roles(name) values('USER');
insert into roles(name) values('ADMIN');

-- Create users
insert into users(name, salt, password) values('user', '', '$2a$12$p0tEtzLhuzwkJI87yQPI9.m4oq4QXHZZj8B6agK.zmzwqpUr17gnS');
insert into users(name, salt, password) values('admin', '', '$2a$12$p0tEtzLhuzwkJI87yQPI9.m4oq4QXHZZj8B6agK.zmzwqpUr17gnS');

-- Assign roles to users
insert into user_has_role(user_id, role_id) values(1, 1);
insert into user_has_role(user_id, role_id) values(2, 1);
insert into user_has_role(user_id, role_id) values(2, 2);

-- Create boats
insert into boats(name, description) values('Raft', 'The raft is the most basic type of boat. It is made out of wood logs attached together with a rope.');
insert into boats(name, description) values('Titanic', 'The Titanic is a huge boat that has the reputation of being unsinkable.');