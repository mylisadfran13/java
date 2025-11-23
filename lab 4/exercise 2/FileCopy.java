import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileCopy {
    public static void main(String[] args) {
        String sourceFile = "reading.txt";
        String destinationFile = "recording.txt";
        
        FileInputStream inputStream = null;
        FileOutputStream outputStream = null;
        
        try {
            System.out.println("открывается файл " + sourceFile + " для чтения...");
            inputStream = new FileInputStream(sourceFile);

            System.out.println("открывется файл " + destinationFile + " для записи...");
            outputStream = new FileOutputStream(destinationFile);
            
            System.out.println("начинается копирование...");
            byte[] buffer = new byte[1024];
            int bytesRead;
            
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            
            System.out.println("копирование успешно завершено!");

        } catch (IOException e) {
            System.out.println("ошибка при работе с файлами: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                    System.out.println("исходный файл закрыт");
                }
            } catch (IOException e) {
                System.out.println("ошибка при закрытии исходного файла: " + e.getMessage());
            }
            
            try {
                if (outputStream != null) {
                    outputStream.close();
                    System.out.println("целевой файл закрыт");
                }
            } catch (IOException e) {
                System.out.println("ошибка при закрытии целевого файла: " + e.getMessage());
            }
        }
    }
}