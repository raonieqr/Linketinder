package linketinder.model.entities

class Candidate extends Person {
    int id;
    //  TODO: criar a classe de skill futuramente
    ArrayList<String> skills
    int age
    String state
    String description
    String cpf
    int cep
    ArrayList<MatchVacancy> matchVacancies = new ArrayList<>();

    Candidate(int id, String name, String email, ArrayList<String> skills =
            ["Python", "Java", "Spring Framework", "Angular", "C"],
              int age, String state, String description, String cpf, int cep) {
        super(name, email)
        this.id = id
        this.skills = skills
        this.age = age
        this.state = state
        this.description = description
        this.cpf = cpf
        this.cep = cep
    }

    int getId() {
        return id
    }

    void setId(int id) {
        this.id = id
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

    ArrayList<MatchVacancy> getMatchVacancies() {
        return matchVacancies
    }

    @Override
    void showInfo() {
        def skill = skills.join(", ")
        println("name: $name, email: $super.email, cpf: $cpf, estado: $state," +
                " cep: $cep, descrição: $description, competências: $skill ")
    }
}