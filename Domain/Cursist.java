package Domain;

import java.time.LocalDate;


public class Cursist {
    private String email;
    private String voornaam;
    private String achternaam;
    private LocalDate geboortedatum;
    private GeslachtCursist geslacht;
    private String straatnaam;
    private String huisnummer;
    private String postcode;
    private String woonplaats;
    private String land;

    public Cursist(String email, String voornaam, String achternaam, LocalDate geboortedatum, GeslachtCursist geslacht,
            String straatnaam, String huisnummer, String postcode, String woonplaats, String land) {
        this.email = email;
        this.voornaam = voornaam;
        this.achternaam = achternaam;
        this.geboortedatum = geboortedatum;
        this.geslacht = geslacht;
        this.straatnaam = straatnaam;
        this.huisnummer = huisnummer;
        this.postcode = postcode;
        this.woonplaats = woonplaats;
        this.land = land;
    }

    
    
    public Cursist(String email) {
        this.email = email;
    }


    public Cursist(String email, String voornaam, String achternaam) {
        this.email = email;
        this.voornaam = voornaam;
        this.achternaam = achternaam;
    }



    public String getEmail() {
        return email;
    }

    public String getVoornaam() {
        return voornaam;
    }

    public String getAchternaam() {
        return achternaam;
    }

    public LocalDate getGeboortedatum() {
        return geboortedatum;
    }

    public GeslachtCursist getGeslacht() {
        return geslacht;
    }

    public String getStraatnaam() {
        return straatnaam;
    }

    public String getHuisnummer() {
        return huisnummer;
    }

    public String getPostcode() {
        return postcode;
    }

    public String getWoonplaats() {
        return woonplaats;
    }

    public String getLand() {
        return land;
    }  
    
    

    public void setEmail(String email) {
        this.email = email;
    }



    public void setVoornaam(String voornaam) {
        this.voornaam = voornaam;
    }



    public void setAchternaam(String achternaam) {
        this.achternaam = achternaam;
    }



    public void setGeboortedatum(LocalDate geboortedatum) {
        this.geboortedatum = geboortedatum;
    }



    public void setGeslacht(GeslachtCursist geslacht) {
        this.geslacht = geslacht;
    }



    public void setStraatnaam(String straatnaam) {
        this.straatnaam = straatnaam;
    }



    public void setHuisnummer(String huisnummer) {
        this.huisnummer = huisnummer;
    }



    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }



    public void setWoonplaats(String woonplaats) {
        this.woonplaats = woonplaats;
    }



    public void setLand(String land) {
        this.land = land;
    }



    @Override
    public String toString() {
        return email;
    }

}
