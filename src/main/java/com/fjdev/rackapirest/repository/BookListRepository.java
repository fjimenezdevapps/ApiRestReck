package com.fjdev.rackapirest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.fjdev.rackapirest.model.BookList;
import com.fjdev.rackapirest.model.User;
import java.util.List;


public interface BookListRepository extends JpaRepository<BookList, String> {
    List<BookList> findByUser(User user);
}
