package com.example.demo;

import me.gosimple.nbvcxz.Nbvcxz;
import me.gosimple.nbvcxz.scoring.Result;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.authentication.password.CompromisedPasswordDecision;

public class NbvcxzPasswordChecker implements CompromisedPasswordChecker {
    private final Nbvcxz nbvcxz;

    public NbvcxzPasswordChecker(Nbvcxz nbvcxz) {
        this.nbvcxz = nbvcxz;
    }

    @Override
    public CompromisedPasswordDecision check(String password) {
        Result estimate = nbvcxz.estimate(password);

        return new CompromisedPasswordDecision(!estimate.isMinimumEntropyMet());
    }
}
