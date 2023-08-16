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
    console.log("oi");
    console.log(localStorage.getItem("test"));
});
//candidate_registration.html
var btnRegister = document.getElementById("register");
btnRegister === null || btnRegister === void 0 ? void 0 : btnRegister.addEventListener("click", function () {
    window.location.href = "./candidate_profile.html";
});
