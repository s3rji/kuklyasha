package ru.serji.kuklyasha.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.transaction.annotation.*;
import ru.serji.kuklyasha.model.*;

import java.util.*;

@Transactional(readOnly = true)
public interface UserInfoRepository extends BaseRepository<UserInfo> {

    @Query("SELECT ui FROM UserInfo ui WHERE ui.user = :user")
    Optional<UserInfo> findByUser(User user);
}