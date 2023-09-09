package model.entities

class MatchVacancy {
    int id
    Vacancy vacancy
    Candidate candidate
    boolean companyLiked


    MatchVacancy(int id, Vacancy vacancy, Candidate candidate) {
        this.id = id
        this.vacancy = vacancy
        this.candidate = candidate
        this.companyLiked = false
    }

    int getId() {
        return id
    }

    void setId(int id) {
        this.id = id
    }

    Vacancy getVacancy() {
        return vacancy
    }

    void setVacancy(Vacancy vacancy) {
        this.vacancy = vacancy
    }

    Candidate getCandidate() {
        return candidate
    }

    void setCandidate(Candidate candidate) {
        this.candidate = candidate
    }

    boolean getCompanyLiked() {
        return companyLiked
    }

    void setCompanyLiked(boolean companyLiked) {
        this.companyLiked = companyLiked
    }

    boolean getCandidateLiked() {
        return candidateLiked
    }



    @Override
    public String toString() {
        return "MatchVacancy{" +
                "id=" + id +
                ", vacancy=" + vacancy.toString() +
                ", candidate=" + candidate.showInfo() +
                ", companyLiked=" + companyLiked +
                '}';
    }
}
