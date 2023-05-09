package com.sysmap.parrot.services.user;

import com.sysmap.parrot.dtrepo.IUserRepo;
import com.sysmap.parrot.entities.User;
import com.sysmap.parrot.services.fileUpload.IFileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
public class UserService implements IUserService {

    @Autowired
    private IUserRepo _I_userRepo;
    @Autowired
    private PasswordEncoder _passwordEncoder;
    @Autowired
    private IFileUploadService _fileUploadService;


    public String createUser(CreateUserRequest request) {
        var user = new User(request.name, request.email);

        if (!_I_userRepo.findUserByEmail(request.email).isEmpty()) {
            return null;
        }

        var hash = _passwordEncoder.encode(request.password);

        user.setPassword(hash);

        _I_userRepo.save(user);

        return user.getId().toString();
    }

    public FindUserResponse findUserByEmail(String email) {
        var user = _I_userRepo.findUserByEmail(email).get();

        var response = new FindUserResponse(user.getId(), user.getName(), user.getEmail(), user.getPhotoUri());

        return response;
    }

    public User getUser(String email) {
        return _I_userRepo.findUserByEmail(email).get();
    }

    public User getUserById(UUID id) {
        return _I_userRepo.findUserById(id).get();
    }

    public void uploadPhotoProfile(MultipartFile photo) throws Exception {
        var user = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        var photoUri = "";

        try {
            var fileName = user.getId() + "." + photo.getOriginalFilename().substring(photo.getOriginalFilename().lastIndexOf(".") + 1);

            photoUri = _fileUploadService.upload(photo, fileName);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

        user.setPhotoUri(photoUri);
        _I_userRepo.save(user);
    }
}
