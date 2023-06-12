package entities;

public class Person {
        protected String name;
        protected Integer age;
        protected final String id;
        protected Character gender;

    public Person(String name, Integer age, Character gender, String id) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.id = id;
    }

    //Setters and getters
        public String getName() { return name; }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getId() {
            return id;
        }

        public char getGender() {
            return gender;
        }

        public void setGender(char gender) {
            this.gender = gender;
        }

        //Método que recorta o nome com o operador ternário e o substring, pro caso de ser grande, coloca reticências.
        public String truncateName(int nameLength) {
            return name.length() > nameLength ? name.substring(0, nameLength - 2) + "…" : name;
        }

        //Métodos que substitui o "toString" para imprimir os dados em formato tabular
        public String standardString(int nameLength) {
            String truncatedName = truncateName(nameLength);
            return String.format("%-" + nameLength + "s \b\b| %s \t| %d", truncatedName, id, age);
        }

}