package linketinder.model.entities

class EntityFactory {

  static Candidate createCandidate(int idCandidate, String name, String email,
                                          ArrayList<String> skills, int age,
                                   String state, String description, String cpf,
                                   int cep) {
    return new Candidate(idCandidate, name, email, skills, age, state, description, cpf, cep);
  }

  static Company createCompany(int idCompany, String name, String email,
                                      String cnpj, String country,
                                      String description, String state, int cep) {
    return new Company(idCompany, name, email, cnpj, country, description, state, cep);
  }
}
