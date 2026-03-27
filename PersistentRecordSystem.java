import java.io.*;
import java.util.*;

public class PersistentRecordSystem {

    static final String FILE_NAME = "records.txt";
    static ArrayList<String> names = new ArrayList<>();
    static ArrayList<Integer> grades = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        loadFile(); 

        while (true) {
            System.out.println("\n MENU ");
            System.out.println("1. Add Record");
            System.out.println("2. View Records");
            System.out.println("3. Update Record");
            System.out.println("4. Delete Record");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");

            int choice = getNumber();

            switch (choice) {
                case 1: addRecord(); break;
                case 2: viewRecords(); break;
                case 3: updateRecord(); break;
                case 4: deleteRecord(); break;
                case 5:
                    System.out.println("Program closed.");
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    static void addRecord() {
        System.out.print("Enter name: ");
        String name = sc.nextLine();

        System.out.print("Enter grade: ");
        int grade = getNumber();

        names.add(name);
        grades.add(grade);

        saveFile(); 
        System.out.println("Record saved!");
    }

    static void viewRecords() {
        if (names.isEmpty()) {
            System.out.println("No records found.");
            return;
        }

        System.out.println("\n--- RECORDS ---");
        for (int i = 0; i < names.size(); i++) {
            System.out.println(i + " | " + names.get(i) + " - " + grades.get(i));
        }
    }

    static void updateRecord() {
        viewRecords();
        if (names.isEmpty()) return;

        System.out.print("Enter record number to update: ");
        int index = getNumber();

        if (index < 0 || index >= names.size()) {
            System.out.println("Invalid input!");
            return;
        }

        System.out.print("Enter new name: ");
        String newName = sc.nextLine();

        System.out.print("Enter new grade: ");
        int newGrade = getNumber();

        names.set(index, newName);
        grades.set(index, newGrade);

        saveFile(); // rewrite file
        System.out.println("Record updated!");
    }

    static void deleteRecord() {
        viewRecords();
        if (names.isEmpty()) return;

        System.out.print("Enter record number to delete: ");
        int index = getNumber();

        if (index < 0 || index >= names.size()) {
            System.out.println("Invalid input!");
            return;
        }

        names.remove(index);
        grades.remove(index);

        saveFile(); 
        System.out.println("Record deleted!");
    }

    static void saveFile() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME));

            for (int i = 0; i < names.size(); i++) {
                writer.write(names.get(i) + "," + grades.get(i));
                writer.newLine();
            }

            writer.close();
        } catch (IOException e) {
            System.out.println("Error saving file!");
        }
    }

    static void loadFile() {
        try {
            File file = new File(FILE_NAME);

            if (!file.exists()) {
                file.createNewFile(); // create file if not exists
                System.out.println("New file created.");
                return;
            }

            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;

            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");

                if (data.length == 2) {
                    names.add(data[0]);
                    grades.add(Integer.parseInt(data[1]));
                }
            }

            reader.close();
            System.out.println("Records loaded successfully!");
        } catch (Exception e) {
            System.out.println("Error loading file!");
        }
    }

    static int getNumber() {
        while (true) {
            try {
                return Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                System.out.print("Enter a valid number: ");
            }
        }
    }
}