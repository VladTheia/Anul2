class Binding{
    public static void main(String args[]) {
        Hero h1 = new Warrior(), h2 = new Ninja();
        Hero h3 = new Rogue();
        BadLuck bl = new StormFire();
        bl.execute(h1);
        bl.execute(h2);
        bl.execute(h3);
    }
}

abstract class BadLuck {
    abstract void execute(Hero h);
    abstract void execute(Warrior w);
    abstract void execute(Ninja n);
    abstract void execute(Rogue r);
}

class StormFire extends BadLuck {
    public void execute(Hero h) {
        h.execute();
    }
    public void execute(Warrior w) {
        w.execute();
    }
    public void execute(Ninja n) {
        n.execute();
    }
    public void execute(Rogue r) {
        r.execute();
    }
}

abstract class Hero {
    abstract void execute();
}

class Warrior extends Hero {
    public void execute() {
        System.out.println("Warrior executed");
    }
}

class Ninja extends Hero {
    public void execute() {
        System.out.println("Ninja executed");
    }
}

class Rogue extends Hero {
    public void execute() {
        System.out.println("Rogue executed");
    }
}