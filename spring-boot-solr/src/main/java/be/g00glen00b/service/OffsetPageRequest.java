package be.g00glen00b.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class OffsetPageRequest implements Pageable {
    private static final int PAGE_OFFSET = 1;
    private static final int FIRST_INDEX_OFFSET = 0;
    private static final int MIN_LIMIT = 1;
    private int limit;
    private int offset;
    private Sort sort;

    public OffsetPageRequest(int offset, int limit) {
        this(offset, limit, null);
    }

    public OffsetPageRequest(int offset, int limit, Sort sort) {
        if (offset < FIRST_INDEX_OFFSET) {
            throw new IllegalArgumentException("Offset is zero based and should be at least zero");
        }
        if (limit < MIN_LIMIT) {
            throw new IllegalArgumentException("Limit should be at least one");
        }
        this.offset = offset;
        this.limit = limit;
        this.sort = sort;
    }

    @Override
    public Pageable first() {
        return new OffsetPageRequest(FIRST_INDEX_OFFSET, limit, sort);
    }

    @Override
    public int getOffset() {
        return offset;
    }

    @Override
    public int getPageNumber() {
        return offset / limit + PAGE_OFFSET;
    }

    @Override
    public int getPageSize() {
        return limit;
    }

    @Override
    public Sort getSort() {
        return sort;
    }

    @Override
    public boolean hasPrevious() {
        return offset > FIRST_INDEX_OFFSET;
    }

    @Override
    public Pageable next() {
        return new OffsetPageRequest(offset + limit, limit, sort);
    }

    @Override
    public Pageable previousOrFirst() {
        return new OffsetPageRequest(Math.max(offset - limit, FIRST_INDEX_OFFSET), limit, sort);
    }
}

