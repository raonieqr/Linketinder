package model.entities

class Company extends Person {
    int id;
    String cnpj
    String description
    String state
    String country
    int cep
    ArrayList<MatchVacancy> matchVacancies = new ArrayList<>();

    Company(int id, String name, String email, String cnpj, String country,
            String description, String state,
            int cep) {
        super(name, email)
        this.id = id
        this.cnpj = cnpj
        this.country = country
        this.description = description
        this.state = state
        this.cep = cep
    }

    int getId() {
        return id
    }

    void setId(int id) {
        this.id = id
    }

    String getCnpj() {
        return cnpj
    }

    void setCnpj(String cnpj) {
        this.cnpj = cnpj
    }

    String getPais() {
        return country
    }

    void setPais(String country) {
        this.country = country
    }

    String getDescription() {
        return description
    }

    void setDescription(String description) {
        this.description = description
    }

    int getCep() {
        return cep
    }

    void setCep(int cep) {
        this.cep = cep
    }

    Vacancy getVacancy() {
        return vacancy
    }

    void setVacancy(Vacancy vacancy) {
        this.vacancy = vacancy
    }

    ArrayList<MatchVacancy> getMatchVacancies() {
        return matchVacancies
    }

    void setMatchVacancies(ArrayList<MatchVacancy> matchVacancies) {
        this.matchVacancies = matchVacancies
    }

    @Override
    void showInfo() {
        println("name: $name, email: $email, cnpj: $cnpj, pais: $country, " +
                "estado: $state, cep: $cep, descrição: $description")
    }
}
