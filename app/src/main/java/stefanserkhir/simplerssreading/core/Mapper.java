package stefanserkhir.simplerssreading.core;

import java.util.List;

public interface Mapper<T, E> {

    List<T> map(List<E> list);
}
