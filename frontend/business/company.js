//intro_company.html
var btnRegisterC = document.getElementById("register-comp");
var btnShowProfileC = document.getElementById("show-prof-comp");
btnRegisterC === null || btnRegisterC === void 0 ? void 0 : btnRegisterC.addEventListener("click", function () {
    window.location.href = "./company_registration.html";
});
var modal = document.getElementById("modal");
var behindModal = document.getElementById("behind-modal");
btnShowProfileC === null || btnShowProfileC === void 0 ? void 0 : btnShowProfileC.addEventListener("click", function () {
    if (modal)
        modal.style.display = "flex";
    if (behindModal)
        behindModal.classList.add("modalBlur");
});
var btnExitModal = document.getElementById("exitModal");
btnExitModal === null || btnExitModal === void 0 ? void 0 : btnExitModal.addEventListener("click", function () {
    if (modal)
        modal.style.display = "none";
    if (behindModal)
        behindModal.classList.remove("modalBlur");
});
var btnsigIn = document.getElementById("sigIn");
btnsigIn === null || btnsigIn === void 0 ? void 0 : btnsigIn.addEventListener("click", function () {
    window.location.href = "./company_profile.html";
});
//company_registration.html 
var btnRegister = document.getElementById("register");
btnRegister === null || btnRegister === void 0 ? void 0 : btnRegister.addEventListener("click", function () {
    console.log("start check input");
    if (checkInput())
        window.location.href = "./company_profile.html";
});
function isDigit(input) {
    return /^\d+$/.test(input);
}
function isSpecialCharacter(input) {
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
            return showAlert("Error: ".concat(nameVar, " inv\u00E1lido"));
        }
        return input.value;
    }
    function validateCnpj(input) {
        if (!isDigit(input.value) || input.value.length !== 14) {
            return showAlert("cnpj inválido");
        }
        return input.value;
    }
    function validateCep(input) {
        if (!isDigit(input.value) || input.value.length !== 8) {
            return showAlert("cep inválido");
        }
        return input.value;
    }
    function validateEmail(input) {
        if (isDigit(input.value) || !validationEmail(input.value)) {
            return showAlert("email inválido");
        }
        return input.value;
    }
    var nameInput = document.getElementById("name");
    var emailInput = document.getElementById("email");
    var skillsInput = document.getElementById("skills");
    var countryInput = document.getElementById("country");
    var cnpjInput = document.getElementById("cnpj");
    var stateInput = document.getElementById("state");
    var cepInput = document.getElementById("cep");
    var passwordInput = document.getElementById("password");
    function isEmpty(inputElement) {
        return inputElement.value.trim() === "";
    }
    if (isEmpty(nameInput) || isEmpty(emailInput) || isEmpty(skillsInput) || isEmpty(countryInput) ||
        isEmpty(cnpjInput) || isEmpty(stateInput) || isEmpty(cepInput) || isEmpty(passwordInput)) {
        showAlert("Error: Nenhum campo pode estar vazio");
    }
    var isSuccessful = validateInput(nameInput, "nome") &&
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
        var skills = void 0;
        if (skillsInput && skillsInput.value) {
            if (skillsInput.value.match(","))
                skills = new Set(skillsInput.value.split(","));
            else
                skills = new Set(skillsInput.value.split(" "));
        }
        var companyLocal = {
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
