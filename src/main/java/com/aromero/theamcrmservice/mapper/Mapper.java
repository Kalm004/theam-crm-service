package com.aromero.theamcrmservice.mapper;

public interface Mapper<S, T> {
    T mapTo(S source);
    S mapFrom(T target);
}
