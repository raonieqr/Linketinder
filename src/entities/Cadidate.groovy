package entities

class Candidate extends Person {
    ArrayList<String> skills
    String email
    int age
    String state
    String description
    String cpf
    int cep

    Candidate(String name, String email, ArrayList<String> skills =
            ["Python", "Java", "Spring Framework", "Angular", "C"],
              int age, String state, String description, String cpf, int cep) {
        super(name, email)
        this.skills = skills
        this.age = age
        this.state = state
        this.description = description
        this.cpf = cpf
        this.cep = cep
    }

    ArrayList<String> getSkills() {
        return skills
    }

    int getAge() {
        return age
    }

    void setAge(int age) {
        this.age = age
    }

    String getState() {
        return state
    }

    void setState(String state) {
        this.state = state
    }

    String getDescription() {
        return description
    }

    void setDescription(String description) {
        this.description = description
    }

    String getCpf() {
        return cpf
    }

    void setCpf(String cpf) {
        this.cpf = cpf
    }

    int getCep() {
        return cep
    }

    void setCep(int cep) {
        this.cep = cep
    }

    @Override
    void showInfo() {
        println("name: $name, email: $super.email, cpf: $cpf, " +
                "estado: $state, cep: $cep, descrição: $description, " +
                "competências: $skills)")
    }
}