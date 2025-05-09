package repository;

import com.google.inject.ImplementedBy;
import impl.BookRepositoryImpl;
import models.Book;

import java.util.concurrent.CompletionStage;
import java.util.stream.Stream;

@ImplementedBy(BookRepositoryImpl.class)
public interface BookRepository {
    CompletionStage<Book> addBook(Book book);

    CompletionStage<Stream<Book>> books();
}
