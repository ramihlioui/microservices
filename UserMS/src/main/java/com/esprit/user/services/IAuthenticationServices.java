package com.esprit.user.services;

import com.esprit.user.entities.AuthenticationResponse;
import com.esprit.user.entities.Etudiant;
import com.esprit.user.entities.RefreshTokenRequest;

import java.util.HashMap;

public interface IAuthenticationServices {
    Etudiant registerEtudiant(Etudiant etudiant);
    AuthenticationResponse login(String email, String password);
    AuthenticationResponse refreshToken(RefreshTokenRequest refreshToken);
   // HashMap<String,String> forgetPassword(String email);
    HashMap<String,String> resetPassword(String passwordResetToken, String newPassword);
}
