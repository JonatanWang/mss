package se.cygni.mss.tsv.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@AllArgsConstructor
@Data
@ToString
public class Principal {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer principal_id;
    private String tconst;
    private String ordering;
    private String nconst;
    private String category;
    private String job;
    private String characters;

    public Principal() {}

    public Principal(String tconst, String nconst, String category, String characters) {
        this.tconst = tconst;
        this.nconst = nconst;
        this.category = category;
        this.characters = characters;
    }
}
