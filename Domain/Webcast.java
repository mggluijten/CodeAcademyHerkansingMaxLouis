package Domain;

import java.sql.Date;
import java.sql.Time;

public class Webcast extends ContentItem {
    private String naamSpreker;
    private String organisatieNaam;
    private String webcastURL;
    private Time tijdsduur;

    public Webcast(Cursus cursusNaam, Date publicatiedatum, Status status, String titel, String beschrijving, String naamSpreker, String organisatieNaam, String webcastURL, Time tijdsduur) {
        super(0, cursusNaam, publicatiedatum, status, titel, beschrijving);
        this.naamSpreker = naamSpreker;
        this.organisatieNaam = organisatieNaam;
        this.webcastURL = webcastURL;
        this.tijdsduur = tijdsduur;
    }

    @Override
    public void cursusNaam() {
         getTitel();
    }

    public String getNaamSpreker() {
        return naamSpreker;
    }

    public String getOrganisatieNaam() {
        return organisatieNaam;
    }

    public String getWebcastURL() {
        return webcastURL;
    }

    public Time getTijdsduur() {
        return tijdsduur;
    }
}
