import { VacancyPosting, Company } from "../module";
import ApexCharts from 'apexcharts';
import * as check from "../validations";

//intro_company.html


let registerCompanyButton = document.getElementById("register-comp");
let showProfileCompanyButton = document.getElementById("show-prof-comp");
let exitModalButton = document.getElementById("exitModal");

registerCompanyButton?.addEventListener("click", redirectToCompanyRegistration);
showProfileCompanyButton?.addEventListener("click", showModal);
exitModalButton?.addEventListener("click", hideModal);

function redirectToCompanyRegistration() {
  window.location.href = "./company_registration.html";
}

function showModal() {
  let modalElement = document.getElementById("modal");
  let behindModalElement = document.getElementById("behind-modal");

  if (modalElement)
    modalElement.style.display = "flex";

  if (behindModalElement)
    behindModalElement.classList.add("modalBlur");
}

function hideModal() {
  let modalElement = document.getElementById("modal");
  let behindModalElement = document.getElementById("behind-modal");

  if (modalElement) 
    modalElement.style.display = "none";

  if (behindModalElement)
    behindModalElement.classList.remove("modalBlur");
}

function handleSignInClick(){
    let compCheck = localStorage.getItem("companyLocal");
    let compName = check.getInput("companyUser") as HTMLInputElement;
    let compPass = check.getInput("companyPass") as HTMLInputElement;

  if (!compCheck || !compName || !compPass) {
    alert("Error: campo vazio");
    return;
  }
    const compObj = JSON.parse(compCheck);

  if (!check.isLoginValid(compObj, compName.value, compPass.value)) {
    alert("Error: login ou senha inválido");
    return;
  }
  window.location.href = "./company_profile.html";

}

const btnSignIn = document.getElementById("sigIn");

if (btnSignIn) 
  btnSignIn.addEventListener("click", handleSignInClick);


//company_registration.html 


let btnRegister = document.getElementById("register");

function handleRegisterClick(): void {
    if (saveCompanyData()) 
      window.location.href = "./company_profile.html";
}

btnRegister?.addEventListener("click", handleRegisterClick);

function validateInputFields() {

    let nameInput = check.getInput("name") as HTMLInputElement;
    let emailInput = check.getInput("email") as HTMLInputElement;
    let skillsInput = check.getInput("skills") as HTMLInputElement;
    let countryInput = check.getInput("country") as HTMLInputElement;
    let cnpjInput = check.getInput("cnpj") as HTMLInputElement;
    let stateInput = check.getInput("state") as HTMLInputElement;
    let cepInput = check.getInput("cep") as HTMLInputElement;
    let passwordInput = check.getInput("password") as HTMLInputElement;
    
    if (check.isEmpty(nameInput) || check.isEmpty(emailInput) ||
        check.isEmpty(skillsInput) || check.isEmpty(countryInput) ||
        check.isEmpty(cnpjInput) || check.isEmpty(stateInput) ||
        check.isEmpty(cepInput) || check.isEmpty(passwordInput)) {
        alert("Error: Nenhum campo pode estar vazio");
        return false;
    }

    const isSuccessful = check.validateInput(nameInput, "nome") &&
            check.validateInput(countryInput, "país") &&
            check.validateInput(stateInput, "estado") &&
            check.validateCnpj(cnpjInput) &&
            check.validateCep(cepInput) &&
            check.validateEmail(emailInput);

    if (!check.validatePasswordLength(passwordInput))
        return false;

    return isSuccessful;

}

function saveCompanyData() {
    if (validateInputFields()) {
        let nameInput = check.getInput("name") as HTMLInputElement;
        let emailInput = check.getInput("email") as HTMLInputElement;
        let skillsInput = check.getInput("skills") as HTMLInputElement;
        let countryInput = check.getInput("country") as HTMLInputElement;
        let cnpjInput = check.getInput("cnpj") as HTMLInputElement;
        let stateInput = check.getInput("state") as HTMLInputElement;
        let cepInput = check.getInput("cep") as HTMLInputElement;
        let passwordInput = check.getInput("password") as HTMLInputElement;

        let skills = check.parseSkillsInput(skillsInput)

        const companyLocal: Company = {
            name: nameInput.value,
            email: emailInput.value,
            skills: Array.from(skills).join().toLowerCase().trim().split(/[;, ]+/),
            country: countryInput.value,
            cnpj: cnpjInput.value,
            state: stateInput.value,
            cep: Number.parseInt(cepInput.value),
            password: passwordInput.value,
            vacancy: null
        };
        localStorage.setItem("companyLocal", JSON.stringify(companyLocal));
        return true;
    }
    return false;
}


//company_vacancies.html


let registerVacancyButton = document.getElementById("registerVacancy");

function registerVacancy() {
    let nameVacancyInput = check.getInput("nameVacancy") as HTMLInputElement;
    let skillsVacancyInput = check.getInput("skillsVacancy") as HTMLInputElement;
    let descriptionVacancyInput = check.getInput("descriptionVacancy") as HTMLInputElement;
  
    let companyLocal = localStorage.getItem("companyLocal");
    let companyObj = companyLocal ? JSON.parse(companyLocal) : null;
  
    if (!nameVacancyInput || !skillsVacancyInput || !descriptionVacancyInput || !companyObj) {
      alert("Erro: Informações incompletas");
      return;
    }
  
    let skills = check.parseSkillsInput(skillsVacancyInput.value);
  
    let now = new Date();
    let day = now.getDate().toString().padStart(2, '0');
    let month = (now.getMonth() + 1).toString().padStart(2, '0');
    let year = now.getFullYear();
  
    const vacancyPosting: VacancyPosting = {
      company: companyObj,
      name: nameVacancyInput.value,
      description: descriptionVacancyInput.value,
      date: `${day}/${month}/${year}`,
      skills: Array.from(skills).join()
      .toLowerCase().trim().split(/[;, ]+/)
    };
  
    companyObj.vacancy = JSON.parse(JSON.stringify(vacancyPosting));
    localStorage.setItem("companyLocal", JSON.stringify(companyObj));
    window.location.href = "./company_profile.html";
  }
  
  registerVacancyButton?.addEventListener("click", registerVacancy);
  

//company_profile.html


function generateGraph() {

    var options = {
        chart: {
          type: "bar",
        },
        series: [
          {
            name: "candidatos",
            data: [30, 40, 10, 5, 18, 40],
          },
        ],
        xaxis: {
          categories: ["python", "c", "c++", "java", "php", "groovy"],
        },
        colors: ['#457350'],
      };
    
      var chart = new ApexCharts(document.querySelector("#chart"), options);
    
      chart.render();
}

generateGraph();

function generateTable() {
    let tbody = document.querySelector("tbody");
    let row = document.querySelector("tbody tr");
    let table = document.querySelector("table")

    let c0 = document.createElement('td');
    let c1 = document.createElement('td');
    let c2 = document.createElement('td');
    let c3 = document.createElement('td');
    let c4 = document.createElement('td');

    let company = localStorage.getItem("companyLocal");
    
    if (company) 
        var compObj = JSON.parse(company);
    
    
    if (compObj.vacancy) {

        var candidate = localStorage.getItem("candidateLocal");
        if (candidate) {

            var candiObj = JSON.parse(candidate)
            if (compObj.vacancy.skills && candiObj.skills) 
                var matchingSkills = compObj.vacancy.skills.
                filter((skill: string) => candiObj.skills.includes(skill));
        }
    }

    var matchPercentage = (matchingSkills.length / compObj.vacancy.skills.length) * 100
    c0.innerText = compObj.vacancy.name;
    c1.innerText = "x";
    c2.innerText = "Engenheiro de software";
    c3.innerText = candiObj.skills;
    c4.innerHTML = `${matchPercentage.toFixed(2).toString()}%`;

    row?.appendChild(c0);
    row?.appendChild(c1);
    row?.appendChild(c2);
    row?.appendChild(c3);
    row?.appendChild(c4);
    if (row && tbody) {
        tbody?.appendChild(row);
        table?.appendChild(tbody);
    }
}

generateTable();