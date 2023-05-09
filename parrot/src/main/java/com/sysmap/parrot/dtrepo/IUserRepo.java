package com.sysmap.parrot.dtrepo;

import com.sysmap.parrot.entities.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import java.util.UUID;

public interface IUserRepo extends MongoRepository<User, UUID> {
    Optional<User> findUserByEmail(String email);
    Optional<User> findUserById(UUID id);
}
