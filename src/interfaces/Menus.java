package interfaces;

import entities.Person;
import entities.Student;
import entities.Teacher;
import util.PersonUtil;

import java.util.*;
import java.util.stream.Collectors;

public class Menus {

    char role; //Escolha de aluno ou professor
    boolean registerFound, modify; //Verificador de registro e verificador de modificação
    //Strings autoexplicativas
    String studentHeader = PersonUtil.studentColumnHeader()
            ,teacherHeader = PersonUtil.teacherColumnHeader()
            ,error404 = PersonUtil.registerNotFound();

    //Menu pra escolha de opção
    public int mainMenu(Scanner sc) {
        int choice;
        System.out.print("\n\tEscolha a opção desejada:" +
                "\n(1) Listar" +
                "\n(2) Cadastrar" +
                "\n(3) Buscar" +
                "\n(4) Modificar" +
                "\n(5) Deletar" +
                "\n(0) Encerrar" +
                "\n\nOpção: ");
        choice = sc.nextInt();
        return choice;
    }
    //Menu pra escolha de atributo
    public int atributteStudentMenu(Scanner sc, boolean modify) {
        if (!modify) { //não modificar = buscar
            System.out.print("\n\tQual atributo deseja buscar?" +
                    "\n(1) Nome" +
                    "\n(2) Idade" +
                    "\n(3) Id" +
                    "\n(4) Gênero" +
                    "\n(5) Semestre" +
                    "\n(0) Não quero buscar nada" +
                    "\n\nOpção: ");
        } else {
            System.out.print("\n\tQual atributo deseja modificar?" +
                    "\n(1) Nome" +
                    "\n(2) Idade" +
                    "\n(3) Gênero" +
                    "\n(4) Semestre" +
                    "\n(0) Não quero modificar nada" +
                    "\n\nOpção: ");
        }
        return sc.nextInt();
    }
    public int atributteTeacherMenu(Scanner sc, boolean modify) {
        if (!modify) { //não modificar = buscar
            System.out.print("\n\tQual atributo deseja buscar?" +
                    "\n(1) Nome" +
                    "\n(2) Idade" +
                    "\n(3) Id" +
                    "\n(4) Gênero" +
                    "\n(5) Disciplina" +
                    "\n(6) Salário" +
                    "\n(0) Não quero buscar nada" +
                    "\n\nOpção: ");
        } else {
            System.out.print("\n\tQual atributo deseja modificar?" +
                    "\n(1) Nome" +
                    "\n(2) Idade" +
                    "\n(3) Gênero" +
                    "\n(4) Disciplina" +
                    "\n(5) Salário" +
                    "\n(0) Não quero modificar nada" +
                    "\n\nOpção: ");
        }
        return sc.nextInt();
    }

    //Ação derivada da escolha do usuário no menu principal
    public void choiceMenu(int choice, ArrayList<Person> personList, Scanner sc) {
        //Comparadores pra ordenação
        //Ordenar como 1°Professor e 2°Aluno
        Comparator<Person> typeComparator = Comparator.comparing(person -> person instanceof Student);
        //Ordenar alfabeticamente
        Comparator<Person> nameComparator = Comparator.comparing(Person::getName);
        // Ordenar a lista inicial em Professor e Alunos e em ordem alfabética em cada loop
        personList.sort(nameComparator);

        switch (choice) {
            case 1: //Listar
                int listChoice;
                do {
                    System.out.print("\n\tQual a listagem desejada?" +
                            "\n(1) Geral" +
                            "\n(2) Alunos" +
                            "\n(3) Professores" +
                            "\n(0) Voltar" +
                            "\n\nOpção: ");
                    listChoice = sc.nextInt();
                    switch (listChoice) {
                        case 1: //Listar todos
                            //Ordena a lista geral em Professor e alunos e depois em ordem alfabética
                            personList.sort(typeComparator.thenComparing(nameComparator));
                            System.out.println("S/P  | Name\t\t\t\t | Id\t\t\t| Age" + //Só aparece uma vez então deixei assim, mas posso fazer como função depois
                                    "\n---------------------------------------------");
                            for (Person person : personList) {
                                if (person instanceof Teacher) {
                                    System.out.println("Prof | " + person.standardString(19));
                                } else if (person instanceof Student) {
                                    System.out.println("Std  | " + person.standardString(19));
                                }
                            }
                            break;
                        case 2: //Listar alunos
                            System.out.println(studentHeader);
                            for (Person person: personList) {
                                if (person instanceof Student) {
                                    System.out.println(((Student) person).completeStudentString(19)); //Cast de Person pra Student
                                }
                            }
                            break;
                        case 3: //Listar professores
                            System.out.println(teacherHeader);
                            for (Person person: personList) {
                                if (person instanceof Teacher) {
                                    System.out.println(((Teacher) person).completeTeacherString(19, 12)); //Cast de Person pra Student
                                }
                            }
                            break;
                        case 0: //Voltar
                            break;
                        default:
                            System.out.println("Opção inválida!");
                            listChoice = 4;
                    }
                } while (listChoice == 4);
                break;
            case 2: //Cadastrar
                System.out.println("Deseja cadastrar alunos ou professores?");
                PersonUtil.clearBuffer(sc);
                role = sc.nextLine().toUpperCase().charAt(0);
                if (role == 'P') {
                    System.out.println("\nCadastro do Professor");
                } else if (role == 'A') {
                    System.out.println("\nCadastro do Aluno");
                } else {
                    System.out.println("Opção inválida!");
                    break;
                }
                boolean repeat = true; //Manter loop
                do {
                    if (role == 'P') {
                        personList.add((PersonUtil.teacherRecord(sc, personList))); //Adição de Professor na lista pelo método record
                        System.out.print("\nDeseja cadastrar outro Profesor? ");
                    } else if (role == 'A'){
                        personList.add(PersonUtil.studentRecord(sc, personList)); //Adição de Aluno na lista pelo método record
                        System.out.print("\nDeseja cadastrar outro aluno? ");
                    }

                    char verify = sc.next().toLowerCase().charAt(0);
                    PersonUtil.clearBuffer(sc);
                    if (verify == 'n') repeat = false;
                } while (repeat);
                break;
            case 3: //Buscar
                modify = false;
                System.out.println("Deseja buscar alunos ou professores?");
                PersonUtil.clearBuffer(sc);
                role = sc.nextLine().toUpperCase().charAt(0);
                if (role == 'P') {
                    System.out.println("\nBusca de Professores");
                } else if (role == 'A') {
                    System.out.println("\nBusca de Alunos");
                } else {
                    System.out.println("Opção inválida!");
                    break;
                }

                if (role == 'P') {
                    atributteChoice(atributteTeacherMenu(sc, modify), personList, sc, role);
                } else atributteChoice(atributteStudentMenu(sc, modify), personList, sc, role);

                break;
            case 4: //Modificar
                modify = true;
                PersonUtil.clearBuffer(sc);
                System.out.println("Você deseja modificar o registro de um aluno ou um professor?");
                role = sc.nextLine().toUpperCase().charAt(0);
                if (role != 'P' && role != 'A') {
                    System.out.println("Opção inválida!");
                    break;
                }
                System.out.println("Digite o id do registro que deseja modificar: ");
                String userModify = sc.nextLine();

                for (Person person: personList) { //Verifica se o registro existe, escreve ele e retorna um boolean
                    if (role == 'P') {
                        if (person instanceof Teacher teacher && Objects.equals(person.getId(), userModify)) {
                            System.out.println("\n" + teacherHeader);
                            System.out.println(teacher.completeTeacherString(19, 12));
                            registerFound = true;
                            break;
                        }
                    } else if (role == 'A') {
                        if (person instanceof Student student && Objects.equals(person.getId(), userModify)) {
                            System.out.println("\n" + studentHeader);
                            System.out.println(student.completeStudentString(19));
                            registerFound = true;
                            break;
                        }
                    }
                }
                if (!registerFound) {
                    System.out.println(error404);
                    break;
                }

                if (role == 'P') {
                    choice = atributteTeacherMenu(sc, modify);
                } else if (role == 'A') {
                    choice = atributteStudentMenu(sc, modify);
                }

                switch (choice) {
                    case 1: //Nome
                        List<Person> personModify = personList.stream()
                                .filter(person -> Objects.equals(person.getId(), userModify))
                                .peek(person -> { //map e peek aparentemente servem pra modificar, usei map mas intellij recomendou colocar peek,
                                    // única diferença visível é que não precisei retornar
                                    if (person instanceof Teacher teacher) {
                                        System.out.print("\nDigite o novo nome: ");
                                        PersonUtil.clearBuffer(sc);
                                        String newName = sc.nextLine();

                                        teacher.setName(newName);
                                        System.out.println("Nome modificado com sucesso!");
                                    } else if (person instanceof Student student) {
                                        System.out.print("\nDigite o novo nome: ");
                                        PersonUtil.clearBuffer(sc);
                                        String newName = sc.nextLine();

                                        student.setName(newName);
                                        System.out.println("Nome modificado com sucesso!");
                                    }
                                })
                                .toList();
                        break;
                    case 2: //Idade
                        personModify = personList.stream()
                                .filter(person -> Objects.equals(person.getId(), userModify))
                                .peek(person -> { //map e peek aparentemente servem pra modificar, usei map mas intellij recomendou colocar peek,
                                    // única diferença visível é que não precisei retornar
                                    if (person instanceof Teacher teacher) {
                                        System.out.print("\nDigite a nova idade: ");
                                        PersonUtil.clearBuffer(sc);
                                        int newAge = sc.nextInt();

                                        teacher.setAge(newAge);
                                        System.out.println("Idade modificada com sucesso!");
                                    } else if (person instanceof Student student) {
                                        System.out.print("\nDigite a nova idade: ");
                                        PersonUtil.clearBuffer(sc);
                                        int newAge = sc.nextInt();

                                        student.setAge(newAge);
                                        System.out.println("Idade modificada com sucesso!");
                                    }
                                })
                                .toList();
                        break;
                    case 3: //Gênero
                        personModify = personList.stream()
                                .filter(person -> Objects.equals(person.getId(), userModify))
                                .peek(person -> { //map e peek aparentemente servem pra modificar, usei map mas intellij recomendou colocar peek,
                                    // única diferença visível é que não precisei retornar
                                    if (person instanceof Teacher teacher) {
                                        if (teacher.getGender() == 'F') teacher.setGender('M');
                                        else if (teacher.getGender() == 'M') teacher.setGender('F');
                                        System.out.println("Gênero alterado com sucesso!");
                                    } else if (person instanceof Student student) {
                                        if (student.getGender() == 'F') student.setGender('M');
                                        else if (student.getGender() == 'M') student.setGender('F');
                                        System.out.println("Gênero alterado com sucesso!");
                                    }
                                })
                                .toList();
                        break;
                    case 0: //Voltar
                        break;
                    default:
                        if (role == 'P') {
                            switch (choice) {
                                case 4: //Disciplina
                                    personModify = personList.stream()
                                            .filter(person -> Objects.equals(person.getId(), userModify))
                                            .peek(person -> { //map e peek aparentemente servem pra modificar, usei map mas intellij recomendou colocar peek,
                                                // única diferença visível é que não precisei retornar
                                                if (person instanceof Teacher teacher) {
                                                    System.out.print("\nDigite a nova disciplina: ");
                                                    PersonUtil.clearBuffer(sc);
                                                    String newCourse = sc.nextLine();

                                                    teacher.setCourse(newCourse);
                                                    System.out.println("Disciplina modificada com sucesso!");
                                                }
                                            })
                                            .toList();
                                    break;
                                case 5: //Salário
                                    personModify = personList.stream()
                                            .filter(person -> Objects.equals(person.getId(), userModify))
                                            .peek(person -> { //map e peek aparentemente servem pra modificar, usei map mas intellij recomendou colocar peek,
                                                // única diferença visível é que não precisei retornar
                                                if (person instanceof Teacher teacher) {
                                                    System.out.print("\nDigite o novo salário: ");
                                                    PersonUtil.clearBuffer(sc);
                                                    double newSalary = sc.nextDouble();

                                                    teacher.setSalary(newSalary);
                                                    System.out.println("Salário modificado com sucesso!");
                                                }
                                            })
                                            .toList();
                                    break;
                                default:
                                    System.out.println("Opção inválida");
                            }

                        } else if (role == 'A') {
                            switch (choice) {
                                case 4:
                                    personModify = personList.stream()
                                            .filter(person -> Objects.equals(person.getId(), userModify))
                                            .peek(person -> { //map e peek aparentemente servem pra modificar, usei map mas intellij recomendou colocar peek,
                                                // única diferença visível é que não precisei retornar
                                                if (person instanceof Student student) {
                                                    System.out.print("\nDigite o novo : ");
                                                    PersonUtil.clearBuffer(sc);
                                                    int newSemester = sc.nextInt();

                                                    student.setSemester(newSemester);
                                                    System.out.println("Semestre alterado com sucesso!");
                                                }
                                            })
                                            .toList();
                                    break;
                                default:
                                    System.out.println("Opção inválida");
                            }
                        }
                }
                break;
            case 5: //Remover
                PersonUtil.clearBuffer(sc);
                System.out.println("\nRemover usuário");
                System.out.print("Digite o id do registro que deseja remover: ");
                String userRemove = sc.nextLine();
                registerFound = false;

                Iterator<Person> itr = personList.iterator(); //Precisa disso pra remover, é o mais recomendado, pq? Nem ideia
                while (itr.hasNext()) {
                    Person person = itr.next();
                    if (person instanceof Teacher teacher && Objects.equals(teacher.getId(), userRemove)) { //Verifica se existe e é professor
                        System.out.println("\n" + teacherHeader);
                        System.out.println(teacher.completeTeacherString(19, 12) + "\n");
                        System.out.print("Deseja realmente remover esse registro de professor? ");
                    } else if (person instanceof Student student && Objects.equals(student.getId(), userRemove)) { //Verifica se existe e é estudante
                        System.out.println("\n" + studentHeader);
                        System.out.println(student.completeStudentString(19) + "\n");
                        System.out.print("Deseja realmente remover esse registro de aluno? ");
                    }
                    if (Objects.equals(person.getId(), userRemove)) {
                        role = sc.nextLine().toUpperCase().charAt(0); //Confirmação de remoção

                        if (role == 'S') {
                            itr.remove();
                            System.out.println("Registro removido com sucesso!");
                        } else if (role == 'N') {
                            System.out.println("Registro não removido!");
                        } else System.out.println("Opção inválida");
                        registerFound = true;
                    }
                }
                if (!registerFound) System.out.println(error404);
                break;
            case 0: //Encerrar
                System.out.println("\nPrograma encerrado!");
                break;
            default:
                System.out.println("\nOpção inválida, Tente novamente!");
                break;
        }
    }
    //Ação derivada da escolha do usuário no menu de atributo
    public void atributteChoice(int choiceAtributte, ArrayList<Person> personList, Scanner sc, char role) {
        PersonUtil.clearBuffer(sc);
        switch (choiceAtributte) {
            case 1: //Nome
                System.out.print("Digite o nome que deseja procurar: ");
                String nameSearch = sc.nextLine();
                //Lista para busca de nomes
                List<Person> orderName = personList.stream() //Transformando em stream pra usar função lambda
                        .filter(person -> {
                            if (person instanceof Teacher teacher) { //Filtra Professores
                                String[] names = teacher.getName().split(" "); //Separando os nomes e sobrenomes com arrays
                                String firstName = names[0]; // Obtém o primeiro nome
                                return firstName.equalsIgnoreCase(nameSearch); //comparação dos nomes desconsiderando letras maiúsculas e minúsculas
                            } else if (person instanceof Student student) { //Filtra alunos
                                String[] names = student.getName().split(" "); //Separando os nomes e sobrenomes com arrays
                                String firstName = names[0]; // Obtém o primeiro nome
                                return firstName.equalsIgnoreCase(nameSearch); //comparação dos nomes desconsiderando letras maiúsculas e minúsculas
                            }
                            return false; // Caso a pessoa não seja nem professor nem aluno
                        })
                        .toList();

                if (role == 'P') {
                    if (orderName.stream().noneMatch(person -> person instanceof Teacher)) { //Verificação de existência de registro
                        System.out.println(error404);
                    } else {
                        System.out.println(teacherHeader);
                        for (Person name : orderName) {
                            if (name instanceof Teacher teacher) { // Verifica se é instância de Teacher e converte para o mesmo pra usar método
                                System.out.println(teacher.completeTeacherString(19, 12));
                            }
                        }
                    }
                } else if (role == 'A'){
                    if (orderName.stream().noneMatch(person -> person instanceof Student)) { //Verificação de existência de registro
                        System.out.println(error404);
                    } else {
                        System.out.println(studentHeader);
                        for (Person name : orderName) {
                            if (name instanceof Student student) { // Verifica se é instância de Student e converte para o mesmo pra usar método
                                System.out.println(student.completeStudentString(19));
                            }
                        }

                    }
                }
                break;
            case 2: //Idade
                System.out.print("Digite a idade que deseja procurar: ");
                int numberSearch = sc.nextInt();

                //List -> Stream -> filter -> List
                List<Person> orderNumber = personList.stream()
                        .filter(person -> {
                            if (person instanceof Teacher teacher) { //Filtra professores
                                return teacher.getAge() == numberSearch;
                            } else if (person instanceof Student student) { //Filtra alunos
                                return student.getAge() == numberSearch;
                            }
                            return false; // Caso a pessoa não seja nem professor nem aluno
                        })
                        .toList();

                if (role == 'P') {
                    if (orderNumber.stream().noneMatch(person -> person instanceof Teacher)) { //Verificação de existência de registro
                        System.out.println(error404);
                    } else {
                        System.out.println(teacherHeader);
                        for (Person age : orderNumber) {
                            if (age instanceof Teacher teacher) // Verifica se é instância de Teacher e converte para o mesmo pra usar método
                                System.out.println(teacher.completeTeacherString(19, 12));
                        }
                    }
                } else if (role == 'A'){
                    if (orderNumber.stream().noneMatch(person -> person instanceof Student)) { //Verificação de existência de registro
                        System.out.println(error404);
                    } else {
                        System.out.println(studentHeader);
                        for (Person age : orderNumber) {
                            if (age instanceof Student student) // Verifica se é instância de Student e converte para o mesmo pra usar método
                                System.out.println(student.completeStudentString(19));
                        }
                    }
                }
                break;
            case 3: //Id
                System.out.print("Digite o id que deseja procurar: ");
                String idSearch = sc.next();

                //List -> Stream -> filter -> List
                orderNumber = personList.stream() //Lista de pessoa
                        .filter(person -> {
                            if (person instanceof Teacher teacher) { //Filtra professores
                                return Objects.equals(teacher.getId(), idSearch);
                            } else if (person instanceof Student student) { //Filtra alunos
                                return Objects.equals(student.getId(), idSearch);
                            }
                            return false; // Caso a pessoa não seja nem professor nem aluno
                        })
                        .toList();

                if (role == 'P') { //Professor
                    if (orderNumber.stream().noneMatch(person -> person instanceof Teacher)) { //Verificação de existência de registro
                        System.out.println(error404);
                    } else {
                        System.out.println(teacherHeader);
                        for (Person id : orderNumber) {
                            if (id instanceof Teacher teacher) { // Verifica se é instância de Teacher e converte para o mesmo pra usar método
                                System.out.println(teacher.completeTeacherString(19, 12));
                            }
                        }
                    }
                } else { //Aluno
                    if (orderNumber.stream().noneMatch(person -> person instanceof Student)) { //Verificação de existência de registro
                        System.out.println(error404);
                    } else if (role == 'A'){
                        System.out.println(studentHeader);
                        for (Person id : orderNumber) {
                            if (id instanceof Student student) { // Verifica se é instância de Student e converte para o mesmo pra usar método
                                System.out.println(student.completeStudentString(19));
                            }
                        }
                    }
                }
                break;
            case 4: //Gênero
                System.out.print("Digite o gênero que deseja procurar: ");
                char genderSearch = sc.next().toUpperCase().charAt(0);

                //List -> Stream -> filter -> List
                List<Person> orderGender = personList.stream()
                        .filter(person -> {
                            if (person instanceof Teacher teacher) { //Filtra Professores
                                return teacher.getGender() == genderSearch;
                            } else if (person instanceof Student student) { //Filtra alunos
                                return student.getGender() == genderSearch;
                            }
                            return false; // Caso a pessoa não seja nem professor nem aluno
                        })
                        .toList();

                if (role == 'P') {
                    if (orderGender.stream().noneMatch(person -> person instanceof Teacher)) { //Verificação de existência de registro
                        System.out.println(error404);
                    } else {
                        System.out.println(teacherHeader);
                        for (Person gender : orderGender) {
                            if (gender instanceof Teacher teacher) { // Verifica se é instância de Teacher e converte para o mesmo pra usar método
                                System.out.println(teacher.completeTeacherString(19, 12));
                            }
                        }
                    }
                } else {
                    if (orderGender.stream().noneMatch(person -> person instanceof Student)) { //Verificação de existência de registro
                        System.out.println(error404);
                    } else if (role == 'A'){
                        System.out.println(studentHeader);
                        for (Person gender : orderGender) {
                            if (gender instanceof Student student) { // Verifica se é instância de Student e converte para o mesmo pra usar método
                                System.out.println(student.completeStudentString(19));
                            }
                        }
                    }
                }
                break;
            case 0:
                break;
            default:
                if (role == 'P') {
                    switch (choiceAtributte) { //Professores
                        case 5: //Disciplina
                            System.out.print("Digite a disciplina que deseja procurar: ");
                            String courseSearch = sc.nextLine();

                            //List -> Stream -> filter -> List
                            orderName = personList.stream()
                                    .filter(person -> person instanceof Teacher teacher //Filtra instância de teacher e converte para o mesmo
                                            && Objects.equals(teacher.getCourse().toLowerCase(), courseSearch.toLowerCase())) //Pra usar método. LowerCase pra ficar igual
                                    .toList();

                            if (orderName.isEmpty()) {
                                System.out.println(error404);
                            } else {
                                System.out.println(teacherHeader);
                                for (Person course: orderName) {
                                    if (course instanceof Teacher teacher) {
                                        System.out.println(teacher.completeTeacherString(19, 12));
                                    }
                                }
                            }
                            break;
                        case 6: //Salário
                            System.out.print("Digite o salário que deseja procurar: ");
                            double salarySearch = sc.nextDouble();

                            //List -> Stream -> filter -> List
                            orderNumber = personList.stream()
                                    .filter(person -> person instanceof Teacher teacher //Filtra instância de teacher e converte para o mesmo
                                            && teacher.getSalary() == salarySearch) //Pra usar método
                                    .toList();

                            if (orderNumber.isEmpty()) {
                                System.out.println(error404);
                            } else {
                                System.out.println(teacherHeader);
                                for (Person salary: orderNumber) {
                                    if (salary instanceof Teacher teacher) { // Verifica se é instância de Teacher e converte para o mesmo pra usar método
                                        System.out.println(teacher.completeTeacherString(19, 12));
                                    }
                                }
                            }
                            break;
                        default:
                            System.out.println("\nOpção inválida, Tente novamente!");
                            break;
                    }
                } else if (role == 'A'){
                    switch (choiceAtributte) { //Alunos
                        case 5: //Semestre
                            System.out.print("Digite o semestre que deseja procurar: ");
                            int semesterSearch = sc.nextInt();

                            //List -> Stream -> filter -> List
                            orderNumber = personList.stream()
                                    .filter(person -> person instanceof Student student //Filtra instância de student e converte para o mesmo
                                            && student.getSemester() == semesterSearch) //Pra usar método
                                    .toList();

                            if (orderNumber.isEmpty()) {
                                System.out.println(error404);
                            } else {
                                System.out.println(studentHeader);
                                for (Person semester : orderNumber) {
                                    if (semester instanceof Student student) // Verifica se é instância de Student e converte para o mesmo pra usar método
                                        System.out.println(student.completeStudentString(19));
                                }
                            }
                            break;
                        default:
                            System.out.println("\nOpção inválida, Tente novamente!");
                            break;
                    }
                }
        }
    }
}