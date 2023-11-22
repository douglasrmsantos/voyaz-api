package io.github.dsjdevelopment.voyaz.api.domain.user;

import io.github.dsjdevelopment.voyaz.api.domain.ExceptionValidation;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository repository;


    public UserDetailData registerUser(UserRegistrationData data) {

            if (repository.existsByLoginAndActiveTrue(data.login())) {
                throw new ExceptionValidation("Email already exist in database: " + data.login());
            }
            if (repository.existsByLoginAndActiveFalse(data.login())) {
                var user = repository.getReferenceByLogin(data.login());
                user.dataUpdate(new UserUpdateData(user));
                return new UserDetailData(user);

            }
                var user = new User(data.login(), passwordEncoder.encode(data.password()));
                repository.save(user);
                return new UserDetailData(user);
        }

    public UserDetailData updateUser(UserUpdateData data) {
        if (!repository.existsByIdAndActiveTrue(data.id())) {
            throw new EntityNotFoundException("User not found in database: " + data.id());
        }
        var user = repository.getReferenceById(data.id());
        user.dataUpdate(data);
        return new UserDetailData(user);
    }

    public String deleteUser(Long id){
        if (!repository.existsById(id)) {
            throw new ExceptionValidation("User not found in database: " + id);
        }

        if (!repository.getReferenceById(id).getActive()){
            throw new ExceptionValidation("User already deleted in database: " + id);
        }

        var user = repository.getReferenceById(id);
        user.delete();

        return "User deleted successfully";

    }

    public UserDetailData detailUser(Long id) {
        if (!repository.existsById(id)) {
            throw new ExceptionValidation("User not found in database: " + id);
        }
        var user = repository.getReferenceById(id);
        return new UserDetailData(user);
    }
}
