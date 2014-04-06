# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table curriculo (
  id                        integer not null,
  max_periodos              integer,
  min_creditos_periodo      integer,
  max_creditos_periodo      integer,
  min_creditos_obrigatorias integer,
  min_disciplinas_obrigatorias integer,
  min_creditos_optativas    integer,
  min_disciplinas_optativas integer,
  min_creditos_complementares integer,
  min_disciplinas_complementares integer,
  nome                      varchar(255),
  constraint pk_curriculo primary key (id))
;

create table disciplina (
  id                        bigint not null,
  curriculo_id              integer not null,
  nome                      varchar(255),
  creditos                  integer,
  categoria                 varchar(255),
  tipo                      integer,
  constraint ck_disciplina_tipo check (tipo in (0,1,2)),
  constraint pk_disciplina primary key (id))
;

create table estudante (
  email                     varchar(255) not null,
  nome                      varchar(255),
  senha                     varchar(255),
  plano_id                  bigint,
  constraint pk_estudante primary key (email))
;

create table grade (
  id                        bigint not null,
  nome                      varchar(255),
  curriculo_id              integer,
  constraint pk_grade primary key (id))
;

create table periodo (
  id                        bigint not null,
  grade_id                  bigint not null,
  semestre                  integer,
  politica_de_creditos_id   integer,
  constraint pk_periodo primary key (id))
;

create table plano (
  id                        bigint not null,
  curriculo_id              integer,
  grade_id                  bigint,
  periodo_atual_id          bigint,
  constraint pk_plano primary key (id))
;

create table politica_de_creditos (
  dtype                     varchar(10) not null,
  id                        integer not null,
  min_creditos              integer,
  max_creditos              integer,
  constraint pk_politica_de_creditos primary key (id))
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

create sequence estudante_seq;

create sequence grade_seq;

create sequence periodo_seq;

create sequence plano_seq;

create sequence politica_de_creditos_seq;

alter table disciplina add constraint fk_disciplina_curriculo_1 foreign key (curriculo_id) references curriculo (id) on delete restrict on update restrict;
create index ix_disciplina_curriculo_1 on disciplina (curriculo_id);
alter table estudante add constraint fk_estudante_plano_2 foreign key (plano_id) references plano (id) on delete restrict on update restrict;
create index ix_estudante_plano_2 on estudante (plano_id);
alter table grade add constraint fk_grade_curriculo_3 foreign key (curriculo_id) references curriculo (id) on delete restrict on update restrict;
create index ix_grade_curriculo_3 on grade (curriculo_id);
alter table periodo add constraint fk_periodo_grade_4 foreign key (grade_id) references grade (id) on delete restrict on update restrict;
create index ix_periodo_grade_4 on periodo (grade_id);
alter table periodo add constraint fk_periodo_politicaDeCreditos_5 foreign key (politica_de_creditos_id) references politica_de_creditos (id) on delete restrict on update restrict;
create index ix_periodo_politicaDeCreditos_5 on periodo (politica_de_creditos_id);
alter table plano add constraint fk_plano_curriculo_6 foreign key (curriculo_id) references curriculo (id) on delete restrict on update restrict;
create index ix_plano_curriculo_6 on plano (curriculo_id);
alter table plano add constraint fk_plano_grade_7 foreign key (grade_id) references grade (id) on delete restrict on update restrict;
create index ix_plano_grade_7 on plano (grade_id);
alter table plano add constraint fk_plano_periodoAtual_8 foreign key (periodo_atual_id) references periodo (id) on delete restrict on update restrict;
create index ix_plano_periodoAtual_8 on plano (periodo_atual_id);



alter table disciplina_requisitos add constraint fk_disciplina_requisitos_disc_01 foreign key (disciplina_id) references disciplina (id) on delete restrict on update restrict;

alter table disciplina_requisitos add constraint fk_disciplina_requisitos_disc_02 foreign key (requisito_id) references disciplina (id) on delete restrict on update restrict;

alter table periodo_disciplina add constraint fk_periodo_disciplina_periodo_01 foreign key (periodo_id) references periodo (id) on delete restrict on update restrict;

alter table periodo_disciplina add constraint fk_periodo_disciplina_discipl_02 foreign key (disciplina_id) references disciplina (id) on delete restrict on update restrict;

# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists curriculo;

drop table if exists disciplina;

drop table if exists disciplina_requisitos;

drop table if exists estudante;

drop table if exists grade;

drop table if exists periodo;

drop table if exists periodo_disciplina;

drop table if exists plano;

drop table if exists politica_de_creditos;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists curriculo_seq;

drop sequence if exists disciplina_seq;

drop sequence if exists estudante_seq;

drop sequence if exists grade_seq;

drop sequence if exists periodo_seq;

drop sequence if exists plano_seq;

drop sequence if exists politica_de_creditos_seq;

