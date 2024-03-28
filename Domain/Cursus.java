package Domain;

 
public class Cursus {
    private String cursusNaam;
    private String cursusInteressant;
    private String onderwerp;
    private String introductie;
    private Niveau niveau;
 
    public Cursus(String cursusNaam,  String onderwerp, String introductie, Niveau niveau, String cursusInteressant) {
        this.cursusNaam = cursusNaam;
        this.cursusInteressant = cursusInteressant;
        this.onderwerp = onderwerp;
        this.introductie = introductie;
        this.niveau = niveau;
    }

    //Constructor voor de Cursusoverzicht klasse
    public Cursus(String cursusNaam) {
        this.cursusNaam = cursusNaam;
    }

    //Ook een constructor voor in de cursusTable in de CursusOverzicht klasse
    public Cursus(String cursusNaam, String onderwerp, String introductie, Niveau niveau) {
        this.cursusNaam = cursusNaam;
        this.onderwerp = onderwerp;
        this.introductie = introductie;
        this.niveau = niveau;
    }

    public String getCursusNaam() {
        return cursusNaam;
    }

    public String getCursusInteressant() {
        return cursusInteressant;
    } 
    public String getOnderwerp() {
        return onderwerp;
    }
 
    public String getIntroductie() {
        return introductie;
    }
 
    public Niveau getNiveau() {
        return niveau;
    }

    public void setCursusNaam(String cursusNaam) {
        this.cursusNaam = cursusNaam;
    }


    public void setCursusInteressant(String cursusInteressant) {
        this.cursusInteressant = cursusInteressant;
    }

    public void setOnderwerp(String onderwerp) {
        this.onderwerp = onderwerp;
    }

    public void setIntroductie(String introductie) {
        this.introductie = introductie;
    }

    public void setNiveau(Niveau niveau) {
        this.niveau = niveau;
    }

    @Override
    public String toString() {
        return cursusNaam;
    }


}
