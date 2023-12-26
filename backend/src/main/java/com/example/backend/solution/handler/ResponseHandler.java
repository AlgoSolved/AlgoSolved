package com.example.backend.solution.handler;

import com.example.backend.solution.domain.Solution;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResponseHandler {
	public ResponseEntity<Object> solutionsWithUser(Page<Solution> solutions) {
		Map<String, Object> map = new HashMap<String, Object>();
		int page = solutions.getPageable().getPageNumber();
		int totalPages = solutions.getTotalPages();
		List<Solution> solutionElements = solutions.getContent();

		map.put("page", page);
		map.put("totalPages", totalPages);

		JSONArray elements = new JSONArray();
		for (Solution solution : solutionElements) {
			JSONObject obj = new JSONObject();
			obj.put("id", solution.getId());
			obj.put("problemProvider", solution.getProblem().getProvider().getName());
			obj.put("problemTitle", solution.getProblem().getTitle());
			// user role converter 버그 수정되면 유저이름으로 변경할 것
			obj.put("repositoryUrl", solution.getGithubRepository().getUrl());
			elements.add(obj);
		}
		map.put("elements", elements);

		return new ResponseEntity<Object> (map, HttpStatus.OK);
	}

	public ResponseEntity<Object> solution(Solution solution) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", solution.getId());
		map.put("problemProvider", solution.getProblem().getProvider().getName());
		map.put("problemTitle", solution.getProblem().getTitle());
		map.put("language", solution.getLanguage());
		map.put("sourceCode", solution.getSourceCode());
		map.put("createdAt", solution.getCreatedAt());
		map.put("repositoryUrl", solution.getGithubRepository().getUrl());
		return new ResponseEntity<Object> (map, HttpStatus.OK);
	}
}
