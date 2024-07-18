package bg.softuni.campingcars.service.impl;

import bg.softuni.campingcars.repository.UserRepository;
import bg.softuni.campingcars.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;


}
