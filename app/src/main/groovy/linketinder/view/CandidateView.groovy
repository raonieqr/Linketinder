package linketinder.view

import linketinder.model.entities.Candidate
import linketinder.utils.InputValidator

class CandidateView {
    static showCandidates(ArrayList<Candidate> candidates) {
        if (candidates.isEmpty())
            println("Não há candidatos cadastrados")
        else {
            candidates.each { candidate ->
                candidate.showInfo()
                println("-----------------------------------" +
                        "----------------------------")
            }
        }
    }

    static Candidate createCandidate(int idCandidate, ArrayList<Candidate> candidates) {

        String name = InputValidator.promptForUserInput("Nome: ")
        String email = InputValidator.promptForUserInput("E-mail: ")

        email = InputValidator.ensureUniqueEmail(candidates, email)

        String state = InputValidator.promptForUserInput("Estado: ")
        String description = InputValidator
                .promptForUserInput("Descrição: ")
        String skills = InputValidator.promptForUserInput("Habilidades " +
                "(separadas" +
                " " +
                "por vírgula): ")
        String cpf = InputValidator.promptForUserInput("CPF: ")

        while(cpf.length() != 11)
            cpf = InputValidator.promptForUserInput("Error: O CPF deve" +
                    " ter 11 caracteres. Tente novamente: ")

        cpf = InputValidator.ensureUniqueCpf(candidates, cpf)

        Integer age = InputValidator.promptForIntegerInput("Idade: ")

        Integer cep = InputValidator.promptForIntegerInput("CEP: ")

        ArrayList<String> skillsList = skills.split("[,;\\s]+")
        skillsList = skillsList.collect { it.toLowerCase() }
        skillsList = skillsList.collect { it.capitalize() }

        return new Candidate(idCandidate, name, email, skillsList, age,
                state, description, cpf, cep)
    }

}
