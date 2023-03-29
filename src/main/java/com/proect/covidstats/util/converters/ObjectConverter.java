package com.proect.covidstats.util.converters;

import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

public class ObjectConverter {
    private ObjectConverter() {
        // hidden constructor
    }

    private static ModelMapper modelMapper = new ModelMapper();

    public static <S, T> T convertObject(S object, Class<T> targetClass) {
        return convertObject(modelMapper, object, targetClass);
    }

    public static <S, T> List<T> convertList(List<S> list, Class<T> targetClass) {
        return convertList(modelMapper, list, targetClass);
    }

    public static <S, T> T convertObject(ModelMapper externalMapper, S object, Class<T> targetClass) {
        return externalMapper.map(object, targetClass);
    }

    public static <S, T> List<T> convertList(ModelMapper externalMapper, List<S> list, Class<T> targetClass) {
        List<T> targetList = new ArrayList<>();
        list.forEach(object -> targetList.add(convertObject(externalMapper, object, targetClass)));
        return targetList;
    }
}
