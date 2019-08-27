package com.fush.service.impl;

import com.fush.domain.City;
import com.fush.domain.User;
import com.fush.mapper.db1.CityMapper;
import com.fush.mapper.db2.UserMapper;
import com.fush.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userServiceImpl")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper; // 主数据源

    @Autowired
    private CityMapper cityMapper; // 从数据源

    @Override
    public User findByName(String userName) {
        User user = userMapper.findByName(userName);
        City city = cityMapper.findByName("杭州市");
        user.setCity(city);
        return user;
    }
}
