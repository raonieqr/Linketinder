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
    window.location.href = "./company_profile.html";
});

//company_registration.html 
let btnRegister = document.getElementById("register");

btnRegister?.addEventListener("click", function(): void {
        console.log("start check input");
        if (checkInput())
            window.location.href = "./company_profile.html";
});

interface Company {
    name: string,
    email: string,
    skills: Set<string>,
    country: string,
    cnpj: string,
    state: string,
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

    function validateCnpj(input) {
        if (!isDigit(input.value) || input.value.length !== 14) {
            return showAlert("Error: cnpj inválido");
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

    let nameInput = document.getElementById("name") as HTMLInputElement;
    let emailInput = document.getElementById("email") as HTMLInputElement;
    let skillsInput = document.getElementById("skills") as HTMLInputElement;
    let countryInput = document.getElementById("country") as HTMLInputElement;
    let cnpjInput = document.getElementById("cnpj") as HTMLInputElement;
    let stateInput = document.getElementById("state") as HTMLInputElement;
    let cepInput = document.getElementById("cep") as HTMLInputElement;
    let passwordInput = document.getElementById("password") as HTMLInputElement;

    function isEmpty(inputElement) {
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

        let skills: Set<string>;
        if (skillsInput && skillsInput.value) {
            if (skillsInput.value.match(","))
                skills = new Set(skillsInput.value.split(","));
            else
                skills = new Set(skillsInput.value.split(" "));
        }

        const companyLocal: Company = {
            name: nameInput.value,
            email: emailInput.value,
            skills: skills,
            country: countryInput.value,
            cnpj: cnpjInput.value,
            state: stateInput.value,
            cep: Number.parseInt(cepInput.value),
            password: passwordInput.value
        };
        // localStorage.setItem("companyLocal", JSON.stringify(companyLocal));
        return true;
    }
    return false;
}

