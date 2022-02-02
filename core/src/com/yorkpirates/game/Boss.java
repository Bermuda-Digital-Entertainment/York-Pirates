package com.yorkpirates.game;

/**
* Boss Class. Extends the College class with different variables
*/
public class Boss extends College{
    public float health=220;
    public float maxHealth=220;
    public Float projectileDamage = 20f;
    public Float projectileRange = 600f;
    protected float projectileSpeed = 90;
    protected float firingRate = 2;

    /**
    * Constructor method for Boss. It simply gives the Boss a unique ID
    * @param boatID int Passes a unique ID to the Boss (to prevent an object hitting itself with a cannon ball)
    */
    public Boss(int collegeID) {
        super(collegeID);
        
    }
    
}
