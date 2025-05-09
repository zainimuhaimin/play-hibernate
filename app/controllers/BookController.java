package controllers;

import models.Book;
import play.data.FormFactory;
import play.libs.concurrent.ClassLoaderExecutionContext;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import repository.BookRepository;

import javax.inject.Inject;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;

import static play.libs.Json.toJson;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class BookController extends Controller {

    //inject book repository

    private final BookRepository bookRepository;
    private final FormFactory formFactory;
    private final ClassLoaderExecutionContext ec;

    @Inject
    public BookController(BookRepository bookRepository, FormFactory formFactory, ClassLoaderExecutionContext ec) {
        this.bookRepository = bookRepository;
        this.formFactory = formFactory;
        this.ec = ec;
    }


    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
    //todo list of books
    public Result index() {
        return ok(views.html.index.render());
    }

    public Result addBook(final Http.Request request) {
        return ok(views.html.form_add_book.render(request));
    }

    //CompletionStage untuk response yang bukan UI sotoy gua aja
    public CompletionStage<Result> insertBook(final Http.Request request){
        // binding berdasarkan name di html nya ke Book Model sotoy
        Book book = formFactory.form(Book.class).bindFromRequest(request).get();
        return bookRepository.addBook(book)
                .thenApplyAsync(e -> redirect(routes.BookController.index()), ec.current());
    }

    public CompletionStage<Result> books(){
        return bookRepository.books()
                .thenApplyAsync(bookStream -> ok(toJson(bookStream.collect(Collectors.toList()))), ec.current());
    }
}
