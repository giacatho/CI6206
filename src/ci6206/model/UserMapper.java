/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ci6206.model;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author nguyentritin
 */
public class UserMapper {
    public static void map(User user, ResultSet resSet) throws SQLException{
        user.setId(resSet.getInt("c_id"));
        user.setUsername(resSet.getString("c_username"));
        user.setFirstname(resSet.getString("c_firstname"));
        user.setLastname(resSet.getString("c_lastname"));
    }
}
