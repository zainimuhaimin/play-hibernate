// get list of books
$.get("/books", (persons) => {
    $.each(persons, (index, person) => {
        $("#books").append($("<li>").text(person.namaBuku));
    });
});


