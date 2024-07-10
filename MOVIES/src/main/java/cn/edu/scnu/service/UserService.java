package cn.edu.scnu.service;

import cn.edu.scnu.entity.User;
import cn.edu.scnu.mapper.UserMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class UserService extends ServiceImpl<UserMapper, User> {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    public User login(String username, String pwd) {
        if (!StringUtils.hasText(username) || !StringUtils.hasText(pwd)) {
            throw new IllegalArgumentException("UserName and Password must not be null or empty");
        }
        User user = (User) redisTemplate.opsForValue().get(username);
        if (user == null) {
            user = userMapper.selectById(username);
            if (user != null && user.getPwd().equals(pwd)) {
                redisTemplate.opsForValue().set(username, user);
            } else {
                user = null;
            }
        } else if (!user.getPwd().equals(pwd)) {
            user = null;
        }

        return user;
    }

    public boolean register(String username, String pwd) {
        User existingUser = userMapper.selectById(username);
        if (existingUser != null) {
            return false;
        }

        User newuser = new User();
        newuser.setUsername(username);
        newuser.setPwd(pwd);
        save(newuser);
        return true;
    }
}
