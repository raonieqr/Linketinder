CREATE DATABASE linketinder;

-- CREATE TABLES FOR LINKETINDER

CREATE TABLE candidates (
    id SERIAL PRIMARY KEY,
    cep integer NOT NULL,
    cpf varchar(11) NOT NULL,
    "state" varchar(50) NOT NULL,
    age integer NOT NULL,
    description varchar(100) NOT NULL,
    name varchar(100) NOT NULL,
    email varchar(100) NOT NULL,
    password varchar(20) NOT NULL
);

CREATE TABLE companies (
    id SERIAL PRIMARY KEY,
    name varchar(100) NOT NULL,
    email varchar(100) NOT NULL,
    description varchar(100) NOT NULL,
    "state" varchar(50) NOT NULL,
    country char(3) NOT NULL,
    cep integer NOT NULL,
    cnpj varchar(14) NOT NULL,
    password varchar(20) NOT NULL
);

CREATE TABLE roles (
    id SERIAL PRIMARY KEY,
    name varchar(100) NOT NULL,
    description varchar(100) NOT NULL,
    id_company integer REFERENCES companies(id),
    "date" date NOT NULL
);

CREATE TABLE roles_skills (
    id_role integer REFERENCES roles(id),
    id_skill integer REFERENCES skills(id)
);

CREATE TABLE skills (
    id SERIAL PRIMARY KEY,
    description varchar(50) NOT NULL
);

CREATE TABLE candidate_skills (
    id_candidate integer REFERENCES candidates(id),
    id_skill integer REFERENCES skills(id)
);

CREATE TABLE role_matching (
    id SERIAL PRIMARY KEY,
    companymatched boolean DEFAULT FALSE,
    id_candidate integer REFERENCES candidates(id),
    id_role integer REFERENCES roles(id)
);



-- INSERE SKILLS

INSERT INTO skills (description) VALUES
    ('C'),
    ('C++'),
    ('Groovy'),
    ('Java'),
    ('Python'),
    ('JavaScript'),
    ('Angular'),
    ('React'),
    ('HTML'),
    ('CSS'),
    ('SQL'),
    ('Ruby'),
    ('PHP'),
    ('Swift'),
    ('Kotlin'),
    ('C#'),
    ('Unity');




-- INSERE OS CANDIDATOS

INSERT INTO candidates ("name", email, age, "state", description, cep, cpf, "password")
VALUES
    ('Raoni', 'raoniestevan@gmail.com', 25, 'RJ', 'Desenvolvedor', 22785055, 45678912345, 'batatinha'),
    ('Alice', 'alice@example.com', 28, 'SP', 'Web Developer', 12345678, 45678912345, 'batatinha'),
    ('Bob', 'bob@example.com', 30, 'MG', 'Full Stack Developer', 54321098, 45678912345, 'batatinha'),
    ('Eva', 'eva@example.com', 22, 'RS', 'Mobile Developer', 98765432, 45678912345, 'batatinha'),
    ('Carlos', 'carlos@example.com', 26, 'BA', 'Game Developer', 11223344, 45678912345, 'batatinha');
    
    

-- INSERE SKILLS DOS CANDIDATOS

INSERT INTO candidate_skills(id_candidate, id_skill) VALUES
(1, 1),
(1, 2),
(1, 7),
(1, 8),
(2, 8), 
(2, 7), 
(2, 12),
(2, 13),
(3, 7),
(3, 9),
(3, 14),
(3, 11),
(4, 15),
(4, 16),
(4, 17),
(4, 11),
(5, 8),
(5, 10),
(5, 3);




-- INSERE AS EMPRESAS

INSERT INTO companies ("name", email, cnpj, country, description, "state", cep, "password")
VALUES
    ('ZG', 'zg@zg.com.br', '12345678945567', 'BR', 'Empresa', 'GO', 70806000, 'batatinha'),
    ('TechCorp', 'info@techcorp.com', '98765432101234', 'USA', 'Empresa', 'CA', 90210, 'batatinha'),
    ('InnovateTech', 'contact@innovatetech.com', '55555555556789', 'CA', 'Empresa', 'ON', 54321098, 'batatinha'),
    ('GameWorld', 'info@gameworld.com', '11111111234567', 'BR', 'Empresa', 'SP', 12345678, 'batatinha'),
    ('SoftSys', 'contact@softsys.com', '22222222345678', 'USA', 'Empresa', 'NY', 10001, 'batatinha');

    
    
-- VISUALIZAR EXEMPLO DE CANDIDATOS E SKILL
    
SELECT candidates.id, candidates."name", candidates.age, candidates."state", candidates.email, skills.description AS skill
FROM candidates
JOIN candidate_skills ON candidates.id = candidate_skills.id_candidate
JOIN skills ON candidate_skills.id_skill = skills.id


    
-- DELETA AS TABELAS

/*DROP TABLE candidates, candidate_skills, companies, role_matching, roles, skills, roles_skills*/

