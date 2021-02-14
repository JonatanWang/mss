package se.cygni.mss.tsv.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@AllArgsConstructor
@Data
@ToString
public class NameBasic {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer name_id;
    private String nconst;
    private String primaryName;
    private String birthYear;
    private String deathYear;
    private String primaryProfession;
    private String knownForTitles;

    public NameBasic() {}

    public NameBasic(String nconst, String primaryName, String primaryProfession, String knownForTitles) {
        this.nconst = nconst;
        this.primaryName = primaryName;
        this.primaryProfession = primaryProfession;
        this.knownForTitles = knownForTitles;
    }
}
