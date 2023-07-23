package com.hb.file.core.commons;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public class PageUtils {

    public static <T> PageBean<T> emptyPageBean(Long current, Long size) {
        final BaseDto dto = new BaseDto();
        dto.setCurrent(current);
        dto.setSize(size);
        return emptyPageBean(dto);
    }

    public static <T> PageBean<T> emptyPageBean(BaseDto baseDto) {
        final List<T> emptyList = Collections.emptyList();
        return PageBean.<T>builder()
                .content(emptyList)
                .size(baseDto.getSize().intValue())
                .total(0)
                .current(baseDto.getCurrent().intValue())
                .build();
    }

    public static <T> PageBean<T> convertPageBean(IPage<T> page) {
        final List<T> records = page.getRecords();
        if (CollectionUtils.isEmpty(records)) {
            return emptyPageBean(page.getCurrent(), page.getSize());
        }
        return PageBean.<T>builder()
                .content(records)
                .current((int) page.getCurrent())
                .size((int) page.getSize())
                .total((int) page.getTotal())
                .build();
    }

    public static <T, R> PageBean<R> convertPage(IPage<T> page, List<R> resList) {
        if (CollectionUtils.isEmpty(resList)) {
            return emptyPageBean(page.getCurrent(), page.getSize());
        }
        return PageBean.<R>builder()
                .content(resList)
                .current((int) page.getCurrent())
                .size((int) page.getSize())
                .total((int) page.getTotal())
                .build();
    }

    public static <T, K> PageBean<T> convertPage(IPage<K> page, Function<List<K>, List<T>> func) {
        final List<K> records = page.getRecords();
        if (CollectionUtils.isEmpty(records)) {
            return emptyPageBean(page.getCurrent(), page.getSize());
        }
        final List<T> apply = func.apply(records);
        return PageBean.<T>builder()
                .content(apply)
                .current((int) page.getCurrent())
                .size((int) page.getSize())
                .total((int) page.getTotal())
                .build();
    }

    public static <T> Page<T> getPage(BaseDto baseDto) {
        return new Page<>(baseDto.getCurrent(), baseDto.getSize());
    }
}
