/**
 * 
 */
package com.ak.registration.dao;

import java.util.List;

import com.ak.registration.form.User;

/**
 * @author anuragkapur
 *
 */
public interface UserDAO {
    public void addUser(User user);
    public List<User> listUser();
}
