package com.pakerek.auth.user.service;

import com.pakerek.auth.email.service.EmailSenderService;
import com.pakerek.auth.exception.UsernameAlreadyExistsException;
import com.pakerek.auth.user.model.TokenType;
import com.pakerek.auth.user.model.VerificationToken;
import com.pakerek.auth.user.model.dto.UserRegisterDto;
import com.pakerek.auth.user.model.dto.UserResponseDto;
import com.pakerek.auth.user.repository.UserRepository;
import com.pakerek.auth.user.model.User;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserToUserResponseDtoMapper userToUserResponseDtoMapper;
    private final PasswordEncoder passwordEncoder;
    private final EmailSenderService emailSenderService;
    private final VerificationTokenService verificationTokenService;
    private static final String USERNAME_NOT_FOUND_MESSAGE = "Nie znaleziono użytkownika";

    @Override
    public User getUserByEmail(String email){
        return userRepository.findByEmail(email).orElseThrow(()->new UsernameNotFoundException(USERNAME_NOT_FOUND_MESSAGE));
    }

    @Override
    public UserResponseDto getUserById(Long id){
        return userRepository.findById(id).map(userToUserResponseDtoMapper::map).orElseThrow(()->new UsernameNotFoundException(USERNAME_NOT_FOUND_MESSAGE));
    }

    @Override
    public UserResponseDto getOwnUser(String email){
        return userToUserResponseDtoMapper.map(getUserByEmail(email));
    }

    public Page<User> findUsersWithPaginationAndFilter(int page, int size, String filter){
        return userRepository.findAll(PageRequest.of(page,size));
    }

    @Override
    public List<UserResponseDto> findAllUsers(){
        return userRepository.findAll().stream().map(userToUserResponseDtoMapper::map).toList();
    }

    @Override
    public UserResponseDto addUserWithDefaultRole(UserRegisterDto registrationUser){
        isUserAlreadyExists(registrationUser.getEmail());
        User user = userRepository.save(new User(registrationUser.getEmail(),passwordEncoder.encode(registrationUser.getPassword())));
        VerificationToken token = verificationTokenService.generateToken(TokenType.REGISTRATION,user);
        emailSenderService.sendActivation(user,token);
        return  userToUserResponseDtoMapper.map(user);
    }

    private void isUserAlreadyExists(String username) {
        if(userRepository.existsByEmail(username)){
            throw new UsernameAlreadyExistsException();
        }
    }

    @Override
    public void activateUser(String uuid) {
        VerificationToken token = verificationTokenService.getActivationToken(uuid);
        User user = token.getUser();
        user.setEnabled(true);
        userRepository.save(user);
    }

    @Override
    public void changePasswordRequest(String email) {
        User user = getUserByEmail(email);
        VerificationToken token = verificationTokenService.generateToken(TokenType.PASSWORD_CHANGE,user);
        emailSenderService.sendPasswordChange(user,token);
    }

    @Override
    public void changePasswordForUser(String uuid, String password) {
        VerificationToken token = verificationTokenService.getPasswordChangeToken(uuid);
        User user = token.getUser();
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }

}
