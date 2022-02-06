-- Cria tabela paciente com Flyway
create table tb_paciente (
                             id serial not null,
                             data_nascimento timestamp,
                             nome varchar(60) not null,
                             primary key (id)
                         );

