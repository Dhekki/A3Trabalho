package util;

import entities.Person;
import entities.Student;
import entities.Teacher;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class PersonUtil {
    //Função cadastro
    public static Person personRecord(Scanner sc,ArrayList<Person> personListRecord) {
        //Inserir variáveis de cadastro
        String name, id;
        int age;
        char gender;

        //Inserção de dados
        System.out.print("Digite o nome completo: ");
        name = sc.nextLine();

        System.out.print("Digite a idade: ");
        age = sc.nextInt();

        do { //Verificação de gênero válido
            System.out.print("Digite o gênero: ");
            gender = sc.next().toUpperCase().charAt(0);
        } while (genderValidator(gender));

        System.out.print("Digite o id: ");
        do { //Verificação de id válido
            id = sc.next();
            if (id.matches("\\d+")) { //Verificar se só tem números
                if (!idLimiter(id, 10)) { //Verificar tamanho
                    System.out.print("Id inválido! Tente novamente com 10 caracteres: ");
                }
            } else System.out.print("Id inválido! Tente novamente com números apenas: ");

            if (!idEqual(id, personListRecord)) System.out.print("Id já existente! Tente novamente com outro id: "); //Verificar repetição de id
        } while (!idLimiter(id, 10) || !idEqual(id, personListRecord) || !id.matches("\\d+"));

        return new Person(name, age, gender, id);
    }

    public static Student studentRecord(Scanner sc, ArrayList<Person> personListRecord) {
        Student student = new Student(personRecord(sc, personListRecord));
        System.out.print("Digite o semestre: ");
        int semester = sc.nextInt();
        student.setSemester(semester);
        return student;
    }

    public static Teacher teacherRecord(Scanner sc, ArrayList<Person> personListRecord) {
        Teacher teacher = new Teacher(personRecord(sc, personListRecord));
        clearBuffer(sc);
        System.out.print("Digite a disciplina: ");
        String course = sc.nextLine();
        teacher.setCourse(course);
        System.out.print("Digite o salário: ");
        double salary = sc.nextDouble();
        teacher.setSalary(salary);
        return teacher;
    }

    //Método para o obrigar que o id tenha exatamente 10 dígitos.
    public static boolean idLimiter(String id, int size) {
        return (id.length() == size);
    }
    //Método pra verificar se não há id repetido
    public static boolean idEqual(String id, ArrayList<Person> verify) {
        List<Person> matchingIds = verify.stream().filter(idType -> Objects.equals(idType.getId(), id)).toList();
        return matchingIds.isEmpty(); //Se tiver vazio, não tem id repetido, então pode
    }
    public static boolean genderValidator(char gender) {
        if ((gender != 'M') && (gender != 'F')) {
            System.out.println("Gênero inválido!");
            return true;
        } else return false;
    }

    public static void clearBuffer(Scanner sc) {
        if (sc.hasNextLine()) {
            sc.nextLine();
        }
    }

    public static String studentColumnHeader () {
        return ("Name\t\t\t\t| Age | Gender |  Semester  | Id" +
                "\n---------------------------------------------------------");
    }

    public static String teacherColumnHeader () {
        return ("Name\t\t\t\t| Age | Gender |       Id       |\tCourse\t\t\t| Salary" +
                "\n------------------------------------------------------------------------------------");
    }

    public static String registerNotFound () {
        return ("\nRegistro não encontrado!");
    }
}