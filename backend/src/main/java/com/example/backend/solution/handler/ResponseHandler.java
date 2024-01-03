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
		Map<String, Object> response = new HashMap<String, Object>();
		if (solutions.isEmpty()) {
			response.put("page", 0);
			response.put("totalPages", 0);
			response.put("elements", new JSONArray());
			return new ResponseEntity<Object> (response, HttpStatus.OK);
		}

		int page = solutions.getPageable().getPageNumber();
		int totalPages = solutions.getTotalPages();
		List<Solution> solutionElements = solutions.getContent();

		response.put("page", page);
		response.put("totalPages", totalPages);

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
		response.put("elements", elements);

		return new ResponseEntity<Object> (response, HttpStatus.OK);
	}

	public ResponseEntity<Object> solution(Solution solution) {
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("id", solution.getId());
		response.put("problemProvider", solution.getProblem().getProvider().getName());
		response.put("problemTitle", solution.getProblem().getTitle());
		response.put("language", solution.getLanguage());
		response.put("sourceCode", solution.getSourceCode());
		response.put("createdAt", solution.getCreatedAt());
		response.put("repositoryUrl", solution.getGithubRepository().getUrl());
		return new ResponseEntity<Object> (response, HttpStatus.OK);
	}
}
