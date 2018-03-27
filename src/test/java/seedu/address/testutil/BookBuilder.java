package seedu.address.testutil;

import java.util.Collections;
import java.util.Set;

import seedu.address.model.book.Author;
import seedu.address.model.book.Book;
import seedu.address.model.book.Category;
import seedu.address.model.book.Description;
import seedu.address.model.book.Gid;
import seedu.address.model.book.Isbn;
import seedu.address.model.book.Priority;
import seedu.address.model.book.PublicationDate;
import seedu.address.model.book.Publisher;
import seedu.address.model.book.Rating;
import seedu.address.model.book.Status;
import seedu.address.model.book.Title;
import seedu.address.model.util.BookDataUtil;

/**
 * A utility class to help with building Book objects.
 */
public class BookBuilder {

    public static final String DEFAULT_AUTHOR = "Andy Weir";
    public static final String DEFAULT_TITLE = "Artemis";
    public static final String DEFAULT_CATEGORY = "Science Fiction";
    public static final String DEFAULT_DESCRIPTION = "This is a description for Artemis.";
    public static final int DEFAULT_RATING = -1;
    public static final String DEFAULT_PRIORITY = "LOW";
    public static final String DEFAULT_STATUS = "UNREAD";
    public static final String DEFAULT_ID = "ry3GjwEACAAJ";
    public static final String DEFAULT_ISBN = "9780525572664";
    public static final String DEFAULT_PUBLISHER = "Someone";
    public static final String DEFAULT_PUBLICATION_DATE = "2017-11-14";

    private Set<Author> authors;
    private Title title;
    private Set<Category> categories;
    private Description description;
    private Status status;
    private Priority priority;
    private Rating rating;
    private Priority priority;
    private Status status;
    private Gid gid;
    private Isbn isbn;
    private PublicationDate publicationDate;
    private Publisher publisher;

    public BookBuilder() {
        authors = Collections.singleton(new Author(DEFAULT_AUTHOR));
        title = new Title(DEFAULT_TITLE);
        categories = Collections.singleton(new Category(DEFAULT_CATEGORY));
        description = new Description(DEFAULT_DESCRIPTION);
        rating = new Rating(DEFAULT_RATING);
        priority = new Priority(DEFAULT_PRIORITY);
        status = new Status(DEFAULT_STATUS);
        gid = new Gid(DEFAULT_ID);
        isbn = new Isbn(DEFAULT_ISBN);
        publicationDate = new PublicationDate(DEFAULT_PUBLICATION_DATE);
        publisher = new Publisher(DEFAULT_PUBLISHER);
    }

    /**
     * Initializes the BookBuilder with the data of {@code bookToCopy}.
     */
    public BookBuilder(Book bookToCopy) {
        authors = new HashSet<>(bookToCopy.getAuthors());
        title = bookToCopy.getTitle();
        categories = new HashSet<>(bookToCopy.getCategories());
        description = bookToCopy.getDescription();
        rating = bookToCopy.getRating();
        priority = bookToCopy.getPriority();
        status = bookToCopy.getStatus();
    }

    /**
     * Parses the {@code authors} into a {@code Set<Author>} and set it to the {@code Book} that we are building.
     */
    public BookBuilder withAuthors(String... authors) {
        this.authors = BookDataUtil.getAuthorSet(authors);
        return this;
    }

    /**
     * Sets the {@code Title} of the {@code Book} that we are building.
     */
    public BookBuilder withTitle(String title) {
        this.title = new Title(title);
        return this;
    }

    /**
     * Parses the {@code categories} into a {@code Set<Category>} and set it to the {@code Book} that we are building.
     */
    public BookBuilder withCategories(String... categories) {
        this.categories = BookDataUtil.getCategorySet(categories);
        return this;
    }

    /**
     * Sets the {@code Description} of the {@code Book} that we are building.
     */
    public BookBuilder withDescription(String description) {
        this.description = new Description(description);
        return this;
    }

    /**
     * Sets the {@code Status} of the {@code Book} that we are building.
     */
    public BookBuilder withStatus(Status status) {
        this.status = status;
        return this;
    }

    /**
     * Sets the {@code Priority} of the {@code Book} that we are building.
     */
    public BookBuilder withPriority(Priority priority) {
        this.priority = priority;
        return this;
    }

    /**
     * Sets the {@code Rating} of the {@code Book} that we are building.
     */
    public BookBuilder withRating(int rating) {
        this.rating = new Rating(rating);
        return this;
    }

    /**
     * Sets the {@code Priority} of the {@code Book} that we are building.
     */
    public BookBuilder withPriority(String priority) {
        this.priority = new Priority(priority);
        return this;
    }

    /**
     * Sets the {@code Status} of the {@code Book} that we are building.
     */
    public BookBuilder withStatus(String status) {
        this.status = new Status(status);
        return this;
    }

    /**
     * Sets the {@code Gid} of the {@code Book} that we are building.
     */
    public BookBuilder withGid(String gid) {
        this.gid = new Gid(gid);
        return this;
    }

    /**
     * Sets the {@code Isbn} of the {@code Book} that we are building.
     */
    public BookBuilder withIsbn(String isbn) {
        this.isbn = new Isbn(isbn);
        return this;
    }

    /**
     * Sets the {@code Publisher} of the {@code Book} that we are building.
     */
    public BookBuilder withPublisher(String publisher) {
        this.publisher = new Publisher(publisher);
        return this;
    }

    /**
     * Sets the {@code PublicationDate} of the {@code Book} that we are building.
     */
    public BookBuilder withPublicationDate(String date) {
        this.publicationDate = new PublicationDate(date);
        return this;
    }

    /**
     * Returns a new {@code Book} object.
     */
    public Book build() {
        return new Book(gid, isbn, authors, title, categories, description, rating,
                priority, status, publisher, publicationDate);
    }

}
