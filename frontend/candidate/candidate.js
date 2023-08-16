//intro_candidate.html
var btnRegisterC = document.getElementById("register-c");
var btnShowProfileC = document.getElementById("show-profile-c");
btnRegisterC === null || btnRegisterC === void 0 ? void 0 : btnRegisterC.addEventListener("click", function () {
    window.location.href = "./candidate_registration.html";
});
var modal = document.getElementById("modal");
var behindModal = document.getElementById("behind-modal");
btnShowProfileC === null || btnShowProfileC === void 0 ? void 0 : btnShowProfileC.addEventListener("click", function () {
    localStorage.setItem("test", "abc");
    if (modal)
        modal.style.display = "flex";
    if (behindModal) {
        behindModal.classList.add("modalBlur");
    }
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
    window.location.href = "./candidate_profile.html";
});
//candidate_registration.html
var btnRegister = document.getElementById("register");
btnRegister === null || btnRegister === void 0 ? void 0 : btnRegister.addEventListener("click", function () {
    console.log("start check");
    if (checkInput())
        window.location.href = "./candidate_profile.html";
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
    var nameInput = document.getElementById("name");
    var emailInput = document.getElementById("email");
    var skillsInput = document.getElementById("skills");
    var ageInput = document.getElementById("age");
    var cpfInput = document.getElementById("cpf");
    var cepInput = document.getElementById("cep");
    var passwordInput = document.getElementById("password");
    var descriptionInput = document.getElementById("description");
    var isSuccessful = validateInput(nameInput, "nome") &&
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
        var skills = void 0;
        if (skillsInput && skillsInput.value) {
            if (skillsInput.value.match(","))
                skills = skillsInput.value.split(",");
            else
                skills = skillsInput.value.split(" ");
        }
       
        return true;
    }
    return false;
}
