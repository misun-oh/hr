package com.example.hr.service;

import com.example.hr.config.Config;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.example.hr.dto.AttachmentDto;
import com.example.hr.dto.EmpDto;
import com.example.hr.dto.EmpForm;
import com.example.hr.dto.EmpSearchCond;
import com.example.hr.dto.PageDto;
import com.example.hr.mapper.EmpMapper;

import lombok.RequiredArgsConstructor;

/*
 * 인터페이스의 구현체 
 */
@Service
@RequiredArgsConstructor
public class EmpServiceImpl implements EmpService {

	// @Autowired : 필드 주입
	// 리플렉션으로 필드에 직접 주입


	// DI
	// 1. 필드 주입
	// 2. Setter 주입
	// 3. 생성자 주입
	private final EmpMapper mapper;
	private final AttachmentService attachmentService;

	// 필드주입
	@Autowired
	private BCryptPasswordEncoder encoder;

	private static final String REF_TYPE_EMP = "EMP";
	
	@Override
	public int totalCnt() {
		
		return mapper.totalCnt();
	}

	@Override
	public void selectByCond(EmpSearchCond cond, Model model) {
		// countByCond·selectByCond는 서로 독립적인 쿼리 - 둘 다 실행한 뒤에 PageDto를 만든다
		int totalCnt = mapper.countByCond(cond);
		List<EmpDto> list = mapper.selectByCond(cond);

		model.addAttribute("list", list);
		model.addAttribute("totalCnt", totalCnt);
		model.addAttribute("pageDto", new PageDto(cond.getPage(), totalCnt));
	}

	@Override
	public EmpDto selectById(String empId) {
		return mapper.selectById(empId);
	}

	@Override
	public EmpDto login(String id, String pw) throws Exception {
		
		// 1. 사용자 조회
		EmpDto emp = mapper.selectByUserId(id);
		
		// 2. 아이디가 없는경우 -> 메세지 처리(예외를 발생 시킴 -> 예외 메세지를 전달)
		if(emp == null)
			throw new Exception("존재하지 않는 아이디 입니다.");
		
		// 3. 잠긴 계정인지 확인 -> 잠겼으면 메세지 처리
		if(emp.getIsLocked() == 1)
			throw new Exception("비밀번호 5회 실패로 잠긴 계정입니다. 관리자에게 문의 해주세요.");
		
		// 4. 비밀번호 일치 확인 -> 일치하지 않으면 실패 카운트후 메세지 처리
		// -> 예외가 발생되면 (( 롤백 ))이 되어버림 
		// -> 컨트롤러에서 로그인 실패시 메서드를 다시 호출 
		//if(!emp.getPw().equals(pw)) {
		System.out.println(pw + " / " + emp.getPw());
		if(!encoder.matches(pw, emp.getPw())) {
			throw new Exception("비밀번호가 일치하지 않습니다.");
		}
		
		// 5. 로그인 성공 -> empDto반환
		// 실패카운트 초기화
		// TODO : commit 안됨!!!
		mapper.resetFailCount(id);
		return emp;
	}


	@Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
	public int updateFailCount(String id) {
		
		return mapper.updateFailCount(id);
	}

	@Override
	public int resetFailCount(String id) {
		return mapper.resetFailCount(id);
	}

	@Override
	@Transactional
	public void register(EmpForm form, List<MultipartFile> images) throws IOException {
		List<MultipartFile> files = (images == null) ? List.of()
				: images.stream().filter(f -> f != null && !f.isEmpty()).toList();

		if (files.size() > 5) {
			throw new IllegalArgumentException("이미지는 최대 5장까지 업로드할 수 있습니다.");
		}

		EmpDto emp = new EmpDto();
		emp.setEmpId(mapper.nextEmpId());
		emp.setEmpName(form.getEmpName());
		emp.setEmpNo(form.getEmpNo());
		emp.setEmail(form.getEmail());
		emp.setPhone(form.getPhone());
		emp.setDeptId(form.getDeptId());
		emp.setSalary(form.getSalary());
		emp.setHireDate(form.getHireDate());

		mapper.insertEmp(emp);

		// 사번(emp_id)이 발급된 후에야 그 사번으로 첨부파일을 등록할 수 있다.
		String empId = String.valueOf(emp.getEmpId());
		List<AttachmentDto> saved = new ArrayList<>();
		try {
			for (MultipartFile file : files) {
				saved.add(attachmentService.save(REF_TYPE_EMP, empId, file));
			}
		} catch (Exception e) {
			for (AttachmentDto a : saved) {
				attachmentService.deleteStoredFile(a.getStoredPath());
			}
			throw e;
		}
	}
}




















