create database ms;

use ms;

create table user_base_info (
	t_user_id bigint auto_increment primary key,
    t_email varchar(50) not null,
    t_password varchar(50) not null,
    t_thumb varchar(50) not null,
    t_position int(1) not null,
    t_age int(3),
    t_sex varchar(10),
    t_bir timestamp,
    t_constellation varchar(25),
    t_phone varchar(50)
) default char set utf8;

create table teacher_subject_info (
    t_user_id bigint not null,
    t_subject varchar(100) not null
) default char set utf8;

alter table teacher_subject_info add constraint primary key (t_user_id, t_subject);

create table student_score_info (
    t_user_id bigint not null,
    t_subject varchar(100) not null,
    t_score int(5),
    t_teacher_user_id bigint not null
) default char set utf8;

alter table student_score_info add constraint primary key (t_user_id, t_subject);