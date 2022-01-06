package Domain;

public class Book {
    //private fields
    private String ISBN;
    private String title;
    private Integer year;
    private String published_by;
    private String previous_edition;
    private Double price;

    //constructor
    public Book(){}

    public Book(String isbn, String title, Integer year, String publisher, String previous_edition, Double price) {
        this.ISBN=isbn;
        this.title=title;
        this.year=year;
        this.published_by=publisher;
        this.previous_edition=previous_edition;
        this.price=price;
    }

    //getters and setters
    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {this.title = title;}

    public Integer getYear() {return year;}

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getPublished_by() {
        return published_by;
    }

    public void setPublished_by(String published_by) {
        this.published_by = published_by;
    }

    public String getPrevious_edition() {
        return previous_edition;
    }

    public void setPrevious_edition(String previous_edition) {
        this.previous_edition = previous_edition;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {this.price = price;}
}
