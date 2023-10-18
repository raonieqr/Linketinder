import * as check from './validations';

export async function lookupCEPAndProcessResponse(processCEPData: any) {
    const cepInput = check.getInput("cep");
    if (cepInput) 
      if (check.validateCep(cepInput)){
        var cep = cepInput.value;
        const url = 'https://viacep.com.br/ws/' + cep + '/json/';
        
        try {
          const response = await fetch(url);
          if (response.ok) {
            const address = await response.json();
            if (processCEPData(address))
               return true;
          } 
        } catch (error) {
          alert("Error: CEP não encontrado.");
        }
      }
    return false;
  }