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
    String[] names; //Utilizável no método de separar nomes/sobrenomes

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
                                if (person instanceof Student student) {
                                    System.out.println(student.completeStudentString(19)); //Cast de Person pra Student
                                }
                            }
                            break;
                        case 3: //Listar professores
                            System.out.println(teacherHeader);
                            for (Person person: personList) {
                                if (person instanceof Teacher teacher) {
                                    System.out.println(teacher.completeTeacherString(19, 12)); //Cast de Person pra Student
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

                    char verify;
                    do {
                        verify = sc.next().toUpperCase().charAt(0);
                        PersonUtil.clearBuffer(sc);
                        if (verify == 'N') {
                            repeat = false;
                        } else if (verify != 'S') {
                            repeat = false;
                            System.out.println("Opção inválida!");
                            System.out.print("Deseja fazer mais algum cadastro? ");
                        }
                    } while (verify != 'N' && verify != 'S');
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
                } else if (role == 'A') atributteChoice(atributteStudentMenu(sc, modify), personList, sc, role);
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
                if (!registerFound) System.out.println(error404);

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
                                    System.out.print("\nDigite o novo nome: ");
                                    PersonUtil.clearBuffer(sc);
                                    String newName = sc.nextLine();
                                    if (person instanceof Teacher teacher) {
                                        teacher.setName(newName);
                                    } else if (person instanceof Student student) {
                                        student.setName(newName);
                                    }
                                    System.out.println("Nome modificado com sucesso!");
                                })
                                .toList();
                        break;
                    case 2: //Idade
                        personModify = personList.stream()
                                .filter(person -> Objects.equals(person.getId(), userModify))
                                .peek(person -> { //map e peek aparentemente servem pra modificar, usei map mas intellij recomendou colocar peek,
                                    // única diferença visível é que não precisei retornar
                                    System.out.print("\nDigite a nova idade: ");
                                    PersonUtil.clearBuffer(sc);
                                    int newAge = sc.nextInt();
                                    if (person instanceof Teacher teacher) {
                                        teacher.setAge(newAge);
                                    } else if (person instanceof Student student) {
                                        student.setAge(newAge);
                                    }
                                    System.out.println("Idade modificada com sucesso!");
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
                                    } else if (person instanceof Student student) {
                                        if (student.getGender() == 'F') student.setGender('M');
                                        else if (student.getGender() == 'M') student.setGender('F');
                                    }
                                    System.out.println("Gênero alterado com sucesso!");
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
                                                System.out.print("\nDigite o novo salário: ");
                                                PersonUtil.clearBuffer(sc);
                                                double newSalary = sc.nextDouble();
                                                if (person instanceof Teacher teacher) {
                                                    teacher.setSalary(newSalary);
                                                }
                                                System.out.println("Salário modificado com sucesso!");
                                            })
                                            .toList();
                                    break;
                                default:
                                    System.out.println("Opção inválida");
                            }

                        } else if (role == 'A') {
                            switch (choice) {
                                case 4: //Semestre
                                    personModify = personList.stream()
                                            .filter(person -> Objects.equals(person.getId(), userModify))
                                            .peek(person -> { //map e peek aparentemente servem pra modificar, usei map mas intellij recomendou colocar peek,
                                                // única diferença visível é que não precisei retornar
                                                System.out.print("\nDigite o novo : ");
                                                PersonUtil.clearBuffer(sc);
                                                int newSemester = sc.nextInt();
                                                if (person instanceof Student student) {
                                                    student.setSemester(newSemester);
                                                }
                                                System.out.println("Semestre alterado com sucesso!");
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
                    Person person = itr.next(); //Tbm n sei pra que serve
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
                        char confirm = sc.nextLine().toUpperCase().charAt(0); //Confirmação de remoção

                        if (confirm == 'S') {
                            itr.remove();
                            System.out.println("Registro removido com sucesso!");
                        } else if (confirm == 'N') {
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

                for (Person person: personList) { //Cabeçalho
                    String firstName = PersonUtil.firstName(names, person);
                    if (role == 'P' && firstName.equalsIgnoreCase(nameSearch)) {
                        System.out.println("\n" + teacherHeader);
                        break;
                    } else if (role == 'A' && firstName.equalsIgnoreCase(nameSearch)) {
                        System.out.println("\n" + studentHeader);
                        break;
                    }
                }
                //List -> Stream -> Filter -> List
                List<Person> orderPerson = personList.stream() //Transformando em stream pra usar função lambda
                        .filter(person -> { //Registros encontrados
                            if (person instanceof Teacher teacher) { //Filtra Professores
                                String firstName = PersonUtil.firstName(names, person);
                                if (firstName.equalsIgnoreCase(nameSearch)) { //comparação dos nomes desconsiderando letras maiúsculas e minúsculas
                                    System.out.println(teacher.completeTeacherString(19, 12));
                                    return true;
                                }
                            } else if (person instanceof Student student) { //Filtra alunos
                                String firstName = PersonUtil.firstName(names, person);
                                if (firstName.equalsIgnoreCase(nameSearch)) {
                                    System.out.println(student.completeStudentString(19));//comparação dos nomes desconsiderando letras maiúsculas e minúsculas
                                return true;
                                }
                            }
                                return false; // Caso não seja nem professor nem aluno
                        })
                        .toList();

                if (orderPerson.isEmpty()) System.out.println(error404);
                break;
            case 2: //Idade
                System.out.print("Digite a idade que deseja procurar: ");
                int numberSearch = sc.nextInt();

                for (Person person: personList) { //Cabeçalho
                    if (role == 'P' && person.getAge() == numberSearch) {
                        System.out.println(teacherHeader);
                        break;
                    } else if (role == 'A' && person.getAge() == numberSearch) {
                        System.out.println(studentHeader);
                        break;
                    }
                }
                //List -> Stream -> filter -> List
                orderPerson = personList.stream()
                        .filter(person -> { //Registros encontrados
                            if (person instanceof Teacher teacher && person.getAge() == numberSearch) { //Filtra professores
                                System.out.println(teacher.completeTeacherString(19, 12));
                                return true;
                            } else if (person instanceof Student student && person.getAge() == numberSearch) { //Filtra alunos
                                System.out.println(student.completeStudentString(19));
                                return true;
                            }
                            return false; //Caso não seja nem professor nem aluno
                        })
                        .toList();

                if (orderPerson.isEmpty()) System.out.println(error404);
                break;
            case 3: //Id
                System.out.print("Digite o id que deseja procurar: ");
                String idSearch = sc.next();

                for (Person person: personList) { //Cabeçalho
                    if (role == 'P' && Objects.equals(person.getId(), idSearch)) {
                        System.out.println(teacherHeader);
                        break;
                    } else if (role == 'A' && Objects.equals(person.getId(), idSearch)) {
                        System.out.println(studentHeader);
                        break;
                    }
                }

                //List -> Stream -> filter -> List
                orderPerson = personList.stream() //Lista de pessoa
                        .filter(person -> { //Registros encontrados
                            if (person instanceof Teacher teacher && Objects.equals(person.getId(), idSearch)) { //Filtra professores
                                System.out.println(teacher.completeTeacherString(19, 12));
                                return true;
                            } else if (person instanceof Student student && Objects.equals(person.getId(), idSearch)) { //Filtra alunos
                                System.out.println(student.completeStudentString(19));
                                return true;
                            }
                            return false; // Caso não seja nem professor nem aluno
                        })
                        .toList();

                if (orderPerson.isEmpty()) System.out.println(error404);
                break;
            case 4: //Gênero
                System.out.print("Digite o gênero que deseja procurar: ");
                char genderSearch = sc.next().toUpperCase().charAt(0);

                for (Person person: personList) { //Cabeçalho
                    if (role == 'P' && Objects.equals(person.getGender(), genderSearch)) {
                        System.out.println(teacherHeader);
                        break;
                    } else if (role == 'A' && Objects.equals(person.getGender(), genderSearch)) {
                        System.out.println(studentHeader);
                        break;
                    }
                }

                //List -> Stream -> filter -> List
                orderPerson = personList.stream()
                        .filter(person -> { //Registros encontrados
                            if (person instanceof Teacher teacher && person.getGender() == genderSearch) { //Filtra Professores
                                System.out.println(teacher.completeTeacherString(19, 12));
                                return true;
                            } else if (person instanceof Student student && person.getGender() == genderSearch) { //Filtra alunos
                                System.out.println(student.completeStudentString(19));
                                return true;
                            }
                            return false; // Caso não seja nem professor nem aluno
                        })
                        .toList();

                if (orderPerson.isEmpty()) System.out.println(error404);
                break;
            case 0:
                break;
            default:
                if (role == 'P') {
                    switch (choiceAtributte) { //Professores
                        case 5: //Disciplina
                            System.out.print("Digite a disciplina que deseja procurar: ");
                            String courseSearch = sc.nextLine();

                            for (Person person: personList) { //Cabeçalho
                                if (person instanceof Teacher teacher && teacher.getCourse().equalsIgnoreCase(courseSearch)) {
                                    System.out.println(teacherHeader);
                                    break;
                                }
                            }
                            //List -> Stream -> filter -> List
                            orderPerson = personList.stream()
                                    .filter(person -> { //Registros encontrados
                                        if (person instanceof Teacher teacher && teacher.getCourse().equalsIgnoreCase(courseSearch)) {
                                            System.out.println(teacher.completeTeacherString(19, 12));
                                            return true;
                                        }
                                        return false; //Se não existir
                                    }) //Pra usar método
                                    .toList();

                            if (orderPerson.isEmpty()) System.out.println(error404);
                            break;
                        case 6: //Salário
                            System.out.print("Digite o salário que deseja procurar: ");
                            double salarySearch = sc.nextDouble();

                            for (Person person: personList) { //Cabeçlho
                                if (person instanceof Teacher teacher && teacher.getSalary() == salarySearch) {
                                    System.out.println(teacherHeader);
                                    break;
                                }
                            }
                            //List -> Stream -> filter -> List
                            orderPerson = personList.stream()
                                    .filter(person -> { //Registros encontrados
                                        if (person instanceof Teacher teacher && teacher.getSalary() == salarySearch) {
                                            System.out.println(teacher.completeTeacherString(19, 12));
                                            return true;
                                        }
                                        return false; //Se não existir
                                    })
                                    .toList();

                            if (orderPerson.isEmpty()) System.out.println(error404);
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

                            for (Person person: personList) { //Cabeçlho
                                if (person instanceof Student student && student.getSemester() == semesterSearch) {
                                    System.out.println(studentHeader);
                                    break;
                                }
                            }
                            //List -> Stream -> filter -> List
                            orderPerson = personList.stream()
                                    .filter(person -> {
                                        if (person instanceof Student student && student.getSemester() == semesterSearch) {
                                            System.out.println(student.completeStudentString(19));
                                            return true;
                                        }
                                        return false;
                                    })
                                    .toList();

                            if (orderPerson.isEmpty()) System.out.println(error404);
                            break;
                        default:
                            System.out.println("\nOpção inválida, Tente novamente!");
                            break;
                    }
                }
        }
    }
}