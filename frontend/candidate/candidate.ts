import { VacancyPosting, Candidate, VacancyApplication } from "../module";
import * as check from "../validations";

// ********* intro_candidate.html *********


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

if (btnRegisterC)
  btnRegisterC.addEventListener("click", redirectToCandidateRegistration);

if (btnShowProfileC)
  btnShowProfileC.addEventListener("click", showModal);

if (btnExitModal)
  btnExitModal.addEventListener("click", hideModal);

function handleSignInClick() {
  let candCheck: any = localStorage.getItem("candidateLocal");
  let userName: HTMLInputElement = check.getInput("userName") as HTMLInputElement;
  let userPass: HTMLInputElement = check.getInput("userPass") as HTMLInputElement;

  if (!candCheck || !userName || !userPass) {
    alert("Error: campo vazio");
    return;
  }

    const candObj = JSON.parse(candCheck);

  if (!check.isLoginValid(candObj, userName.value, userPass.value)) {
    alert("Error: login ou senha inválido");
    return;
  }

  window.location.href = "./candidate_profile.html";
}

const btnSignIn = document.getElementById("sigIn");

if (btnSignIn)
  btnSignIn.addEventListener("click", handleSignInClick);


// ********* candidate_registration.html *********


const btnRegister = document.getElementById("register");

function handleRegisterButtonClick() {
  if (saveCandidateData())
    redirectToCandidateProfile();
}

function redirectToCandidateProfile() {
  window.location.href = "./candidate_profile.html";
}

if (btnRegister) {
  btnRegister.addEventListener("click", handleRegisterButtonClick);
}

function validateInputFields(): boolean {

  const nameInput = check.getInput("name");
  const emailInput = check.getInput("email");
  const skillsInput = check.getInput("skills");
  const ageInput = check.getInput("age");
  const cpfInput = check.getInput("cpf");
  const cepInput = check.getInput("cep");
  const passwordInput = check.getInput("password");
  const descriptionInput = check.getInput("description");

  if (
    check.isEmpty(nameInput) ||
    check.isEmpty(emailInput) ||
    check.isEmpty(skillsInput) ||
    check.isEmpty(ageInput) ||
    check.isEmpty(cpfInput) ||
    check.isEmpty(cepInput) ||
    check.isEmpty(passwordInput) ||
    check.isEmpty(descriptionInput)
  ) {

    alert("Error: Nenhum campo pode estar vazio");

    return false;
  }

  const isSuccessful =
    check.validateInput(nameInput, "nome") &&
    check.validateCpf(cpfInput) &&
    check.validateAge(ageInput) &&
    check.validateCep(cepInput) &&
    check.validateDescription(descriptionInput) &&
    check.validateEmail(emailInput);

    if (!check.validatePasswordLength(passwordInput))
      return false;

  return isSuccessful;
}

function saveCandidateData(): boolean {
  if (validateInputFields()) {

    const nameInput = check.getInput("name");
    const emailInput = check.getInput("email");
    const skillsInput = check.getInput("skills");
    const ageInput = check.getInput("age");
    const cpfInput = check.getInput("cpf");
    const cepInput = check.getInput("cep");
    const passwordInput = check.getInput("password");
    const descriptionInput = check.getInput("description");

    const skills = check.parseSkillsInput(skillsInput);

    if (
      !nameInput || !emailInput || !skillsInput ||
      !ageInput || !cpfInput || !cepInput ||
      !passwordInput || !descriptionInput
    ) {
      alert("Error: campo vazio");
      return false;
    }

    const candidateLocal: Candidate = {
      name: nameInput.value,
      age: parseInt(ageInput.value, 10),
      email: emailInput.value,
      skills: Array.from(skills)
        .join()
        .toLowerCase()
        .trim()
        .split(/[;, ]+/),
      description: descriptionInput.value,
      cpf: cpfInput.value,
      cep: parseInt(cepInput.value, 10),
      password: passwordInput.value,
      applications: null,
    };

    localStorage.setItem("candidateLocal", JSON.stringify(candidateLocal));

    return true;
  }
  return false;
}


// ********* candidate_vacancies *********


function updateVacancy() {
  const companyLocal = localStorage.getItem("companyLocal");
  const candidateLocal = localStorage.getItem("candidateLocal");
  const parentDiv = document.querySelector(".grid");

  if (!companyLocal || !candidateLocal)
  {
    if (parentDiv)
      parentDiv.innerHTML = `<h2>Sem vaga</h2>`
    return;
  }

  const compObj = JSON.parse(companyLocal);
  const candiObj = JSON.parse(candidateLocal);

  if (!compObj.vacancy || !compObj.vacancy.skills || !candiObj.skills)
    return;

  const matchingSkills = compObj.vacancy.skills.filter((skill: string) =>
    candiObj.skills.includes(skill)
  );


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
  let vacancyLink: HTMLElement | null = document.getElementById("vacancyLink");

  if (vacancyLink) {
    vacancyLink.addEventListener('click', function() {
      updateVacancy();
    });
  }
});


// ********* candidate_profile *********


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
