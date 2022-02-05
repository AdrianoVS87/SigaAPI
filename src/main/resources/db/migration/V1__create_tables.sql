create table tb_paciente (
                             id serial not null,
                             data_nascimento timestamp,
                             nome varchar(60) not null,
                             primary key (id)
                         );

insert
into
    tb_paciente
(data_nascimento, nome)
values
    ('1987-04-13 12:00:43.379', 'Adriano Viera dos Santos');