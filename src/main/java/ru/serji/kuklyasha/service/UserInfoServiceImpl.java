package ru.serji.kuklyasha.service;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import ru.serji.kuklyasha.model.*;
import ru.serji.kuklyasha.repository.*;

import java.util.*;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    private final UserInfoRepository userInfoRepository;

    @Autowired
    public UserInfoServiceImpl(UserInfoRepository userInfoRepository) {
        this.userInfoRepository = userInfoRepository;
    }

    @Override
    public Optional<UserInfo> get(int id) {
        return userInfoRepository.findById(id);
    }

    @Override
    public Optional<UserInfo> getByUser(User user) {
        return userInfoRepository.findByUser(user);
    }

    @Override
    public UserInfo save(UserInfo userInfo) {
        return userInfoRepository.save(userInfo);
    }

    @Override
    public void delete(int id) {
        userInfoRepository.deleteExisted(id);
    }
}