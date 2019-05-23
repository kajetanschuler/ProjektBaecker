public class Artikel {
    private String name;
    private float durchschnittsgewicht;

    public Artikel(String name, float durchschnittsgewicht) {
        this.name = name;
        this.durchschnittsgewicht = durchschnittsgewicht;
    }

    public String getName() {
        return name;
    }

    public float getDurchschnittsgewicht() {
        return durchschnittsgewicht;
    }
}
