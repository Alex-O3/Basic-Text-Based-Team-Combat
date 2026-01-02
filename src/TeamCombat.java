
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

public class TeamCombat
{
    private static final int MAX_COOLDOWN = 2; //after an attack, the cooldown of the attacker is set to this
    private static final double HEAL_THRESHOLD = 0.15; //the percentage of maximum health one must be under to heal
    private static final double HEAL_MIN_THRESHOLD = 40.0; //an absolute amount of health one must be under to heal if not under the percentage
    private static final double HEAL_AMOUNT = 0.5; //the percentage of maximum health one gains when healed
    private static final double REVIVE_HEALTH = 0.15; //the percentage of maximum health one has when revived
    private static final double SHIELD_THRESHOLD = 0.25; //the percentage of maximum health under which the enemy may shield
    private static final double DEFENSE_PROTECTION = 5.0; //the amount of damage taken off for each point of defense

    private static int maxCooldown = MAX_COOLDOWN;
    private static int numBones = 100;
    private static int numPotatoes = 0;
    private static final Scanner scnr = new Scanner(System.in);
    private static final Random rand = new Random();
    public static ArrayList<Team> Teams = new ArrayList<>();
    private static boolean programExit = false;
    private static boolean inCombat = false;
    public static Person noOne = new Person();

    public static void main(String[] args) {
        Team Goofballs = new Team("Goofballs", 5);
        Goofballs.getMembers().add(new Person("Dilly", 15, true));
        Goofballs.get(0).setLevel(10);
        Goofballs.getMembers().add(new Person("Alex", 16, true));
        Goofballs.get(1).setLevel(5);
        Goofballs.getMembers().add(new Person("Sherby", 2, true));
        Goofballs.get(2).setLevel(5);
        Teams.add(Goofballs);
        Team Deatheaters = new Team("Deatheaters", 20);
        Deatheaters.getMembers().add(new Person("Voldemort", 90, true));
        Deatheaters.get(0).setLevel(25);
        Deatheaters.getMembers().add(new Person("Bellatrix", 35, false));
        Deatheaters.get(1).setLevel(20);
        Teams.add(Deatheaters);


        System.out.println("All inputs for names should have no spaces in between words and should not be duplicates of each other.\nAll character or number specific prompts should only be answered within those options.\nIf you are prompted to answer with two characters and do so with a different character, the second option will be assumed.");
        System.out.println("If you do not pick a choice, oftentimes the computer will choose for you.");
        noOne.setIsPerson(false);
        while (programExit == false) {
            displayMenu();
        }



    }
    public static int getNumBones() {
        return(numBones);
    }
    public static void setNumBones(int inputNumBones) {
        numBones = inputNumBones;
    }
    public static void displayTeams() {
        for (int i = 0; i < Teams.size(); i = i + 1) {
            Teams.get(i).print();
        }
        if (Teams.size() == 0) {
            System.out.println("No teams created.");
        }
    }
    public static void addTeams() {
        boolean isDone = false;
        while (isDone == false) {
            System.out.print("Create a team (y/n)? ");
            if (scnr.next().charAt(0) != 'y') {
                isDone = true;
                break;
            }
            else {
                System.out.println();
                Teams.add(new Team());
                Teams.get(Teams.size() - 1).loadTeam();
            }
        }
    }
    public static void beginCombat() {
        if (Teams.size() < 2) {
            System.out.println("Please create at least two teams.");
        }
        else {
            //displayTeams();
            inCombat = true;
            System.out.print("\nChoose your team by name: ");
            int yourTeam = -1;
            String input = scnr.next();
            for (int i = 0; i < Teams.size(); i = i + 1) {
                if (Teams.get(i).getName().equals(input)) {
                    yourTeam = i;
                    break;
                }
            }
            if (yourTeam == -1) {
                yourTeam = 0;
            }
            System.out.print("\nChoose your enemy team by name: ");
            int enemyTeam = -1;
            input = scnr.next();
            for (int i = 0; i < Teams.size(); i = i + 1) {
                if (Teams.get(i).getName().equals(input)) {
                    enemyTeam = i;
                    break;
                }
            }
            if (enemyTeam == -1 && yourTeam != 1) {
                enemyTeam = 1;
            }
            else if (yourTeam == 1 && enemyTeam == -1) {
                enemyTeam = 0;
            }
            while (inCombat) {
                Teams.get(yourTeam).print();
                System.out.println("1: Heal");
                System.out.println("2: Attack");
                System.out.println("3: Defend");
                System.out.println("4: View Items");
                System.out.println("5: Flee");
                if (Teams.get(yourTeam).checkForDeaths() < Teams.get(yourTeam).size() && numPotatoes > 0) {
                    System.out.println("6: Revive ally");
                }
                switch (UserInput.getInt("Enter a choice: ", 1, 6)) {
                    case 1:
                        heal(yourTeam, enemyTeam);
                        break;
                    case 2:
                        attack(enemyTeam, yourTeam);

                        break;
                    case 3:
                        defend(yourTeam,enemyTeam);
                        break;
                    case 4:
                        viewItems(yourTeam, enemyTeam);
                        break;
                    case 6:
                        revive(yourTeam);
                        break;
                    case 5:
                    default:
                        System.out.println("You fled...");
                        numPotatoes = 0;
                        inCombat = false;
                        break;
                }
            }


        }
    }
    public static void viewItems(int yourTeam, int enemyTeam) {
        System.out.println();
        for (int i = 0; i < Teams.get(yourTeam).getItems().size(); i = i + 1) {
            if (Teams.get(yourTeam).getItems().get(i).getItemID() == -1) {
                Teams.get(yourTeam).getItems().remove(i);
            }
        }
        for (int i = 0; i < Teams.get(yourTeam).getItems().size(); i = i + 1) {
           System.out.println((i + 1) + ": " + Teams.get(yourTeam).getItems().get(i).getName());
        }
        if (Teams.get(yourTeam).getItems().size() > 0) {
            int input = UserInput.getInt("Enter a choice: ");
            if (input >= 1 && input <= Teams.get(yourTeam).getItems().size()) {
                input = input - 1;
                Teams.get(yourTeam).getItems().get(input).use(yourTeam, enemyTeam);
                Teams.get(yourTeam).getItems().remove(input);
                System.out.println("Item used.");
            }
            else {
                System.out.println("Item not selected.");
            }
        }
    }
    public static void defend(int yourTeam, int enemyTeam) {
        System.out.print("Shielding prevents attacking with that ally. Ally to shield: ");
        String defendName = scnr.next();
        if (Teams.get(yourTeam).findPerson(defendName).getIsPerson() == true) { //only noOne has an attack of 0.0
            if (Teams.get(yourTeam).findPerson(defendName).getIsShielded() == false) {
                Teams.get(yourTeam).findPerson(defendName).setIsShielded(true);
                System.out.println("You shielded " + Teams.get(yourTeam).findPerson(defendName).getName() + ". They can no longer attack.");
                cooldowns(yourTeam, "No Name",false);
                if (rand.nextInt(2) == 1) {
                    enemyShield(yourTeam,enemyTeam);
                }
                else {
                    enemyHeal(yourTeam,enemyTeam);
                }
            }
            else {
                Teams.get(yourTeam).findPerson(defendName).setIsShielded(false);
                System.out.println("You unshielded " + Teams.get(yourTeam).findPerson(defendName).getName() + ".");
            }

        }
        else {
            System.out.println("You shielded no one. They can no longer heal or attack");
        }
    }
    public static void enemyShield(int yourTeam, int enemyTeam) {
        Team availableToShield = new Team(Teams.get(enemyTeam).getName(), Teams.get(enemyTeam).getDefaultLevel());
        for (int i = 0; i < Teams.get(enemyTeam).size(); i = i + 1) {
            if (Teams.get(enemyTeam).get(i).getIsShielded() == false && Teams.get(enemyTeam).get(i).getHealth() > 0.0 && Teams.get(enemyTeam).get(i).getHealth() <= SHIELD_THRESHOLD * Teams.get(enemyTeam).get(i).getMaxHealth()) {
                availableToShield.getMembers().add(Teams.get(enemyTeam).get(i));
            }
        }
        if (availableToShield.size() > 0) {
            int toShieldIndex = rand.nextInt(availableToShield.size());
            String selectedToShield = availableToShield.get(toShieldIndex).getName();
            Teams.get(enemyTeam).findPerson(selectedToShield).setIsShielded(true);
            System.out.println(selectedToShield + " shielded.");
            cooldowns(enemyTeam, "No Name",false);
            effects(yourTeam, enemyTeam);
        }
        else {
            enemyHeal(yourTeam,enemyTeam);
        }
        availableToShield = null;
    }
    public static void enemyUnshield(int yourTeam, int enemyTeam) {
        Team availableToUnshield = new Team(Teams.get(enemyTeam).getName(), Teams.get(enemyTeam).getDefaultLevel());
        for (int i = 0; i < Teams.get(enemyTeam).size(); i = i + 1) {
            if (Teams.get(enemyTeam).get(i).getIsShielded() == true && Teams.get(enemyTeam).get(i).getHealth() > 0.0) {
                availableToUnshield.getMembers().add(Teams.get(enemyTeam).get(i));
            }
        }
        if (availableToUnshield.size() > 0) {
            int toUnshieldIndex = rand.nextInt(availableToUnshield.size());
            String selectedToUnshield = availableToUnshield.get(toUnshieldIndex).getName();
            Teams.get(enemyTeam).findPerson(selectedToUnshield).setIsShielded(false);
            System.out.println(selectedToUnshield + " unshielded.");
        }
        enemyAttack(yourTeam,enemyTeam);
        availableToUnshield = null;
    }
    public static void revive(int yourTeam) {
        if (Teams.get(yourTeam).checkForDeaths() < Teams.get(yourTeam).size() && numPotatoes > 0) {
            System.out.print("Ally to revive: ");
            String reviveName = scnr.next();
            for (int i = 0; i < Teams.get(yourTeam).size(); i = i + 1) {
                if (Teams.get(yourTeam).get(i).getName().equals(reviveName) && Teams.get(yourTeam).get(i).getHealth() <= 0.0) {
                    Teams.get(yourTeam).get(i).setHealth(Teams.get(yourTeam).get(i).getMaxHealth()  * REVIVE_HEALTH);
                    numPotatoes = numPotatoes - 1;
                    break;
                }
            }
        }
    }
    public static void heal(int yourTeam, int enemyTeam) {
        System.out.print("Enter the name of an ally to heal: ");
        String allyName = scnr.next();
        Person selectedPerson = Teams.get(yourTeam).findPerson(allyName);
        if (selectedPerson.getIsPerson() == false) {
            System.out.println("You healed no one for 0.0 health.");
        }
        else if (selectedPerson.getHealth() <= HEAL_MIN_THRESHOLD || selectedPerson.getHealth() <= HEAL_THRESHOLD * selectedPerson.getMaxHealth()) {
            double newHealth = selectedPerson.getHealth() + (selectedPerson.getMaxHealth() * HEAL_AMOUNT);
            selectedPerson.setHealth(newHealth);
            System.out.println("You healed " + selectedPerson.getName() + " for " + (selectedPerson.getMaxHealth() * HEAL_AMOUNT) + " health.");
            cooldowns(yourTeam, "No Name",false);
            selectedPerson = null;
            if (rand.nextInt(4) == 1) {
                enemyAttack(yourTeam,enemyTeam);
            }
            else {
                enemyHeal(yourTeam, enemyTeam);
            }
        }
        else {
            System.out.println("Ally didn't have low enough health.");
        }
    }
    public static void enemyHeal(int yourTeam, int enemyTeam) {
        Team availablePatients = new Team(Teams.get(enemyTeam).getName(),Teams.get(enemyTeam).getDefaultLevel());
        for (int i = 0; i < Teams.get(enemyTeam).size(); i = i + 1) {
            if (Teams.get(enemyTeam).get(i).getHealth() <= HEAL_MIN_THRESHOLD || Teams.get(enemyTeam).get(i).getHealth() <= Teams.get(enemyTeam).get(i).getMaxHealth() * HEAL_THRESHOLD) {
                if (Teams.get(enemyTeam).get(i).getHealth() > 0) {
                    availablePatients.getMembers().add(Teams.get(enemyTeam).get(i));
                }
            }
        }
        if (availablePatients.size() > 0) {
            int patientIndex = rand.nextInt(availablePatients.size());
            String selectedPatient = availablePatients.get(patientIndex).getName();
            double newHealth = Teams.get(enemyTeam).findPerson(selectedPatient).getHealth() + (Teams.get(enemyTeam).findPerson(selectedPatient).getMaxHealth() * HEAL_AMOUNT);
            Teams.get(enemyTeam).findPerson(selectedPatient).setHealth(newHealth);
            System.out.println(Teams.get(enemyTeam).findPerson(selectedPatient).getName() + " healed for " + (Teams.get(enemyTeam).findPerson(selectedPatient).getMaxHealth() * HEAL_AMOUNT) + " health.");
            cooldowns(enemyTeam, "No Name",false);
            effects(yourTeam, enemyTeam);
        }
        else {
            enemyAttack(yourTeam,enemyTeam);
        }
        availablePatients = null;
    }
    public static void attack(int enemyTeam, int yourTeam) {
        String opponentName = "No Name";
        String allyName = "No Name";
        int numAttacks = 1;
        if (Teams.get(yourTeam).size() != 0) {
            numAttacks = rand.nextInt(Teams.get(yourTeam).size()) + 1;
        }
        System.out.print("You get " + numAttacks + " attack(s). ");
        for (int i = 0; i < numAttacks; i = i + 1) {
            Teams.get(enemyTeam).print();
            System.out.print("Enter the name of an opponent to attack: ");
            opponentName = scnr.next();
            Teams.get(yourTeam).print();
            System.out.print("Enter the name of an ally to attack with: ");
            allyName = scnr.next();
            if (Teams.get(yourTeam).findPerson(allyName).getIsPerson() == false) {
                System.out.println("No one attacked.");
                cooldowns(yourTeam, "No Name", false);
            }
            else if (Teams.get(enemyTeam).findPerson(opponentName).getIsPerson() == false) {
                System.out.println(allyName + " attacked no one.");
                cooldowns(yourTeam, "No Name", false);
            }
            else {
                Teams.get(yourTeam).setIsAttacked(false);
                calcDamage(Teams.get(enemyTeam).findPerson(opponentName),Teams.get(yourTeam).findPerson(allyName),true);
                cooldowns(yourTeam, allyName,true);
            }
            Teams.get(yourTeam).setIsAttacked(true);
        }
        if (Teams.get(enemyTeam).checkForDeaths() == 0) {
            System.out.println("All enemies defeated!");
            int totalLevel = 0;
            for (int i = 0; i < Teams.get(enemyTeam).size(); i = i + 1) {
                totalLevel = totalLevel + Teams.get(enemyTeam).get(i).getLevel();
            }
            numBones = numBones + (2 * totalLevel) + 5;
            System.out.println("You got " + numBones + " bones that you can use to level up");
            for (int i = 0; i < Teams.get(yourTeam).size(); i = i + 1) {
                if (Teams.get(yourTeam).get(i).getHealth() <= 0.0) {
                    Teams.get(yourTeam).get(i).setHealth(Teams.get(yourTeam).get(i).getMaxHealth() * HEAL_THRESHOLD);
                }
            }
            inCombat = false;
        }
        else if (Teams.get(yourTeam).checkForDeaths() == 0) {
            System.out.println("You have been defeated.");
            inCombat = false;
        }
        else {
            if (rand.nextInt(4) == 3) {
                enemyHeal(yourTeam,enemyTeam);
            }
            else {
                enemyAttack(yourTeam,enemyTeam);
            }
        }
    }
    public static void enemyAttack(int yourTeam, int enemyTeam) {
        if (rand.nextInt(2) == 1) {
            enemyUnshield(yourTeam,enemyTeam);
        }
        else {
            Teams.get(enemyTeam).setIsAttacked(false);
            int numAttacks = rand.nextInt(Teams.get(enemyTeam).checkForDeaths()) + 1;
            for (int i = 0; i < numAttacks; i = i + 1) {
                Team availableDefendees = new Team(Teams.get(yourTeam).getName(), Teams.get(yourTeam).getDefaultLevel());
                for (int j = 0; j < Teams.get(yourTeam).size(); j = j + 1) {
                    if (Teams.get(yourTeam).get(j).getHealth() > 0.0) {
                        availableDefendees.getMembers().add(Teams.get(yourTeam).get(j));
                    }
                }
                Team availableAttackers = new Team(Teams.get(enemyTeam).getName(), Teams.get(enemyTeam).getDefaultLevel());
                for (int j = 0; j < Teams.get(enemyTeam).size(); j = j + 1) {
                    if (Teams.get(enemyTeam).get(j).getCooldown() <= 0 && Teams.get(enemyTeam).get(j).getHealth() > 0.0) {
                        availableAttackers.getMembers().add(Teams.get(enemyTeam).get(j));
                    }
                }
                int defendeeIndex;
                int attackerIndex;
                if (availableAttackers.size() > 0 && availableDefendees.size() > 0) {
                    attackerIndex = rand.nextInt(availableAttackers.size());
                    defendeeIndex = rand.nextInt(availableDefendees.size());
                    calcDamage(Teams.get(yourTeam).findPerson(availableDefendees.get(defendeeIndex).getName()), Teams.get(enemyTeam).findPerson(availableAttackers.get(attackerIndex).getName()), false);
                    cooldowns(enemyTeam, availableAttackers.get(attackerIndex).getName(), true);
                }
                else if (availableAttackers.size() == 0) {
                    cooldowns(enemyTeam, "No Name", false);
                }
                else {
                    System.out.println("No one attacked.");
                }
                availableAttackers = null;
                availableDefendees = null;
            }
            effects(yourTeam, enemyTeam);
            Teams.get(enemyTeam).setIsAttacked(true);
            if (Teams.get(yourTeam).checkForDeaths() == 0) {
                System.out.println("You have been defeated.");
            }
        }

    }
    public static void cooldowns(int inputTeam, String inputName, boolean setCooldown) {
        maxCooldown = MAX_COOLDOWN;
        if (Teams.get(inputTeam).size() < 2) {
            maxCooldown = 0;
        }
        else if (Teams.get(inputTeam).size() == 2) {
            maxCooldown = 1;
        }
        for (int j = 0; j < Teams.get(inputTeam).size(); j = j + 1) {
            if (Teams.get(inputTeam).get(j).getCooldown() > 0) {
                int newCooldown = Teams.get(inputTeam).get(j).getCooldown() - 1;
                Teams.get(inputTeam).get(j).setCooldown(newCooldown);
            }
        }
        if (setCooldown == true) {
            Teams.get(inputTeam).findPerson(inputName).setCooldown(maxCooldown);
        }
    }
    public static void effects(int yourTeam, int enemyTeam) {
        for (int i = 0; i < Teams.get(yourTeam).size(); i = i + 1) {
            for (int j = 0; j < Teams.get(yourTeam).get(i).getEffectTurns().length; j = j + 1) {
                if (Teams.get(yourTeam).get(i).getEffectTurns(j) > 0) {
                    Teams.get(yourTeam).get(i).setEffectTurns(j, Teams.get(yourTeam).get(i).getEffectTurns(j) - 1);
                }
            }
            if (Teams.get(yourTeam).get(i).getEffectTurns(0) == 0) {
                Teams.get(yourTeam).get(i).setDefense(0.0);
            }
            else if (Teams.get(yourTeam).get(i).getEffectTurns(1) > 0) {
                Teams.get(yourTeam).get(i).setEffectTurns(0, 0);
                double newDefense = Teams.get(yourTeam).get(i).getHealth() / (-0.5 * Teams.get(yourTeam).get(i).getMaxHealth());
                int enemyLevel = 0;
                for (int j = 0; j < TeamCombat.Teams.get(enemyTeam).size(); j = j + 1) {
                    enemyLevel = enemyLevel + TeamCombat.Teams.get(enemyTeam).get(j).getLevel();
                }
                enemyLevel = enemyLevel / TeamCombat.Teams.get(enemyTeam).size();
                double newDefenseMax = (((double) enemyLevel * 2.5) / 5.0) + 10.0;
                newDefense = newDefense * newDefenseMax + newDefenseMax;
                Teams.get(yourTeam).get(i).setDefense(newDefense);
            }
            if (Teams.get(yourTeam).get(i).getEffectTurns(2) == 0) {
                Teams.get(yourTeam).get(i).setDodgeChance(0);
            }
            if (Teams.get(yourTeam).get(i).getEffectTurns(3) > 0) {
                Teams.get(yourTeam).get(i).setEffectTurns(2, 0);
                int newDodgeChance = (int)(Teams.get(yourTeam).get(i).getHealth() / (-0.5 * Teams.get(yourTeam).get(i).getMaxHealth()));
                int newDodgeChanceMax = 50;
                newDodgeChance = newDodgeChanceMax * newDodgeChance + newDodgeChanceMax;
                Teams.get(yourTeam).get(i).setDodgeChance(newDodgeChance);
            }
        }
    }
    public static void levelUp() {
        int selectedTeam = 0;
        System.out.println("It takes a number of bones equivalent to your level to level up once. You have " + numBones + " bones.");
        System.out.print("Team to level up: ");
        String input1 = scnr.next();
        System.out.print("Person to level up: ");
        String input2 = scnr.next();
        int input3 = UserInput.getInt("Level up how many times? ", 0);
        for (int i = 0; i < Teams.size(); i = i + 1) {
            if (Teams.get(i).getName().equals(input1) == true) {
                Teams.get(i).setIsAttacked(true);
                int currentLevel = Teams.get(i).findPerson(input2).getLevel();
                int bonesNeeded = ((currentLevel + input3) * (currentLevel + input3 + 1) / 2) - ((currentLevel) * (currentLevel + 1) / 2);
                if (numBones >= bonesNeeded && Teams.get(i).findPerson(input2).getIsPerson() == true) {
                    numBones = numBones - bonesNeeded;
                    Teams.get(i).findPerson(input2).setLevel(currentLevel + input3);
                    //((currentLevel + input3)(currentLevel + input3 + 1) / 2) - ((currentLevel)(currentLevel + 1) / 2); this is the formula for the sum of natural numbers

                }
                else if (Teams.get(i).findPerson(input2).getIsPerson() == false) {
                    System.out.println("You didn't pick a person or they're dead");
                }
                else if (numBones < ((currentLevel + input3) * 5)) {
                    System.out.println("Not enough bones");
                }
                Teams.get(i).setIsAttacked(false);
                break;
            }
        }
    }
    public static void displayMenu() {
        System.out.println("\n");
        System.out.println("You have " + numBones + " bones.");
        System.out.println("1: View Teams");
        System.out.println("2: Add Teams");
        System.out.println("3: Combat");
        System.out.println("4: Level Up");
        System.out.println("5: Shop");
        System.out.println("6: Exit Program");
        //put more options here if added later
        switch (UserInput.getInt("Enter a choice: ", 1, 6)) {
            case 1:
                System.out.println();
                displayTeams();
                break;
            case 2:
                System.out.println();
                addTeams();
                break;
            case 3:
                System.out.println();
                beginCombat();
                break;
            case 4:
                System.out.println();
                levelUp();
                break;
            case 5:
                System.out.println();
                if (TeamCombat.Teams.size() > 0) {
                    System.out.print("Which team's items? ");
                    String input1 = scnr.next();
                    Team selectedTeam = TeamCombat.Teams.get(0);
                    int yourTeam = -1;
                    for (int i = 0; i < TeamCombat.Teams.size(); i = i + 1) {
                        if (TeamCombat.Teams.get(i).getName().equals(input1) == true) {
                            selectedTeam = TeamCombat.Teams.get(i);
                            yourTeam = i;
                            break;
                        }
                    }
                    if (yourTeam == -1) {
                        System.out.println("You did not select a team.");
                    }
                    else {
                        System.out.println("You have " + TeamCombat.getNumBones() + " bones to spend.");
                        selectedTeam.shop();
                    }
                    selectedTeam = null;
                }
                else {
                    System.out.println("Please add at least one team.");
                }

                break;
            case 6:
                programExit = true;
                break;
            //put more options here if added later
            default:
                System.out.println("Select an option in the range.");
                break;
        }
    }
    public static void calcDamage(Person defendee, Person attacker, boolean isAlly) {
        double damage = attacker.getAttack();
        if (defendee.getIsShielded() == true) {
            damage = (double) Math.round(10.0 * ((rand.nextDouble(0.3) + 0.1) * damage)) / 10.0;
        }
        if (rand.nextInt(100) + 1 <= defendee.getDodgeChance()) {
            System.out.println(defendee.getName() + " dodged the attack!");
        }
        else {
            double damageReduction = DEFENSE_PROTECTION * defendee.getDefense();
            if (damageReduction > damage) {
                damageReduction = damage;
            }
            damage = damage - damageReduction;
            double newHealth = defendee.getHealth() - damage;
            defendee.setHealth(newHealth);
            System.out.println(attacker.getName() + " hit " + defendee.getName() + " for " + damage + " points of damage!");
            if (newHealth <= 0.0 && isAlly == true) {
                numPotatoes = numPotatoes + 1;
                System.out.println("You got a potato from an enemy! Use it to revive an ally. Potatoes are dropped when the battle ends.");
            }
        }

    }
}
