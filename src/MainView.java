import Domain.Book;
import Domain.Publisher;

import java.util.List;
import java.util.Scanner;

public class MainView {

    private BookManagerDAO dao = new BookManagerDAO(); //need DAO object to send user inputs to in order to execute queries

    public void run(){ //TODO change database.properties to Dr. Wei's properties
        Scanner sc = new Scanner(System.in);

        while (true) {
            // print text menu
            System.out.println("---------------Book Manager Software---------------");
            System.out.println("1.Add Publisher 2.Add Book 3.Edit Book 4.Delete Book 5.Search Books 6.Exit");
            System.out.println("Please select the function, type [1-6] and press enter:");

            int functionNum = sc.nextInt();
            switch (functionNum) {
                case 1:
                    addPublisher();
                    break;
                case 2:
                    addBook();
                    break;
                case 3:
                    EditBook();
                    break;
                case 4:
                    DeleteBook();
                    break;
                case 5:
                    SearchBooks();
                    break;
                case 6:
                    System.out.println("Thank you for using Book Manager Software!");
                    System.exit(0);
                    break;

            }
        }
    }

    public void addPublisher() {
        //get user inputs
        System.out.println("Please enter the following information:");
        Scanner s = new Scanner(System.in);
        System.out.println("Enter name of the Publisher:");
        String name = s.nextLine();
        System.out.println("Enter phone number of the Publisher:");
        String phone = s.nextLine();
        System.out.println("Enter city of the Publisher:");
        String city = s.nextLine();

        //create publisher object and send to DAO
        Publisher pub = new Publisher(name,phone,city);
        dao.addPublisher(pub);

        System.out.println("Publisher Added Successfully!");
    }

    public void addBook() {
        //get user inputs
        System.out.println("Please enter the following information:");
        Scanner s = new Scanner(System.in);
        System.out.println("Enter ISBN of the Book:");
        String ISBN = s.nextLine();
        System.out.println("Enter title of the Book:");
        String title = s.nextLine();
        System.out.println("Enter year the Book was published in this format: YYYY");
        Integer year = Integer.parseInt(s.nextLine());

        selectAllPublishers();
        System.out.println("Enter the Publisher Name of the Book:");
        String publisher = s.nextLine();

        selectAllBooks();
        System.out.println("Enter ISBN of previous edition of the Book: (If none enter 'null')");
        String previous_edition = s.nextLine();

        System.out.println("Enter price of the Book:");
        Double price = Double.parseDouble(s.nextLine());

        //create book object and send to DAO
        Book book = new Book(ISBN,title,year,publisher,previous_edition,price);
        dao.addBook(book);

        System.out.println("Book Added Successfully!");
    }

    public void EditBook() {
        // We first display all books to the user
        selectAllBooks();
        //get user inputs
        System.out.println("Which Book do you want to edit?");
        Scanner s = new Scanner(System.in);
        System.out.print("Type ISBN of the book and press enter:");
        String ISBN = s.nextLine();

        System.out.println("Enter new title of the Book:");
        String title = s.nextLine();
        System.out.println("Enter new year the Book was published in this format: YYYY");
        Integer year = Integer.parseInt(s.nextLine());

        selectAllPublishers();
        System.out.println("Enter the new Publisher name of the Book:");
        String publisher = s.nextLine();

        selectAllBooks();
        System.out.println("Enter new ISBN of previous edition of the Book: (If none enter 'null')");
        String previous_edition = s.nextLine();

        System.out.println("Enter new price of the Book:");
        Double price = Double.parseDouble(s.nextLine());

        //create book object and send to DAO
        Book book = new Book(ISBN,title,year,publisher,previous_edition,price);
        dao.editBook(book);

        System.out.println("Book Edited Successfully!");
    }

    public void DeleteBook() {
        //list all books
        selectAllBooks();
        //get user input
        System.out.println("Enter the ISBN of the book you want to delete and press enter:");
        String ISBN = new Scanner(System.in).nextLine();
        //send book to DAO to delete
        dao.deleteBook(ISBN);

        System.out.println("Book is deleted.");
    }

    public void SearchBooks() {
        System.out.println("1. Search All Books 2. Search Books Based on Criteria");
        Scanner s = new Scanner(System.in);
        int selectSearch = s.nextInt();
        switch (selectSearch) {
            case 1:
                // select all books from DB
                selectAllBooks();
                break;
            case 2:
                // select based on certain criteria
                selectBooks();
                break;
        }
    }


    public void selectAllBooks(){
        List<Book> bookList = dao.selectAllBooks();
        if (bookList.size() != 0)
            printBook(bookList); //print out books to user
        else
            System.out.println("No Book is found!");
    }

    public void selectBooks(){
        //get user input for which criteria to search by
        System.out.println("Which criteria would you like to search by?");
        System.out.println("1. Search by title 2. Search by ISBN 3. Search by Publisher " +
                "4. Search by Price Range 5. Search by Year 6. Search by title and Publisher");
        Scanner s = new Scanner(System.in);
        int selectCriteria = s.nextInt();
        switch (selectCriteria) {
            case 1: //search by title
                System.out.println("Enter title to search by:");
                Scanner s1 = new Scanner(System.in);
                String title = s1.nextLine();
                List<Book> bookList = dao.searchByTitle(title);

                if (bookList.size() != 0)
                    printBook(bookList); //print out books
                else
                    System.out.println("No Book is found!");

                break;
            case 2: //search by ISBN
                System.out.println("Enter ISBN to search by:");
                Scanner s2 = new Scanner(System.in);
                String isbn = s2.nextLine();
                bookList = dao.searchByISBN(isbn);

                if (bookList.size() != 0)
                    printBook(bookList); //print out books
                else
                    System.out.println("No Book is found!");

                break;
            case 3: //search by Publisher
                System.out.println("Enter publisher to search by:");
                Scanner s3 = new Scanner(System.in);
                String publisher = s3.nextLine();
                bookList = dao.searchByPublisher(publisher);

                if (bookList.size() != 0)
                    printBook(bookList); //print books
                else
                    System.out.println("No Book is found!");

                break;
            case 4: //search by price range
                System.out.println("Enter min price to search by:");
                Scanner s4 = new Scanner(System.in);
                Double minPrice = Double.parseDouble(s4.nextLine());

                System.out.println("Enter max price to search by:");
                Double maxPrice = Double.parseDouble(s4.nextLine());
                bookList = dao.searchByPrice(minPrice, maxPrice);

                if (bookList.size() != 0)
                    printBook(bookList); //print books
                else
                    System.out.println("No Book is found!");

                break;
            case 5: //search by year
                System.out.println("Enter year to search by:");
                Scanner s5 = new Scanner(System.in);
                Integer year = Integer.parseInt(s5.nextLine());
                bookList = dao.searchByYear(year);

                if (bookList.size() != 0)
                    printBook(bookList); //print books
                else
                    System.out.println("No Book is found!");

                break;
            case 6: //search by title and publisher
                System.out.println("Enter title to search by:");
                Scanner s6 = new Scanner(System.in);
                title = s6.nextLine();
                System.out.println("Enter publisher to search by:");
                publisher = s6.nextLine();
                bookList = dao.searchByTitleAndPublisher(title, publisher);

                if (bookList.size() != 0)
                    printBook(bookList); //print books
                else
                    System.out.println("No Book is found!");

                break;
        }
    }

    public void selectAllPublishers(){ //print all publishers
        List<Publisher> publisherList = dao.selectAllPublishers();
        if (publisherList.size() != 0)
            printPublisher(publisherList);
        else
            System.out.println("No Publisher is found!");
    }

    private void printPublisher(List<Publisher> publisherList) {
        System.out.printf("%-25s %-10s %-20s %n", "Name", "Phone", "City"); //list field names
        System.out.println("------------------------------------------------------------");
        // Iterate the list and print each item to console
        for (Publisher pub : publisherList) { //list all publishers
            System.out.printf("%-25s %-10s %-20s%n", pub.getName(), pub.getPhone(), pub.getCity());
        }
    }

    private void printBook(List<Book> bookList) {
            System.out.printf("%-10s %-50s %-4s %-25s %-20s %-10s %n", "ISBN", "Title",
                    "Year", "Publisher", "Previous Edition", "Price"); //list field names
            System.out.println("-----------------------------------------------------" +
                            "----------------------------------------------------------------------");
            // Iterate the list and print each item to console
            for (Book act : bookList) { //print each book from the list
                System.out.printf("%-10s %-50s %-4d %-25s %-20s %-10.2f%n", act.getISBN(), act.getTitle(), act.getYear(),
                        act.getPublished_by(), act.getPrevious_edition(), act.getPrice());
            }
    }

}
