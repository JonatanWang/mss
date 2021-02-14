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
public class TitleBasic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer title_id;
    private String tconst;
    private String titleType;
    private String primaryTitle;
    private String originalTitle;
    private String isAdult;
    private String startYear;
    private String endYear;
    private String runtimeMinutes;
    private String genres;

    public TitleBasic() {}

    public TitleBasic(String tconst, String primaryTitle, String originalTitle, String genres) {
        this.tconst = tconst;
        this.primaryTitle = primaryTitle;
        this.originalTitle = originalTitle;
        this.genres = genres;
    }
}