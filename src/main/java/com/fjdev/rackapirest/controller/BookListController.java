package com.fjdev.rackapirest.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import com.fjdev.rackapirest.model.BookList;
import com.fjdev.rackapirest.service.BookListService;
import java.util.List;

@RestController
@RequestMapping("/api/booklist")
public class BookListController {

    private final BookListService bookListService;

    public BookListController(BookListService bookListService) {
        this.bookListService = bookListService;
    }

    // Añadir un libro a la lista de un usuario
    @PostMapping("/add/{userName}")
    public ResponseEntity<BookList> addBookToList(@PathVariable String userName, @RequestBody BookList book) {
        try {
            BookList addedBook = bookListService.addBookToList(userName, book);
            return new ResponseEntity<>(addedBook, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // Actualizar un libro en la lista
    @PutMapping("/update/{bookId}")
    public ResponseEntity<BookList> updateBookInList(@PathVariable String bookId, 
                                                      @RequestParam boolean statebook, 
                                                      @RequestParam int pagereader) {
        try {
            BookList updatedBook = bookListService.updateBookInList(bookId, statebook, pagereader);
            return new ResponseEntity<>(updatedBook, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // Eliminar un libro de la lista de un usuario
    @DeleteMapping("/delete/{bookId}")
    public ResponseEntity<Void> deleteBookFromList(@PathVariable String bookId) {
        try {
            bookListService.deleteBookFromList(bookId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Obtener la lista de libros de un usuario
    @GetMapping("/user/{userName}")
    public ResponseEntity<List<BookList>> getUserBookList(@PathVariable String userName) {
        try {
            List<BookList> bookList = bookListService.getUserBookList(userName);
            return new ResponseEntity<>(bookList, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}