package eu.primaris;

import java.time.LocalDate;
import java.util.Random;

public class PersonGenerator {

    private static final String[] MALE_NAMES = {"Jan", "Piotr", "Adam", "Tomasz"};
    private static final String[] FEMALE_NAMES = {"Anna", "Maria", "Katarzyna", "Agnieszka"};
    private static final String[] LAST_NAMES = {"Kowalski", "Nowak", "Wiśniewski", "Wójcik"};

    public PersonData generate(int age, String gender) {

        Random random = new Random();

        PersonData p = new PersonData();

        if (gender.equalsIgnoreCase("M")) {
            p.firstName = MALE_NAMES[random.nextInt(MALE_NAMES.length)];
        } else {
            p.firstName = FEMALE_NAMES[random.nextInt(FEMALE_NAMES.length)];
        }

        p.lastName = LAST_NAMES[random.nextInt(LAST_NAMES.length)];

        p.birthDate = LocalDate.now()
                .minusYears(age)
                .minusDays(random.nextInt(365));

        p.pesel = generatePesel(p.birthDate);

        p.documentNumber = "ABC" + (100000 + random.nextInt(900000));

        p.phone = "+48" + (500000000 + random.nextInt(99999999));

        p.email = p.firstName.toLowerCase()
                + "."
                + p.lastName.toLowerCase()
                + "@example.com";

        return p;
    }

    private String generatePesel(LocalDate birthDate) {

        String year = String.format("%02d", birthDate.getYear() % 100);
        String month = String.format("%02d", birthDate.getMonthValue());
        String day = String.format("%02d", birthDate.getDayOfMonth());

        int random = 10000 + new Random().nextInt(89999);

        return year + month + day + random;
    }
}