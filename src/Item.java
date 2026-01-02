import java.util.Scanner;

public class Item {
    private static final Scanner scnr = new Scanner(System.in);
    private int itemID;
    private String name;
    private double effect;
    public Item() {
        itemID = 0;
    }
    public Item(int inputID) {
        itemID = inputID;
        name = "No Name";
        effect = 0.0;
        setTraits();
    }
    public String getName() {
        return(name);
    }
    public int getItemID() {
        return(itemID);
    }
    public void setName(String inputName) {
        name = inputName;
    }
    public void setItemID(int inputID) {
        itemID = inputID;
        setTraits();
    }
    public void use(int yourTeam, int enemyTeam) {

        String allyName;
        switch (itemID) {
            case 0:
            case 1:
            case 2:
            case 3:
                System.out.print("Select a teammate: ");
                allyName = scnr.next();
                if (TeamCombat.Teams.get(yourTeam).findPerson(allyName).getIsPerson()) {
                    double newHealth = TeamCombat.Teams.get(yourTeam).findPerson(allyName).getHealth() + (TeamCombat.Teams.get(yourTeam).findPerson(allyName).getMaxHealth() * effect);
                    TeamCombat.Teams.get(yourTeam).findPerson(allyName).setHealth(newHealth);
                }
                break;
            case 4:
            case 5:
            case 6:
                System.out.print("Select a teammate: ");
                allyName = scnr.next();
                if (TeamCombat.Teams.get(yourTeam).findPerson(allyName).getIsPerson()) {
                    TeamCombat.Teams.get(yourTeam).findPerson(allyName).setDefense(effect);
                    TeamCombat.Teams.get(yourTeam).findPerson(allyName).setEffectTurns(0,3);
                }
                break;
            case 7:
                System.out.print("Select a teammate: ");
                allyName = scnr.next();
                if (TeamCombat.Teams.get(yourTeam).findPerson(allyName).getIsPerson()) {
                    Person selectedPerson = TeamCombat.Teams.get(yourTeam).findPerson(allyName);
                    double newDefense =  selectedPerson.getHealth() / (-0.5 * selectedPerson.getMaxHealth());
                    int enemyLevel = 0;
                    for (int i = 0; i < TeamCombat.Teams.get(enemyTeam).size(); i = i + 1) {
                        enemyLevel = enemyLevel + TeamCombat.Teams.get(enemyTeam).get(i).getLevel();
                    }
                    enemyLevel = enemyLevel / TeamCombat.Teams.get(enemyTeam).size();
                    double newDefenseMax = (((double) enemyLevel * 2.5) / 5.0) + 10.0;
                    newDefense = newDefense * newDefenseMax + newDefenseMax;
                    TeamCombat.Teams.get(yourTeam).findPerson(allyName).setDefense(newDefense);
                    TeamCombat.Teams.get(yourTeam).findPerson(allyName).setEffectTurns(1,5);
                }
                break;
            case 8:
                System.out.print("Select a teammate: ");
                allyName = scnr.next();
                if (TeamCombat.Teams.get(yourTeam).findPerson(allyName).getIsPerson()) {
                    TeamCombat.Teams.get(yourTeam).findPerson(allyName).setDefense(effect * TeamCombat.Teams.get(yourTeam).findPerson(allyName).getMaxHealth());
                    TeamCombat.Teams.get(yourTeam).findPerson(allyName).setEffectTurns(0,3);
                }
                break;
            case 9:
            case 10:
                System.out.print("Select a teammate: ");
                allyName = scnr.next();
                if (TeamCombat.Teams.get(yourTeam).findPerson(allyName).getIsPerson()) {
                    TeamCombat.Teams.get(yourTeam).findPerson(allyName).setDodgeChance((int)effect);
                    TeamCombat.Teams.get(yourTeam).findPerson(allyName).setEffectTurns(2,3);
                }
                break;
            case 11:
                System.out.print("Select a teammate: ");
                allyName = scnr.next();
                if (TeamCombat.Teams.get(yourTeam).findPerson(allyName).getIsPerson()) {
                    Person selectedPerson = TeamCombat.Teams.get(yourTeam).findPerson(allyName);
                    int newDodgeChance = (int)(selectedPerson.getHealth() / (-0.5 * selectedPerson.getMaxHealth()));
                    int newDodgeChanceMax = 50;
                    newDodgeChance = newDodgeChanceMax * newDodgeChance + newDodgeChanceMax;
                    TeamCombat.Teams.get(yourTeam).findPerson(allyName).setDodgeChance(newDodgeChance);
                    TeamCombat.Teams.get(yourTeam).findPerson(allyName).setEffectTurns(3,5);
                }
                break;
        }
    }
    public void shop(int teamLevel) {
        itemID = -1;
        System.out.println("1: Health Potions");
        System.out.println("2: Defense Potions");
        System.out.println("3: Shadow Potions");
        System.out.println("4: Exit Shop");
        System.out.print("Enter Choice: ");
        int input1 = scnr.nextInt();
        if (input1 < 1 || input1 > 3) {
            input1 = 4;
        }
        int input2;
        int cost = 0;
        System.out.println();
        switch (input1) {
            case 1:
                System.out.println("1: Health Potion. Heals 10% health. " + teamLevel + " bones");
                System.out.println("2: Health Potion +1. Heals 25% health. " + 2 * teamLevel + " bones");
                System.out.println("3: Health Potion +2. Heals 50% health. " + 9 * teamLevel + " bones");
                System.out.println("4: Health Potion +3. Heals 75% health. " + 14 * teamLevel + " bones");
                System.out.println("5: Exit Shop");
                System.out.print("Enter Choice: ");
                input2 = scnr.nextInt();
                if (input2 < 1 || input2 > 4) {
                    System.out.println("Exiting Shop...");
                }
                else {
                    cost = switch (input2) {
                        case 1 -> teamLevel;
                        case 2 -> 2 * teamLevel;
                        case 3 -> 9 * teamLevel;
                        case 4 -> 14 * teamLevel;
                        default -> cost;
                    };
                    if (TeamCombat.getNumBones() >= cost) {
                        itemID = input2 - 1;
                        TeamCombat.setNumBones(TeamCombat.getNumBones() - cost);
                    }
                    else {
                        System.out.println("Not enough bones.");
                    }
                }
                break;
            case 2:
                System.out.println("1: Standard Defense Potion. Defense at 5 for three turns. " + teamLevel + " bones");
                System.out.println("2: Standard Defense Potion +1. Defense at 10 for three turns. " + 2 * teamLevel + " bones");
                System.out.println("3: Standard Defense Potion +2. Defense at 20 for three turns. " + 5 * teamLevel + " bones");
                System.out.println("4: Last Straw Defense Potion. Higher defense for lower health. " + 10 * teamLevel + " bones");
                System.out.println("5: Scaling Defense Potion. Defense at 5 per hundred health. " + 10 * teamLevel + " bones");
                System.out.println("6: Exit Shop");
                System.out.print("Enter Choice: ");
                input2 = scnr.nextInt();
                if (input2 < 1 || input2 > 5) {
                    System.out.println("Exiting Shop...");
                }
                else {
                    cost = switch (input2) {
                        case 1 -> teamLevel;
                        case 2 -> 2 * teamLevel;
                        case 3 -> 5 * teamLevel;
                        case 4, 5 -> 10 * teamLevel;
                        default -> cost;
                    };
                    if (TeamCombat.getNumBones() >= cost) {
                        itemID = input2 + 3;
                        TeamCombat.setNumBones(TeamCombat.getNumBones() - cost);
                    }
                    else {
                        System.out.println("Not enough bones.");
                    }
                }
                break;
            case 3:
                System.out.println("1: Standard Shadow Potion. 10% dodge chance for three turns. " + 4 * teamLevel + " bones");
                System.out.println("2: Standard Shadow Potion +1. 20% dodge chance for three turns. " + 8 * teamLevel + " bones");
                System.out.println("3: Last Straw Shadow Potion. Higher dodge chance for lower health. " + 10 * teamLevel + " bones");
                System.out.println("4: Exit Shop");
                System.out.print("Enter Choice: ");
                input2 = scnr.nextInt();
                if (input2 < 1 || input2 > 3) {
                    System.out.println("Exiting Shop...");
                }
                else {
                    cost = switch (input2) {
                        case 1 -> 4 * teamLevel;
                        case 2 -> 8 * teamLevel;
                        case 3 -> 10 * teamLevel;
                        default -> cost;
                    };
                    if (TeamCombat.getNumBones() >= cost) {
                        itemID = input2 + 8;
                        TeamCombat.setNumBones(TeamCombat.getNumBones() - cost);
                    }
                    else {
                        System.out.println("Not enough bones.");
                    }
                }
                break;
            case 4:
            default:
                System.out.println("Exiting Shop...");
                break;
        }
        setTraits();
    }
    private void setTraits() {
        switch (itemID) {
            case 0:
                name = "Health Potion";
                effect = 0.1;
                break;
            case 1:
                name = "Health Potion +1";
                effect = 0.25;
                break;
            case 2:
                name = "Health Potion +2";
                effect = 0.5;
                break;
            case 3:
                name = "Health Potion +3";
                effect = 0.75;
                break;
            case 4:
                name = "Standard Defense Potion";
                effect = 5.0;
                break;
            case 5:
                name = "Standard Defense Potion +1";
                effect = 10.0;
                break;
            case 6:
                name = "Standard Defense Potion +2";
                effect = 20.0;
                break;
            case 7:
                name = "Last Straw Defense Potion";
                effect = 5.0;
                break;
            case 8:
                name = "Scaling Defense Potion";
                effect = 0.05;
                break;
            case 9:
                name = "Standard Shadow Potion";
                effect = 10;
                break;
            case 10:
                name = "Standard Shadow Potion +1";
                effect = 20;
                break;
            case 11:
                name = "Last Straw Shadow Potion";
                effect = 20;
                break;
            case -1:
            default:
                name = "Empty";
                break;
        }
    }
}
