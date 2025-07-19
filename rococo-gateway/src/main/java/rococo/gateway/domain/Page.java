package rococo.gateway.domain;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface Page<T> {
    List<T> getContent();
    int getNumber();
    int getSize();
    long getTotalElements();
    int getTotalPages();
    boolean isFirst();
    boolean hasNext();
    boolean hasPrevious();
    Sort getSort();
    boolean isLast();
    int getNumberOfElements();
    long getOffset();
    boolean isEmpty();
    Pageable getPageable();
}
