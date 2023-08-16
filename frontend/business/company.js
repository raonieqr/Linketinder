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
    // document.addEventListener("DOMContentLoaded", function() {
    console.log("start check input");
    checkInput();
    //   });
    // window.location.href = "./company_profile.html";
});
function isDigit(input) {
    return /^\d+$/.test(input);
}
function isSpecialCharacter(input) {
    return /[/[0-9!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?]+/.test(input);
}
function checkInput() {
    var nameInput = document.getElementById("name");
    var emailInput = document.getElementById("email");
    var skillsInput = document.getElementById("skills");
    var countryInput = document.getElementById("country");
    var cnpjInput = document.getElementById("cnpj");
    var stateInput = document.getElementById("state");
    var cepInput = document.getElementById("cep");
    var passwordInput = document.getElementById("password");
    if (nameInput && nameInput.value) {
        if (isSpecialCharacter(nameInput.value)) {
            console.log("É digito ou caracter especial");
            return;
        }
        if (isDigit(nameInput.value)) {
            console.log("É digito ou caracter especial");
            return;
        }
        else
            console.log(nameInput.value);
    }
}
// const testCompany: Company = {
//     name: nameInput,
//     email: emailInput,
//     skills: skillsInput,
//     country: countryInput,
//     cnpj: cnpjInput,
//     state: stateInput,
//     cep: cepInput,
//     password: passwordInput
// };
