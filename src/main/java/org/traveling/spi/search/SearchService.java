package org.traveling.spi.search;


import org.traveling.domain.LineOfBusiness;

public interface SearchService<T extends SearchResult<? extends LineOfBusiness>> {

    T search(Search search);

}
