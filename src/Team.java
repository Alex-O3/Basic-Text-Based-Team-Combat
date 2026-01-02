import java.util.ArrayList;
import java.util.Scanner;

public class Team {
    private static Scanner scnr = new Scanner(System.in);
    private String name = "No Name";
    private ArrayList<Person> Members;
    private ArrayList<Item> Items;
    private static int numTeams = 0;
    private int defaultLevel;
    private boolean isAttacked;
    public Team() {
        name = "No Name";
        Members = new ArrayList<Person>();
        Items = new ArrayList<Item>();
        defaultLevel = 0;
        isAttacked = true;
    }
    public Team(String name, int defaultLevel) {
        this.name = name;
        this.defaultLevel = defaultLevel;
        Members = new ArrayList<Person>();
        Items = new ArrayList<Item>();
        isAttacked = true;
    }
    public String getName() {
        return(name);
    }
    public int getDefaultLevel() {
        return(defaultLevel);
    }
    public ArrayList<Person> getMembers() {
        return(Members);
    }
    public ArrayList<Item> getItems() {
        return(Items);
    }
    public boolean getIsAttacked() {
        return(isAttacked);
    }
    public void setIsAttacked(boolean inputIsAttacked) {
        isAttacked = inputIsAttacked;
    }
    public void setName(String inputName) {
        name = inputName;
    }
    public int size() {
        return(Members.size());
    }
    public void print() {
        System.out.println("\n\nTeam \"" + name +"\", size: " + Members.size() + ", default level: " + defaultLevel);
        for (int i = 0; i < Members.size(); i = i + 1) {
            Members.get(i).print();
        }
    }
    public Person findPerson(String inputName) {
        int indexOfOutput = -1;
        for (int i = 0; i < Members.size(); i = i + 1) {
            if (Members.get(i).getName().equals(inputName) == true && Members.get(i).getHealth() > 0.0 && ((Members.get(i).getCooldown() == 0 && Members.get(i).getIsShielded() == false) || isAttacked == true)) {
                indexOfOutput = i;
                break;
            }
        }
        Person output = TeamCombat.noOne;
        if (indexOfOutput != -1) {
            output = Members.get(indexOfOutput);
        }
        return(output);
    }
    public Person get(int index) {
        return(Members.get(index));
    }
    public int checkForDeaths() {
        int output = Members.size();
        for (int i = 0; i < Members.size(); i = i + 1) {
            if (Members.get(i).getHealth() <= 0.0) {
                output = output - 1;
            }
        }
        return(output);
    }
    public void loadTeam() {
        numTeams = numTeams + 1;
        System.out.print("Enter a name for team #" + numTeams + ": ");
        name = scnr.next();
        System.out.print("Default level for team \"" + name + "\": ");
        defaultLevel = scnr.nextInt();
        if (defaultLevel < 0) {
            defaultLevel = 0;
        }
        boolean isDone = false;
        boolean firstTime = true;
        while (isDone == false) {
            if (firstTime == false) {
                System.out.print("Add a person to the team \"" + name +"\"(y/n)? ");
            }
            if (firstTime == false && scnr.next().charAt(0) != 'y') {
                isDone = true;
                break;
            }
            else {
                firstTime = false;
                Members.add(new Person());
                System.out.print("Name: ");
                Members.get(Members.size() - 1).setName(scnr.next());
                System.out.print("Age: ");
                Members.get(Members.size() - 1).setAge(scnr.nextInt());
                System.out.print("Gender (m/f): ");
                if (scnr.next().charAt(0) == 'm') {
                    Members.get(Members.size() - 1).setGender(true);
                }
                else {
                    Members.get(Members.size() - 1).setGender(false);
                }
                Members.get(Members.size() - 1).setLevel(defaultLevel);
                Members.get(Members.size() - 1).setHealth(Members.get(Members.size() - 1).getMaxHealth());
            }
        }
    }
    public void shop() {
        Items.add(new Item());
        int teamLevel = 0;
        for (int i = 0; i < Members.size(); i = i + 1) {
            teamLevel = teamLevel + Members.get(i).getLevel();
        }
        teamLevel = teamLevel / Members.size();
        teamLevel = teamLevel + 1;
        Items.get(Items.size() - 1).shop(teamLevel);
    }
}