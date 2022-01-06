import Domain.Book;
import Domain.Publisher;
import Tools.JDBCUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

public class BookManagerDAO {

    private QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource()); //query runner created to execute queries to database

    public void addPublisher(Publisher pub) {
        try {
            // Write SQL statement with placeholders
            String sql = "INSERT INTO Publisher (name, phone, city) VALUES(?,?,?)";

            Object[] params = {pub.getName(), pub.getPhone(), pub.getCity()}; //list params to go into placeholders

            qr.update(sql, params); //execute statement
        } catch (SQLException ex) {
            System.out.println(ex);
            throw new RuntimeException("Insert Exception!");
        }
    }

    public void addBook(Book book) {
        try {
            // Write SQL prepared statement
            PreparedStatement sql = JDBCUtils.getConnection().prepareStatement(
                    "INSERT INTO Book (ISBN,title,year,published_by,previous_edition,price) VALUES(?,?,?,?,?,?)");

            //put parameters into placeholders
            sql.setString(1, book.getISBN());
            sql.setString(2, book.getTitle());
            sql.setInt(3, book.getYear());
            sql.setString(4,book.getPublished_by());
            if(book.getPrevious_edition().equals("null")){
                sql.setNull(5, Types.NULL);
            }else{
                sql.setString(5,book.getPrevious_edition());
            }
            sql.setDouble(6,book.getPrice());

            sql.execute(); //execute statements
        } catch (SQLException ex) {
            System.out.println(ex);
            throw new RuntimeException("Insert Exception!");
        }
    }

    public void editBook(Book book) {
        try {
            // Write SQL prepared statement
            PreparedStatement sql = JDBCUtils.getConnection().prepareStatement(
                    "UPDATE Book SET title=?,year=?,published_by=?,previous_edition=?,price=? WHERE ISBN=?");

            //set parameters in placeholders
            sql.setString(6, book.getISBN());
            sql.setString(1, book.getTitle());
            sql.setInt(2, book.getYear());
            sql.setString(3,book.getPublished_by());
            if(book.getPrevious_edition().equals("null")){
                sql.setNull(4, Types.NULL);
            }else{
                sql.setString(4,book.getPrevious_edition());
            }
            sql.setDouble(5,book.getPrice());

            sql.executeUpdate(); //execute statement
        } catch (SQLException ex) {
            System.out.println(ex);
            throw new RuntimeException("Edit Exception!");
        }
    }

    public void deleteBook(String ISBN) {
        try {
            // Write SQL statement with placeholder
            String sql = "DELETE FROM Book WHERE ISBN=?";
            qr.update(sql, ISBN); //execute statement
        } catch (SQLException ex) {
            System.out.println(ex);
            throw new RuntimeException("Delete Exception!");
        }
    }

    public List<Book> selectAllBooks() {
        try {
            String sql = "SELECT * FROM Book"; //make sql statement -- No placeholders necessary
            List<Book> list = qr.query(sql, new BeanListHandler<>(Book.class)); //create beans for the list returned
            return list;
        } catch (SQLException ex) {
            System.out.println(ex);
            throw new RuntimeException("Select All Exception!");
        }
    }

    public List<Publisher> selectAllPublishers() {
        try {
            String sql = "SELECT * FROM Publisher"; //create sql statement
            List<Publisher> list = qr.query(sql, new BeanListHandler<>(Publisher.class)); //create beans for list returned
            return list;
        } catch (SQLException ex) {
            System.out.println(ex);
            throw new RuntimeException("Select All Exception!");
        }
    }

    public List<Book> searchByTitle(String title) {
        try {
            String sql = "SELECT * FROM Book WHERE title = ?"; //select statement with specific title
            List<Book> list = qr.query(sql, new BeanListHandler<>(Book.class), title);
            return list;
        } catch (SQLException ex) {
            System.out.println(ex);
            throw new RuntimeException("Select Exception!");
        }
    }

    public List<Book> searchByISBN(String isbn) {
        try {
            String sql = "SELECT * FROM Book WHERE ISBN = ?"; //select statement with specific ISBN
            //should only return 1 book but list made to make easier to print when returned
            List<Book> list = qr.query(sql, new BeanListHandler<>(Book.class), isbn);
            return list;
        } catch (SQLException ex) {
            System.out.println(ex);
            throw new RuntimeException("Select Exception!");
        }
    }

    public List<Book> searchByPublisher(String publisher) {
        try {
            String sql = "SELECT * FROM Book WHERE published_by = ?"; //select statement with specific publisher
            List<Book> list = qr.query(sql, new BeanListHandler<>(Book.class), publisher);
            return list;
        } catch (SQLException ex) {
            System.out.println(ex);
            throw new RuntimeException("Select Exception!");
        }
    }

    public List<Book> searchByPrice(Double minPrice, Double maxPrice) {
        try {
            String sql = "SELECT * FROM Book WHERE price > ? and price < ?"; //select statement with a price range
            Object[] params = {minPrice, maxPrice};
            List<Book> list = qr.query(sql, new BeanListHandler<>(Book.class), params);
            return list;
        } catch (SQLException ex) {
            System.out.println(ex);
            throw new RuntimeException("Select Exception!");
        }
    }

    public List<Book> searchByYear(Integer year) {
        try {
            String sql = "SELECT * FROM Book WHERE year = ?"; //select statement with specific year
            List<Book> list = qr.query(sql, new BeanListHandler<>(Book.class), year);
            return list;
        } catch (SQLException ex) {
            System.out.println(ex);
            throw new RuntimeException("Select Exception!");
        }
    }

    public List<Book> searchByTitleAndPublisher(String title, String publisher) {
        try {
            String sql = "SELECT * FROM Book WHERE title = ? and published_by = ?"; //select statement with specific publisher
            Object[] params = {title, publisher};
            //should in most cases return 1 book but list returned to make printing list to user easier
            List<Book> list = qr.query(sql, new BeanListHandler<>(Book.class), params);
            return list;
        } catch (SQLException ex) {
            System.out.println(ex);
            throw new RuntimeException("Select Exception!");
        }
    }
}
