# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table curriculo (
  id                        integer not null,
  nome                      varchar(255),
  max_periodos              integer,
  max_creditos_periodo      integer,
  constraint pk_curriculo primary key (id))
;

create table disciplina (
  id                        bigint not null,
  curriculo_id              integer not null,
  nome                      varchar(255),
  creditos                  integer,
  categoria                 varchar(255),
  constraint pk_disciplina primary key (id))
;

create table grade (
  id                        bigint not null,
  nome                      varchar(255),
  constraint pk_grade primary key (id))
;

create table periodo (
  id                        bigint not null,
  grade_id                  bigint not null,
  semestre                  integer,
  constraint pk_periodo primary key (id))
;

create table plano_de_curso (
  id                        bigint not null,
  curriculo_id              integer,
  grade_id                  bigint,
  constraint pk_plano_de_curso primary key (id))
;

create table usuario (
  email                     varchar(255) not null,
  nome                      varchar(255),
  senha                     varchar(255),
  plano_de_curso_id         bigint,
  constraint pk_usuario primary key (email))
;


create table disciplina_requisitos (
  disciplina_id                  bigint not null,
  requisito_id                   bigint not null,
  constraint pk_disciplina_requisitos primary key (disciplina_id, requisito_id))
;

create table periodo_disciplina (
  periodo_id                     bigint not null,
  disciplina_id                  bigint not null,
  constraint pk_periodo_disciplina primary key (periodo_id, disciplina_id))
;
create sequence curriculo_seq;

create sequence disciplina_seq;

create sequence grade_seq;

create sequence periodo_seq;

create sequence plano_de_curso_seq;

create sequence usuario_seq;

alter table disciplina add constraint fk_disciplina_curriculo_1 foreign key (curriculo_id) references curriculo (id) on delete restrict on update restrict;
create index ix_disciplina_curriculo_1 on disciplina (curriculo_id);
alter table periodo add constraint fk_periodo_grade_2 foreign key (grade_id) references grade (id) on delete restrict on update restrict;
create index ix_periodo_grade_2 on periodo (grade_id);
alter table plano_de_curso add constraint fk_plano_de_curso_curriculo_3 foreign key (curriculo_id) references curriculo (id) on delete restrict on update restrict;
create index ix_plano_de_curso_curriculo_3 on plano_de_curso (curriculo_id);
alter table plano_de_curso add constraint fk_plano_de_curso_grade_4 foreign key (grade_id) references grade (id) on delete restrict on update restrict;
create index ix_plano_de_curso_grade_4 on plano_de_curso (grade_id);
alter table usuario add constraint fk_usuario_planoDeCurso_5 foreign key (plano_de_curso_id) references plano_de_curso (id) on delete restrict on update restrict;
create index ix_usuario_planoDeCurso_5 on usuario (plano_de_curso_id);



alter table disciplina_requisitos add constraint fk_disciplina_requisitos_disc_01 foreign key (disciplina_id) references disciplina (id) on delete restrict on update restrict;

alter table disciplina_requisitos add constraint fk_disciplina_requisitos_disc_02 foreign key (requisito_id) references disciplina (id) on delete restrict on update restrict;

alter table periodo_disciplina add constraint fk_periodo_disciplina_periodo_01 foreign key (periodo_id) references periodo (id) on delete restrict on update restrict;

alter table periodo_disciplina add constraint fk_periodo_disciplina_discipl_02 foreign key (disciplina_id) references disciplina (id) on delete restrict on update restrict;

# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists curriculo;

drop table if exists disciplina;

drop table if exists disciplina_requisitos;

drop table if exists grade;

drop table if exists periodo;

drop table if exists periodo_disciplina;

drop table if exists plano_de_curso;

drop table if exists usuario;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists curriculo_seq;

drop sequence if exists disciplina_seq;

drop sequence if exists grade_seq;

drop sequence if exists periodo_seq;

drop sequence if exists plano_de_curso_seq;

drop sequence if exists usuario_seq;

