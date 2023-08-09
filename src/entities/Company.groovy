package entities

class Company extends Person {
    String cnpj
    String pais
    String description
    String state
    String country
    ArrayList<String> skills
    int cep

    Company(String name, String email, String cnpj, String country,
            String description, String state, ArrayList<String> skills =
                    ["Python", "Java", "Spring Framework", "Angular", "C"],
            int cep) {
        super(name, email)
        this.cnpj = cnpj
        this.country = country
        this.description = description
        this.state = state
        this.skills = skills
        this.cep = cep
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

    @Override
    void showInfo() {
        println("name: $name, email: $email, cnpj: $cnpj, pais: $country, " +
                "estado: $state, cep: $cep, descrição: $description, " +
                "competências: $skills")
    }
}
