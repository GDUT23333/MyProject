package com.lce.merchantmini.domain.dto;

import com.lce.merchantmini.domain.vo.FormTable;
import com.lce.merchantmini.domain.vo.RegisteredMember;
import lombok.*;

import java.util.List;

@Data

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class RegisteredMemberDetail extends RegisteredMember {
    private List<FormTable> tables;
}
