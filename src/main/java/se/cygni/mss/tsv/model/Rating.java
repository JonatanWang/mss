package se.cygni.mss.tsv.model;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigInteger;
import static org.springframework.data.elasticsearch.annotations.FieldType.Text;

@Document(indexName = "ratingIndex", type = "rating")
@Entity
public class Rating {

    @Id
    private BigInteger rating_id;

    @Field(type = Text, fielddata = true)
    private String tconst;

    @Field(type = Text, fielddata = true)
    private String averageRating;

    @Field(type = Text, fielddata = true)
    private String numVotes;

    public Rating () {}

    public Rating(String tconst) {
        this.tconst = tconst;
    }

    public Rating(String tconst, String averageRating, String numVotes) {
        this.tconst = tconst;
        this.averageRating = averageRating;
        this.numVotes = numVotes;
    }

    public BigInteger getId() {
        return rating_id;
    }

    public void setId(BigInteger rating_id) {
        this.rating_id = rating_id;
    }

    public String getTconst() {
        return tconst;
    }

    public void setTconst(String tconst) {
        this.tconst = tconst;
    }

    public String getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(String averageRating) {
        this.averageRating = averageRating;
    }

    public String getNumVotes() {
        return numVotes;
    }

    public void setNumVotes(String numVotes) {
        this.numVotes = numVotes;
    }

    @Override
    public String toString() {
        return "Rating{" +
                "rating_id=" + rating_id +
                ", tconst='" + tconst + '\'' +
                ", averageRating='" + averageRating + '\'' +
                ", numVotes='" + numVotes + '\'' +
                '}';
    }
}