import entities.Candidate
import entities.Company

class Main {
    static void main(String[] args) {
        println("Bem vindo ao LinkeTinder")
        ArrayList<Candidate> candidates = new ArrayList<>()
        ArrayList<Company> companies = new ArrayList<Company>()

        candidates.add(new Candidate("Raoni", "raoniestevan@gmail.com", ["C", "C++", "Java", "Python"], 25, "RJ", "Desenvolvedor", "12344566701", 22785055))
        candidates.add(new Candidate("Alice", "alice@example.com", ["Python", "Java", "HTML", "CSS"], 28, "SP", "Web Developer", "987654321", 12345678))
        candidates.add(new Candidate("Bob", "bob@example.com", ["Java", "JavaScript", "SQL", "React"], 30, "MG", "Full Stack Developer", "555555555", 54321098))
        candidates.add(new Candidate("Eva", "eva@example.com", ["Ruby", "PHP", "Swift", "Kotlin"], 22, "RS", "Mobile Developer", "111111111", 98765432))
        candidates.add(new Candidate("Carlos", "carlos@example.com", ["Python", "C#", "Unity", "Game Development"], 26, "BA", "Game Developer", "222222222", 11223344))

        companies.add(new Company("ZG", "zg@zg.com.br", "12345678945567", "Brasil", "Desenvolvedor Java", "GO", ["Java", "Groovy"], 70806000))
        companies.add(new Company("TechCorp", "info@techcorp.com", "98765432101234", "USA", "Web Developer", "CA", ["JavaScript", "HTML", "CSS"], 90210))
        companies.add(new Company("InnovateTech", "contact@innovatetech.com", "55555555556789", "Canada", "Full Stack Developer", "ON", ["Java", "Python", "Angular"], 54321098))
        companies.add(new Company("GameWorld", "info@gameworld.com", "11111111234567", "Brazil", "Game Developer", "SP", ["Unity", "C#", "Game Development"], 12345678))
        companies.add(new Company("SoftSys", "contact@softsys.com", "22222222345678", "USA", "Software Engineer", "NY", ["Java", "Python", "SQL"], 10001))

        int option;
        BufferedReader reader = new BufferedReader(new InputStreamReader
                (System.in))
        while (option != 4) {

            println("Escolha um dos comandos abaixo:")
            println("1 - Listar empresas")
            println("2 - Listar candidatos")
            println("3 - Adicionar novo candidato")
            println("4 - Sair")
            option = Integer.parseInt(reader.readLine())

            if (option == 1) {
                companies.each { companie ->
                    companie.showInfo()
                }
            }
            else if (option == 2) {
                candidates.each { candidate ->
                    candidate.showInfo()
                }
            }
            else if (option == 3) {
                def name = getUserInput("Nome: ")
                def email = getUserInput("E-mail: ")
                def skills = getUserInput("Habilidades (separadas por vírgula): ")
                def age = getUserInputInt("Idade: ")
                def state = getUserInput("Estado: ")
                def description = getUserInput("Descrição: ")
                def cpf = getUserInput("CPF: ")
                def cep = getUserInputInt("CEP: ")

                ArrayList<String> skillsList = skills.split(',').collect { it.trim() }
                candidates.add(new Candidate(name, email, skillsList, age, state, description, cpf, cep))
                println("Cadastrado com sucesso")
            }
        }

    }
    static String getUserInput(String prompt) {
        BufferedReader reader = new BufferedReader(new InputStreamReader
                (System.in))
        print(prompt)
        def input = reader.readLine()
        while (input.trim().isEmpty()) {
            println("Error: O campo não pode estar vazio. Tente novamente.")
            print(prompt)
            input = reader.readLine()
        }
        return input
    }

    static int getUserInputInt(String prompt) {
        BufferedReader reader = new BufferedReader(new InputStreamReader
                (System.in))
        print(prompt)
        def input = reader.readLine()
        while (!input.isInteger()) {
            println("Error: Entrada inválida. Deve ser um número inteiro. " +
                    "Tente novamente.")
            print(prompt)
            input = reader.readLine()
        }
        return Integer.parseInt(input)
    }
}
