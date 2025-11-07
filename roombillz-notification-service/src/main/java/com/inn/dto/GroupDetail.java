package com.inn.dto;

import com.inn.entity.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupDetail extends BaseEntity {

	private Integer id;
	private String groupId;
	private String groupName;
	private Boolean isDeleted;

}
