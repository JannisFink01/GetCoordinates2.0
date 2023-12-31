package com.example.getcoordinates.Entity;

import lombok.*;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
public class Adresse {
    public String strasse;
    public int hausnummer;
    public String stadt;
    public String plz;
    public String land;
    public int leistung;
    public Koordinaten koordinaten;
    public String bundesland;

    /**
     * Konstruktor für URL erstellung
     * @param strasse
     * @param hausnummer
     * @param stadt
     * @param bundesland
     * @param plz
     * @param land
     */
    public Adresse(String strasse, int hausnummer, String stadt, String bundesland, String plz, String land) {
        this.strasse = strasse;
        this.hausnummer = hausnummer;
        this.stadt = stadt;
        this.bundesland = bundesland;
        this.plz = plz;
        this.land = land;
    }

    public String getStrasse() {
        return strasse;
    }

    public int getHausnummer() {
        return hausnummer;
    }

    public String getStadt() {
        return stadt;
    }

    public String getPlz() {
        return plz;
    }

    public String getLand() {
        return land;
    }

    public int getLeistung() {
        return leistung;
    }

    public Koordinaten getKoordinaten() {
        return koordinaten;
    }

    public String getBundesland() {
        return bundesland;
    }
}
