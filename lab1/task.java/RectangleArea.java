import java.util.Scanner;

public class RectangleArea {
    public static double rectangleArea(double width, double height) {
        return width * height;
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        double width = scanner.nextDouble();
        double height = scanner.nextDouble();
    
        double area = rectangleArea(width, height);

        if (area == (int)area) {
            System.out.println((int)area);
        } else {
            System.out.println(area);
        }
        
        scanner.close();
    }
}