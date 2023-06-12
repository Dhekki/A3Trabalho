package application;

import entities.Person;
import entities.Student;
import entities.Teacher;
import interfaces.Menus;

import java.util.ArrayList;
import java.util.Scanner;

public class Programa {
    public static void main(String[] args) {
        //Iniciar o Scanner
        Scanner sc = new Scanner(System.in);

        //Instanciação do Array list, Declaração da classe Student e Teacher
        ArrayList<Person> personList = new ArrayList<>();
        Student student;
        Teacher teacher;

        //7 entradas automáticas de estudantes
        personList.add(new Student("Mario Pereira da Silva", 18, 'M', "1242526987", 2));
        personList.add(new Student("Cleber de Nascimento Souza", 16, 'M', "1242526949", 1));
        personList.add(new Student("Joao Pedro", 19, 'M', "1242736987", 3));
        personList.add(new Student("Larissa Vasconcelos", 20, 'F', "1842526987", 4));
        personList.add(new Teacher("Julia de Alcantra", 41, 'F', "1241626987", "Matemática", 1900.00));
        personList.add(new Teacher("Clara Borges", 17, 'F', "1242521787", "História", 1800.00));
        personList.add(new Teacher("Paulo Marcos Silva dos Santos", 20, 'M', "1242522887", "Geografia", 1600.00));

        //Instanciando Menu e Declarando escolha do usuário
        Menus menus = new Menus();
        int choice; //variável para controle de repetição
        do {
            choice = menus.mainMenu(sc); //Escolha do usuário
            menus.choiceMenu(choice, personList, sc); //Ação derivada da escolha do usuário
        } while (choice != 0);

        //Encerrar Scanner
        sc.close();
    }
}