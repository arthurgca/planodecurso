# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table alocacao (
  id                        bigint not null,
  plano_de_curso_id         bigint not null,
  semestre                  integer,
  disciplina_id             integer,
  constraint pk_alocacao primary key (id))
;

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
create sequence alocacao_seq;

create sequence disciplina_seq;

create sequence plano_de_curso_seq;

alter table alocacao add constraint fk_alocacao_plano_de_curso_1 foreign key (plano_de_curso_id) references plano_de_curso (id) on delete restrict on update restrict;
create index ix_alocacao_plano_de_curso_1 on alocacao (plano_de_curso_id);
alter table alocacao add constraint fk_alocacao_disciplina_2 foreign key (disciplina_id) references disciplina (id) on delete restrict on update restrict;
create index ix_alocacao_disciplina_2 on alocacao (disciplina_id);



alter table disciplina_requisitos add constraint fk_disciplina_requisitos_disc_01 foreign key (disciplina_id) references disciplina (id) on delete restrict on update restrict;

alter table disciplina_requisitos add constraint fk_disciplina_requisitos_disc_02 foreign key (requisito_id) references disciplina (id) on delete restrict on update restrict;

# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists alocacao;

drop table if exists disciplina;

drop table if exists disciplina_requisitos;

drop table if exists plano_de_curso;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists alocacao_seq;

drop sequence if exists disciplina_seq;

drop sequence if exists plano_de_curso_seq;

