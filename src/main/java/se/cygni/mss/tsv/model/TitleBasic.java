package se.cygni.mss.tsv.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class TitleBasic {

    private String tconst;
    private String titleType;
    private String primaryTitle;
    private String originalTitle;
    private String isAdult;
    private String startYear;
    private String endYear;
    private String runtimeMinutes;
    private String genres;

    public TitleBasic(String tconst, String primaryTitle, String originalTitle, String genres) {
        this.tconst = tconst;
        this.primaryTitle = primaryTitle;
        this.originalTitle = originalTitle;
        this.genres = genres;
    }
}