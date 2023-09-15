package com.mindhub.homebanking.utils;

import com.mindhub.homebanking.dtos.LoanAplicationDTO;
import com.mindhub.homebanking.models.Loan;

public final class LoanUtils {
    public static Double calcularIntereses(Loan loan, LoanAplicationDTO loanAplicationDTO) {



        double porcentajeFinal = 0.0;
        do {
            double porcentajeInicial = loan.getInteres();
            porcentajeFinal = porcentajeInicial;
            porcentajeInicial += 5.0;

        }while(loan.getPayments().equals(loanAplicationDTO.getPayments()));
        return porcentajeFinal;

    }
}
