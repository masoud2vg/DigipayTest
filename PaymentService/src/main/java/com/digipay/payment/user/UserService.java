package com.digipay.payment.user;

import com.digipay.payment.entity.User;

import java.util.Optional;

public interface UserService {

    Optional<User> findUser(Long userId);
}
