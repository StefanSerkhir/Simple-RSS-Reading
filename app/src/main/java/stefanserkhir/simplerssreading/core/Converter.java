package stefanserkhir.simplerssreading.core;

import java.util.List;

public interface Converter<T, E> {

    List<T> map(List<E> list);
}
