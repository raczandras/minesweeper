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

public class Leaderboard {
    private static final Logger logger = LoggerFactory.getLogger(ManageField.class);

    private LocalDateTime startingDate;
    private long startTime;
    private long endTime;
    private static boolean didwin;
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("minesweeper");

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

    public void initStartingDate(){
        startingDate = LocalDateTime.now();
        startTime = System.currentTimeMillis();
    }

    public void setDidwin(boolean didwin) {
        Leaderboard.didwin = didwin;
    }
}
