package se.cygni.mss.tsv.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class Rating {

    private String tconst;
    private String averageRating;
    private String numVotes;
}