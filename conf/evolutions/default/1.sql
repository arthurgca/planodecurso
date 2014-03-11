# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table disciplina (
  id                        integer not null,
  nome                      varchar(255),
  creditos                  integer,
  constraint pk_disciplina primary key (id))
;

create table plano_de_curso (
  id                        bigint not null,
  constraint pk_plano_de_curso primary key (id))
;


create table disciplina_requisitos (
  disciplina_id                  integer not null,
  requisito_id                   integer not null,
  constraint pk_disciplina_requisitos primary key (disciplina_id, requisito_id))
;
create sequence disciplina_seq;

create sequence plano_de_curso_seq;




alter table disciplina_requisitos add constraint fk_disciplina_requisitos_disc_01 foreign key (disciplina_id) references disciplina (id) on delete restrict on update restrict;

alter table disciplina_requisitos add constraint fk_disciplina_requisitos_disc_02 foreign key (requisito_id) references disciplina (id) on delete restrict on update restrict;

# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists disciplina;

drop table if exists disciplina_requisitos;

drop table if exists plano_de_curso;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists disciplina_seq;

drop sequence if exists plano_de_curso_seq;

