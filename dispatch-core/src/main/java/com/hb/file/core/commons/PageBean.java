package com.hb.file.core.commons;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class PageBean<T> implements Serializable {

    private static final long serialVersionUID = -3200538385338884588L;

    private List<T> content;

    private Integer total;

    private Integer current;

    private Integer size;
}
