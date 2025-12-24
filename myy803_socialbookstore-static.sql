CREATE DATABASE  IF NOT EXISTS `myy803_socialbookstore`;
USE `myy803_socialbookstore`;

create table book_authors (author_id integer not null auto_increment, name varchar(255), primary key (author_id)) engine=InnoDB;
create table book_categories (category_id integer not null auto_increment, name varchar(255), primary key (category_id)) engine=InnoDB;
create table books (category_id integer, offer_id integer not null auto_increment, profile_username varchar(255), title varchar(255), primary key (offer_id)) engine=InnoDB;
create table books_authors (book_author_id integer not null, book_offer_id integer not null) engine=InnoDB;
create table books_requesting_users (book_offer_id integer not null, requesting_user_id varchar(255) not null) engine=InnoDB;
create table profiles_authors (book_author_id integer not null, user_profile_id varchar(255) not null, primary key (book_author_id, user_profile_id)) engine=InnoDB;
create table profiles_categories (book_category_id integer not null, user_profile_id varchar(255) not null, primary key (book_category_id, user_profile_id)) engine=InnoDB;
create table user_profiles (age integer, full_name varchar(255), username varchar(255) not null, primary key (username)) engine=InnoDB;
create table users (password varchar(255), username varchar(255) not null, role enum ('USER'), primary key (username)) engine=InnoDB;
alter table books add constraint FKf65lsitwn7k54dkh1jxyoc825 foreign key (category_id) references book_categories (category_id);
alter table books add constraint FKr3msdjh9c6cmbgopqf7702d6 foreign key (profile_username) references user_profiles (username);
alter table books_authors add constraint FK6401mq33s1jy3c2f6cp65t47f foreign key (book_author_id) references book_authors (author_id);
alter table books_authors add constraint FKfmdjdk7yfmpe0shs5arqyoh32 foreign key (book_offer_id) references books (offer_id);
alter table books_requesting_users add constraint FKg4n7mnkyvsjnrahcl683e766f foreign key (requesting_user_id) references user_profiles (username);
alter table books_requesting_users add constraint FK86bxs672vg7bxsg8i8tyecpru foreign key (book_offer_id) references books (offer_id);
alter table profiles_authors add constraint FKlhcgyx3yxjp0fuce2gl5cytpp foreign key (book_author_id) references book_authors (author_id);
alter table profiles_authors add constraint FK8ko44yivpk7eu5knnsevhs2dx foreign key (user_profile_id) references user_profiles (username);
alter table profiles_categories add constraint FKs2tpqi711ketvbnb45chnhskr foreign key (book_category_id) references book_categories (category_id);
alter table profiles_categories add constraint FK8guaoptqillahqd78mxlytum0 foreign key (user_profile_id) references user_profiles (username);

create table notifications (notificationid integer not null auto_increment,content varchar(255) not null ,profile_notification varchar(255),primary key (notificationid)) engine=InnoDB;
alter table notifications add constraint FK_profile_notification foreign key (profile_notification) references user_profiles (username);
ALTER TABLE notifications add COLUMN time TIMESTAMP;

insert into book_categories (name) values ('Fantasy');
insert into book_categories (name) values ('Mystery');
insert into book_categories (name) values ('History');
insert into book_categories (name) values ('Literature');
insert into book_categories (name) values ('Other');
ALTER TABLE books AUTO_INCREMENT = 1;



