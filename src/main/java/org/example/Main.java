package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Main {
    static String staticFileName = "library_data.txt";
    public static void main(String[] args) {
        boolean run = true;
        Library library = new Library();

        library.loadFile(staticFileName);
        while (run){
            System.out.print("명령) ");
            Scanner sc = new Scanner(System.in);
            String order = sc.next();

            switch (order) {
                case "종료":
                    Runtime.getRuntime().addShutdownHook(new Thread(() -> library.saveFile(staticFileName)));
                    run = false;
                    break;
                case "등록":
                    sc.nextLine();
                    System.out.print("명언 : ");
                    String title = sc.nextLine();
                    System.out.print("작가 : ");
                    String author = sc.nextLine();

                    if (title.equals("")) {
                        System.out.println("제목은 빈칸일 수 없습니다.");
                    } else if (author.equals("")) {
                        System.out.println("저자는 빈칸일 수 없습니다.");
                    } else library.booking(title, author);
                    break;
                case "목록":
                    library.check();
                    break;
                case "삭제":
                    sc.nextLine();
                    library.check();
                    System.out.print("삭제할 명언을 선택하세요 : ");
                    int deleteIdx = sc.nextInt();
                    library.delete(deleteIdx, library);
                    break;
                case "수정":
                    sc.nextLine();
                    library.check();
                    System.out.print("수정할 명언을 선택하세요 : ");
                    int updateIdx = sc.nextInt();
                    library.update(updateIdx, library);
                    break;
                case "빌드":
                    library.exportJson();
                    break;
                default:
                    System.out.println("올바른 명령어를 입력해주세요.");
                    break;
            }
        }
    }
}
class Book{
    private int id;
    private String content;
    private String author;

    public int getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getAuthor() {
        return author;
    }

    public Book(int id, String content, String author) {
        this.id = id;
        this.content = content;
        this.author = author;
    }
}
class Library{

    List<String> bookshelf = new ArrayList<>();
    int count = 1;

    void booking(String title, String author){
        System.out.println(count+"번 명언이 등록되었습니다");
        bookshelf.add(count + " / " + author + " / " + title);
        count++;
    }
    void check(){
        if (bookshelf.size() < 1) {
            System.out.println("도서관에 책이 없습니다.");
        }else {
            for (String str : bookshelf) {
                System.out.println(str);
            }
        }
    }

    public void delete(int deleteNum, Library library) {
        for (int i = library.bookshelf.size() - 1; i >= 0; i--) {
            if (library.bookshelf.get(i).startsWith(String.valueOf(deleteNum))) {
                library.bookshelf.remove(i);
            }
        }
        System.out.println("삭제되었습니다.");
    }

    void update(int updateNum, Library library){
        Scanner sc = new Scanner(System.in);
        for (int i = library.bookshelf.size() - 1; i >= 0; i--) {
            if (library.bookshelf.get(i).startsWith(String.valueOf(updateNum))) {
                String[] parts = bookshelf.get(i).split(" / ");
                String author = parts[1];
                String title = parts[2];

                System.out.println("명언(기존) : " + title);
                System.out.print("명언 : ");
                String newTitle = sc.nextLine();

                System.out.println("작가(기존) : " + author);
                System.out.print("작가 : ");
                String newAuthor = sc.nextLine();

                bookshelf.set(i,updateNum + " / " + newAuthor + " / " + newTitle);
                System.out.println("수정되었습니다");
            }
        }
    }

    void saveFile(String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (String item : bookshelf) {
                writer.write(item);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void exportJson() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

            String fileName = "library_data.json";
            String filePath = Paths.get("").toAbsolutePath() + File.separator + fileName;

            List<Book> bookList = new ArrayList<>();
            for (String item : bookshelf) {
                String[] parts = item.split(" / ");

                int id = Integer.parseInt(parts[0]);
                String author = parts[1];
                String content = parts[2];
                Book tempBook = new Book(id, content, author);
                bookList.add(tempBook);
            }

            objectMapper.writeValue(new File(filePath), bookList);

            System.out.println("내보내기 완료되었습니다");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadFile(String fileName){
        bookshelf.clear();
        int maxCount = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                bookshelf.add(line);
                String[] parts = line.split(" / ");
                if (parts.length > 0) {
                    try {
                        int currentCount = Integer.parseInt(parts[0]);
                        if (currentCount > maxCount) {
                            maxCount = currentCount;
                        }
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
            }
            count = maxCount + 1;
        } catch (FileNotFoundException e) {
            saveFile(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
