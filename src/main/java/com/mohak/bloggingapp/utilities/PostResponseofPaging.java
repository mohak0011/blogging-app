package com.mohak.bloggingapp.utilities;

import java.util.List;

import com.mohak.bloggingapp.payloads.PostDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PostResponseofPaging {

	private List<PostDto> content;
	private int pageNumber;
	private int pageSize;
	private long totalElements;
	private int totalPages;
	private boolean lastPage;
}
