package Domain;

import java.time.LocalDate;

public class Medewerker {
    private String naamMedewerker;
    private String email;
    private String wachtwoord;
    private LocalDate geboortedatum;

    public Medewerker(String naamMedewerker, String email, String wachtwoord, LocalDate geboortedatum) {
        this.naamMedewerker = naamMedewerker;
        this.email = email;
        this.wachtwoord = wachtwoord;
        this.geboortedatum = geboortedatum;
    }

    public Medewerker(String naamMedewerker) {
        this.naamMedewerker = naamMedewerker;
    }
    public String getNaamMedewerker() {
        return naamMedewerker;
    }

    public void setNaamMedewerker(String naamMedewerker) {
        this.naamMedewerker = naamMedewerker;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWachtwoord() {
        return wachtwoord;
    }

    public void setWachtwoord(String wachtwoord) {
        this.wachtwoord = wachtwoord;
    }

    public LocalDate getGeboortedaum() {
        return geboortedatum;
    }

    public void setGeboortedatum(LocalDate geboortedatum) {
        this.geboortedatum = geboortedatum;
    }

    @Override
    public String toString() {
        return naamMedewerker;
    }
}