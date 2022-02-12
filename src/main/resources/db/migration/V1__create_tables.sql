-- Cria tabela paciente com Flyway
create table tb_paciente (
                             id serial not null,
                             data_nascimento date,
                             nome varchar(60) not null,
                             cpf varchar(14),
                             primary key (id)
                         );

