package impl;

import config.DatabaseExecutionContext;
import jakarta.persistence.EntityManager;
import models.Book;
import play.db.jpa.JPAApi;
import repository.BookRepository;

import javax.inject.Inject;
import java.util.List;
import java.util.concurrent.CompletionStage;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.concurrent.CompletableFuture.supplyAsync;

public class BookRepositoryImpl implements BookRepository {

    private static JPAApi jpaApi;
    private static DatabaseExecutionContext executionContext;

    @Inject
    public BookRepositoryImpl(JPAApi jpaApi, DatabaseExecutionContext executionContext) {
        this.jpaApi = jpaApi;
        this.executionContext = executionContext;
    }

    @Override
    public CompletionStage<Book> addBook(Book book) {
        return supplyAsync(() -> wrap(em -> insert(em, book)), executionContext);
    }

    @Override
    public CompletionStage<Stream<Book>> books() {
        return supplyAsync(() -> wrap(em -> list(em)), executionContext);
    }

    private Stream<Book> list(EntityManager em) {
        List<Book> bookList = em.createQuery("select b from Book b", Book.class).getResultList();
        return bookList.stream();
    }

    private <T> T wrap(Function<EntityManager, T> function) {
        return jpaApi.withTransaction(function);
    }

    private Book insert(EntityManager em, Book book) {
        System.out.println("book : {}" + book);
        System.out.println("book ID "+ book.getId());
        em.persist(book);
        return book;
    }
}
