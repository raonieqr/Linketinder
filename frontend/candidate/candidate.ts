//intro_candidate.html
let btnRegisterC = document.getElementById("register-c");
let btnShowProfileC = document.getElementById("show-profile-c");

btnRegisterC?.addEventListener("click", function(): void {
    window.location.href = "./candidate_registration.html";
});


let modal = document.getElementById("modal");
let behindModal = document.getElementById("behind-modal");

btnShowProfileC?.addEventListener("click", function(): void {
    localStorage.setItem("test", "abc");
    if (modal)
        modal.style.display = "flex";
    if (behindModal) {
        behindModal.classList.add("modalBlur");
    }
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
    window.location.href = "./candidate_profile.html";
});


//candidate_registration.html
let btnRegister = document.getElementById("register");

btnRegister?.addEventListener("click", function(): void {
    console.log("start check");
    if (checkInput())
        window.location.href = "./candidate_profile.html";
});

interface Candidate {
    name: string,
    age: number,
    email: string,
    skills: string[],
    description: string,
    cpf: string,
    cep: number,
    password: string
}

function isDigit(input: string): boolean {
    return /^\d+$/.test(input);
}

function isSpecialCharacter(input: string): boolean {
    return /[/[0-9!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?]+/.test(input);
}

function validationEmail(input) {
    return /@.*\.com/.test(input);
}
  
function checkInput() {
    function showAlert(errorMsg) {
        alert("Error: " + errorMsg);
        return false;
    }

    function validateInput(input, nameVar) {
        if (isSpecialCharacter(input.value) || isDigit(input.value)) {
            return showAlert(`Error: ${nameVar} inválido`);
        }
        return input.value;
    }

    function validateCpf(input) {
        if (!isDigit(input.value) || input.value.length !== 11) {
            return showAlert("Error: cpf inválido");
        }
        return input.value;
    }

    function validateAge(input) {
        if (!isDigit(input.value)) {
            return showAlert("Error: idade inválida");
        }
        return input.value;
    }

    function validateCep(input) {
        if (!isDigit(input.value) || input.value.length !== 8) {
            return showAlert("Error: cep inválido");
        }
        return input.value;
    }

    function validateEmail(input) {
        if (isDigit(input.value) || !validationEmail(input.value)) {
            return showAlert("Error: email inválido");
        }
        return input.value;
    }

    function validateDescription(input) {
        if (isDigit(input.value) || input.value.length < 10) {
            return showAlert("Error: descrição inválida");
        }
        return input.value;
    }

    let nameInput = document.getElementById("name") as HTMLInputElement;
    let emailInput = document.getElementById("email") as HTMLInputElement;
    let skillsInput = document.getElementById("skills") as HTMLInputElement;
    let ageInput = document.getElementById("age") as HTMLInputElement;
    let cpfInput = document.getElementById("cpf") as HTMLInputElement;
    let cepInput = document.getElementById("cep") as HTMLInputElement;
    let passwordInput = document.getElementById("password") as HTMLInputElement;
    let descriptionInput = document.getElementById("description") as HTMLInputElement;

    const isSuccessful = validateInput(nameInput, "nome") &&
                         validateCpf(cpfInput) &&
                         validateAge(ageInput) &&
                         validateCep(cepInput) &&
                         validateDescription(descriptionInput) &&
                         validateEmail(emailInput);

    if (isSuccessful) {
        if (passwordInput && passwordInput.value.length < 8) {
            showAlert("Error: senha muito curta");
            return false;
        }

        let skills: string[];
        if (skillsInput && skillsInput.value) {
            if (skillsInput.value.match(","))
                skills = skillsInput.value.split(",");
            else
                skills = skillsInput.value.split(" ");
        }

        // const companyLocal: Company = {
        //     name: nameInput.value,
        //     email: emailInput.value,
        //     skills: skills,
        //     country: countryInput.value,
        //     cnpj: cnpjInput.value,
        //     state: stateInput.value,
        //     cep: Number.parseInt(cepInput.value),
        //     password: passwordInput.value
        // };
        // localStorage.setItem("companyLocal", JSON.stringify(companyLocal));
        return true;
    }
    return false;
}
