package com.fjdev.rackapirest.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fjdev.rackapirest.model.BookList;
import com.fjdev.rackapirest.model.User;
import com.fjdev.rackapirest.repository.BookListRepository;
import com.fjdev.rackapirest.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BookListService {

    private final BookListRepository bookListRepository;
    private final UserRepository userRepository;

    // Añadir un libro a la lista de un usuario
    @Transactional
    public BookList addBookToList(String userName, BookList book) {
        // Buscar el usuario por su nombre
        Optional<User> userOptional = userRepository.findByNameC(userName);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            book.setUser(user); // Asignar el usuario al libro
            return bookListRepository.save(book);
        } else {
            throw new RuntimeException("Usuario no encontrado");
        }
    }

    // Actualizar un libro en la lista
    @Transactional
    public BookList updateBookInList(String bookId, boolean statebook, int pagereader) {
        Optional<BookList> bookOptional = bookListRepository.findById(bookId);
        if (bookOptional.isPresent()) {
            BookList book = bookOptional.get();
            book.setStatebook(statebook); // Actualizar el estado del libro
            book.setPagereader(pagereader); // Actualizar las páginas leídas
            return bookListRepository.save(book);
        } else {
            throw new RuntimeException("Libro no encontrado");
        }
    }

    // Eliminar un libro de la lista de un usuario
    @Transactional
    public void deleteBookFromList(String bookId) {
        Optional<BookList> bookOptional = bookListRepository.findById(bookId);
        if (bookOptional.isPresent()) {
            bookListRepository.deleteById(bookId);
        } else {
            throw new RuntimeException("Libro no encontrado");
        }
    }

    // Obtener la lista de libros de un usuario
    public List<BookList> getUserBookList(String userName) {
        Optional<User> userOptional = userRepository.findByNameC(userName);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            // Buscar libros que pertenezcan al usuario
            return bookListRepository.findByUser(user);
        } else {
            throw new RuntimeException("Usuario no encontrado");
        }
    }
}