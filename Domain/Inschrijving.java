package Domain;

import java.time.LocalDate;

public class Inschrijving {
    private Cursus cursusNaam;
    private Cursist email;
    private LocalDate inschrijfDatum;
    private double beoordeling;
    private Medewerker naamMedewerker;

    // Constructor voor Inschrijving
    public Inschrijving(Cursus cursusNaam, Cursist email, LocalDate inschrijfDatum) {
        this.cursusNaam = cursusNaam;
        this.email = email;
        this.inschrijfDatum = inschrijfDatum;
    }

    // Constructor voor certificaat
    public Inschrijving(Cursus cursusNaam, Cursist email, double beoordeling, Medewerker naamMedewerker) {
        this.cursusNaam = cursusNaam;
        this.email = email;
        this.beoordeling = beoordeling;
        this.naamMedewerker = naamMedewerker;
    }



    public Cursus getCursusNaam() {
        return cursusNaam;
    }


    public void setCursusNaam(Cursus cursusNaam) {
        this.cursusNaam = cursusNaam;
    }


    public Cursist getEmail() {
        return email;
    }


    public void setEmail(Cursist email) {
        this.email = email;
    }


    


    public LocalDate getInschrijfDatum() {
        return inschrijfDatum;
    }


    public void setInschrijfDatum(LocalDate inschrijfDatum) {
        this.inschrijfDatum = inschrijfDatum;
    }


    public double getBeoordeling() {
        return beoordeling;
    }


    public void setBeoordeling(double beoordeling) {
        this.beoordeling = beoordeling;
    }


    public Medewerker getNaamMedewerker() {
        return naamMedewerker;
    }


    public void setNaamMedewerker(Medewerker naamMedewerker) {
        this.naamMedewerker = naamMedewerker;
    }

    @Override
    public String toString() {
        return "Inschrijving{" +
                "cursusNaam=" + cursusNaam +
                ", email=" + email +
                ", inschrijfDatum=" + inschrijfDatum +
                ", beoordeling=" + beoordeling +
                ", naamMedewerker=" + naamMedewerker +
                '}';
    }
}
