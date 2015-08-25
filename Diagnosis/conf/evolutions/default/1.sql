# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table attached_file (
  id                        varchar(255) not null,
  path                      varchar(255),
  file_name                 varchar(255),
  content_type              varchar(255),
  constraint pk_attached_file primary key (id))
;

create table diagnosis (
  id                        varchar(255) not null,
  company_id                varchar(255),
  plan_id                   varchar(255),
  date_created              timestamp,
  date_updated              timestamp,
  excel_file_id             varchar(255),
  constraint uq_diagnosis_excel_file_id unique (excel_file_id),
  constraint uq_diagnosis_1 unique (company_id,plan_id),
  constraint pk_diagnosis primary key (id))
;

create table employee (
  id                        varchar(255) not null,
  company_id                varchar(255) not null,
  name                      varchar(255),
  email                     varchar(255),
  team                      varchar(255),
  constraint uq_employee_company_id unique (company_id),
  constraint pk_employee primary key (id))
;

create table plan (
  id                        varchar(255) not null,
  name                      varchar(255),
  constraint pk_plan primary key (id))
;

create table user (
  id                        varchar(255) not null,
  email                     varchar(255) not null,
  name                      varchar(255),
  auth_token                varchar(255),
  sha_password              varbinary(255),
  constraint uq_user_email unique (email),
  constraint pk_user primary key (id))
;

create sequence attached_file_seq;

create sequence diagnosis_seq;

create sequence employee_seq;

create sequence plan_seq;

create sequence user_seq;

alter table diagnosis add constraint fk_diagnosis_excelFile_1 foreign key (excel_file_id) references attached_file (id) on delete restrict on update restrict;
create index ix_diagnosis_excelFile_1 on diagnosis (excel_file_id);



# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists attached_file;

drop table if exists diagnosis;

drop table if exists employee;

drop table if exists plan;

drop table if exists user;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists attached_file_seq;

drop sequence if exists diagnosis_seq;

drop sequence if exists employee_seq;

drop sequence if exists plan_seq;

drop sequence if exists user_seq;

