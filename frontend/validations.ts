export function isDigit(input: string): boolean {
    return /^\d+$/.test(input);
}

export function isSpecialCharacter(input: string): boolean {
    return /[/[0-9!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?]+/.test(input);
}

export function validationEmail(input: string) {
    return /\S+@\w+\.\w{2,6}(\.\w{2})?/g.test(input);
}

export function showAlert(errorMsg: string) {
    alert(errorMsg);
    return false;
}

export function validateInput(input: any, nameVar: string) {
    if (isSpecialCharacter(input.value) || isDigit(input.value)) {
        return showAlert(`Error: ${nameVar} inválido`);
    }
    return input.value;
}

export function validateCpf(input: any) {
    if (!isDigit(input.value) || input.value.length !== 11) {
        return showAlert("Error: cpf inválido");
    }
    return input.value;
}

export function validateAge(input: any) {
    if (!isDigit(input.value)) {
        return showAlert("Error: idade inválida");
    }
    return input.value;
}

export function validateCep(input: any) {
    if (!isDigit(input.value) || input.value.length !== 8) {
        return showAlert("Error: cep inválido");
    }
    return input.value;
}

export function validateEmail(input: any) {
    if (isDigit(input.value) || !validationEmail(input.value)) {
        return showAlert("Error: email inválido");
    }
    return input.value;
}

export function validateDescription(input: any) {
    if (isDigit(input.value) || input.value.length < 10) {
        return showAlert("Error: descrição inválida");
    }
    return input.value;
}

export function isEmpty(inputElement: any) {
    return inputElement.value.trim() === "";
}

export function validatePasswordLength(passwordInput: any) {
    if (passwordInput && passwordInput.value.length < 8) {
        showAlert("Error: senha muito curta");
        return false;
    }
    return true;
}

export function validateCnpj(input: any) {
    if (!isDigit(input.value) || input.value.length !== 14) {
        return showAlert("Error: cnpj inválido");
    }
    return input.value;
}

export function parseSkillsInput(skillsInput: any) {
    const skillsSet = new Set();

    if (skillsInput) {
        if (skillsInput.value) {
            const skillValues = skillsInput.value.split(/[, ]+/);
            for (const skill of skillValues) {
                skillsSet.add(skill);
              }
        }
    }

    return skillsSet;
}

export function isLoginValid(Obj: any, username: string, password: string
): boolean {
    return Obj.name === username && Obj.password === password;
}

export function getInput(id: string): HTMLInputElement | null {
    return document.getElementById(id) as HTMLInputElement | null;
  }
