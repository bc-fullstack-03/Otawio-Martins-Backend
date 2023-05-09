package com.sysmap.parrot.services.authentication;

public interface IAutheticationService {
    AuthenticateResponse authenticate(AuthenticateRequest request) throws Exception;
}
