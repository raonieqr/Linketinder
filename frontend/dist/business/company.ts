import { VacancyPosting, Company } from "../module";
import ApexCharts from 'apexcharts';

//intro_company.html
let btnRegisterC = document.getElementById("register-comp");
let btnShowProfileC = document.getElementById("show-prof-comp");

btnRegisterC?.addEventListener("click", function(): void {
    window.location.href = "./company_registration.html";
});


let modal = document.getElementById("modal");
let behindModal = document.getElementById("behind-modal");

btnShowProfileC?.addEventListener("click", function(): void {
    if (modal)
        modal.style.display = "flex";
    if (behindModal)
        behindModal.classList.add("modalBlur");
});

let btnExitModal = document.getElementById("exitModal");

btnExitModal?.addEventListener("click", function (): void {
    if (modal)
        modal.style.display = "none";
    if (behindModal)
        behindModal.classList.remove("modalBlur");
});

let btnsigIn = document.getElementById("sigIn");
btnsigIn?.addEventListener("click", function(): void {
    let compCheck = localStorage.getItem("companyLocal");
    let compName = document.getElementById("companyUser") as HTMLInputElement;
    let compPass = document.getElementById("companyPass") as HTMLInputElement;

    function isLoginValid(companyObj: any, username: string, password: string): boolean {
        return companyObj.name === username && companyObj.password === password;
    }

    if (compCheck) {
        var compObj = JSON.parse(compCheck);
        if (compObj && compName && compPass) {
            if (isLoginValid(compObj,compName.value, compPass.value)) {
                window.location.href = "./company_profile.html";
                return;
            }
        }
    }
    alert("Error: login ou senha inválido");
});


//company_registration.html 
let btnRegister = document.getElementById("register");

btnRegister?.addEventListener("click", function(): void {
        if (checkInput())
            window.location.href = "./company_profile.html";
});


function isDigit(input: string): boolean {
    return /^\d+$/.test(input);
}

function isSpecialCharacter(input: string): boolean {
    return /[/[0-9!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?]+/.test(input);
}

function validationEmail(input: string) {
    return /\S+@\w+\.\w{2,6}(\.\w{2})?/g.test(input);
}
  
function checkInput() {
    function showAlert(errorMsg: string) {
        alert(errorMsg);
        return false;
    }

    function validateInput(input: any, nameVar: string) {
        if (isSpecialCharacter(input.value) || isDigit(input.value)) {
            return showAlert(`Error: ${nameVar} inválido`);
        }
        return input.value;
    }

    function validateCnpj(input: any) {
        if (!isDigit(input.value) || input.value.length !== 14) {
            return showAlert("Error: cnpj inválido");
        }
        return input.value;
    }

    function validateCep(input: any) {
        if (!isDigit(input.value) || input.value.length !== 8) {
            return showAlert("Error: cep inválido");
        }
        return input.value;
    }

    function validateEmail(input: any) {
        if (isDigit(input.value) || !validationEmail(input.value)) {
            return showAlert("Error: email inválido");
        }
        return input.value;
    }

    let nameInput = document.getElementById("name") as HTMLInputElement;
    let emailInput = document.getElementById("email") as HTMLInputElement;
    let skillsInput = document.getElementById("skills") as HTMLInputElement;
    let countryInput = document.getElementById("country") as HTMLInputElement;
    let cnpjInput = document.getElementById("cnpj") as HTMLInputElement;
    let stateInput = document.getElementById("state") as HTMLInputElement;
    let cepInput = document.getElementById("cep") as HTMLInputElement;
    let passwordInput = document.getElementById("password") as HTMLInputElement;

    function isEmpty(inputElement: any) {
        return inputElement.value.trim() === "";
    }
    
    if (isEmpty(nameInput) || isEmpty(emailInput) || isEmpty(skillsInput) || isEmpty(countryInput) ||
        isEmpty(cnpjInput) || isEmpty(stateInput) || isEmpty(cepInput) || isEmpty(passwordInput)) {
        showAlert("Error: Nenhum campo pode estar vazio");
    }

    const isSuccessful = validateInput(nameInput, "nome") &&
                         validateInput(countryInput, "país") &&
                         validateInput(stateInput, "estado") &&
                         validateCnpj(cnpjInput) &&
                         validateCep(cepInput) &&
                         validateEmail(emailInput);

    if (isSuccessful) {
        if (passwordInput && passwordInput.value.length < 8) {
            showAlert("Error: senha muito curta");
            return false;
        }

        let skills: Set<string[]> = new Set();
        if (skillsInput && skillsInput.value) {
            if (skillsInput.value.match(","))
                skills.add(skillsInput.value.split(","));
            else
                skills.add(skillsInput.value.split(" "));
        }

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
let registerVacancy = document.getElementById("registerVacancy");
registerVacancy?.addEventListener("click", function() {

    let nameVacancy = document.getElementById("nameVacancy") as HTMLInputElement;
    let skillsVacancy = document.getElementById("skillsVacancy") as HTMLInputElement;
    let descriptionVacancy = document.getElementById("descriptionVacancy") as HTMLInputElement;
    
    let company = localStorage.getItem('companyLocal');
    let companyObj = null;
    if (company)
        companyObj = JSON.parse(company);
    
    
    let skills: Set<string[]> = new Set();
    if (skillsVacancy && skillsVacancy.value) {
        if (skillsVacancy.value.match(","))
            skills.add(skillsVacancy.value.split(","));
        else
            skills.add(skillsVacancy.value.split(" "));
    }

    let now = new Date();

    let day = now.getDate().toString().padStart(2, '0');
    let month = (now.getMonth() + 1).toString().padStart(2, '0');
    let year = now.getFullYear();
    
    const vacancyPosting: VacancyPosting = {
        company: companyObj,
        name: nameVacancy?.value,
        description: descriptionVacancy?.value,
        date: `${day}/${month}/${year}`,
        skills: Array.from(skills).join().toLowerCase().trim().split(/[;, ]+/)
    };
    companyObj.vacancy = JSON.parse(JSON.stringify(vacancyPosting));
    window.location.href = "./company_profile.html";
    localStorage.removeItem('companyLocal');
    localStorage.setItem("companyLocal", JSON.stringify(companyObj));
});

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
            var matchingSkills = compObj.vacancy.skills.filter((skill: string) => candiObj.skills.includes(skill));
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