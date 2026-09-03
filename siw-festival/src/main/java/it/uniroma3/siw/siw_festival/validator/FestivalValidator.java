package it.uniroma3.siw.siw_festival.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.siw_festival.model.Festival;

@Component
public class FestivalValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Festival.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Festival festival = (Festival) target;

        if (festival.getDataInizio() != null && festival.getDataFine() != null) {
            if (festival.getDataFine().isBefore(festival.getDataInizio())) {
                errors.rejectValue("dataFine", "date.invalide",
                        "La data di fine non può essere precedente alla data di inizio");
            }
        }
    }
}
