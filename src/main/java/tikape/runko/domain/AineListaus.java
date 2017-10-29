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
public class AineListaus {
    private String nimi;
    private Integer jarjestys;
    private String maara;
    private String ohje;

    public AineListaus(String nimi, String maara, String ohje) {
        this.nimi = nimi;
        this.maara = maara;
        this.ohje = ohje;
    }
    
    public String getNimi() {
        return nimi;
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
