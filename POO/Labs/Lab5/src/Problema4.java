import java.util.Vector;

class Warrior {
    public int health;
    public String name;
    public int damage;

    public Warrior (int health, String name) {
        this.health = health;
        this.name = name;
    }

    public int getDamage() {
        return damage;
    }

    public String toString(String clasa) {
        return "Class: " + clasa + " Name: " + name + " Health: " + health +"\n";
    }
}

class SNAKE_Warrior extends Warrior {
    public SNAKE_Warrior(int health, String name) {
        super(health, name);
        damage = 10;
    }

    public String toString() {
        return super.toString("Snake");
    }
}

class OGRE_Warrior extends Warrior {
    public OGRE_Warrior(int health, String name) {
        super(health, name);
        damage = 6;
    }


    public String toString() {
        return super.toString("Ogre");
    }
}

class MARSH_Warrior extends Warrior {
    public MARSH_Warrior(int health, String name) {
        super(health, name);
        damage = 1;
    }

    public String toString() {
        return super.toString("Marshmallow");
    }
}

class WarriorPack {
    private Vector warriors ;

    public WarriorPack () {
        warriors = new Vector();
    }

    public void addWarrior(Warrior newWarrior) {
        warriors.add(newWarrior);
    }

    public Vector getWarriors() {
        return warriors ;
    }

    public int calculateDamage() {
        int damage = 0;
        for (int i = 0; i < warriors.size(); i++) {
            Warrior auxWar = (Warrior) warriors.get(i);
            damage += auxWar.getDamage();
        }
        return damage;
    }

    public String toString() {
        String result = "";
        for (int i = 0; i < warriors.size(); i++) {
            Warrior auxWar = (Warrior) warriors.get(i);
            result += auxWar.toString();
        }
        return result;
    }
}

class Test {
    public static void main(String args[]){
        WarriorPack wpack = new WarriorPack();

        OGRE_Warrior og = new OGRE_Warrior(100, "ceva");
        MARSH_Warrior og1 = new MARSH_Warrior(200, "aceva");
        SNAKE_Warrior og2 = new SNAKE_Warrior(300, "vceva");
        OGRE_Warrior og3 = new OGRE_Warrior(400, "dceva");

        wpack.addWarrior(og);
        wpack.addWarrior(og1);
        wpack.addWarrior(og2);
        wpack.addWarrior(og3);
        System.out.println(wpack);


    }
}
