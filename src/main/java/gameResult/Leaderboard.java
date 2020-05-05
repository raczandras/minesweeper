package gameResult;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Leaderboard {

    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("minesweeper");

    public static void makeNewResult(LocalDateTime startingDate, long startTime, long endTime){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDate = startingDate.format(formatter);
        Result result = new Result();
        float secondsTook = (endTime - startTime) / 1000F;
        String name;
        Scanner sc = new Scanner(System.in);
        System.out.println("What's your name?");
        name = sc.nextLine();

        result.setName(name);
        result.setSecondsTaken(secondsTook);
        result.setStartingDate(formattedDate);
        saveResult(result);
        getResults();
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

    private static void getResults() {
        EntityManager em = emf.createEntityManager();
        try {
            System.out.println("Displaying Leaderboard:\n");
            em.createQuery("SELECT r FROM Result r ORDER BY r.id", Result.class)
                    .getResultList().forEach(System.out::println);
        } finally {
            em.close();
        }
    }
}
