package com.klef.jfsd.exam;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.Scanner;

public class ClientDemo {
    private static SessionFactory sessionFactory;

    static {
        sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
    }

    public static void insertDepartment(String name, String location, String hodName) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            Department department = new Department(name, location, hodName);
            session.save(department);

            transaction.commit();
            System.out.println("Department inserted successfully: " + department);
        }
    }

    public static void deleteDepartmentById(int departmentId) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            String hql = "DELETE FROM Department WHERE departmentId = :deptId";
            int deletedCount = session.createQuery(hql)
                                      .setParameter("deptId", departmentId)
                                      .executeUpdate();

            transaction.commit();

            if (deletedCount > 0) {
                System.out.println("Department with ID " + departmentId + " deleted successfully.");
            } else {
                System.out.println("No department found with ID " + departmentId);
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n1. Insert Department");
            System.out.println("2. Delete Department by ID");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter Department Name: ");
                    String name = scanner.next();
                    System.out.print("Enter Location: ");
                    String location = scanner.next();
                    System.out.print("Enter HoD Name: ");
                    String hodName = scanner.next();
                    insertDepartment(name, location, hodName);
                    break;

                case 2:
                    System.out.print("Enter Department ID to delete: ");
                    int deptId = scanner.nextInt();
                    deleteDepartmentById(deptId);
                    break;

                case 3:
                    sessionFactory.close();
                    System.out.println("Exiting...");
                    System.exit(0);

                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}
