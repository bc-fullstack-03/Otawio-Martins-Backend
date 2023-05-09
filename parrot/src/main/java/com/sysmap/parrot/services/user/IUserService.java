package com.sysmap.parrot.services.user;

import com.sysmap.parrot.entities.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface IUserService {
    String createUser(CreateUserRequest request);
    FindUserResponse findUserByEmail(String email);
    User getUser(String email);
    User getUserById(UUID id);
    void uploadPhotoProfile(MultipartFile photo) throws Exception;
}
