package gameResult;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Leaderboard {
    static LocalDateTime startingDate;
    static long startTime;
    static long endTime;
    static boolean didwin;
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("minesweeper");

    public static void makeNewResult(String name){
        endTime = System.currentTimeMillis();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        Result result = new Result();

        result.setName(name);
        result.setSecondsTaken( (endTime - startTime) / 1000F);
        result.setStartingDate(startingDate.format(formatter) );
        result.setDidwin(didwin);
        saveResult(result);
    }

    private static void saveResult( Result result){
        EntityManager em = emf.createEntityManager();
        try {
                em.getTransaction().begin();
                em.persist(result);
                em.getTransaction().commit();

        } finally{
            em.close();
        }
    }

    public static List<Result> getResults() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT r FROM Result r WHERE r.didwin = true ORDER BY r.secondsTaken", Result.class)
                    .setMaxResults(10)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public static void initStartingDate(){
        startingDate = LocalDateTime.now();
        startTime = System.currentTimeMillis();
    }

    public static void setDidwin(boolean didwin) {
        Leaderboard.didwin = didwin;
    }
}
