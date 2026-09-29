package com.example.hr.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.example.hr.dto.AttachmentDto;

@Mapper
public interface AttachmentMapper {

	int insert(AttachmentDto dto);

	// mapper에 파라메터를 여러개 전달 할 경우, 어노테이션을 달아주어야 함!!!!
	List<AttachmentDto> selectByRef(@Param("refType") String refType
									,@Param("refId") String refId);

	@Select("select * from attachment where attachment_id = #{attachmentId}")
	AttachmentDto selectById(int attachmentId);
}
