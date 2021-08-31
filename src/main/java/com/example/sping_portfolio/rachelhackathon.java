import java.util.Scanner;

public class rachelhackathon {

    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);

        System.out.println("What is your name?");
        String name = scanner.nextLine();
        //asking for user input for Grade1, grade2, grade3, grade4, grade5
        System.out.println("Enter grade1: ");
        int grade1 = scanner.nextInt();

        System.out.println("Enter grade2: ");
        int grade2 = scanner.nextInt();

        System.out.println("Enter grade3: ");
        int grade3 = scanner.nextInt();

        System.out.println("Enter grade4: ");
        int grade4 = scanner.nextInt();

        System.out.println("Enter grade5: ");
        int grade5 = scanner.nextInt();

        //creating averages
        double avg12 = (grade1 + grade2) / 2.0;
        double avg34 = (grade3 + grade4) / 2.0;
        double finalAvg = (avg12 + avg34 + grade5) / 3.0;

        System.out.println("Hi "+name+"! Your final grade is "+finalAvg);
    }
}