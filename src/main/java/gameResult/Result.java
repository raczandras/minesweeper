package gameResult;

import javax.persistence.*;
import lombok.Data;

/**
 * Class representing the result of a game played by a specific player.
 */
@Entity
@Data
public class Result {
        @Id
        @GeneratedValue
        private Long id;

        /**
         * The name of the player.
         */
        @Column( nullable = false)
        private String name;

        /**
         * The timestamp when the game was started.
         */
        @Column( nullable = false)
        private String startingDate;

        /**
         * The duration of the game.
         */
        @Column( nullable = false)
        private float secondsTaken;

        /**
         * Indicates whether the player has won or not.
         */
        @Column( nullable = false)
        private boolean didwin;
}
