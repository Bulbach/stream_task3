import by.clevertec.model.House;
import by.clevertec.model.Person;
import by.clevertec.util.Util;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Period;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskTest {

    @Test
    public void testTask13() {
        List<House> houses = Util.getHouses();

        int maxChildrenAge = 18;
        int minOldAge = 65;

        List<Person> firstPriorityEvacuation = houses.stream()
                .sorted(Comparator.comparing(house -> {
                    if (house.getBuildingType().equalsIgnoreCase("Hospital")) {
                        return 0;
                    } else if (ageСomparison(house.getPersonList().get(0), maxChildrenAge, minOldAge)) {
                        return 1;
                    } else {
                        return 2;
                    }
                }))
                .flatMap(house -> house.getPersonList().stream())
                .limit(500)
                .collect(Collectors.collectingAndThen(Collectors.toList(), evacuatedPersons -> {
                    evacuatedPersons.forEach(System.out::println);
                    return evacuatedPersons;
                }));
                    System.out.println("=============Next task=========================");



        assertEquals(500,firstPriorityEvacuation.size() );
    }

    private static boolean ageСomparison(Person person, int maxChildrenAge, int minOldAge) {
        LocalDate dateOfBirth = LocalDate.parse((person.getDateOfBirth()).toString());
        LocalDate currentDate = LocalDate.now();
        int age = Period.between(dateOfBirth, currentDate).getYears();
        return (age < maxChildrenAge || age > minOldAge);
    }

}
