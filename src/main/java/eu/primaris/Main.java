package eu.primaris;

public class Main {

    public static void main(String[] args) throws Exception {

        if (args.length < 4) {

            System.out.println("""
                    Usage:
                    java -jar oracle-person-generator.jar AGE GENDER [COUNT] DB_URL DB_USER DB_PASSWORD
                    
                    Example:
                    java -jar oracle-person-generator.jar 30 M 10 jdbc:oracle:thin:@localhost:1521/XEPDB1 dev dev
                    """);

            return;
        }

        int age = Integer.parseInt(args[0]);
        String gender = args[1];

        int count;
        int index;

        if (args.length == 5) {
            count = 1;
            index = 2;
        } else {
            count = Integer.parseInt(args[2]);
            index = 3;
        }

        String dbUrl = args[index];
        String dbUser = args[index + 1];
        String dbPassword = args[index + 2];

        PersonGenerator generator = new PersonGenerator();
        DatabaseService db = new DatabaseService(dbUrl, dbUser, dbPassword);

        for (int i = 0; i < count; i++) {

            PersonData person = generator.generate(age, gender);

            db.savePerson(person);

            System.out.println(
                    "Created: "
                            + person.firstName
                            + " "
                            + person.lastName
                            + " PESEL=" + person.pesel
            );
        }

        System.out.println("Total created: " + count);
    }
}