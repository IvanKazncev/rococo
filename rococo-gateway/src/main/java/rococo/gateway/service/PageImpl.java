package rococo.gateway.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import rococo.gateway.domain.Page;

import java.util.Collections;
import java.util.List;

public class PageImpl<T> implements Page<T> {
    private final List<T> content;
    private final Pageable pageable;
    private final long totalElements;

    public PageImpl(List<T> content, Pageable pageable, long totalElements) {
        this.content = Collections.unmodifiableList(content);
        this.pageable = pageable;
        this.totalElements = totalElements;
    }

    /**
     * Возвращает содержимое текущей страницы.
     */
    @Override
    public List<T> getContent() {
        return content;
    }

    /**
     * Номер текущей страницы (индексы страниц начинаются с нуля).
     */
    @Override
    public int getNumber() {
        if (pageable != null && pageable.isPaged()) {
            return pageable.getPageNumber();
        }
        return 0;
    }

    /**
     * Размер страницы (количество элементов на странице).
     */
    @Override
    public int getSize() {
        if (pageable != null && pageable.isPaged()) {
            return pageable.getPageSize();
        }
        return content.size();
    }

    /**
     * Общее количество элементов во всей коллекции.
     */
    @Override
    public long getTotalElements() {
        return totalElements;
    }

    /**
     * Количество страниц (полученное делением общего количества элементов на размер страницы).
     */
    @Override
    public int getTotalPages() {
        return Math.toIntExact((totalElements + getSize() - 1) / getSize());
    }

    /**
     * Является ли текущая страница первой страницей результатов.
     */
    @Override
    public boolean isFirst() {
        return !hasPrevious();
    }

    /**
     * Есть ли следующая страница.
     */
    @Override
    public boolean hasNext() {
        return (getNumber() + 1) * getSize() < totalElements;
    }

    /**
     * Есть ли предыдущая страница.
     */
    @Override
    public boolean hasPrevious() {
        return getNumber() > 0;
    }

    /**
     * Язык сортировки примененных правил сортировки.
     */
    @Override
    public Sort getSort() {
        if (pageable != null) {
            return pageable.getSort();
        }
        return Sort.unsorted();
    }

    /**
     * Является ли текущая страница последней страницей результатов.
     */
    @Override
    public boolean isLast() {
        return !hasNext();
    }

    /**
     * Количество элементов на текущей странице.
     */
    @Override
    public int getNumberOfElements() {
        return content.size();
    }

    /**
     * Смещение первого элемента на текущей странице.
     */
    @Override
    public long getOffset() {
        if (pageable != null) {
            return pageable.getOffset();
        }
        return 0;
    }

    /**
     * Представляет ли данная коллекция пустую страницу.
     */
    @Override
    public boolean isEmpty() {
        return content.isEmpty();
    }

    /**
     * Возвращает объект Paging, содержащий подробную информацию о текущей странице.
     */
    @Override
    public Pageable getPageable() {
        return pageable;
    }

    /**
     * Является ли информация о страницах заполненной (не пустой)?
     */
    public boolean isPaged() {
        return pageable != null && pageable.isPaged();
    }

    /**
     * Является ли пагинация отключённой (безраздельность)?
     */
    public boolean isUnpaged() {
        return pageable == null || !pageable.isPaged();
    }

}