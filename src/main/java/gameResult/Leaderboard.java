package gameResult;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Leaderboard {

    public static Result makeNewResult(LocalDateTime startingDate, long startTime, long endTime){
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
        return result;
    }
}
