package entities;

public class Teacher extends Person {
    private String course;
    private Double salary;
    private Person person;

    //Métodos Construtores
    //Inserção inicial
    public Teacher(String name, Integer age, Character gender, String id, String course, Double salary) {
        super(name, age, gender, id);
        this.course = course;
        this.salary = salary;
    }
    //Inserção do usuário
    public Teacher(Person person) {
        super(person.name, person.age, person.gender, person.id);
    }

    //Setters and getters
    public String getCourse() {
        return course;
    }
    public void setCourse(String course) {
        this.course = course;
    }

    public Double getSalary() {
        return salary;
    }
    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public String truncateCourse(int courseLength) {
        return course.length() > courseLength ? course.substring(0, courseLength - 2) + "…" : course;
    }

    public String completeTeacherString(int nameLength, int courseLength) {
        String truncatedName = truncateName(nameLength)
                , truncatedCourse = truncateCourse(courseLength);
        return String.format("%-" + nameLength + "s | %d  |   %s    |   %s   |   %-" + courseLength + "s\t| R$%.2f",
                truncatedName, age, gender, id, truncatedCourse, salary);
    }
}