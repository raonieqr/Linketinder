export interface Company {
    name: string,
    email: string,
    skills: string[],
    country: string,
    cnpj: string,
    state: string,
    cep: number,
    password: string,
    vacancy: VacancyPosting | null
}

export interface VacancyPosting {
    company: Company,
    name: string,
    description: string,
    skills: string[]
}

export interface Candidate {
    name: string,
    age: number,
    email: string,
    skills: string[],
    description: string,
    cpf: string,
    cep: number,
    password: string,
    applications: VacancyApplication[] | null
}

export interface VacancyApplication {
    candidate: Candidate,
    vacancyPosting: VacancyPosting,
}