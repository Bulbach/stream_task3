package by.clevertec;

import by.clevertec.model.Animal;
import by.clevertec.model.Car;
import by.clevertec.model.Examination;
import by.clevertec.model.Flower;
import by.clevertec.model.House;
import by.clevertec.model.Person;
import by.clevertec.model.Student;
import by.clevertec.util.Util;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        task1();
        task2();
        task3();
        task4();
        task5();
        task6();
        task7();
        task8();
        task9();
        task10();
        task11();
        task12();
        task13();
        task14();
        task15();
        task16();
        task17();
        task18();
        task19();
        task20();
        task21();
        task22();
    }

    public static void task1() {
        List<Animal> animals = Util.getAnimals();
        int zooParkNumber = 3;
        animals.stream()
                .filter(animal -> animal.getAge() >= 10 && animal.getAge() <= 20)
                .sorted(Comparator.comparingInt(Animal::getAge))
                .skip((zooParkNumber - 1) * 7)
                .limit(7)
                .toList().forEach(System.out::println);
        System.out.println("=============Next task=========================");
    }

    public static void task2() {
        List<Animal> animals = Util.getAnimals();
        animals.stream()
                .filter(animal -> animal.getOrigin().equals("Japanese"))
                .peek(animal -> animal.setBread(animal.getBread().toUpperCase()))
                .filter(animal -> animal.getGender().equals("Female"))
                .map(Animal::getBread)
                .forEach(System.out::println);
        System.out.println("=============Next task=========================");
    }

    public static void task3() {
        List<Animal> animals = Util.getAnimals();
        animals.stream()
                .filter(animal -> animal.getAge() > 30)
                .map(Animal::getOrigin)
                .filter(origin -> origin.startsWith("A"))
                .distinct()
                .toList().forEach(System.out::println);
        System.out.println("=============Next task=========================");
    }

    public static void task4() {
        List<Animal> animals = Util.getAnimals();
        System.out.println(
                animals.stream()
                        .filter(animal -> animal.getGender().equalsIgnoreCase("Female"))
                        .count()
        );
        System.out.println("=============Next task=========================");
    }

    public static void task5() {
        List<Animal> animals = Util.getAnimals();
        animals.stream()
                .filter(animal -> animal.getAge() >= 20 && animal.getAge() <= 30)
                .filter(animal -> animal.getOrigin().equalsIgnoreCase("Hungarian"))
                .toList()
                .forEach(System.out::println);
        System.out.println("=============Next task=========================");
    }

    public static void task6() {
        List<Animal> animals = Util.getAnimals();
        System.out.println("Are all animals of gender Male or Female? " + animals.stream()
                .allMatch(animal -> animal.getGender().equalsIgnoreCase("Male")
                        || animal.getGender().equalsIgnoreCase("Female"))
        );
        System.out.println("=============Next task=========================");
    }

    public static void task7() {
        List<Animal> animals = Util.getAnimals();
        System.out.println(
                animals.stream()
                        .noneMatch(animal -> animal.getOrigin().equalsIgnoreCase("Oceania"))
        );
        System.out.println("=============Next task=========================");
    }

    public static void task8() {
        List<Animal> animals = Util.getAnimals();

        System.out.println(
                animals.stream()
                        .sorted(Comparator.comparing(Animal::getBread))
                        .limit(100)
                        .map(Animal::getAge)
                        .sorted()
                        .max(Integer::compareTo)
                        .get()
        );
        /*
        Я нашел два варианта решения. Очень интересно какой лучше.
        animals.stream()
                .sorted(Comparator.comparing(Animal::getBread))
                .limit(100)
                .map(Animal::getAge)
                .sorted()
                .skip(99)
                .toList().forEach(System.out::println);
         */
        System.out.println("=============Next task=========================");
    }

    public static void task9() {
        List<Animal> animals = Util.getAnimals();
        System.out.println(
                animals.stream()
                        .map(Animal::getBread)
                        .map(String::toCharArray)
                        .mapToInt(arr -> arr.length)
                        .min()
                        .orElse(0)
        );
        System.out.println("=============Next task=========================");
    }

    public static void task10() {
        List<Animal> animals = Util.getAnimals();
        System.out.println(
                animals.stream()
                        .map(Animal::getAge)
                        .reduce(Integer::sum).get()
        );
        System.out.println("=============Next task=========================");
    }

    public static void task11() {
        List<Animal> animals = Util.getAnimals();
        System.out.println(
                animals.stream()
                        .filter(animal -> animal.getOrigin().equalsIgnoreCase("Indonesian"))
                        .mapToInt(Animal::getAge)
                        .average()
                        .orElse(0)
        );
        System.out.println("=============Next task=========================");
    }

    public static void task12() {
        List<Person> persons = Util.getPersons();
        persons.stream()
                .filter(person -> person.getGender().equalsIgnoreCase("Male"))
                .filter(person -> {
                    LocalDate dateOfBirth = LocalDate.parse((person.getDateOfBirth()).toString());
                    LocalDate currentDate = LocalDate.now();
                    int age = Period.between(dateOfBirth, currentDate).getYears();
                    return (age >= 18 && age <= 27);

                })
                .sorted(Comparator.comparing(Person::getRecruitmentGroup))
                .limit(200)
                .toList().forEach(System.out::println);
        System.out.println("=============Next task=========================");
    }

    public static void task13() {
        List<House> houses = Util.getHouses();
        List<Person> secondLine = new ArrayList<>();
        int maxChildrenAge = 18;
        int minOldAge = 65;
        houses.stream()
                .sorted((h1, h2) -> {
                    if (h1.getBuildingType().equalsIgnoreCase("Hospital")) {
                        return -1;
                    } else if (h2.getBuildingType().equalsIgnoreCase("Hospital")) {
                        return 1;
                    } else {
                        return 0;
                    }
                })
                .mapMulti((house, consumer) -> {
                    if (house.getBuildingType().equalsIgnoreCase("Hospital")) {
                        for (Person person : house.getPersonList()) {
                            consumer.accept(person);
                        }
                    } else {
                        for (Person person : house.getPersonList()) {
                            if (extracted(person, maxChildrenAge, minOldAge)) {
                                consumer.accept(person);
                            } else {
                                secondLine.add(person);
                            }
                        }
                    }
                })
                .limit(500)
                .forEach(System.out::println);
        System.out.println("=============Next task=========================");
    }

    public static void task14(){
        List<Car> cars = Util.getCars();
        double transportationCostPerTon = 7.14;
        double totalCost = cars.stream()
                .collect(Collectors.groupingBy(Main::determineCountry))
                .entrySet()
                .stream()
                .filter(entry -> !entry.getKey().isEmpty())
                .map(entry -> {
                    double totalMass = entry.getValue()
                            .stream()
                            .mapToDouble(Car::getMass)
                            .sum() * transportationCostPerTon / 1000;
                    System.out.printf(entry.getKey() + " : %.2f$%n ", totalMass);
                    return totalMass;
                })
                .mapToDouble(Double::doubleValue).sum();
        System.out.printf("Oбщяя выручка логистической кампании %.2f$%n", totalCost);
        System.out.println("=============Next task=========================");
    }
    private static String determineCountry(Car car) {
        return getCountryForJaguarOrWhite(car)
                .orElseGet(() -> getCountryForLightCarFromCertainMakes(car)
                        .orElseGet(() -> getCountryForPuceHeavyCarFromCertainMakes(car)
                                .orElseGet(() -> getCountryForOldCarWithCertainModels(car)
                                        .orElseGet(() -> getCountryForUncommonColorOrExpensive(car)
                                                .orElseGet(() -> getCountryForVinContains59(car)
                                                        .orElse(""))))));
    }
    private static Optional<String> getCountryForJaguarOrWhite(Car car) {
        Predicate<Car> predicate = c -> c.getCarMake().equalsIgnoreCase("Jaguar") || c.getColor().equalsIgnoreCase("White");
        return predicate.test(car) ? Optional.of("Туркменистан") : Optional.empty();
    }

    private static Optional<String> getCountryForLightCarFromCertainMakes(Car car) {
        Predicate<Car> predicate = c -> c.getMass() <= 1500 &&
                (c.getCarMake().equalsIgnoreCase("BMW") ||
                        c.getCarMake().equalsIgnoreCase("Lexus") ||
                        c.getCarMake().equalsIgnoreCase("Chrysler") ||
                        c.getCarMake().equalsIgnoreCase("Toyota"));
        return predicate.test(car) ? Optional.of("Узбекистан") : Optional.empty();
    }

    private static Optional<String> getCountryForPuceHeavyCarFromCertainMakes(Car car) {
        Predicate<Car> predicate = c -> c.getColor().equalsIgnoreCase("Puce") &&
                c.getMass() > 4000 &&
                (c.getCarMake().equalsIgnoreCase("GMC") ||
                        c.getCarMake().equalsIgnoreCase("Dodge"));
        return predicate.test(car) ? Optional.of("Казахстан") : Optional.empty();
    }

    private static Optional<String> getCountryForOldCarWithCertainModels(Car car) {
        Predicate<Car> predicate = c -> c.getReleaseYear() <= 1982 ||
                (c.getCarModel().equalsIgnoreCase("Civic") ||
                        c.getCarModel().equalsIgnoreCase("Cherokee"));
        return predicate.test(car) ? Optional.of("Кыргызстан") : Optional.empty();
    }
    private static Optional<String> getCountryForUncommonColorOrExpensive(Car car) {
        Predicate<Car> predicate = c -> (!c.getColor().equalsIgnoreCase("Yellow") &&
                !c.getColor().equalsIgnoreCase("Red") &&
                !c.getColor().equalsIgnoreCase("Green") &&
                !c.getColor().equalsIgnoreCase("Blue")) ||
                c.getPrice() > 40000;
        return predicate.test(car) ? Optional.of("Россия") : Optional.empty();
    }

    private static Optional<String> getCountryForVinContains59(Car car) {
        Predicate<Car> predicate = c -> c.getVin().contains("59");
        return predicate.test(car) ? Optional.of("Монголия") : Optional.empty();
    }


    public static void task15() {
        List<Flower> flowers = Util.getFlowers();
        double waterCostPerCubicMeter = 1.39; // Стоимость кубометра воды

        double totalMaintenanceCost = flowers.stream()
                .sorted(Comparator.comparing(Flower::getOrigin).reversed()
                        .thenComparing(Comparator.comparingDouble(Flower::getPrice).reversed())
                        .thenComparing(Comparator.comparingDouble(Flower::getWaterConsumptionPerDay).reversed()))
                .filter(flower -> flower.getCommonName().toUpperCase().charAt(0) <= 'S' &&
                        flower.getCommonName().toUpperCase().charAt(0) >= 'C')
                .filter(flower -> flower.isShadePreferred() &&
                        (flower.getFlowerVaseMaterial().contains("Glass")
                                || flower.getFlowerVaseMaterial().contains("Aluminum")
                                || flower.getFlowerVaseMaterial().contains("Steel")))
                .mapToDouble(flower -> {
                    double waterCost = (flower.getWaterConsumptionPerDay() * 365 * 5) / 1000 * waterCostPerCubicMeter;

                    return flower.getPrice() + waterCost;
                })
                .sum();

        System.out.printf("Общие затраты составили : $ %.2f%n", totalMaintenanceCost);
        System.out.println("=============Next task=========================");
    }

    public static void task16() {
        /*
        В этом задании искусственно изменен возраст одного из студентов на 17, так как им всем больше 18:)
         */
        List<Student> students = Util.getStudents();
        students.stream()
                .filter(student -> student.getAge() < 18)
                .sorted(Comparator.comparing(Student::getSurname))
                .forEach(student -> System.out.println(student.getSurname() + " - " + student.getAge() + " лет"));
        System.out.println("=============Next task=========================");
    }

    public static void task17() {
        List<Student> students = Util.getStudents();
        students.stream()
                .map(Student::getGroup)
                .distinct()
                .sorted()
                .toList().forEach(System.out::println);
        System.out.println("=============Next task=========================");
    }


    public static void task18() {
        List<Student> students = Util.getStudents();

        students.stream()
                .sorted(Comparator.comparingInt(Student::getAge))
                .collect(Collectors.groupingBy(Student::getFaculty, Collectors.averagingInt(Student::getAge)))
                .forEach((key, value) -> System.out.println("Факультет: " + key + ", Средний возраст: " + value));
        System.out.println("=============Next task=========================");
    }

    public static void task19() {
        List<Student> students = Util.getStudents();
        List<Examination> examinations = Util.getExaminations();
        students.stream()
                .parallel()
                .filter(student -> examinations.stream()
                        .parallel()
                        .anyMatch(examination -> examination.getStudentId() == student.getId() && examination.getExam3() > 4)

                )
                .toList().forEach(System.out::println);
        System.out.println("=============Next task=========================");
    }

    public static void task20() {
        List<Student> students = Util.getStudents();
        List<Examination> examinations = Util.getExaminations();

        students.stream()
                .collect(Collectors.groupingBy(Student::getFaculty,
                        Collectors.averagingDouble(student -> getFirstExamGrade(student, examinations))))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue()).stream().collect(Collectors.toSet())
                .forEach(stringDoubleEntry -> {
                    System.out.printf("Факультет с максимальной средней оценкой %s которая равна = %.2f%n", stringDoubleEntry.getKey(), stringDoubleEntry.getValue());
                });
        System.out.println("=============Next task=========================");
    }

    private static double getFirstExamGrade(Student student, List<Examination> examinations) {
        return examinations.stream()
                .filter(exam -> exam.getStudentId() == student.getId())
                .findFirst()
                .map(Examination::getExam1)
                .orElse(0);
    }

    public static void task21() {
        List<Student> students = Util.getStudents();
        students.stream()
                .collect(Collectors.groupingBy(Student::getGroup, Collectors.counting()))
                .forEach((group, count) -> System.out.println("Группа: " + group + ", Количество студентов: " + count));
        System.out.println("=============Next task=========================");
    }

    public static void task22() {
        List<Student> students = Util.getStudents();
        students.stream()
                .collect(Collectors.groupingBy(Student::getFaculty,
                        Collectors.mapping(Student::getAge, Collectors.minBy(Comparator.naturalOrder()))))
                .entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().orElse(0)))
                .forEach((faculty, minAge) ->
                        System.out.println("Минимальный возраст факультета:" + faculty + " : " + minAge));

    }

    private static boolean extracted(Person person, int maxChildrenAge, int minOldAge) {
        LocalDate dateOfBirth = LocalDate.parse((person.getDateOfBirth()).toString());
        LocalDate currentDate = LocalDate.now();
        int age = Period.between(dateOfBirth, currentDate).getYears();
        return (age < maxChildrenAge || age > minOldAge);
    }

}
