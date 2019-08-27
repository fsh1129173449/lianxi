package com.fush.mapper.db2;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.fush.domain.User;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Mapper

public interface UserMapper{
    /**
     * 根据用户名获取用户信息
     *
     * @param userName
     * @return
     */
    User findByName(@Param("userName") String userName);
}
