package Domain;

import java.sql.Date;

public abstract class ContentItem {
    protected int contentItemID;
    protected Cursus cursusNaam;
    protected Date publicatiedatum;
    protected Status status;
    protected String titel;
    protected String beschrijving;



    public ContentItem(int contentItemID, Cursus cursusNaam, Date publicatiedatum, Status status, String titel, String beschrijving) {
        this.contentItemID = contentItemID;
        this.cursusNaam = cursusNaam;
        this.publicatiedatum = publicatiedatum;
        this.status = status;
        this.titel = titel;
        this.beschrijving = beschrijving;
    }

     public int getContentItemID() {
        return contentItemID;
    }

    public Cursus getCursusNaam() {
        return cursusNaam;
    }

    public Date getPublicatiedatum() {
        return publicatiedatum;
    }

    public Status getStatus() {
        return status;
    }

    public String getTitel() {
        return titel;
    }

    public String getBeschrijving() {
        return beschrijving;
    }

    public abstract void cursusNaam();

    

}

