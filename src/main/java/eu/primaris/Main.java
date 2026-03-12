package eu.primaris;

public class Main {

    public static void main(String[] args) throws Exception {

        if (args.length < 2) {
            System.out.println("""
                    Usage:
                    mvn exec:java -Dexec.args="AGE GENDER [COUNT]"
                    
                    Examples:
                    mvn exec:java -Dexec.args="30 M"
                    mvn exec:java -Dexec.args="25 F 10"
                    """);
            return;
        }

        int age = Integer.parseInt(args[0]);
        String gender = args[1];

        int count = 1;

        if (args.length >= 3) {
            count = Integer.parseInt(args[2]);
        }

        PersonGenerator generator = new PersonGenerator();
        DatabaseService db = new DatabaseService();

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
