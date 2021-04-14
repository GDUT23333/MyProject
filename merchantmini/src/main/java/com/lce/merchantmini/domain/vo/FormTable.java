package com.lce.merchantmini.domain.vo;

import com.lce.common.model.dto.WebForm;
import lombok.*;

/**
 * 报名者的报名表信息回显
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class FormTable extends WebForm{
    //主键
    private Integer cvId;
    //报名者填写报名表的数据
    private String tableValue;
    //如果类型为多选时，该数组储存数据
    private String[] tableContents;
}
