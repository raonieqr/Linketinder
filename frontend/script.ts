//index.html
let btnCompany = document.getElementById("company");
let btnCandidate = document.getElementById("candidate");

btnCompany?.addEventListener("click", function(): void {
    window.location.href = "./business/intro_company.html";
});

btnCandidate?.addEventListener("click", function(): void {
    window.location.href = "./candidate/intro_candidate.html";
});
