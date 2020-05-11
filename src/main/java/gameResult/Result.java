package gameResult;

import javax.persistence.*;
import lombok.Data;

@Entity
@Data
public class Result {
        @Id
        @GeneratedValue
        private Long id;

        @Column( nullable = false)
        private String name;

        @Column( nullable = false)
        private String startingDate;

        @Column( nullable = false)
        private float secondsTaken;

        @Column( nullable = false)
        private boolean didwin;
        
}
