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
    console.log("oi");
    console.log(localStorage.getItem("test"));
});


//candidate_registration.html
let btnRegister = document.getElementById("register");

btnRegister?.addEventListener("click", function(): void {
    window.location.href = "./candidate_profile.html";
});