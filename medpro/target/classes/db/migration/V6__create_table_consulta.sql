CREATE TABLE consultas (
    id BIGINT NOT NULL AUTO_INCREMENT,
    paciente_id BIGINT NOT NULL,
    medico_id BIGINT NOT NULL,
    dataHora DATETIME NOT NULL,
    PRIMARY KEY (id)
);

ALTER TABLE consultas
ADD CONSTRAINT fk_consulta_paciente
FOREIGN KEY (paciente_id)
REFERENCES pacientes(id);

ALTER TABLE consultas
ADD CONSTRAINT fk_consulta_medico
FOREIGN KEY (medico_id)
REFERENCES medicos(id);
