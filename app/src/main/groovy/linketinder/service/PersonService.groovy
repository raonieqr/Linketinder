package linketinder.service

import linketinder.model.entities.Candidate
import linketinder.model.entities.Company

interface PersonService {

    void showInfo()

    Candidate createCandidateFactory(int idCandidate, String name,
                                     String email, ArrayList<String> skills,
                                     int age, String state, String description,
                                     String cpf, int cep)

    Company createCompanyFactory(int idCompany, String name, String email,
                          String cnpj, String country, String description,
                          String state, int cep)

}
