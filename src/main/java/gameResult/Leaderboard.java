package gameResult;
import gameplayLogic.ManageField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Class dealing with making new results, saving the results,
 * and getting the results.
 */
public class Leaderboard {
    private static final Logger logger = LoggerFactory.getLogger(ManageField.class);

    /**
     * The timestamp when the game was started.
     */
    private LocalDateTime startingDate;

    /**
     * The timestamp when the game was started in miliseconds.
     */
    private long startTime;

    /**
     * The timestamp when the game ended in miliseconds.
     */
    private long endTime;

    /**
     * Indicates whether the player has won or not.
     */
    private static boolean didwin;

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("minesweeper");

    /**
     * Makes a new result.
     *
     * @param name the name of the player
     */
    public void makeNewResult(String name){
        endTime = System.currentTimeMillis();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        Result result = new Result();

        result.setName(name);
        result.setSecondsTaken( (endTime - startTime) / 1000F);
        result.setStartingDate(startingDate.format(formatter) );
        result.setDidwin(didwin);
        saveResult(result);
    }

    /**
     * Uploads the result into the database
     *
     * @param result the result that needs to be uploaded
     */
    public void saveResult( Result result){
        EntityManager em = emf.createEntityManager();
        try {
                em.getTransaction().begin();
                em.persist(result);
                em.getTransaction().commit();

        } finally{
            em.close();
        }
    }

    /**
     * Returns the 10 best results with respect to the time
     * spent for winning the game.
     *
     * @return the list of the 10 best results with respect
     * to the time spent for winning the game
     */
    public List<Result> getResults() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT r FROM Result r WHERE r.didwin = true ORDER BY r.secondsTaken", Result.class)
                    .setMaxResults(10)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * Initializes the starting timestamp.
     */
    public void initStartingDate(){
        startingDate = LocalDateTime.now();
        startTime = System.currentTimeMillis();
    }

    /**
     * Assigns true to the didwin variable if the player lost or true if the player won.
     */
    public void setDidwin(boolean didwin) {
        Leaderboard.didwin = didwin;
    }
}
