import { VacancyPosting, Candidate, VacancyApplication } from "../module";
import * as check from "../validations";
//intro_candidate.html
const modal = document.getElementById("modal");
const behindModal = document.getElementById("behind-modal");
const btnRegisterC = document.getElementById("register-c");
const btnShowProfileC = document.getElementById("show-profile-c");
const btnExitModal = document.getElementById("exitModal");

function redirectToCandidateRegistration() {
  window.location.href = "./candidate_registration.html";
}

function showModal() {
  if (modal) modal.style.display = "flex";

  if (behindModal) behindModal.classList.add("modalBlur");
}

function hideModal() {
  if (modal) modal.style.display = "none";

  if (behindModal) behindModal.classList.remove("modalBlur");
}

if (btnRegisterC) {
  btnRegisterC.addEventListener("click", redirectToCandidateRegistration);
}

if (btnShowProfileC) {
  btnShowProfileC.addEventListener("click", showModal);
}

if (btnExitModal) {
  btnExitModal.addEventListener("click", hideModal);
}

let btnsigIn = document.getElementById("sigIn");
btnsigIn?.addEventListener("click", function (): void {
  let candCheck: string | null = localStorage.getItem("candidateLocal");
  let userName: HTMLInputElement = document.getElementById("userName") as HTMLInputElement;
  let userPass: HTMLInputElement = document.getElementById("userPass") as HTMLInputElement;

  if (candCheck) {
    var candObj = JSON.parse(candCheck);
    if (candObj && userName && userPass) {
      if (check.isLoginValid(candObj, userName.value, userPass.value)) {
        window.location.href = "./candidate_profile.html";
        return;
      }
    }
  }
  alert("Error: login ou senha inválido");
});

//candidate_registration.html
let btnRegister: HTMLElement | null = document.getElementById("register");

btnRegister?.addEventListener("click", function (): void {
  if (checkInput()) {
    window.location.href = "./candidate_profile.html";
  }

});

function checkInput(): boolean {

  let nameInput: HTMLInputElement = document.getElementById("name") as HTMLInputElement;
  let emailInput: HTMLInputElement = document.getElementById("email") as HTMLInputElement;
  let skillsInput: HTMLInputElement = document.getElementById("skills") as HTMLInputElement;
  let ageInput: HTMLInputElement = document.getElementById("age") as HTMLInputElement;
  let cpfInput: HTMLInputElement = document.getElementById("cpf") as HTMLInputElement;
  let cepInput: HTMLInputElement = document.getElementById("cep") as HTMLInputElement;
  let passwordInput: HTMLInputElement = document.getElementById("password") as HTMLInputElement;
  let descriptionInput: HTMLInputElement = document.getElementById("description") as HTMLInputElement;

  if (check.isEmpty(nameInput) || check.isEmpty(emailInput) ||
      check.isEmpty(skillsInput) || check.isEmpty(ageInput) ||
      check.isEmpty(cpfInput) || check.isEmpty(cepInput) ||
      check.isEmpty(passwordInput) || check.isEmpty(descriptionInput)) {
        check.showAlert("Error: Nenhum campo pode estar vazio");
  }

  const isSuccessful = check.validateInput(nameInput, "nome") &&
                       check.validateCpf(cpfInput) &&
                       check.validateAge(ageInput) &&
                       check.validateCep(cepInput) &&
                       check.validateDescription(descriptionInput) &&
                       check.validateEmail(emailInput);

  if (isSuccessful) {

      if (!check.validatePasswordLength(passwordInput))
          return false;
      let skills = check.parseSkillsInput(skillsInput)

      const candidateLocal: Candidate = {
          name: nameInput.value,
          age: Number.parseInt(ageInput.value),
          email: emailInput.value,
          skills: Array.from(skills).join().toLowerCase().trim().split(/[;, ]+/),
          description: descriptionInput.value,
          cpf: cpfInput.value,
          cep: Number.parseInt(cepInput.value),
          password: passwordInput.value,
          applications: null
      };
      localStorage.setItem("candidateLocal", JSON.stringify(candidateLocal));
      return true;
  }
  return false;
}


function updateVacancy() {
  let company: string | null = localStorage.getItem("companyLocal");
  
  if (company) 
    var compObj = JSON.parse(company);
  
  
  if (compObj.vacancy) {

    var candidate: string | null = localStorage.getItem("candidateLocal");
    if (candidate) {

      var candiObj = JSON.parse(candidate)
      if (compObj.vacancy.skills && candiObj.skills) 
        var matchingSkills = compObj.vacancy.skills.filter((skill: string) => candiObj.skills.includes(skill));
    }
  }

  let parentDiv: Element | null = document.querySelector(".grid");

  if (parentDiv) {
    var matchPercentage: number = (matchingSkills.length / compObj.vacancy.skills.length) * 100

    parentDiv.innerHTML = ` <div id="vacancies">
      <div class="vacancies-detail">
        <div class="title">
          <h3 id="titleVacancy">${compObj.vacancy.name}</h3>
          <p id="dateVacancy">${compObj.vacancy.date}</p>
        </div>
        <p id="match">Match: ${matchPercentage.toFixed(2).toString()}%</p>
        <!-- <i class="ph-fill ph-heart"></i> -->
      </div>
    </div>`
  }
}

updateVacancy();

document.addEventListener('DOMContentLoaded', function() {
  let vacancyLink: HTMLElement | null = document.getElementById("vacancyLinkacancyLink");
  vacancyLink?.addEventListener('click', function() {    
    updateVacancy();
     
  });
});

function generateTable() {
  let tbody: HTMLTableSectionElement | null = document.querySelector("tbody");
  let row: Element | null = document.querySelector("tbody tr");
  let table: HTMLTableElement | null = document.querySelector("table")

  let c0: HTMLTableCellElement = document.createElement('td');
  let c1: HTMLTableCellElement = document.createElement('td');
  let c2: HTMLTableCellElement = document.createElement('td');

  let company: string | null = localStorage.getItem("companyLocal");
  
  if (company) 
      var compObj = JSON.parse(company);
  
  if (compObj.vacancy) {

    c0.innerText = compObj.vacancy.name;
    c1.innerText = "x";
    c2.innerText = compObj.vacancy.skills;

    row?.appendChild(c0);
    row?.appendChild(c1);
    row?.appendChild(c2);
    
    if (row && tbody) {
        tbody?.appendChild(row);
        table?.appendChild(tbody);
    }
  }
}

generateTable();