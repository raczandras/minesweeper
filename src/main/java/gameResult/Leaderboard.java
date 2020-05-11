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
    private static LocalDateTime startingDate;

    /**
     * The timestamp when the game was started in miliseconds.
     */
    private static long startTime;

    /**
     * The timestamp when the game ended in miliseconds.
     */
    private static long endTime;

    /**
     * Indicates whether the player has won or not.
     */
    private static boolean didwin;

    private static String difficulty;

    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("minesweeper");

    /**
     * Makes a new result.
     *
     * @param name the name of the player
     */
    public static void makeNewResult(String name){
        endTime = System.currentTimeMillis();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        logger.trace("Saving result...");

        Result result = new Result();
        result.setName(name);
        result.setSecondsTaken( (endTime - startTime) / 1000F);
        result.setStartingDate(startingDate.format(formatter) );
        result.setDidwin(didwin);
        result.setDifficulty(difficulty);
        saveResult(result);
        logger.trace("Result saved.");
    }

    /**
     * Uploads the result into the database
     *
     * @param result the result that needs to be uploaded
     */
    public static void saveResult( Result result){
        EntityManager em = emf.createEntityManager();
        try {
            logger.trace("Connecting to database...");
            em.getTransaction().begin();
            em.persist(result);
            em.getTransaction().commit();
            getEasyResults();
        } finally{
            em.close();
        }
    }

    /**
     * Returns the 10 best easy difficulty results with respect to the time
     * spent for winning the game.
     *
     * @return the list of the 10 best easy difficulty results with respect
     * to the time spent for winning the game
     */
    public static List<Result> getEasyResults() {
        EntityManager em = emf.createEntityManager();
        try {
            logger.trace("Connecting to the database to get the results...");
            return em.createQuery("SELECT r FROM Result r WHERE r.didwin = true AND r.difficulty LIKE :difficulty ORDER BY r.secondsTaken", Result.class)
                    .setParameter("difficulty", "easy")
                    .setMaxResults(10)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * Returns the 10 best normal difficulty results with respect to the time
     * spent for winning the game.
     *
     * @return the list of the 10 best normal difficulty results with respect
     * to the time spent for winning the game
     */
    public static List<Result> getNormalResults() {
        EntityManager em = emf.createEntityManager();
        try {
            logger.trace("Connecting to the database to get the results...");
            return em.createQuery("SELECT r FROM Result r WHERE r.didwin = true AND r.difficulty LIKE :difficulty ORDER BY r.secondsTaken", Result.class)
                    .setParameter("difficulty", "normal")
                    .setMaxResults(10)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * Returns the 10 best hard difficulty results with respect to the time
     * spent for winning the game.
     *
     * @return the list of the 10 best hard difficulty results with respect
     * to the time spent for winning the game
     */
    public static List<Result> getHardResults() {
        EntityManager em = emf.createEntityManager();
        try {
            logger.trace("Connecting to the database to get the results...");
            return em.createQuery("SELECT r FROM Result r WHERE r.didwin = true AND r.difficulty LIKE :difficulty ORDER BY r.secondsTaken", Result.class)
                    .setParameter("difficulty", "hard")
                    .setMaxResults(10)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * Initializes the starting timestamp.
     */
    public static void initStartingDate(){
        startingDate = LocalDateTime.now();
        startTime = System.currentTimeMillis();
    }

    /**
     * Assigns true to the didwin variable if the player lost or true if the player won.
     */
    public static void setDidwin(boolean didwin) {
        Leaderboard.didwin = didwin;
    }

    /**
     * Assigns a value to the difficulty variable.
     */
    public static void setDifficulty(String difficulty) {
        Leaderboard.difficulty = difficulty;
    }
}
