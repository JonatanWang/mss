package se.cygni.mss.tsv.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class Principal {

    private String tconst;
    private String ordering;
    private String nconst;
    private String category;
    private String job;
    private String characters;

    public Principal(String tconst, String nconst, String category, String characters) {
        this.tconst = tconst;
        this.nconst = nconst;
        this.category = category;
        this.characters = characters;
    }
}
