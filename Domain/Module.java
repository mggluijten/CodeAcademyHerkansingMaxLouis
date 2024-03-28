package Domain;

import java.sql.Date;

public class Module extends ContentItem {
    private int moduleID;
    private double versie;
    private int volgnummer;
    private String naamContactpersoon;
    private String emailContactpersoon;

    public Module(int moduleID, Cursus cursusNaam, Date publicatiedatum, Status status, String titel, String beschrijving, double versie, int volgnummer, String naamContactpersoon, String emailContactpersoon) {
        super(moduleID, cursusNaam, publicatiedatum, status, titel, beschrijving);
        this.moduleID = moduleID;
        this.versie = versie;
        this.volgnummer = volgnummer;
        this.naamContactpersoon = naamContactpersoon;
        this.emailContactpersoon = emailContactpersoon;
    }

    @Override
    public void cursusNaam() {
        getTitel();
    }

    public int getModuleID() {
        return moduleID;
    }

    public double getVersie() {
        return versie;
    }

    public int getVolgnummer() {
        return volgnummer;
    }

    public String getNaamContactpersoon() {
        return naamContactpersoon;
    }

    public String getEmailContactpersoon() {
        return emailContactpersoon;
    }
}
