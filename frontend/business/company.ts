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
    window.location.href = "./company_profile.html";
});
