public class Person {
    private String name;
    private int age;
    private boolean isMale;
    private int level;
    private double health;
    private double maxHealth;
    private double attack;
    private int cooldown;
    private boolean isShielded;
    private double defense;
    private int dodgeChance;
    private int[] effectTurns = {0,0,0,0,0,0,0,0,0,0}; //extra space given here
    private boolean isPerson; //used to detect if inputted name exists within the team
    public Person(String inputName, int inputAge, boolean inputGender) {
        name = inputName;
        age = inputAge;
        isMale = inputGender;
        level = 0;
        maxHealth = 100.0;
        health = maxHealth;
        attack = 25.0;
        cooldown = 0;
        isShielded = false;
        defense = 0.0;
        dodgeChance = 0;
        isPerson = true;
    }
    public Person() {
        name = "no one";
        age = 0;
        isMale = true;
        level = 0;
        maxHealth = 100.0;
        health = maxHealth;
        attack = 25.0;
        cooldown = 0;
        isShielded = false;
        defense = 0.0;
        dodgeChance = 0;
        isPerson = true;
    }
    public void setEffectTurns(int index, int input) {
        effectTurns[index] = input;
    }
    public int getEffectTurns(int index) {
        return(effectTurns[index]);
    }
    public int[] getEffectTurns() {
        return(effectTurns);
    }
    public String getName() {
        return(name);
    }
    public int getAge() {
        return(age);
    }
    public boolean getGender() {
        return(isMale);
    }
    public int getLevel() {
        return(level);
    }
    public double getHealth() {
        return(health);
    }
    public double getMaxHealth() {
        return(maxHealth);
    }
    public int getCooldown() {
        return(cooldown);
    }
    public double getAttack() {
        return(attack);
    }
    public boolean getIsShielded() {
        return(isShielded);
    }
    public double getDefense() {
        return(defense);
    }
    public int getDodgeChance() {
        return(dodgeChance);
    }
    public boolean getIsPerson() {
        return(isPerson);
    }
    public void setCooldown(int inputCooldown) {
        cooldown = inputCooldown;
    }
    public void setName(String inputName) {
        name = inputName;
    }
    public void setAge(int inputAge) {
        age = inputAge;
    }
    public void setGender(boolean inputGender) {
        isMale = inputGender;
    }
    public void setLevel(int inputLevel) {
        level = inputLevel;
        maxHealth = 100.0 + (level * 10.0);
        health = maxHealth;
        attack = 25.0 + (level * 2.5);
    }
    public void setHealth(double inputHealth) {
        if (inputHealth < 0) {
            health = 0;
        }
        else if (inputHealth >= maxHealth) {
            health = maxHealth;
        }
        else {
            health = inputHealth;
        }
    }
    public void setAttack(double inputAttack) {
        attack = inputAttack;
    }
    public void setIsShielded(boolean inputIsShielded) {
        isShielded = inputIsShielded;
    }
    public void setDefense(double inputDefense) {
        defense = inputDefense;
        if (defense < 0.0) {
            defense = 0.0;
        }
    }
    public void setDodgeChance(int inputDodgeChance) {
        dodgeChance = inputDodgeChance;
    }
    public void setIsPerson(boolean inputIsPerson) {
        isPerson = inputIsPerson;
    }
    public void print() {
        if (isMale == true) {
            System.out.print(name + ", " + age + ", male. Level: ");
        }
        else {
            System.out.print(name + ", " + age + ", female. Level: ");
        }
        System.out.print(level + ". Health: " + health + "/" + maxHealth + ". Attack: " + attack);
        if (cooldown > 0) {
            System.out.print(". Cooldown: " + cooldown);
        }
        if (isShielded == true) {
            System.out.print(". Shielded");
        }
        if (defense > 0.0) {
            System.out.print(". " + defense + " defense");
        }
        if (dodgeChance > 0) {
            System.out.print(". " + dodgeChance + "% chance to dodge");
        }
        System.out.println();
    }
}