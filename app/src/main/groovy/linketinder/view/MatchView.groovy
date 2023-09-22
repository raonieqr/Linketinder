package linketinder.view

import linketinder.model.entities.Candidate
import linketinder.model.entities.Company
import linketinder.model.entities.Vacancy

class MatchView {

    static void displayAllVacanciesLiked() {
        println("Você já curtiu todas as vagas disponíveis.")
    }

    static void displayAlreadyLiked() {
        println("Você só pode curtir vagas que " +
                "não foram curtidas anteriormente.")
    }

    static void displayCandidateMatches(Candidate candidate) {
        boolean isMatch = false

        candidate.getMatchVacancies().each { match ->

            if (match.companyLiked) {

                print("Vaga: " + match.getVacancy().getName() + ", ")

                match.getVacancy().getCompany().showInfo()

                isMatch = true

                println("-------------------------------------------")

            }
        }

        if (!isMatch)
            println("Ainda não houve match")
    }

    static void displayCompanyMatches(Company company) {

        boolean isMatch = false

        company.getMatchVacancies().each { match ->

            if (match.getCompanyLiked()) {

                println("Vaga: " + match.getVacancy().getName())

                match.getCandidate().showInfo()

                isMatch = true

                println("-------------------------------------------")
            }

        }

        if (!isMatch)
            println("Ainda não houve match")

    }

    static boolean displayUnlikedVacancies(boolean  containsVacancie,
                                           ArrayList<Integer> printedVacancyIds,
                                           Vacancy vacancy,
                                           boolean allVacanciesLiked) {

        if (!containsVacancie && !printedVacancyIds.contains(vacancy.getId())) {

            allVacanciesLiked = false

            printedVacancyIds.add(vacancy.getId())
            println("Id da vaga: " + vacancy.getId())
            println("Titulo: " + vacancy.getName())
            println("Descrição: " + vacancy.getDescription())
            println("Skills:")
            println(vacancy.getSkills().join(", "))

            println("------------------------------")
        }

        return allVacanciesLiked
    }

    static showLikedMsg(){
        println("Vaga curtida!")
    }

}
