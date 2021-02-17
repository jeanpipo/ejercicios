import java.util.concurrent.Callable;
final public  class BirdExercise {
    public static void main(String[] args) throws Exception {
        Chicken chicken = new Chicken();
        Parrot parrot = new Parrot();

        Egg parrotEgg = parrot.lay();
        Egg chickenEgg = chicken.lay();
        System.out.println(String.format("chicken implements bird %s", chicken instanceof Bird));
        System.out.println(String.format("egg was from a %s", chickenEgg.hatch().getClass()));
        System.out.println(String.format("egg was from a %s", parrotEgg.hatch().getClass()));
        System.out.println(String.format("egg was from a %s", parrotEgg.hatch()));
    }
}

interface Bird {
    Egg lay();
}

class Chicken implements Bird, Callable<Bird> {
    public Chicken() {
    }


    @Override
    public Egg lay() {
        return new Egg(new Chicken());
    }


    @Override
    public Bird call() throws Exception {
        return new Chicken();
    }
}

class Parrot implements Bird, Callable<Bird> {
    public Parrot() {
    }


    @Override
    public Egg lay() {
        return new Egg(new Parrot());
    }


    @Override
    public Bird call() throws Exception {
        return new Parrot();
    }
}

class Egg {
    private Callable<Bird> bird;
    private boolean isHatched;
    public Egg(Callable<Bird> createBird) {
        this.bird = createBird;
        this.isHatched = false;
    }

    public Bird hatch() throws Exception {
        if (getIsHatched()) {
            throw new IllegalStateException("opsss you can't hatch a bird more than one time");
        }
        setIsHatched(true);
        return this.getBird().call();
    }

    public Callable<Bird> getBird() {
        return this.bird;
    }

    public boolean getIsHatched() {
       return this.isHatched;
    }

    public boolean setIsHatched(boolean isHatched) {
        return this.isHatched = isHatched;
    }
}

