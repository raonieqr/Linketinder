-- INSERT ROLE 

INSERT INTO roles (name, description, id_company, date) VALUES ('Desenvolvedor Java', 'Desenvolver sistemas web', 1, CURRENT_DATE)
-- INSERT INTO roles (name, description, id_company, date) VALUES ('Desenvolvedor Groovy', 'Desenvolver sistemas', 2, CURRENT_DATE)
-- INSERT INTO roles (name, description, id_company, date) VALUES ('Desenvolvedor Frontend', 'Desenvolver sites', 3, CURRENT_DATE)


-- INSERT SKILL OF ROLE

-- INSERT INTO roles_skills (id_role, id_skill) VALUES (1, 4)
-- INSERT INTO roles_skills (id_role, id_skill) VALUES (2, 3)
-- INSERT INTO roles_skills (id_role, id_skill) VALUES (3, 6)
-- INSERT INTO roles_skills (id_role, id_skill) VALUES (3, 9)
-- INSERT INTO roles_skills (id_role, id_skill) VALUES (3, 10)


-- INSERT ID_CANDIDATE AND ID_ROLE IF HE/SHE LIKED THE VACANCY

-- INSERT INTO role_matching (id_candidate, id_role) VALUES (1, 1)
-- INSERT INTO role_matching (id_candidate, id_role) VALUES (3, 2)
-- INSERT INTO role_matching (id_candidate, id_role) VALUES (2, 3)


-- UPDATE COMPANYMATCHED IF COMPANY LIKED CANDIDATE

/*UPDATE role_matching 
SET companymatched = TRUE 
WHERE id_role = 1*/

/*UPDATE role_matching 
SET companymatched = TRUE 
WHERE id_role = 2*/
