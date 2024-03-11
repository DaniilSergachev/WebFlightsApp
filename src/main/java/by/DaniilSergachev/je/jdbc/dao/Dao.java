package by.DaniilSergachev.je.jdbc.dao;





import by.DaniilSergachev.je.jdbc.dto.TicketFilter;
import by.DaniilSergachev.je.jdbc.entity.Ticket;

import java.util.List;
import java.util.Optional;

public interface Dao<K,E>   {
    boolean update(E e);
    E save(E e);
    boolean delete(K id);;
    Optional<E> findById(K id);
    List<E> findAll();


}
