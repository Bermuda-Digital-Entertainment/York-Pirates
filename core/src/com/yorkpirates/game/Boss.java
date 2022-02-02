package com.yorkpirates.game;

public class Boss extends College{
    public float health=220;
    public float maxHealth=220;
    public Float projectileDamage = 20f;
    public Float projectileRange = 500f;
    protected float projectileSpeed = 90;
    protected float firingRate = 3;

    public Boss(int collegeID) {
        super(collegeID);
        
    }
    
}
