package com.example.hr.dto;

import lombok.Getter;

// 페이징 처리에 필요한 정보만 담는 범용 DTO (목록 데이터는 갖지 않음 - 어떤 목록 화면에서도 재사용 가능)
// 요청 페이지 번호(page)와 총건수(totalCnt)만 넣으면 나머지 값들이 전부 자동 계산됨
//
// 왜 스프링 빈으로 안 만들고 매번 new로 만드는가
//   - 핵심은 "필드가 있다/없다"가 아니라, 그 필드 값이 "고정"이냐 "호출마다 바뀌는 결과"냐의 차이
//   - EmpServiceImpl의 mapper 필드는 누가 호출하든 항상 같은 값 -> 공유(싱글톤 빈)해도 안전
//   - PageDto의 필드(page, totalPage, startPage...)는 누가 언제 호출하느냐에 따라 매번 다른 "결과값"
//     -> 인스턴스를 공유하면 A의 페이지 정보가 B의 것으로 덮어써지는 문제가 생기므로 공유 불가, 매번 새로 생성해야 함
@Getter
public class PageDto {

	private static final int DEFAULT_PAGE_SIZE = 10;	// 페이지당 게시물수
	private static final int DEFAULT_BLOCK_SIZE = 10;	// 블럭(페이지 번호 묶음)당 페이지수

	private final int page;		// 요청 페이지 번호
	private final int totalCnt;	// 총건수
	private final int pageSize;	// 페이지당 게시물수
	private final int blockSize;	// 블럭당 게시물(페이지)수
	private final int totalPage;	// 총 페이지수
	private final int startPage;	// 현재 블럭의 시작 페이지
	private final int endPage;		// 현재 블럭의 끝 페이지
	private final boolean prev;	// 이전 블럭으로 가는 버튼 노출 여부
	private final boolean next;	// 다음 블럭으로 가는 버튼 노출 여부

	public PageDto(int page, int totalCnt) {
		this(page, totalCnt, DEFAULT_PAGE_SIZE, DEFAULT_BLOCK_SIZE);
	}

	public PageDto(int page, int totalCnt, int pageSize, int blockSize) {
		this.totalCnt = totalCnt;
		this.pageSize = pageSize;
		this.blockSize = blockSize;
		this.totalPage = Math.max((int) Math.ceil(totalCnt / (double) pageSize), 1);
		this.page = Math.min(Math.max(page, 1), totalPage);

		this.startPage = ((this.page - 1) / blockSize) * blockSize + 1;
		this.endPage = Math.min(startPage + blockSize - 1, totalPage);
		this.prev = startPage > 1;
		this.next = endPage < totalPage;
	}
}
