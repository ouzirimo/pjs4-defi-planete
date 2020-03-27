package model;

public class Challenge {

    private int id_challenge = 1; //nécessaire??
    private String name_challenge;
    private String description_challenge;
    private String type_challenge;
    private int xp_challenge;

    public Challenge(int id, String n, String d, String t, int xp){

         this.id_challenge = id;
         this.description_challenge = d;
         this.name_challenge = n;
         this.type_challenge = t;
         this.xp_challenge = xp;

    }

    public int getId_challenge() {

        return this.id_challenge;
    }

    public String getName_challenge(){

        return this.name_challenge;
    }

    public String getDescription_challenge(){

        return this.description_challenge;
    }

    public String getType_challenge(){

        return this.type_challenge;
    }

    public int getXp_challenge(){

        return this.xp_challenge;
    }

    public String toString(){

        String s = "DetailChallenge numéro " + this.id_challenge + " " + this.name_challenge + "catégorie: " + this.type_challenge + " Objectif: " + this.description_challenge + " " + this.xp_challenge + " XP";
        return s;
    }
}
