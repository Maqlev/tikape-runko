/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.domain;

/**
 *
 * @author Miikka
 */
public class AnnosRaakaAine {
    
    private Integer annosId;
    private Integer raakaAineId;
    private Integer jarjestys;
    private String maara;
    private String ohje;

    public AnnosRaakaAine(Integer annosId, Integer raakaAineId, Integer jarjestys, String maara, String ohje) {
        this.annosId = annosId;
        this.raakaAineId = raakaAineId;
        this.jarjestys = jarjestys;
        this.maara = maara;
        this.ohje = ohje;
    }

    public Integer getAnnosId() {
        return annosId;
    }

    public Integer getRaakaAineId() {
        return raakaAineId;
    }

    public Integer getJarjestys() {
        return jarjestys;
    }

    public String getMaara() {
        return maara;
    }

    public String getOhje() {
        return ohje;
    }
}
