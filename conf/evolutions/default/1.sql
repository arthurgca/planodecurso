# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table disciplina (
  id                        integer not null,
  nome                      varchar(255),
  creditos                  integer,
  constraint pk_disciplina primary key (id))
;

create table periodo (
  id                        bigint not null,
  semestre                  integer,
  plano_de_curso_id         bigint,
  constraint pk_periodo primary key (id))
;

create table plano_de_curso (
  id                        bigint not null,
  constraint pk_plano_de_curso primary key (id))
;

create table usuario (
  email                     varchar(255) not null,
  nome                      varchar(255),
  senha                     varchar(255),
  constraint pk_usuario primary key (email))
;


create table disciplina_requisitos (
  disciplina_id                  integer not null,
  requisito_id                   integer not null,
  constraint pk_disciplina_requisitos primary key (disciplina_id, requisito_id))
;

create table periodo_disciplina (
  periodo_id                     bigint not null,
  disciplina_id                  integer not null,
  constraint pk_periodo_disciplina primary key (periodo_id, disciplina_id))
;
create sequence disciplina_seq;

create sequence periodo_seq;

create sequence plano_de_curso_seq;

create sequence usuario_seq;

alter table periodo add constraint fk_periodo_planoDeCurso_1 foreign key (plano_de_curso_id) references plano_de_curso (id) on delete restrict on update restrict;
create index ix_periodo_planoDeCurso_1 on periodo (plano_de_curso_id);



alter table disciplina_requisitos add constraint fk_disciplina_requisitos_disc_01 foreign key (disciplina_id) references disciplina (id) on delete restrict on update restrict;

alter table disciplina_requisitos add constraint fk_disciplina_requisitos_disc_02 foreign key (requisito_id) references disciplina (id) on delete restrict on update restrict;

alter table periodo_disciplina add constraint fk_periodo_disciplina_periodo_01 foreign key (periodo_id) references periodo (id) on delete restrict on update restrict;

alter table periodo_disciplina add constraint fk_periodo_disciplina_discipl_02 foreign key (disciplina_id) references disciplina (id) on delete restrict on update restrict;

# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists disciplina;

drop table if exists disciplina_requisitos;

drop table if exists periodo;

drop table if exists periodo_disciplina;

drop table if exists plano_de_curso;

drop table if exists usuario;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists disciplina_seq;

drop sequence if exists periodo_seq;

drop sequence if exists plano_de_curso_seq;

drop sequence if exists usuario_seq;

