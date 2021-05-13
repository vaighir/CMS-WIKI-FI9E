package com.fi9e.rest.mappers;

import com.fi9e.rest.dto.CategoryDTO;
import com.fi9e.rest.entity.Category;

public class CategoryMapper {
	
	/**
	 * Map Article Entity to CategoryDTO
	 * 
	 * @param category
	 * @return
	 */
	public static CategoryDTO mapCategoryToCategoryDTO(Category category) {
		CategoryDTO dto = new CategoryDTO();
		
		if(category != null) {
			dto.setName(category.getName());
		}
		
		return dto;
	}
	
}
