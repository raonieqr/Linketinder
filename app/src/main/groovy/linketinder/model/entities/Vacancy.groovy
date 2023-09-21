package linketinder.model.entities

class Vacancy {

    int id
    String name
    String description
    Company company
    ArrayList<String> skills

    Vacancy(int id, String name, String description, Company company,
            ArrayList<String> skills) {

        this.id = id
        this.name = name
        this.description = description
        this.company = company
        this.skills = skills
            }

    int getId() {
        return id
    }

    void setId(int id) {
        this.id = id
    }

    String getName() {
        return name
    }

    void setName(String name) {
        this.name = name
    }

    String getDescription() {
        return description
    }

    void setDescription(String description) {
        this.description = description
    }

    Company getCompany() {
        return company
    }

    void setCompany(Company company) {
        this.company = company
    }

    ArrayList<String> getSkills() {
        return skills
    }

    void setSkills(ArrayList<String> skills) {
        this.skills = skills
    }

    @Override
    public String toString() {
        return 'Vacancy{' +
                'id=' + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ', company=' + company.showInfo() +
                ', skills=' + skills +
                '}'
    }

}
