import java.util.Scanner;

abstract class Employee {
    protected String name;
    protected int id;
    protected double salary;
    
    private static int employeeCount = 0;
    
    public Employee() {
        employeeCount++;
    }
    
    public Employee(String name, int id, double salary) {
        this.name = name;
        this.id = id;
        this.salary = salary;
        employeeCount++;
    }

    public abstract void work();
    public abstract void displayInfo();
    
    //геттеры и сеттеры (инкапсуляция)
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public double getSalary() {
        return salary;
    }
    
    public void setSalary(double salary) {
        this.salary = salary;
    }
    
    public static int getEmployeeCount() {
        return employeeCount;
    }
}

class Administrator extends Employee {
    private String department;
    private int yearsOfExperience;
    private boolean hasManagementRights;
    
    //конструктор по умолчанию
    public Administrator() {
        super();
    }

    public Administrator(String name, int id, double salary, String department, int yearsOfExperience, boolean hasManagementRights) {
        super(name, id, salary);
        this.department = department;
        this.yearsOfExperience = yearsOfExperience;
        this.hasManagementRights = hasManagementRights;
    }

    @Override
    public void work() {
        System.out.println(name + " управляет отделом " + department);
    }
    
    @Override
    public void displayInfo() {
        System.out.println("Администратор: " + name + " (ID: " + id + ")");
        System.out.println("Отдел: " + department);
        System.out.println("Опыт работы: " + yearsOfExperience + " лет");
        System.out.println("Зарплата: рублей" + salary);
        System.out.println("Права управления: " + (hasManagementRights ? "Да" : "Нет"));
    }
    
    //специфические методы для администратора
    public void manageTeam() {
        System.out.println(name + " управляет командой в отделе " + department);
    }
    
    public void generateReport() {
        System.out.println(name + " генерирует отчет по отделу " + department);
    }
    
    //геттеры и сеттеры
    public String getDepartment() {
        return department;
    }
    
    public void setDepartment(String department) {
        this.department = department;
    }
    
    public int getYearsOfExperience() {
        return yearsOfExperience;
    }
    
    public void setYearsOfExperience(int yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }
    
    public boolean isHasManagementRights() {
        return hasManagementRights;
    }
    
    public void setHasManagementRights(boolean hasManagementRights) {
        this.hasManagementRights = hasManagementRights;
    }
}

//второй уровень наследования - Программист (наследуется от Employee)
class Programmer extends Employee {
    private String programmingLanguage;
    private String project;
    private int linesOfCodeWritten;

    public Programmer() {
        super();
    }

    public Programmer(String name, int id, double salary, String programmingLanguage, String project, int linesOfCodeWritten) {
        super(name, id, salary);
        this.programmingLanguage = programmingLanguage;
        this.project = project;
        this.linesOfCodeWritten = linesOfCodeWritten;
    }
    
    //абстрактные методы
    @Override
    public void work() {
        System.out.println(name + " пишет код на " + programmingLanguage + " для проекта " + project);
    }
    
    @Override
    public void displayInfo() {
        System.out.println("Программист: " + name + " (ID: " + id + ")");
        System.out.println("Язык программирования: " + programmingLanguage);
        System.out.println("Проект: " + project);
        System.out.println("Написано строк кода: " + linesOfCodeWritten);
        System.out.println("Зарплата: рублей" + salary);
    }

    public void debugCode() {
        System.out.println(name + " оредачит код на " + programmingLanguage);
    }
    
    public void attendMeeting() {
        System.out.println(name + " участвует в собрании по проекту " + project);
    }
    
    //геттеры и сеттеры
    public String getProgrammingLanguage() {
        return programmingLanguage;
    }
    
    public void setProgrammingLanguage(String programmingLanguage) {
        this.programmingLanguage = programmingLanguage;
    }
    
    public String getProject() {
        return project;
    }
    
    public void setProject(String project) {
        this.project = project;
    }
    
    public int getLinesOfCodeWritten() {
        return linesOfCodeWritten;
    }
    
    public void setLinesOfCodeWritten(int linesOfCodeWritten) {
        this.linesOfCodeWritten = linesOfCodeWritten;
    }
}

//второй уровень наследования - Менеджер (наследуется от Employee)
class Manager extends Employee {
    private int teamSize;
    private String managedProject;
    private double budget;

    public Manager() {
        super();
    }

    public Manager(String name, int id, double salary, int teamSize, String managedProject, double budget) {
        super(name, id, salary);
        this.teamSize = teamSize;
        this.managedProject = managedProject;
        this.budget = budget;
    }
    
    //реализация абстрактных методов
    @Override
    public void work() {
        System.out.println(name + " управляет командой из " + teamSize + " человек");
    }
    
    @Override
    public void displayInfo() {
        System.out.println("Менеджер: " + name + " (ID: " + id + ")");
        System.out.println("Размер команды: " + teamSize + " человек");
        System.out.println("Управляемый проект: " + managedProject);
        System.out.println("Бюджет: рублей" + budget);
        System.out.println("Зарплата: реблей" + salary);
    }

    public void conductMeeting() {
        System.out.println(name + " проводит собрание с командой");
    }
    
    public void allocateResources() {
        System.out.println(name + " распределяет ресурсы для проекта " + managedProject);
    }
    
    //геттеры и сеттеры
    public int getTeamSize() {
        return teamSize;
    }
    
    public void setTeamSize(int teamSize) {
        this.teamSize = teamSize;
    }
    
    public String getManagedProject() {
        return managedProject;
    }
    
    public void setManagedProject(String managedProject) {
        this.managedProject = managedProject;
    }
    
    public double getBudget() {
        return budget;
    }
    
    public void setBudget(double budget) {
        this.budget = budget;
    }
}

public class EmployeeManagementSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("=== СИСТЕМА УПРАВЛЕНИЯ СОТРУДНИКАМИ ===\n");
 
        System.out.println("Создание администратора:");
        Administrator admin = createAdministrator(scanner);
        
        System.out.println("\nСоздание программиста:");
        Programmer programmer = createProgrammer(scanner);
        
        System.out.println("\nСоздание менеджера:");
        Manager manager = createManager(scanner);
        
        System.out.println("\n=== ДЕМОНСТРАЦИЯ ПОЛИМОРФИЗМА ===");
        Employee[] employees = {admin, programmer, manager};
        
        for (Employee employee : employees) {
            employee.displayInfo();
            employee.work();
            
            if (employee instanceof Administrator) {
                ((Administrator) employee).manageTeam();
            } else if (employee instanceof Programmer) {
                ((Programmer) employee).debugCode();
            } else if (employee instanceof Manager) {
                ((Manager) employee).conductMeeting();
            }
            System.out.println("--------------------");
        }
        
        //счетчик
        System.out.println("Всего создано сотрудников: " + Employee.getEmployeeCount());
        
        System.out.println("\n=== ДЕМОНСТРАЦИЯ ГЕТТЕРОВ И СЕТТЕРОВ ===");
        System.out.println("Имя программиста: " + programmer.getName());
        programmer.setSalary(programmer.getSalary() + 1000);
        System.out.println("Новая зарплата программиста: $" + programmer.getSalary());
        
        scanner.close();
    }
    
    public static Administrator createAdministrator(Scanner scanner) {
        System.out.print("Введите имя администратора: ");
        String name = scanner.nextLine();
        
        System.out.print("Введите ID: ");
        int id = scanner.nextInt();
        
        System.out.print("Введите зарплату: ");
        double salary = scanner.nextDouble();
        scanner.nextLine(); //очистка буфера
        
        System.out.print("Введите отдел: ");
        String department = scanner.nextLine();
        
        System.out.print("Введите опыт работы (лет): ");
        int experience = scanner.nextInt();
        
        System.out.print("Имеет права управления (true/false): ");
        boolean hasRights = scanner.nextBoolean();
        scanner.nextLine(); //очистка буфера
        
        return new Administrator(name, id, salary, department, experience, hasRights);
    }
    
    public static Programmer createProgrammer(Scanner scanner) {
        System.out.print("Введите имя программиста: ");
        String name = scanner.nextLine();
        
        System.out.print("Введите ID: ");
        int id = scanner.nextInt();
        
        System.out.print("Введите зарплату: ");
        double salary = scanner.nextDouble();
        scanner.nextLine(); 
        
        System.out.print("Введите язык программирования: ");
        String language = scanner.nextLine();
        
        System.out.print("Введите проект: ");
        String project = scanner.nextLine();
        
        System.out.print("Введите количество написанных строк кода: ");
        int lines = scanner.nextInt();
        scanner.nextLine(); 
        
        return new Programmer(name, id, salary, language, project, lines);
    }
    
    public static Manager createManager(Scanner scanner) {
        System.out.print("Введите имя менеджера: ");
        String name = scanner.nextLine();
        
        System.out.print("Введите ID: ");
        int id = scanner.nextInt();
        
        System.out.print("Введите зарплату: ");
        double salary = scanner.nextDouble();
        
        System.out.print("Введите размер команды: ");
        int teamSize = scanner.nextInt();
        scanner.nextLine();
        
        System.out.print("Введите управляемый проект: ");
        String project = scanner.nextLine();
        
        System.out.print("Введите бюджет: ");
        double budget = scanner.nextDouble();
        scanner.nextLine(); 
        
        return new Manager(name, id, salary, teamSize, project, budget);
    }
}