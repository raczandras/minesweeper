package gameResult;

import javax.persistence.*;
import lombok.Data;

@Entity
@Data
public class Result {
        @Id
        @GeneratedValue
        public Long id;

        @Column()
        public String name;

        @Column()
        public String startingDate;

        @Column()
        public float secondsTaken;

        @Column()
        public boolean didwin;


}
