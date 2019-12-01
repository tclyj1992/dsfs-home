package com.dsfs.dsfshome.util;

import com.dsfs.dsfshome.entity.User;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Created by lyj on 2019/10/29.
 */
public class Util {
    public static User getCurrentUser() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user;
    }
}
