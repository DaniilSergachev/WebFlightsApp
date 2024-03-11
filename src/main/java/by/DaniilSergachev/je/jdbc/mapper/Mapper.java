package by.DaniilSergachev.je.jdbc.mapper;

public interface Mapper <T,F>{
    T mapFrom(F f);
}
