/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ci6206.model;

import java.sql.Timestamp;

/**
 *
 * @author Michael Tan
 */
public class User {
    private Timestamp lastUpdate;
    private String username;
    private String firstname;
    private String lastname;
    private double cashBal,yrStartBal;
    public double getYrStartBal() {
		return yrStartBal;
	}

	public void setYrStartBal(double yrStartBal) {
		this.yrStartBal = yrStartBal;
	}

	private String inception;
    public String getInception() {
		return inception;
	}

	public void setInception(String inception) {
		this.inception = inception;
	}

	public Timestamp getLastUpdate() 
    {
        return lastUpdate;
    }

    public void setLastUpdate(Timestamp update) 
    {
    	lastUpdate = update;
    }
    
    public double getCashBal()
    {
    	return cashBal;
    }
    
    public void setCashBal(double amt)
    {
    	cashBal = amt;
    }
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
}
