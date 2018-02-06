package paliy;

public interface CRUD<T> {
     void add(T t);
     void edit(T t);
     void delete(T t);
     T read(T t);
}
