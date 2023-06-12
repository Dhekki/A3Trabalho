package entities;

public class Student extends Person {
    private Integer semester;
    private Person person;

    //Métodos construtores
    //Inserção inicial
    public Student(String name, Integer age, Character gender, String id, Integer semester) {
        super(name, age, gender, id);
        this.semester = semester;
    }
    //Inserção do usuário
    public Student(Person person) {
        super(person.name, person.age, person.gender, person.id);
    }

    //Setters and getters
    public int getSemester() {
        return semester;
    }
    public void setSemester(int semester) {
        this.semester = semester;
    }

    //Métodos que substitui o "toString" para imprimir os dados em formato tabular
    public String completeStudentString(int nameLength) {
        String truncatedName = truncateName(nameLength);
        return String.format("%-" + nameLength + "s | %d  |   %s    |     %d°     | %s", truncatedName, age, gender, semester, id);
    }
}