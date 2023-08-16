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
    // document.addEventListener("DOMContentLoaded", function() {
        console.log("start check input");
        checkInput();
    //   });
    // window.location.href = "./company_profile.html";
});

interface Company {
    name: string,
    email: string,
    skills: string[],
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

function checkInput() {
    let nameInput = document.getElementById("name") as HTMLInputElement;
    let emailInput = document.getElementById("email") as HTMLInputElement;
    let skillsInput = document.getElementById("skills") as HTMLInputElement;
    let countryInput = document.getElementById("country") as HTMLInputElement;
    let cnpjInput = document.getElementById("cnpj") as HTMLInputElement;
    let stateInput = document.getElementById("state") as HTMLInputElement;
    let cepInput = document.getElementById("cep") as HTMLInputElement;
    let passwordInput = document.getElementById("password") as HTMLInputElement;
    
    if (nameInput && nameInput.value) {
        if (isSpecialCharacter(nameInput.value)) {
            console.log("Error: nome inválido");
            return ;
        }
        if (isDigit(nameInput.value)) {
            console.log("Error: nome inválido");
            return ;
        }
        else
            console.log(nameInput.value);
    }
    
    if (emailInput && emailInput.value) {
        if (isDigit(emailInput.value)) {
            console.log("Error: email inválido");
            return ;
        }
        else
            console.log(emailInput.value);
    }

}

const testCompany: Company = {
    name: nameInput,
    email: emailInput,
    skills: skillsInput,
    country: countryInput,
    cnpj: cnpjInput,
    state: stateInput,
    cep: cepInput,
    password: passwordInput
};